package ies;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

/**
 * Created by michaelleung on 30/11/2016.
 */
public class ControlPanel {
    private static Hashtable<String, SocketServer> newThreads = null;

    public ControlPanel(){
        startServer();
    }

    private void startServer(){
        newThreads = new Hashtable<String, SocketServer>();
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(SharedConsts.ServerPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("[ControlPanel] Listening to TCP port# " + serverSocket.getLocalPort());
//        int tmp=0;

        while (true) {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            newMyThread th = new newMyThread(clientSocket);
//            th.start();
                SocketServer socketServer = new SocketServer(clientSocket,"test1", this);
                Thread td = new Thread(socketServer);
                td.start();

        }
    }

    public static void regThread(SocketServer SocketServer) {
        //System.out.println("cp regThread");
        //System.out.println("cp id = " + SocketServer.getID());
        System.out.println("cp receivedMsg = " + SocketServer.getReceivedMsg());
        //System.out.println("cp alive thread = " + Thread.activeCount());
        newThreads.put(SocketServer.getID(), SocketServer);

    } // regThread

    public static void main(String args[]){
        ControlPanel panel = new ControlPanel();
    } // end of main
}
