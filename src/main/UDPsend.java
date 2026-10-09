import java.io.IOException;
import java.net.*;

public class UDPsend {

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();

        String inputString = "TEST";
        byte[] byteArrray = inputString.getBytes();


        InetAddress senderAddress = InetAddress.getByName("127.0.0.1");
        DatagramPacket outPacket = new DatagramPacket(byteArrray, byteArrray.length, senderAddress, 1789);
        socket.send(outPacket);

        socket.close();
    }
}