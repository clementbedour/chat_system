import java.io.IOException;
import java.net.*;

public class UDP {

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(1789);
        boolean running = true;
        byte[] buf = new byte[256];

        while (running) {
            DatagramPacket inPacket  = new DatagramPacket(buf, buf.length);
            socket.receive(inPacket);


            String received = new String(inPacket.getData(), 0, inPacket.getLength());
            System.out.println("« Salutations, je suis Buzz l'Éclair, je viens en paix ! " + received);

        }
        socket.close();
    }
}