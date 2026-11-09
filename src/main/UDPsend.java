import java.io.IOException;
import java.net.*;

public class UDPsend {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Veuillez fournir un nom en argument.");
            return;
        }

        DatagramSocket socket = new DatagramSocket();
        byte[] buf = new byte[256];

        String name = args[0];
        byte[] byteArrray = name.getBytes();

        //broadcast pour prise de contact
        InetAddress senderAddress = InetAddress.getByName("255.255.255.255");
        DatagramPacket outPacket = new DatagramPacket(byteArrray, byteArrray.length, senderAddress, 1789);
        socket.send(outPacket);

        //prep reception
        DatagramSocket socket_in = new DatagramSocket(1788);

        //timeout de 3 secondes pour si aucune réponse (serveur new ou down)
        socket_in.setSoTimeout(3000);

        DatagramPacket inPacket = new DatagramPacket(buf, buf.length);

        try {
            socket_in.receive(inPacket); //bloquant pour timeout / reception

            String received = new String(inPacket.getData(), 0, inPacket.getLength());
            System.out.println(received);

            InetSocketAddress sourceAddr = (InetSocketAddress) inPacket.getSocketAddress();
            InetAddress sourceAddr2 = sourceAddr.getAddress();
            System.out.println("Connecté avec : " + sourceAddr2);

        } catch (SocketTimeoutException e) {
            //aucune réponse
            System.out.println("Vous êtes actuellement la seule personne connectée sur le réseau.");
        } finally {
            socket.close();
            socket_in.close();
        }
    }
}