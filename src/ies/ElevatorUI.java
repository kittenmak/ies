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
public class ElevatorUI extends JFrame implements ActionListener {
    //    static ElevatorUI frm = new ElevatorUI();
    static int p;
    static int hig;
    static int maxfloor = 40;
    static int left = 10;
    boolean clicked = false;
    Object[] EleList;
    int nowLiftFloor[];
    int maxFloor=40;
    int maxLeft=4;
    int windowHeight = 800, windowWidth = 600;
    public volatile static int test = 0;

    public ElevatorUI(int floor, int left) {
        maxFloor = floor;
        maxLeft = left;
        nowLiftFloor = new int[maxLeft];




        ArrayList<Label> LiftA = new ArrayList<>();








        BorderLayout br = new BorderLayout();
        this.setLayout(br);
        JPanel jPanelLeft = new JPanel();
        jPanelLeft.setLayout((new GridLayout(maxFloor, 1)));
        this.add(jPanelLeft, br.WEST);
        JPanel jPanelRight = new JPanel();
        jPanelRight.setLayout((new GridLayout(maxFloor, (maxLeft * 2 + 1))));
        this.add(jPanelRight, br.CENTER);
        for (int n = 0; n < maxFloor; n++) {
            jPanelLeft.add(new Button("Floor " + (maxFloor - n)));
        }
        this.setTitle("ElevetorUI");
        this.setSize(windowHeight, windowWidth);


        EleList = new Object[maxLeft];


        for (int n = 0; n < maxLeft; n++) {
            EleList[n] = new ArrayList<Label>();
        }


        for (int n = 0; n < maxFloor * (maxLeft * 2 + 1); n++) {
            Label temp = new Label("");
            temp.setBackground(Color.gray);

            if ((n % (maxLeft * 2 + 1)) % 2 == 1) {

                ArrayList<Label> tempA;
                tempA = (ArrayList<Label>) EleList[((n % (maxLeft * 2 + 1) - 1) / 2) % maxLeft];
//                switch(((n % (maxLeft * 2 + 1) - 1) / 2) % maxLeft){
//                    case 0:
//
//                        break;
//                    case 1:
//                        break;
//                    case 2:
//                        break;
//                    case 3:
//                        break;
//                }

                tempA.add(0, temp);
                temp.setBackground(Color.LIGHT_GRAY);
                if (n > (maxFloor - 1) * (maxLeft*2+1)) {
                    temp.setBackground(Color.BLACK);        //set Lift's label
                    nowLiftFloor[((n % (maxLeft * 2 + 1) - 1) / 2)]=0;
                    System.out.println(((n % (maxLeft * 2 + 1) - 1) / 2));
                }
            }


            jPanelRight.add(temp);
        }

        this.setVisible(true);
    }

    public synchronized void setLiftFloor(int lift, int floor) {
            ArrayList<Label> tempA;
            tempA = (ArrayList<Label>) EleList[lift];
            tempA.get(nowLiftFloor[lift]).setBackground(Color.LIGHT_GRAY);
            tempA.get(floor).setBackground(Color.BLACK);
            nowLiftFloor[lift] = floor;
            this.repaint();
    }


    public void actionPerformed(ActionEvent e) {
        Graphics g = getGraphics();
        paint(g);
        clicked = true;
    }

}
