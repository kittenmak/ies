package ies;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class Config {
    File file = new File(SharedConsts.ConfigFilePath);
    int mElevator = 1;
    int mFloor = 1;
    boolean mEnd = false;

    public Config() {
        createOrModifyFile(false);
        createElevator();
        setConfig();
    }

    public void setConfig() {
        Scanner scanner = new Scanner(System.in);

        while (!mEnd) {
            System.out.println("0) set Elevator ");
            System.out.println("1) set Floor ");
            System.out.println("2) read config file");
            System.out.println("3) QUIT ");
            System.out.print("please input the number: ");
            int action = Integer.parseInt(scanner.nextLine());

            switch (action) {
                case 0: {
                    System.out.print("please set the Elevator ( Now is " + mElevator + " ) : ");
                    mElevator = Integer.parseInt(scanner.nextLine());
                    createOrModifyFile(true);
                    break;
                }
                case 1: {
                    System.out.print("please set the Floor ( Now is " + mFloor + " ) : ");
                    mFloor = Integer.parseInt(scanner.nextLine());
                    createOrModifyFile(true);
                    break;
                }
                case 2: {
                    System.out.println(SharedConsts.Elevator + " = " + mElevator + " " + SharedConsts.Floor + " = " + mFloor);
                    break;
                }
                case 3: {
                    mEnd = true;
                    break;
                }
                default: {
                    System.out.print("Input error,try again \n");
                    break;
                }
            }
        }
    }

    public void createElevator(){
        Properties prop = new Properties();
        OutputStream output = null;

        for(int i=1; i<=mElevator; i++){
            try {
                output = new FileOutputStream(SharedConsts.Path + "e" + i + ".properties");
                prop.setProperty(SharedConsts.EID, String.valueOf(i));
                prop.setProperty(SharedConsts.Model, "comp4007");
                prop.setProperty(SharedConsts.currentFloor, "0");
                prop.setProperty(SharedConsts.speed, "1000");
                prop.setProperty(SharedConsts.maxWeight, "500");
                prop.setProperty(SharedConsts.currentWeight, "0");
                prop.setProperty(SharedConsts.idleTimer, "5");
                prop.setProperty(SharedConsts.doorTimer, "5");
                prop.setProperty(SharedConsts.ETA, "0");
                prop.setProperty(SharedConsts.doorStatus, "0");
                prop.setProperty(SharedConsts.port, String.valueOf(25000+i));
                prop.setProperty(SharedConsts.host, "localhost");
                prop.setProperty(SharedConsts.destination, "");
                prop.setProperty(SharedConsts.elevatorStatus, "0");
                prop.setProperty(SharedConsts.direction, "stop");
                prop.store(output, null);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createOrModifyFile(boolean editValue) {
        Properties prop = new Properties();
        OutputStream output = null;
//        InputStream input = null;




        if (!file.exists() || editValue) {
//            System.out.println("file not exist");
            try {
                String savePath = SharedConsts.Path;
                File saveLocation = new File(savePath);
                if(!saveLocation.exists()){
                    saveLocation.mkdirs();

                    output = new FileOutputStream(SharedConsts.ConfigFilePath);

                    // set the properties value
                    prop.setProperty(SharedConsts.Elevator, String.valueOf(mElevator));
                    prop.setProperty(SharedConsts.Floor, String.valueOf(mFloor));
                    prop.setProperty(SharedConsts.totalRFID, "0");

                    // save properties to project root folder
                    prop.store(output, null);
                }
                else{
                    output = new FileOutputStream(SharedConsts.ConfigFilePath);

                    // set the properties value
                    prop.setProperty(SharedConsts.Elevator, String.valueOf(mElevator));
                    prop.setProperty(SharedConsts.Floor, String.valueOf(mFloor));
                    prop.setProperty(SharedConsts.totalRFID, "0");

                    // save properties to project root folder
                    prop.store(output, null);
                }
            } catch (IOException io) {
                io.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        } else {
//            System.out.print("file exist");
//            System.out.print("get data");
            try {
                prop.load(new FileInputStream(SharedConsts.ConfigFilePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mElevator = Integer.valueOf(prop.getProperty(SharedConsts.Elevator));
            mFloor = Integer.valueOf(prop.getProperty(SharedConsts.Floor));
//            System.out.println(SharedConsts.Elevator + " = " + mElevator + " " + SharedConsts.Floor + " = " + mFloor);
        }
    }

//    public void editFile(String element, int value){
//        Properties prop = new Properties();
//        OutputStream output = null;
//
//        try {
//            output = new FileOutputStream(SharedConsts.ConfigFilePath);
//
//            // set the properties value
//            if(element.equals(SharedConsts.Elevator)){
//                prop.setProperty(SharedConsts.Elevator, String.valueOf(value));
//                prop.setProperty(SharedConsts.Floor, String.valueOf(mFloor));
//            }
//            else if(element.equals(SharedConsts.Floor)){
//                prop.setProperty(SharedConsts.Elevator, String.valueOf(mElevator));
//                prop.setProperty(SharedConsts.Floor, String.valueOf(value));
//            }
//
//            // save properties to project root folder
//            prop.store(output, null);
//
//        } catch (IOException io) {
//            io.printStackTrace();
//        } finally {
//            if (output != null) {
//                try {
//                    output.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//    }

    public static void main(String args[]) {
        Config config = new Config();
    }

}
