//package ies;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Properties;
//
///**
// * Created by michaelleung on 22/11/2016.
// */
//public class AdminUI extends JFrame implements ActionListener {
//
//    private Panel mAdminPanel;
//    private Button mCreateBtn;
//    private Button mModifyBtn;
//    private Button mDeleteBtn;
//    private int totalFloor;
//
//    public AdminUI() {
//
//        this.setTitle("AdminUI");
//        this.setSize(400, 400);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.WIDTH) / 8, (Toolkit.getDefaultToolkit().getScreenSize().height - this.HEIGHT) / 8);
//
//        mAdminPanel = new Panel();
//        mCreateBtn = new Button(SharedConsts.Create);
//        mModifyBtn = new Button(SharedConsts.Modify);
//        mDeleteBtn = new Button(SharedConsts.Delete);
//
//        mAdminPanel.add(mCreateBtn);
//        mAdminPanel.add(mModifyBtn);
//        mAdminPanel.add(mDeleteBtn);
//
//        mCreateBtn.addActionListener(this);
//        mModifyBtn.addActionListener(this);
//        mDeleteBtn.addActionListener(this);
//
//        add(mAdminPanel, BorderLayout.WEST);
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        String arg = e.getActionCommand();
//
//
//        if (arg == SharedConsts.Create) {
//
//            System.out.println("onClick = " + SharedConsts.Create);
//            createFrame();
//
//
//        } else if (arg == SharedConsts.Modify) {
//            System.out.println("onClick = " + SharedConsts.Modify);
//            modifyFrame();
//        } else if (arg == SharedConsts.Delete) {
//
//            System.out.println("onClick = " + SharedConsts.Delete);
//            deleteFrame();
//        }
//    }
//
//    public void createFrame() {
//        //try create table
//        JFrame cf = new JFrame();
//        cf.setSize(400, 300);
//        cf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        cf.getContentPane().setLayout(new FlowLayout());
//        cf.setVisible(true);
//
//        JPanel cPanel = new JPanel();
//        JLabel cLabel1 = new JLabel();
//        final JTextField cField1 = new JTextField(10);
//        JLabel cLabel2 = new JLabel();
//        final JTextField cField2 = new JTextField(20);
//        JLabel cLabel3 = new JLabel();
//        final JTextField cField3 = new JTextField(20);
//        JLabel cLabel4 = new JLabel();
//        final JTextField cField4 = new JTextField(3);
//        JLabel cLabel5 = new JLabel();
//        final JTextField cField5 = new JTextField(10);
//
//
//        JButton cSubmit = new JButton();
//
//        cField1.setText("");
//        cField1.selectAll();
//        cLabel1.setText("ID: ");
//
//        cField2.setText("");
//        cField2.selectAll();
//        cLabel2.setText("Surname: ");
//
//        cField3.setText("");
//        cField3.selectAll();
//        cLabel3.setText("First Name: ");
//
//        cField4.setText("");
//        cField4.selectAll();
//        cLabel4.setText("Granted Floor: ");
//
//        cField5.setText("");
//        cField5.selectAll();
//        cLabel5.setText("Expiry Date (DD/MM/YYYY): ");
//
//        cSubmit.setText("Submit");
//
//
//        cPanel.setLayout(new BoxLayout(cPanel, BoxLayout.Y_AXIS));
//
//        cPanel.add(cLabel1);
//        cPanel.add(cField1);
//        cPanel.add(cLabel2);
//        cPanel.add(cField2);
//        cPanel.add(cLabel3);
//        cPanel.add(cField3);
//        cPanel.add(cLabel4);
//        cPanel.add(cField4);
//        cPanel.add(cLabel5);
//        cPanel.add(cField5);
//        cPanel.add(cSubmit);
//
//        cf.add(cPanel);
//
//        cSubmit.addActionListener(new ActionListener() { // Submit button Listener
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                boolean canCreate = true;
//                System.out.println("onClick = " + SharedConsts.Create);
//
//
//                String id = cField1.getText().toString();
//                String surName = cField2.getText().toString();
//                String firstName = cField3.getText().toString();
//                String grantedFloor = cField4.getText().toString();
//                String expiryDate = cField5.getText().toString();
//
//                RFIDItem newRFID = new RFIDItem(id, surName, firstName, grantedFloor, expiryDate);
//                AdminPanel adminPanel = new AdminPanel();
//                adminPanel.createRFID(newRFID);
//            }
//
//
//        });
//
//        //try create table
//
//
//    }
//
//    public void modifyFrame() {
//
//        JFrame mf = new JFrame();
//        mf.setSize(400, 300);
//        mf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        mf.getContentPane().setLayout(new FlowLayout());
//        mf.setVisible(true);
//
//        JPanel mPanel = new JPanel();
//        JLabel mLabel1 = new JLabel();
//        final JTextField mField1 = new JTextField(10);
//        JLabel mLabel2 = new JLabel();
//        final JTextField mField2 = new JTextField(20);
//        JLabel mLabel3 = new JLabel();
//        final JTextField mField3 = new JTextField(20);
//        JLabel mLabel4 = new JLabel();
//        final JTextField mField4 = new JTextField(3);
//        JLabel mLabel5 = new JLabel();
//        final JTextField mField5 = new JTextField(10);
//
//
//        JButton mSubmit = new JButton();
//        JButton mLoad = new JButton();
//
//        mField1.setText("");
//        mField1.selectAll();
//        mLabel1.setText("ID: ");
//
//        mField2.setText("");
//        mField2.selectAll();
//        mField2.setEditable(false);
//        mLabel2.setText("Surname: ");
//
//        mField3.setText("");
//        mField3.selectAll();
//        mField3.setEditable(false);
//        mLabel3.setText("First Name: ");
//
//        mField4.setText("");
//        mField4.selectAll();
//        mField4.setEditable(false);
//        mLabel4.setText("Granted Floor: ");
//
//        mField5.setText("");
//        mField5.selectAll();
//        mField5.setEditable(false);
//        mLabel5.setText("Expiry Date (DD/MM/YYYY): ");
//
//        mLoad.setText("Check");
//        mSubmit.setText("Submit");
//
//        mPanel.setLayout(new BoxLayout(mPanel, BoxLayout.Y_AXIS));
//
//        mPanel.add(mLabel1);
//        mPanel.add(mField1);
//        mPanel.add(mLoad);
//        mPanel.add(mLabel2);
//        mPanel.add(mField2);
//        mPanel.add(mLabel3);
//        mPanel.add(mField3);
//        mPanel.add(mLabel4);
//        mPanel.add(mField4);
//        mPanel.add(mLabel5);
//        mPanel.add(mField5);
//
//
//        mPanel.add(mSubmit);
//
//        mf.add(mPanel);
//
//        mSubmit.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                boolean canUpdate = true;
//                String id = mField1.getText();
//                String surName = mField2.getText();
//                String firstName = mField3.getText();
//                String grantedFloor = mField4.getText();
//                String expiryDate = mField5.getText();
//                LogAction logModify = new LogAction("Modified ID " + id + "with {");
//
//                RFIDItem modifyRFID = new RFIDItem(id, surName, firstName, grantedFloor, expiryDate);
//                AdminPanel adminPanel = new AdminPanel();
//                adminPanel.modifyRFID(modifyRFID);
//
//
//            }
//        });
//
//        mLoad.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String id = mField1.getText();
//                AdminPanel adminPanel = new AdminPanel();
//                if (adminPanel.checkPropertyIsExist(id) == true) {
//                    JOptionPane.showMessageDialog(null, "RFID is exist");
//                    mField1.setEditable(false);
//                    mField2.setEditable(true);
//                    mField3.setEditable(true);
//                    mField4.setEditable(true);
//                    mField5.setEditable(true);
//                    mLoad.hide();
//                } else {
//                    JOptionPane.showMessageDialog(null, "RFID is not exist!!!");
//                }
//
//            }
//        });
//
//
//    }
//
//    public void deleteFrame() {
//
//        JFrame mf = new JFrame();
//        mf.setSize(400, 300);
//        mf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        mf.getContentPane().setLayout(new FlowLayout());
//        mf.setVisible(true);
//
//        JPanel dPanel = new JPanel();
//        JLabel dLabel1 = new JLabel();
//        final JTextField dField1 = new JTextField(10);
//
//
//        JButton dDelete = new JButton();
//
//        dField1.setText("");
//        dField1.selectAll();
//        dLabel1.setText("ID: ");
//
//        dDelete.setText("Delete");
//
//        dPanel.setLayout(new BoxLayout(dPanel, BoxLayout.Y_AXIS));
//
//        dPanel.add(dLabel1);
//        dPanel.add(dField1);
//        dPanel.add(dDelete);
//
//        mf.add(dPanel);
//
//
//        dDelete.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String id = dField1.getText();
//
//                AdminPanel adminPanel = new AdminPanel();
//                adminPanel.deleteRFID(id);
//
//            }
//        });
//
//
//    }
//
//
//}
