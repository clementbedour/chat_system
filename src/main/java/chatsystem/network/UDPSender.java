package chatsystem.network;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.*;


/** Class providing utility methods to send UDP messages. */

public class UDPSender {
    /** Send a UDP message on the given address and port. */

    public static void send(InetAddress senderAddr, int port, String message) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        byte[] buf = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length,senderAddr, port);
        socket.send(packet);
        socket.close();
    }

    public static void sendBroadcast(int port, String message) throws IOException{
        InetAddress senderAddr = InetAddress.getByName("255.255.255.255");
        UDPSender.send(senderAddr,port,message);
    }

}
