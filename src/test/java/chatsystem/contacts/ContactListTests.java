package chatsystem.contacts;

import org.junit.jupiter.api.Test;

public class ContactListTests {

    @Test
    void contactListAddTest(){
        ContactList contacts = new ContactList();

        assert !contacts.hasUserName("Alice");
        contacts.addUser("Alice");
        assert contacts.hasUserName("Alice");
        assert !contacts.hasUserName("Bob");

        assert !contacts.hasUserName("Bob");
        contacts.addUser("Bob");
        assert contacts.hasUserName("Bob");
        assert contacts.hasUserName("Alice");
    }

    void contactDuplicationTest(){
        ContactList contacts = new ContactList();

        assert !contacts.hasUserName("Alice");
        contacts.addUser("Alice");
        assert contacts.hasUserName("Alice");

        contacts.addUser("Alice");


    }

}
