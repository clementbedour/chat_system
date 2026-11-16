package chatsystem.network;

import java.net.InetAddress;
import java.util.Objects;

/** Java fait tout le travail*/
public record UDPMessage(String content, InetAddress origin) {

}
