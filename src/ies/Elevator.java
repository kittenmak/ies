package ies;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kitten on 1/12/2016.
 */

//this is a socket client, send parameters automatically
public class Elevator {
    private int currentFloor;
    private ArrayList<Integer> downQueue = new ArrayList<Integer>();
    private ArrayList<Integer> upQueue = new ArrayList<Integer>();
    //private int idleTimer = 10;
    private int direction;
    //private int speed = 1000;

    Socket clientSocket = null;
    DataInputStream in;
    DataOutputStream out;
    private String msg;
    private int EID;
    private int maxFloor = 15;
    Timer speed = new Timer();
    int idleTime = 5000;
    int stopTime = 2500;
    boolean stop = false;
    int moveTime = 1500;
    Timer openDoor = new Timer();

    Timer up = new Timer();
    Timer down = new Timer();
    Timer stopShort = new Timer();
    Timer stopLong = new Timer();
    int status; //0=up, 1=stopLong, 2=down, 3=stopShort

    public Elevator(int eid) {
        this.EID = eid;
        connectServer();


        try {
            initMsg();

            String data = receive();
        } catch (IOException e) {
            e.printStackTrace();
        }

        movement();


    }

    void setMsg(String type, String value) {
        msg = String.valueOf(EID) + "," + type + "," + value;
    }

    void movement() {

        System.out.println("Lets move!");
        if(status==0){ //UP

            up.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (currentFloor < maxFloor && !upQueue.isEmpty() && status==0) {
                        currentFloor++;
                        System.out.println(direction + " cf: " + currentFloor);
                        System.out.println("Current Queue" + upQueue);
                        if (currentFloor == upQueue.get(0)) {
                            System.out.println("Arrived!");
                            upQueue.remove(0);
                            status = 3; //short stop
                            //up.purge();
                            movement();

                        }
                    } else if(currentFloor==maxFloor) {
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
                        setMsg("dir", String.valueOf(direction));
                        send(msg);
                        setMsg("cf", String.valueOf(currentFloor));
                        send(msg);
                    } catch (IOException e) {

                    }
                }
            }, 1500,1500);


        }else if(status==1){

            System.out.println("Lift is already idle.");
            if(currentFloor>0){
                stopLong.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (currentFloor > 0) {
                            downQueue.add(0);
                            System.out.println("Down Queue added G/F: " + downQueue);
                            status=2;
                            direction=2;
                            movement();
                        }
                    }
                }, idleTime);
            }else{
                System.out.println("This lift is waiting for singal on G/F");
            }


            try {
                setMsg("dir", String.valueOf(direction));
                send(msg);
                setMsg("cf", String.valueOf(currentFloor));
                send(msg);
            } catch (IOException e) {

            }

        }else if(status==2){

            down.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (currentFloor > 0 && !downQueue.isEmpty() && status==2) {
                        currentFloor--;
                        System.out.println(direction + " cf: " + currentFloor);
                        if (currentFloor == downQueue.get(0)) {
                            downQueue.remove(0);
                            status = 3;
                        }
                    } else if(currentFloor==0) {
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
                        setMsg("dir", String.valueOf(direction));
                        send(msg);
                        setMsg("cf", String.valueOf(currentFloor));
                        send(msg);
                    } catch (IOException e) {

                    }

                }
            }, 1500, 1500);


        }else{
            System.out.println("This is case 3");
            stopShort.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Door is opened! - direction = " + direction);


                    if (direction == 0 &&!upQueue.isEmpty()) {
                        status = 0;
                        movement();
                    } else if (direction == 2 && !downQueue.isEmpty()) {
                        status = 2;
                        movement();
                    }
                    if(upQueue.isEmpty() && downQueue.isEmpty()){
                        status =1;
                        movement();
                    }
                }
            }, stopTime);

            try {
                setMsg("dir", String.valueOf(direction));
                send(msg);
                setMsg("status", String.valueOf("DOOR OPENED"));
                send(msg);
                setMsg("cf", String.valueOf(currentFloor));
                send(msg);
            } catch (IOException e) {

            }
        }




//        speed.schedule(new
//
//                               TimerTask() {
//                                   @Override
//
//                                   public void run() {
//
//
//                                       if (direction == 0) {//UP
//                                           if (currentFloor < maxFloor) {
//                                               currentFloor++;
//                                               System.out.println(direction + " cf: " + currentFloor);
//                                               if (currentFloor == upQueue.get(0)) {
//                                                   upQueue.remove(0);
//                                                   stop = true;
//                                                   System.out.println("Stop La!");
//                                                   while (stop) {
//                                                       direction = 1;
//
//                                                       openDoor.schedule(new TimerTask() {
//                                                           @Override
//                                                           public void run() {
//                                                               direction = 0;
//                                                               stop = false;
//                                                           }
//                                                       }, 0, stopTime);
//                                                   }
//                                               }
//                                               try {
//                                                   setMsg("dir", String.valueOf(direction));
//                                                   send(msg);
//                                                   setMsg("cf", String.valueOf(currentFloor));
//                                                   send(msg);
//                                               } catch (IOException e) {
//
//                                               }
//                                           } else {
//                                               direction = 1;
//                                               System.out.println("YO changed");
//
//                                               try {
//                                                   setMsg("dir", String.valueOf(direction));
//                                                   send(msg);
//                                                   setMsg("cf", String.valueOf(currentFloor));
//                                                   send(msg);
//                                               } catch (IOException e) {
//
//                                               }
//                                           }
//                                       } else if (direction == 1) {//STOP
//                                           System.out.println(direction + " cf: " + currentFloor);
//                                       } else {//DOWN
//                                           if (currentFloor > 0) {
//                                               currentFloor--;
//                                               try {
//                                                   setMsg("dir", String.valueOf(direction));
//                                                   send(msg);
//                                                   setMsg("cf", String.valueOf(currentFloor));
//                                                   send(msg);
//                                               } catch (IOException e) {
//
//                                               }
//
//                                           } else {
//                                               direction = 1;
//                                               System.out.println("YO changed");
//                                               try {
//                                                   setMsg("dir", String.valueOf(direction));
//                                                   send(msg);
//                                                   setMsg("cf", String.valueOf(currentFloor));
//                                                   send(msg);
//                                               } catch (IOException e) {
//
//                                               }
//                                           }
//                                       }
//                                   }
//                               }, 0, moveTime);
    }

    void initMsg() {
        try {
            setMsg("launch", "launched");
            send(msg);
            setMsg("cf", String.valueOf(currentFloor));
            send(msg);
            setMsg("up", String.valueOf(upQueue));
            send(msg);
            setMsg("down", String.valueOf(downQueue));
            send(msg);
            //setMsg("idle", String.valueOf(idleTimer));
            //send(msg);
            setMsg("dir", String.valueOf(direction));
            send(msg);
        } catch (IOException e) {

        }
    }

    void connectServer() {
        try {
            clientSocket = new Socket(SharedConsts.ServerAddress, SharedConsts.ServerPort);
            System.out.printf("Lift Connected to server using local port: %d.\n", clientSocket.getLocalPort());
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final synchronized void send(String msg) throws IOException {
        out.writeInt(msg.length());
        out.write(msg.getBytes(), 0, msg.length());
        out.flush();
    }

    public final synchronized String receive() throws IOException {
        byte[] data = new byte[4];
        int size;
        int len;

        size = in.readInt();
        data = new byte[size];
        do {
            len = in.read(data, data.length - size, size);
            size -= len;
        } while (size > 0);

        return new String(data);
    }

    public void disconnect() {
        System.out.println("Lift disconnected.");
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException ex) {
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

    public static void main(String args[]) {

        Elevator e = new Elevator(1);
        e.direction = 0;
        e.status = 0;
        e.upQueue.add(3);
        e.upQueue.add(8);

    } // end of main


}
