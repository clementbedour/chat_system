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

        boolean unique_user = true; //part du principe qu'on est seul

        try {
            while (true) {
                socket_in.receive(inPacket); //bloquant pour timeout / reception
                unique_user = false;//on est plus tout seul

                String received = new String(inPacket.getData(), 0, inPacket.getLength());

                InetSocketAddress sourceAddr = (InetSocketAddress) inPacket.getSocketAddress();
                InetAddress sourceAddr2 = sourceAddr.getAddress();
                System.out.println("Connecté avec : " + sourceAddr2+ " : " + received);
            }
        } catch (SocketTimeoutException e) {
            //aucune réponse
            if (unique_user) {
                System.out.println("Vous êtes actuellement la seule personne connectée sur le réseau.");
            } else {
                System.out.println("Fin de la réception des réponses.");
            }
        } finally {
            socket.close();
            socket_in.close();
        }
    }
}