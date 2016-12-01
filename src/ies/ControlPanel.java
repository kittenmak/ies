package ies;


import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Created by michaelleung on 30/11/2016.
 */
public class ControlPanel{
    private static Hashtable<String, SocketServer> newThreads = null;
    private static ArrayList<Integer> requestedFloor = new ArrayList<Integer>();

    private static ArrayList<Integer> destination = new ArrayList<Integer>();

    protected static int[] currentFloorOfElevator = new int[4];
    protected static int[] directionOfElevator = new int[4];
    private ArrayList<String> waitQueue = new ArrayList<String>();
    private int mFloor;
    private int mElevator;

    public ControlPanel() {
        readConfig();
        startServer();
        Algorithm ag = new Algorithm();

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

    private static void solveMsgType(String msg) {
        Algorithm ag = new Algorithm();
        String[] line = msg.split(",");
        if (line[0].equals("e")) {
            System.out.println("msg start with e");
            // ElevatorItem item = new ElevatorItem(line[1],line[2],line[3], line[4], line[5],line[6], line[7]);

        } else if (line[0].equals("k")) {
            System.out.println("msg start with k");
            if (Integer.valueOf(line[1]) == 0) {//RFID
                System.out.println("msg start with k RFID");
                if (findLoc(line[1]) != -1) {
                    System.out.println("msg start with k RFID requestedFloor = " + line[2] + " destination = " + line[3] );
                    requestedFloor.add(Integer.valueOf(line[2]));
                    destination.add(findLoc(line[3]));
                }
            } else if (Integer.valueOf(line[1]) == 1) { //key input
                System.out.println("msg start with k KEYPAD requestedFloor = " + line[2] + " destination = " + line[3] );
                requestedFloor.add(Integer.valueOf(line[2]));
                destination.add(Integer.valueOf(line[3]));
            }
        }
        else if(line[0].equals("ui")){
            System.out.println("msg start with ui");
        }

        while (!requestedFloor.isEmpty()) {
            int tmpDir;
            if (requestedFloor.get(0) < destination.get(0)) {
                tmpDir = 0;
            } else {
                tmpDir = 2;
            }
            if (requestedFloor.get(0) - destination.get(0) < 0) {//going up
                int bestLift = ag.findBestElevator(currentFloorOfElevator, directionOfElevator, requestedFloor.get(0), tmpDir, 40);
                if (bestLift == 0) {
                    System.out.println("bestLift = 0" + " requestedFloor.get(0) = " + requestedFloor.get(0));
                    //update the lift 0

                } else if (bestLift == 1) {
                    System.out.println("bestLift = 1" + " requestedFloor.get(0) = " + requestedFloor.get(0));
                    //update the lift 1
                } else if (bestLift == 2) {
                    System.out.println("bestLift = 2" + " requestedFloor.get(0) = " + requestedFloor.get(0));
                    //update the lift 2
                } else if (bestLift == 3) {
                    System.out.println("bestLift = 3" + " requestedFloor.get(0) = " + requestedFloor.get(0));
                    //update the lift 3
                }

            }
            requestedFloor.remove(0);
        }
    }

    public static int findLoc(String id) {
        Properties prop = new Properties();
        InputStream input = null;
        File f = new File(SharedConsts.Path + id + ".properties");
        if (f.exists()) {
            try {
                input = new FileInputStream(id + ".properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                prop.load(input);
            } catch (IOException e) {
                e.printStackTrace();
            }

            RFIDItem item = new RFIDItem();
            item.setExpiryDate(prop.getProperty(SharedConsts.ExpiryDate));
            item.setFirstName(prop.getProperty(SharedConsts.FirstName));
            item.setGrantedFloor(prop.getProperty(SharedConsts.Floor));
            item.setId(Integer.valueOf(prop.getProperty(SharedConsts.Id)));
            item.setSurname(prop.getProperty(SharedConsts.SurName));
            // load a properties file
            return Integer.valueOf(item.getGrantedFloor());
        }
        return -1;
    }

    private void readConfig() {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream(SharedConsts.ConfigFilePath);

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            mElevator = Integer.valueOf(prop.getProperty(SharedConsts.Elevator));
            mFloor = Integer.valueOf(prop.getProperty(SharedConsts.Floor));

        } catch (IOException ex) {
            ex.printStackTrace();
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

    public static void regThread(SocketServer SocketServer) {
        //System.out.println("cp regThread");
        //System.out.println("cp id = " + SocketServer.getID());
        System.out.println("cp receivedMsg = " + SocketServer.getReceivedMsg());
        //System.out.println("cp alive thread = " + Thread.activeCount());
        newThreads.put(SocketServer.getID(), SocketServer);

        solveMsgType(SocketServer.getReceivedMsg());
//        try {
//            System.out.println("cp send dataaaaaaaa");
//            SocketServer.sendMsg("send data to client from cp");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    } // regThread

    public static void main(String args[]) {

//        ElevatorUI elevatorUI = new ElevatorUI(40, 4);
//        Thread t1 = new Thread(new ControlPanel());
//        elevatorUI.setLiftFloor(1,4);
//        ElevatorPanel ep = new ElevatorPanel();
        ControlPanel panel = new ControlPanel();


    } // end of main
}
