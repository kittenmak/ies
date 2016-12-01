package ies;

import java.io.*;
import java.net.Socket;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UIPanel {
    private Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;

    private static Random rand = new Random();
    private static int mFloor;
    private static int mElevator;

    public UIPanel(){
        connectServer();
    }

    void connectServer(){
        try {
            clientSocket = new Socket(SharedConsts.ServerAddress, SharedConsts.ServerPort);
            System.out.printf("ElevatorPanel Connected to server using local port: %d.\n", clientSocket.getLocalPort());
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

    private static void readConfig() {
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

    public static void main(String Args[]) {
        readConfig();
        UI eUI = new UI(30, 4);

        while (true) {

//            eUI.upFloor((int) ((rand.nextInt(mFloor))));
            eUI.upFloor((int)(Math.random()*4));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(UIPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }

}
