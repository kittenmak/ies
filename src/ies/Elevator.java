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
    Timer idle = new Timer();
    int stopTime = 50000;
    boolean stop = false;
    int moveTime = 1500;
    Timer openDoor = new Timer();

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

        speed.schedule(new TimerTask() {
            @Override

            public void run() {


                if (direction == 0) {//UP
                    if (currentFloor < maxFloor) {
                        currentFloor++;
                        System.out.println(direction + " cf: " +currentFloor);
                        if(currentFloor==upQueue.get(0)){
                            upQueue.remove(0);
                            stop=true;
                            System.out.println("Stop La!");
                            while(stop){
                                direction=1;

                                openDoor.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        direction=0;
                                        stop=false;
                                    }
                                },0, stopTime);
                            }
                        }
                        try {
                            setMsg("dir", String.valueOf(direction));
                            send(msg);
                            setMsg("cf", String.valueOf(currentFloor));
                            send(msg);
                        } catch (IOException e) {

                        }
                    } else {
                        direction = 1;
                        System.out.println("YO changed");

                        try {
                            setMsg("dir", String.valueOf(direction));
                            send(msg);
                            setMsg("cf", String.valueOf(currentFloor));
                            send(msg);
                        } catch (IOException e) {

                        }
                    }
                } else if (direction == 1) {//STOP
                    System.out.println(direction + " cf: " +currentFloor);
                } else {//DOWN
                    if (currentFloor > 0) {
                        currentFloor--;
                        try {
                            setMsg("dir", String.valueOf(direction));
                            send(msg);
                            setMsg("cf", String.valueOf(currentFloor));
                            send(msg);
                        } catch (IOException e) {

                        }

                    } else {
                        direction = 1;
                        System.out.println("YO changed");
                        try {
                            setMsg("dir", String.valueOf(direction));
                            send(msg);
                            setMsg("cf", String.valueOf(currentFloor));
                            send(msg);
                        } catch (IOException e) {

                        }
                    }
                }
            }
        }, 0, moveTime);
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
        e.direction=0;
        e.upQueue.add(3);
        e.upQueue.add(8);

    } // end of main


}
