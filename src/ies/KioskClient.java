package ies;


import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;

/**
 * Created by michaelleung on 30/11/2016.
 */
public class KioskClient {
    private Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;
    private ArrayList<String> stringList = new ArrayList<String>();
    private Random rand = new Random();
    private ArrayList<KioskItem> item = new ArrayList<KioskItem>();
    private int mElevator;
    private int mFloor;

    public KioskClient(){
        readConfig();
        connectServer();

        for(int i=0; i<20; i++) {
            KioskItem item = new KioskItem();
            item.setDevice(String.valueOf(1));
//                    (rand.nextInt(2)));
            item.setCurFloor(String.valueOf(rand.nextInt(mFloor) + 1));
            item.setDestFloor(String.valueOf(rand.nextInt(mFloor) + 1));

            while(item.getCurFloor().equals(item.getDestFloor())){
                item.setCurFloor(String.valueOf(rand.nextInt(mFloor) + 1));
                item.setDestFloor(String.valueOf(rand.nextInt(mFloor) + 1));
            }

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            item.setTime(sdf.format(cal.getTime()));

            String msg = "k," + item.getDevice() + "," + item.getCurFloor() + "," + item.getDestFloor() + "," + item.getTime();
            try {
                send(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String data = null;
        try {
            data = receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stringList.add(data);

    }

    void connectServer(){
        try {
            clientSocket = new Socket(SharedConsts.ServerAddress, SharedConsts.ServerPort);
            System.out.printf("KC Connected to server using local port: %d.\n", clientSocket.getLocalPort());
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
        System.out.println("KC disconnected.");
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException ex) {
        }
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

    public static void main(String args[]){
        KioskClient kioskClient = new KioskClient();
    } // end of main
}
