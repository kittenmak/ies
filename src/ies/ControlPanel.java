package ies;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Created by michaelleung on 30/11/2016.
 */
public class ControlPanel {
    private static Hashtable<String, SocketServer> newThreads = null;
    private static Hashtable<String, SocketServer> kioskThreads = null;
    private ArrayList<Integer> requestedFloor = new ArrayList<Integer>();

    private ArrayList<Integer> destination = new ArrayList<Integer>();

    public ControlPanel() {
        startServer();

    }

    private void startServer() {
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
            SocketServer socketServer = new SocketServer(clientSocket, "test1", this);
            Thread td = new Thread(socketServer);
            td.start();

        }
    }

    private void solveMsgType(String msg) {
        String[] line = msg.split(",");
        if (line[0].equals("e")) {
            // ElevatorItem item = new ElevatorItem(line[1],line[2],line[3], line[4], line[5],line[6], line[7]);
        } else if (line[0].equals("k")) {
            if (Integer.valueOf(line[1]) == 0) {//RFID
                if (findLoc(line[2]) != -1) {
                    requestedFloor.add(Integer.valueOf(line[1]));
                    destination.add(findLoc(line[2]));
                }
            } else if (Integer.valueOf(line[1]) == 1) { //key input
                requestedFloor.add(Integer.valueOf(line[1]));
                destination.add(Integer.valueOf(line[2]));
            }
        }
    }

    public int findLoc(String id) {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(id + ".properties");
            prop.load(input);

            RFIDItem item = new RFIDItem();
//            item.setExpiryDate(prop.getProperty(SharedConsts.ExpiryDate) );
//            item.setFirstName(prop.getProperty(SharedConsts.FirstName));
            item.setFloor(Integer.valueOf(prop.getProperty(SharedConsts.Floor)));
//            item.setId(Integer.valueOf(prop.getProperty(SharedConsts.Id)));
//            item.setSurname(prop.getProperty(SharedConsts.SurName));
            // load a properties file
            return item.getFloor();
        } catch (IOException ex) {
            ex.printStackTrace();
            return -1;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void readConfig() {

    }

    public static void regThread(SocketServer SocketServer) {
        //System.out.println("cp regThread");
        //System.out.println("cp id = " + SocketServer.getID());
        System.out.println("cp receivedMsg = " + SocketServer.getReceivedMsg());
        //System.out.println("cp alive thread = " + Thread.activeCount());
        newThreads.put(SocketServer.getID(), SocketServer);

    } // regThread

    public static void main(String args[]) {
        ControlPanel panel = new ControlPanel();

    } // end of main
}
