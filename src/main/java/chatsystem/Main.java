package chatsystem;

import chatsystem.controller.Controller;
import chatsystem.network.UDPServer;
import chatsystem.ui.View;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Veuillez fournir un nom en argument.");
            return;
        }

        //init la vue;
        View.initialize();

        //start serveur UDP et branche Controller
        UDPServer server = new UDPServer(1789);
        server.addObserver(message -> {
            Controller.handleContactDiscoveryMessage(message);
        });
        server.start();

        //lance prise de contact
        Controller.connecter(args[0]);
    }
}