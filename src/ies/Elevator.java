package ies;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.*;

/**
 * Created by Kitten on 1/12/2016.
 */

//this is a socket client, send parameters automatically
public class Elevator {
    //    private static Hashtable<String, SocketClient> newThreads = null;
    private static SocketClient mSocketClient;
    private static int currentFloor;
    private static ArrayList<Integer> downQueue = new ArrayList<Integer>();
    private static ArrayList<Integer> upQueue = new ArrayList<Integer>();
    //private int idleTimer = 10;
    private static int direction;
    //private int speed = 1000;

    private static String msg;
    private static int EID;
    private static int maxFloor = 15;
    Timer speed = new Timer();
    static int idleTime = 5000;
    static int stopTime = 2500;
    boolean stop = false;
    int moveTime = 1500;
    Timer openDoor = new Timer();

    static Timer up = new Timer();
    static Timer down = new Timer();
    static Timer stopShort = new Timer();
    static Timer stopLong = new Timer();
    static int status; //0=up, 1=stopLong, 2=down, 3=stopShort

    ArrayList<Integer> liftAUpQueue = new ArrayList<Integer>();
    ArrayList<Integer> liftBUpQueue = new ArrayList<Integer>();
    ArrayList<Integer> liftCUpQueue = new ArrayList<Integer>();
    ArrayList<Integer> liftDUpQueue = new ArrayList<Integer>();

    ArrayList<Integer> liftADownQueue = new ArrayList<Integer>();
    ArrayList<Integer> liftBDownQueue = new ArrayList<Integer>();
    ArrayList<Integer> liftCDownQueue = new ArrayList<Integer>();
    ArrayList<Integer> liftDDownQueue = new ArrayList<Integer>();


    public Elevator(int eid) {
        this.EID = eid;
//        connectServer();
//        newThreads = new Hashtable<String, SocketClient>();

        SocketClient socketClient = new SocketClient(String.valueOf(EID), this);
        Thread td = new Thread(socketClient);
        td.start();
    }

    static void setMsg(String currentFloor, String direction, String status) {
        msg = "e," + String.valueOf(EID) + "," + currentFloor + "," + direction + "," + status;

    }

    static void movement() {

        System.out.println("Lets move!");
        if (status == 0) { //UP

            up.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (currentFloor < maxFloor && !upQueue.isEmpty() && status == 0) {
                        currentFloor++;
//                        System.out.println(direction + " cf: " + currentFloor);
//                        System.out.println("Current Queue" + upQueue);
                        if (currentFloor == upQueue.get(0)) {
                            System.out.println("Arrived!");
                            upQueue.remove(0);
                            status = 3; //short stop
                            //up.purge();
                            movement();

                        }
                    } else if (currentFloor == maxFloor) {
                        if (downQueue.isEmpty()) {
                            System.out.println("HI, changed status to ? = " + status);
                            status = 1; // long stop
                            direction = 1;
                            //up.cancel();
                            up.purge();
                            movement();
                        } else {
                            status = 2; //go down
                            direction = 2;
                            // up.cancel();
                            up.purge();
                            movement();
                        }
                    }
                    try {
                        setMsg(Integer.toString(currentFloor), Integer.toString(direction), Integer.toString(status));
                        mSocketClient.send(msg);


                    } catch (IOException e) {

                    }
                }
            }, 1500, 1500);


        } else if (status == 1) {

            System.out.println("Lift is already idle.");
            if (currentFloor > 0) {
                stopLong.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (currentFloor > 0) {
                            downQueue.add(0);
                            System.out.println("Down Queue added G/F: " + downQueue);
                            status = 2;
                            direction = 2;
                            movement();
                        }
                    }
                }, idleTime);
            } else {
                System.out.println("This lift is waiting for singal on G/F");
            }


            try {
                setMsg(Integer.toString(currentFloor), Integer.toString(direction), Integer.toString(status));
                mSocketClient.send(msg);

            } catch (IOException e) {

            }

        } else if (status == 2) {

            down.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (currentFloor > 0 && !downQueue.isEmpty() && status == 2) {
                        currentFloor--;
                        System.out.println(direction + " cf: " + currentFloor);
                        if (currentFloor == downQueue.get(0)) {
                            downQueue.remove(0);
                            status = 3;
                        }
                    } else if (currentFloor == 0) {
                        if (upQueue.isEmpty()) {
                            status = 1;
                            direction = 1;
                            System.out.println("UpQueue is empty.");
                            down.purge();
                            movement();
                        } else {
                            status = 0;
                            direction = 0;
                            down.purge();
                            movement();
                        }
                    }

                    try {
                        setMsg(Integer.toString(currentFloor), Integer.toString(direction), Integer.toString(status));
                        mSocketClient.send(msg);

                    } catch (IOException e) {

                    }

                }
            }, 1500, 1500);


        } else {
            System.out.println("This is case 3");
            stopShort.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Door is opened! - direction = " + direction);


                    if (direction == 0 && !upQueue.isEmpty()) {
                        status = 0;
                        movement();
                    } else if (direction == 2 && !downQueue.isEmpty()) {
                        status = 2;
                        movement();
                    }
                    if (upQueue.isEmpty() && downQueue.isEmpty()) {
                        status = 1;
                        movement();
                    }
                }
            }, stopTime);

            try {
                setMsg(Integer.toString(currentFloor), Integer.toString(direction), Integer.toString(status));
                mSocketClient.send(msg);

            } catch (IOException e) {

            }
        }


    }


    static void initMsg() {
        try {
            setMsg(Integer.toString(currentFloor), Integer.toString(direction), Integer.toString(status));
            mSocketClient.send(msg);
        } catch (IOException e) {

        }
    }

    public ArrayList<Integer> getDownQueue() {
        return downQueue;
    }

    public void setDownQueue(ArrayList<Integer> downQueue) {
        this.downQueue = downQueue;
    }

    public ArrayList<Integer> getUpQueue() {
        return upQueue;
    }

    public void setUpQueue(ArrayList<Integer> upQueue) {
        this.upQueue = upQueue;
    }

    public static void regThread(SocketClient socketClient) {
//        System.out.println("Elevator regThread");
//        System.out.println("Elevator id = " + socketClient.getID());
//        System.out.println("Elevator receivedMsg = " + socketClient.getReceivedMsg());
//        System.out.println("Elevator alive thread = " + Thread.activeCount());
        mSocketClient = socketClient;

        initMsg();
        movement();

        try {
//            mSocketClient.send(msg);
            String msg = mSocketClient.receive();
            System.out.println("From CP's msg : " + msg);
            String[] line = msg.split(",");
            if (line[0].equals("e")) {
                if (Integer.valueOf(line[1]) == EID) {
                    if (Integer.valueOf(line[1]) < Integer.valueOf(line[2])) {
                        upQueue.add(Integer.valueOf(line[1]));
                        upQueue.add(Integer.valueOf(line[2]));
                    } else {
                        downQueue.add(Integer.valueOf(line[1]));
                        downQueue.add(Integer.valueOf(line[2]));
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    } // regThread

    public static void main(String args[]) {

        Elevator e1 = new Elevator(4);
        e1.direction = 0;
        e1.status = 0;
        e1.upQueue.add(3);
        e1.upQueue.add(8);


    } // end of main


}
