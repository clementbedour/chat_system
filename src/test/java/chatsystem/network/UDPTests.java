package chatsystem.network;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class UDPTests {

    @Test
    void sendAndReceiveTest() throws Exception {
        int testPort = 1790;
        AtomicReference<UDPMessage> receivedMessage = new AtomicReference<>();

        UDPServer server = new UDPServer(testPort);
        server.addObserver(message -> {
            receivedMessage.set(message);
        });
        server.start();

        //wait qq ms pour serveur s'ouvre
        Thread.sleep(100);

        UDPSender.send(InetAddress.getLocalHost(), testPort, "Salut en local !");

        //wait qq ms pour le packet le temps d'arriver
        Thread.sleep(200);

        assertNotNull(receivedMessage.get(), "Aucun message reçu par le serveur UDP");
        assertEquals("Salut en local !", receivedMessage.get().content());
    }
}