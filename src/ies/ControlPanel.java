package ies;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Kitten on 30/11/2016.
 */
public class ControlPanel {


    public ControlPanel() {

    }


    public void listen() throws IOException {
        ServerSocket serverSocket = new ServerSocket(SharedConsts.ServerPort);

        System.out.println("Listening to TCP port# " + serverSocket.getLocalPort());

        Socket clientSocket = null;


        while (true) {
            clientSocket = serverSocket.accept();
            new SocketServer(clientSocket).start();
        }
    }


    public static void main(String args[]) {
        ControlPanel cp = new ControlPanel();
        try {
            cp.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
