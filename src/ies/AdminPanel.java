//package ies;
//
//        import javax.swing.*;
//        import java.io.*;
//        import java.util.ArrayList;
//        import java.util.Properties;
//
//public class AdminPanel {
//
//    private static int totalFloor;
//
//    public AdminPanel() {
//        startUI();
//        totalFloor=getTotalFloorNumber();
//    }
//
//    private void startUI(){
//        AdminUI f = new AdminUI();
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.setVisible(true);
//    }
//
//    //TODO to private (create adapter?)
//    public static void createRFID(RFIDItem item) {
//        Properties configProp = new Properties();
//        Properties rfidProp = new Properties();
//        Properties prop3 = new Properties();
//        OutputStream output = null;
//        InputStream input = null;
//        int totalRFIDNumber;
//        boolean canCreate=true;
//
//        //Get the total number of RFID
//
//        if(0>Integer.parseInt(item.getGrantedFloor())||Integer.parseInt(item.getGrantedFloor())>totalFloor) {
//            JOptionPane.showMessageDialog(null, "The range of floor number should be 1-" + totalFloor);
//            canCreate = false;
//        }
//
//        //Save RFID
//        createPropertiesWithRFIDItem(item);
//
//
//    }
//
//    public static void modifyRFID(RFIDItem item){
//        modifyPropertiesWithRFIDItem(item);
//    }
//
//    public static void deleteRFID(String id){
//
//        if(checkPropertyIsExist(id)==true){
//            deletePropertiesWithID(id);
//        }else{
//            JOptionPane.showMessageDialog(null, "The path does not exist");
//        }
//
//    }
//
//    private RFIDItem readRFID(RFIDItem item){
//        return item;
//    }
//
//    private void removeFloorFromAll(int floor){
//        //disable the access right from all users for a certain floor
//    }
//
//    private void addFloorToAll(int floor){
//        //grant access right to all users for a certain floor
//    }
//
//    public static boolean checkPropertyIsExist(String id){
//
//        File file = new File(SharedConsts.Path + id + ".properties");
//        if(file.exists()==true){
//            return true;
//        }else{
//            return false;
//        }
//    }
//
//
//    private static String getTotalRFIDinProperties(){
//        String totalRFID;
//        FileInputStream input = null;
//        Properties configProp = new Properties();
//
//        try {
//            String savePath = SharedConsts.Path;
//            File saveLocation = new File(savePath);
//            input = new FileInputStream(SharedConsts.ConfigFilePath);
//            configProp.load(input);
//
//
//        } catch (IOException io) {
//            io.printStackTrace();
//        } finally {
//            totalRFID=configProp.getProperty(SharedConsts.totalRFID);
//
//            if (input != null) {
//
//
//                try {
//                    input.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//        return totalRFID;
//    }
//
//    private static  void setTotalRFIDinProperties(int newTotalFloor){
//        FileOutputStream output = null;
//        Properties configProp = new Properties();
//
//        try {
//            String savePath = SharedConsts.Path;
//            File saveLocation = new File(savePath);
//            FileInputStream input = new FileInputStream(SharedConsts.ConfigFilePath);
//            configProp.load(input);
//            input.close();
//
//            output = new FileOutputStream(SharedConsts.ConfigFilePath);
//
//            // set the properties value
//            configProp.setProperty(SharedConsts.totalRFID, String.valueOf(newTotalFloor));
//
//            // save properties to project root folder
//            configProp.store(output, null);
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
//
//    private int getTotalFloorNumber(){
//        Properties prop = new Properties();
//        InputStream input = null;
//        int totalFloor;
//
//        try {
//
//            input = new FileInputStream(SharedConsts.Path +SharedConsts.ConfigFileName);
//
//            // load a properties file
//            prop.load(input);
//
//            // get the property value and print it out
//            //System.out.println(prop.getProperty(SharedConsts.Floor));
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        } finally {
//            totalFloor=Integer.parseInt(prop.getProperty(SharedConsts.Floor));
//            if (input != null) {
//
//                try {
//                    input.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return  totalFloor;
//    }
//
//    private static  void createPropertiesWithRFIDItem(RFIDItem item){
//        Properties rfidProp = new Properties();
//        OutputStream output = null;
//        int totalRFIDNumber=Integer.parseInt(getTotalRFIDinProperties());
//
//        if(checkPropertyIsExist(String.valueOf(item.getId()))==false) {
//
//            try {
//
//                String savePathName = SharedConsts.RFIDFilePath;
//                File savePath = new File(savePathName);
//                output = new FileOutputStream(SharedConsts.Path + String.valueOf(item.getId()) + ".properties");
//
//                //TODO
//                // set the properties value
//                rfidProp.setProperty(SharedConsts.Id, String.valueOf(item.getId()));
//                rfidProp.setProperty(SharedConsts.SurName, item.getSurname());
//                rfidProp.setProperty(SharedConsts.FirstName, item.getFirstName());
//                rfidProp.setProperty(SharedConsts.Floor, item.getGrantedFloor());
//                rfidProp.setProperty(SharedConsts.ExpiryDate, item.getExpiryDate());
//                // save properties to project root folder
//
//                totalRFIDNumber++;
//                setTotalRFIDinProperties(totalRFIDNumber);
//
//
//                rfidProp.store(output, null); // save file !!!!!!!!!!!!!
//                output.close();
//                JOptionPane.showMessageDialog(null, "RFID created successfully");
//
//            } catch (IOException io) {
//                io.printStackTrace();
//            } finally {
//                if (output != null) {
//                    try {
//                        output.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }else{
//            JOptionPane.showMessageDialog(null, "RFID has been exist");
//        }
//    }
//
//    private static void modifyPropertiesWithRFIDItem(RFIDItem item){
//        Properties rfidProp = new Properties();
//        OutputStream output = null;
//        boolean canUpdate = true;
//
//        try {
//
//            String savePathName = SharedConsts.RFIDFilePath;
//            File savePath = new File(savePathName);
//            output = new FileOutputStream(SharedConsts.Path+String.valueOf(item.getId())+".properties");
//
//            //TODO
//            // set the properties value
//            rfidProp.setProperty(SharedConsts.Id, String.valueOf(item.getId()));
//            rfidProp.setProperty(SharedConsts.SurName, item.getSurname());
//            rfidProp.setProperty(SharedConsts.FirstName, item.getFirstName());
//            rfidProp.setProperty(SharedConsts.Floor, item.getGrantedFloor());
//            if(0>Integer.parseInt(item.getGrantedFloor())||Integer.parseInt(item.getGrantedFloor())>totalFloor) {
//                JOptionPane.showMessageDialog(null, "The range of floor number should be 1-" + totalFloor);
//                canUpdate = false;
//            }
//            rfidProp.setProperty(SharedConsts.ExpiryDate, item.getExpiryDate());
//
//            // save properties to project root folder
//            if(canUpdate==true){
//                rfidProp.store(output, null); // save file !!!!!!!!!!!!!
//                JOptionPane.showMessageDialog(null, "RFID updated successfully");
//            }else{
//
//            }
//
//
//
//        } catch (IOException io) {
//            io.printStackTrace();
//        } finally {
//            if (output != null) {
//                try {
//                    output.close();
//                } catch (IOException e2) {
//                    e2.printStackTrace();
//                }
//            }
//        }
//
//    }
//
//    private static void deletePropertiesWithID(String id){
//
//        int totalRFIDNumber=Integer.parseInt(getTotalRFIDinProperties());
//
//        try{
//
//            File file = new File(SharedConsts.Path + id + ".properties");
//
//            if(file.delete()){
//                totalRFIDNumber--;
//                setTotalRFIDinProperties(totalRFIDNumber);
//                JOptionPane.showMessageDialog(null, file.getName() + " is deleted!");
//            }else{
//                JOptionPane.showMessageDialog(null, "Delete operation is failed.");
//            }
//
//        }catch(Exception e){
//
//            e.printStackTrace();
//
//        }
//
//    }
//
//
//}
//
