package chatsystem.network;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/** Create a UDP server that (once started) listens indefinitely on a given port. */
public class UDPServer extends Thread {

    // Interface pour quand message est là
    public interface Observer {
        void handle (UDPMessage message);
    }


    private final DatagramSocket socket;

    //grave utile pour vérif pseudo/interface/...
    private final List<Observer> observers = new ArrayList<>();


    public UDPServer(int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    //ajout d'un observer dans la liste
    public void addObserver(Observer obs){
        synchronized(this.observers) {//pour la secu des threads
            this.observers.add(obs);
        }
    }


    @Override
    public void run() {
        boolean running = true;
        byte[] buf = new byte[256];

        System.out.println("En attente de connexions...");
        while (running) {
            try{
                DatagramPacket inPacket = new DatagramPacket(buf, buf.length);

                //attente du prochain message
                socket.receive(inPacket); //bloquant pour timeout / reception
                String received = new String(inPacket.getData(), 0, inPacket.getLength());

                InetSocketAddress senderAddr = (InetSocketAddress) inPacket.getSocketAddress();
                InetAddress senderAddrsource = senderAddr.getAddress();

                //creation msg + bip observer
                UDPMessage msg = new UDPMessage(received, senderAddrsource);
                synchronized (this.observers) {
                    for (Observer obs : this.observers) {
                        obs.handle(msg);
                    }
                }
        }
        catch (IOException e) {
                System.err.println("Receive error: " + e.getMessage());
            }
        }
    }
}