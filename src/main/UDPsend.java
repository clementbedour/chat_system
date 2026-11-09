import java.io.IOException;
import java.net.*;

public class UDPsend {

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        byte[] buf = new byte[256];

        String name = args[0];
        byte[] byteArrray = name.getBytes();




        InetAddress senderAddress = InetAddress.getByName("255.255.255.255");
        DatagramPacket outPacket = new DatagramPacket(byteArrray, byteArrray.length, senderAddress, 1789);
        socket.send(outPacket);

        //verif si connection au serveur OK
        DatagramSocket socket_in = new DatagramSocket(1788);
        DatagramPacket inPacket  = new DatagramPacket(buf, buf.length);
        socket_in.receive(inPacket);


        String received = new String(inPacket.getData(), 0, inPacket.getLength());
        System.out.println(received);

        InetSocketAddress sourceAddr =(InetSocketAddress)inPacket.getSocketAddress();
        InetAddress sourceAddr2 = sourceAddr.getAddress();
        System.out.println(sourceAddr2);


        /*String received = null;
        try {
            received = new String(inPacket.getData(), 0, inPacket.getLength());
        } catch (java.lang.Exception e) {
            throw new RuntimeException(e);
        }

        //Thread.sleep(100);
        if ("serveur_connection_ok".equals(received)){
            System.out.println("Connection au serveur réussi");
        }
        else {
            System.out.println("Connection au serveur échoué...");
            System.out.println("Veuillez réessayer plus tard");
        }*/

        socket.close();
        socket_in.close();
    }
}