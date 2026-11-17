package chatsystem.controller;

import chatsystem.contacts.ContactAlreadyExists;
import chatsystem.contacts.ContactList;
import chatsystem.network.UDPMessage;
import chatsystem.network.UDPSender;

import java.net.InetAddress;

/** Fait le lien entre Network et Contact*/
public class Controller {

    private static boolean unique_user = true; //part du principe qu'on est seul
    private static String myPseudo = "";

    //prise de contact (savoir si on est seul ou pas)
    public static void handleContactDiscoveryMessage(UDPMessage message) {
        ContactList pseudos = ContactList.getInstance();
        String received = message.content();
        InetAddress senderAddr = message.origin();

        //reponse d'un user déjà co
        if (received.startsWith("serveur_connection_ok:")) {
            unique_user = false; //on est plus tout seul
            String otherpseudo = received.split(":")[1];
            System.out.println("Connecté avec : " + senderAddr + " : " + otherpseudo);

            try {
                pseudos.addUser(otherpseudo);
                System.out.println("Création de l'utilisateur : " + message.content());
            } catch (ContactAlreadyExists e) {
                System.err.println(e.getMessage());
            }
        }
        //nouveau user
        else {
            try {
                pseudos.addUser(message.content());
                System.out.println("Créé : " + received);

                if (!myPseudo.isEmpty()) {
                    String name = "serveur_connection_ok" + myPseudo;
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
        //broadcast pour prise de contact
        InetAddress senderAddress = InetAddress.getByName("255.255.255.255");
        UDPSender.send(senderAddress, 1789, name);

        //timeout de 3 secondes pour si aucune réponse (personne de co)
        Thread.sleep(3000);

        ContactList contacts = ContactList.getInstance();

        //verif si pseudo pris dans les 3 secs
        if (contacts.hasUserName(name)) {
            System.out.println("Erreur : pseudo '" + name + "'existe déjà dans la liste");
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
    }
}