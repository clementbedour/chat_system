package chatsystem.contacts;

/** Exception levée quand on tente d'ajouter un contact dont le pseudo est déjà dans la ContactList.
 */

public class ContactAlreadyExists extends Exception{

    public ContactAlreadyExists(String pseudo) {
        //msg d'erreur
        super("Erreur : le pseudo '" + pseudo + "'existe déjà dans la liste");
    }
}
