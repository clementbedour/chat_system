package chatsystem.contacts;
import java.util.*;

public class ContactList {

    public interface Observer {
        void newContactAdded(Contact contact);
    }

    //SINGLETON
    private static final ContactList INSTANCE = new ContactList();

    //constructor
    public static ContactList getInstance(){
        return INSTANCE;
    }

    //hashset pour éviter le doublon de pseudo
    private static HashSet<String> pseudos = new HashSet<>();    //method to add an user
    private List<Observer> observers = new ArrayList<>();

    private ContactList(){
    }

    public synchronized void addObserver (Observer obs) {
        this.observers.add(obs);
    }


    public synchronized void addUser(String pseudo) throws ContactAlreadyExists {
        // On vérifie d'abord si ce pseudo est déjà dans notre mémoire globale
        if (pseudos.contains(pseudo)) {
            throw new ContactAlreadyExists(pseudo);
        }

        // Si tout est ok, on enregistre le pseudo
        pseudos.add(pseudo); // On l'ajoute à la liste globale pour bloquer les prochains

        Contact contact = new Contact(pseudo);
        for (Observer obs : this.observers) {
            obs.newContactAdded(contact);
        }
    }
    //verification if an user present in a list
    public synchronized boolean hasUserName(String pseudo){
        return(pseudos.contains(pseudo));
    }

    public synchronized HashSet<String> getAllPseudos(){
        return new HashSet<>(this.pseudos);
    }

    //juste pour les tests
    public synchronized void clear() {
        this.pseudos.clear();
    }
}
