package chatsystem.controller;

import chatsystem.contacts.ContactList;
import chatsystem.network.UDPMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    @BeforeEach
    void clearContactList() {
        ContactList.getInstance().clear();
    }

    @Test
    void messageHandlingTest() throws UnknownHostException {
        ContactList contacts = ContactList.getInstance();

        UDPMessage msg1 = new UDPMessage("Alice", InetAddress.getByName("10.5.5.10"));
        UDPMessage msg2 = new UDPMessage("Bob", InetAddress.getByName("10.5.5.11"));

        assertFalse(contacts.hasUserName("Alice"));
        Controller.handleContactDiscoveryMessage(msg1);
        assertTrue(contacts.hasUserName("Alice"));

        assertFalse(contacts.hasUserName("Bob"));
        Controller.handleContactDiscoveryMessage(msg2);
        assertTrue(contacts.hasUserName("Bob"));

        // Vérifie qu'un message de réponse "serveur_connection_ok:" est bien traité
        UDPMessage msgReponse = new UDPMessage("serveur_connection_ok:Charlie", InetAddress.getByName("10.5.5.12"));
        Controller.handleContactDiscoveryMessage(msgReponse);
        assertTrue(contacts.hasUserName("Charlie"));
    }
}