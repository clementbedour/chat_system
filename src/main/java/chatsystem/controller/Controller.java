package chatsystem.controller;

import chatsystem.contacts.ContactAlreadyExists;
import chatsystem.contacts.ContactList;
import chatsystem.network.UDPMessage;
import chatsystem.network.UDPSender;
import chatsystem.ui.View;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/** Fait le lien entre Network et Contact*/
public class Controller {

    private static boolean unique_user = true; //part du principe qu'on est seul
    private static boolean pseudo_taken = false; //bool pour dire si pseudo deja pris ou pas
    private static String myPseudo = "";
    private static final Set<String> activeUsers = Collections.synchronizedSet(new HashSet<>()); //liste tmp pour user connecté

    //prise de contact (savoir si on est seul ou pas)
    public static void handleContactDiscoveryMessage(UDPMessage message) {
        ContactList pseudos = ContactList.getInstance();
        String received = message.content();
        InetAddress senderAddr = message.origin();


        //return car loopback
        if (isLocalAddress(senderAddr)) {
            return;
        }

        //reponse d'un user déjà co
        if (received.startsWith("serveur_connection_ok:")) {
            unique_user = false; //on est plus tout seul
            String otherpseudo = received.split(":")[1];
            activeUsers.add(otherpseudo); //ajout car présent
            System.out.println("Connecté avec : " + senderAddr + " : " + otherpseudo);

            try {
                pseudos.addUser(otherpseudo);
                System.out.println("Création de l'utilisateur : " + otherpseudo);
            } catch (ContactAlreadyExists e) {
                System.err.println(e.getMessage());
            }
        }
        //msg pseudo déjà pris
        else if (received.startsWith("pseudo_already_exists:")) {
            pseudo_taken = true;
            String takenPseudo = received.split(":")[1];
            System.out.println("Refusé : le pseudo '" + takenPseudo + "' est déjà utilisé.");
        }
        //ping reçu : repond car toujours connecte
        else if (received.equals("ping")) {
            if (!myPseudo.isEmpty()) {
                try {
                    UDPSender.send(senderAddr, 1789, "pong:" + myPseudo);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        //pong reçu : user tjr co
        else if (received.startsWith("pong:")) {
            String alivePseudo = received.split(":")[1];
            activeUsers.add(alivePseudo);
        }
        //new user
        else {
            //si new user avec mon pseudo
            if (!myPseudo.isEmpty() && received.equals(myPseudo)) {
                try {
                    UDPSender.send(senderAddr, 1789, "pseudo_already_exists:" + myPseudo);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                return;
            }

            try {
                pseudos.addUser(received);
                activeUsers.add(received);
                System.out.println("Créé : " + received);

                if (!myPseudo.isEmpty()) {
                    String name = "serveur_connection_ok:" + myPseudo;
                    UDPSender.send(senderAddr, 1789, name);
                }
            } catch (ContactAlreadyExists e) {
                System.err.println(e.getMessage());
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    //prise de contact au lancement
    public static void connecter(String name) throws Exception {
        unique_user = true;
        pseudo_taken = false;

        //broadcast pour prise de contact
        InetAddress senderAddress = InetAddress.getByName("255.255.255.255");
        UDPSender.send(senderAddress, 1789, name);

        //timeout de 3 secondes pour si aucune réponse (personne de co)
        Thread.sleep(3000);

        ContactList contacts = ContactList.getInstance();

        //verif si pseudo pris dans les 3 secs
        if (pseudo_taken || contacts.hasUserName(name)) { //2 verif mais pour etre sur
            System.out.println("Erreur : pseudo '" + name + "' existe déjà dans la liste");
            System.exit(0);
        }

        //aucune réponse ou fin des msg
        if (unique_user) {
            System.out.println("Vous êtes actuellement la seule personne connectée sur le réseau.");
        } else {
            System.out.println("Fin de la réception des réponses.");
        }

        //valide le name
        myPseudo = name;
        contacts.addUser(myPseudo);

        // lance la vérification de présence toutes les 5 secondes
        startPresenceTimer();
    }

    //Pour mettre à jour les deconnexion
    private static void startPresenceTimer() {
        Thread timerThread = new Thread(() -> {
            ContactList contacts = ContactList.getInstance();
            while (true) {
                try {
                    Thread.sleep(5000);

                    activeUsers.clear();
                    InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
                    UDPSender.send(broadcastAddress, 1789, "ping");

                    //on attend les réponses
                    Thread.sleep(2000);

                    boolean listChanged = false;
                    for (String pseudo : contacts.getAllPseudos()) {
                        if (!pseudo.equals(myPseudo) && !activeUsers.contains(pseudo)) {
                            contacts.removeUser(pseudo);
                            System.out.println("-----------------------------------------------------------------");
                            System.out.println("Déconnecté car pas de réponse : " + pseudo);
                            listChanged = true;
                        }
                    }

                    if (listChanged) {
                        View.displayContactList();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    System.err.println("Erreur timer présence : " + e.getMessage());
                }
            }
        });
        timerThread.setDaemon(true);
        timerThread.start();
    }

    private static boolean isLocalAddress(InetAddress addr) {
        if (addr.isAnyLocalAddress() || addr.isLoopbackAddress()) {
            return true;
        }
        try {
            //verif si IP == myIP
            return NetworkInterface.getByInetAddress(addr) != null;
        } catch (SocketException e) {
            return false;
        }
    }
}