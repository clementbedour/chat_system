package chatsystem.contacts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class ContactListTests {

    @BeforeEach
    void clearContactList() {
        ContactList.getInstance().clear();
    }

    @Test
    void contactListAddTest() throws ContactAlreadyExists {
        ContactList contacts = ContactList.getInstance();

        assert !contacts.hasUserName("Alice");
        contacts.addUser("Alice");
        assert contacts.hasUserName("Alice");
        assert !contacts.hasUserName("Bob");

        assert !contacts.hasUserName("Bob");
        contacts.addUser("Bob");
        assert contacts.hasUserName("Bob");
        assert contacts.hasUserName("Alice");
    }

    @Test
    void contactDuplicationTest() throws ContactAlreadyExists {
        ContactList contacts = ContactList.getInstance();

        assert !contacts.hasUserName("Alice");
        contacts.addUser("Alice");
        assert contacts.hasUserName("Alice");

        try {
            contacts.addUser("Alice");
            throw new RuntimeException("Expected ContactAlreadyExists exception");
        } catch (ContactAlreadyExists e) {
            // ok, expect a exception
        }
    }


    @Test
    void getAllPseudosTest() throws ContactAlreadyExists {
        ContactList contacts = ContactList.getInstance();
        contacts.addUser("Alice");
        contacts.addUser("Bob");

        assert contacts.getAllPseudos().size() == 2;
    }

    @Test
    void observerNotificationTest() throws ContactAlreadyExists {
        ContactList contacts = ContactList.getInstance();
        AtomicBoolean notified = new AtomicBoolean(false);

        contacts.addObserver(contact -> {
            if (contact.pseudo().equals("Charlie")) {
                notified.set(true);
            }
        });

        contacts.addUser("Charlie");
        assert notified.get();
    }
}