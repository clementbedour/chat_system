package chatsystem.ui;

import chatsystem.contacts.Contact;
import chatsystem.contacts.ContactList;

public class View  implements ContactList.Observer {

    public static void initialize() {
        View view = new View();
        ContactList.getInstance().addObserver(view);
    }

    //affiche la liste de contact
    void displayContactList() {
        System.out.println("\n LSITE DES UTILISATEURS :");
        for (String p : ContactList.getInstance().getAllPseudos()) {
            System.out.println(" - " + p);
        }
        System.out.println("\n");
}

@Override
public void newContactAdded(Contact contact) {
    System.out.println("[VUE] New User : " + contact.pseudo());
    displayContactList();
    }

}