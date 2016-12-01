package ies;

import java.io.*;
import java.net.Socket;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UIPanel {
    private static UISocketClient mSocketClient;

    private static Random rand = new Random();
    private static int mFloor;
    private static int mElevator;

    public UIPanel(){
        readConfig();

        UISocketClient uiSocketClient = new UISocketClient(this);
        Thread td = new Thread(uiSocketClient);
        td.start();

        UI eUI = new UI(mFloor, mElevator);
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

    public static void regThread(UISocketClient uiSocketClient){
        System.out.println("UI regThread");
//        System.out.println("UI id = " + uiSocketClient.getID());
        System.out.println("UI receivedMsg = " + uiSocketClient.getReceivedMsg());
        System.out.println("UI alive thread = " + Thread.activeCount());
//        newThreads.put(socketClient.getID(), socketClient);
        mSocketClient = uiSocketClient;

        String msg = "ui,getmsg";
        try {
            mSocketClient.send(msg);
            String receive_msg = mSocketClient.receive();
            System.out.println("UI receive_msg : " + receive_msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String Args[]) {
        UIPanel uiPanel = new UIPanel();
    }

}
