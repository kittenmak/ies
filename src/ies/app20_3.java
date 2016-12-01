package ies;

                        /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Danio
 */
public class app20_3 extends JFrame implements ActionListener {

    static app20_3 frm = new app20_3();
    static int p;
    static int hig;
    static int maxfloor = 20;
    static int left = 5;
    boolean clicked = false;


    public static void main(String args[]) {
        BorderLayout br = new BorderLayout();
        JPanel jPanelLeft = new JPanel();
        jPanelLeft.setLayout((new GridLayout(maxfloor, 1)));
        JPanel jPanelRight = new JPanel();
        jPanelRight.setLayout((new GridLayout(maxfloor, (left * 2 + 1))));
        for (int n = 0; n < maxfloor; n++) {
            jPanelLeft.add(new Button("Floor " + (maxfloor - n)));
        }
//        Button a1 = new Button("Floor 1");
//        jPanelLeft.add(a1);

        Object[] EleList = new Object[left];
        for (int n = 0; n < left; n++) {
            EleList[n] = new ArrayList<Label>();
        }
//        ArrayList<Label> Ele1 = new ArrayList<>();
        for (int n = 0; n < maxfloor * (left * 2 + 1); n++) {
            Label temp = new Label("");

            temp.setBackground(Color.gray);
            if ((n % (left * 2 + 1)) % 2 == 1) {
                ArrayList<Label> tempA;
                tempA = (ArrayList<Label>) EleList[((n % (left * 2 + 1) - 1) / 2) % left];
                tempA.add(temp);
                temp.setBackground(Color.LIGHT_GRAY);
            }

//            if (n == 39 || n == 41 || n == 81 || n == 121 || n == 503 || n == 505 || n == 564 || n == 585 || n == 662 || n == 664) {
//                      temp.setBackground(Color.black);
//            }
//            switch(n%7){
//                case (1):
//                    Ele1.add(0, temp);
//                    break;
//            }
            jPanelRight.add(temp);
        }
//
        ArrayList<Label> tempA;
        tempA = (ArrayList<Label>) EleList[0];
        tempA.get(4).setBackground(Color.red);
        tempA = (ArrayList<Label>) EleList[1];
        tempA.get(2).setBackground(Color.red);
        tempA = (ArrayList<Label>) EleList[2];
        tempA.get(5).setBackground(Color.red);
        tempA = (ArrayList<Label>) EleList[3];
        tempA.get(19).setBackground(Color.red);
        tempA = (ArrayList<Label>) EleList[4];
        tempA.get(11).setBackground(Color.red);

//        System.out.println(Ele1);
        frm.setTitle("ff");
        frm.setLayout(br);
        frm.setSize(800, 640);
        frm.add(jPanelLeft, br.WEST);
        frm.add(jPanelRight, br.CENTER);
        //    a1.addActionListener(frm);
        frm.setVisible(true);
        //   hig = a1.getHeight();
        p = hig;
    }

    public void actionPerformed(ActionEvent e) {
        Graphics g = getGraphics();
        paint(g);
        clicked = true;
    }

    public void paint(Graphics g) {
        System.out.println(hig);
        if (clicked) {
        }
    }
}