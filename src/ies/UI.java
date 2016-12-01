package ies;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Danio
 */
public class UI extends JFrame implements ActionListener {

    //    static ElevatorUI frm = new ElevatorUI();
    Object[] EleList;
    int nowLiftFloor[];
    int maxFloor;
    int maxLift;
    int windowHeight = 800, windowWidth = 600;

    public UI(int floor, int lift) {
        maxFloor = floor;
        maxLift = lift;
        nowLiftFloor = new int[maxLift];
        BorderLayout br = new BorderLayout();
        this.setLayout(br);

        JPanel jPanelButton = new JPanel();
        jPanelButton.setLayout((new GridLayout(1, 3)));

        TextArea jBotton = new TextArea("", 4, 3, TextArea.SCROLLBARS_VERTICAL_ONLY);
        this.add(jBotton, br.SOUTH);

        jPanelButton.add(new Button("Floor " + (maxFloor - 3)));//TESTING

        JPanel jPanelLeft = new JPanel();
        jPanelLeft.setLayout((new GridLayout(maxFloor, 1)));
        this.add(jPanelLeft, br.WEST);

        JPanel jPanelRight = new JPanel();
        jPanelRight.setLayout((new GridLayout(maxFloor, (maxLift * 2 + 1))));
        this.add(jPanelRight, br.CENTER);

        for (int n = 0; n < maxFloor; n++) {

            Button tempB = new Button("F" + (maxFloor - n));
            tempB.setActionCommand("F" + (maxFloor - n));
            tempB.addActionListener(this);
            jPanelLeft.add(tempB);
        }

        this.setTitle("ElevetorUI");
        this.setSize(windowHeight, windowWidth);
        EleList = new Object[maxLift];

        for (int n = 0; n < maxLift; n++) {
            EleList[n] = new ArrayList<Label>();
        }

        for (int n = 0; n < maxFloor * (maxLift * 2 + 1); n++) {
            Label temp = new Label("");
            temp.setBackground(Color.gray);

            if ((n % (maxLift * 2 + 1)) % 2 == 1) {
                ArrayList<Label> tempA;
                tempA = (ArrayList<Label>) EleList[((n % (maxLift * 2 + 1) - 1) / 2) % maxLift];
                tempA.add(0, temp);
                temp.setBackground(Color.LIGHT_GRAY);

                if (n > (maxFloor - 1) * (maxLift * 2 + 1)) {
                    temp.setBackground(Color.BLACK);        //set Lift's label
                    nowLiftFloor[((n % (maxLift * 2 + 1) - 1) / 2)] = 0;
                }
            }
            jPanelRight.add(temp);
        }
        this.setVisible(true);
    }

    public void setLiftFloor(int lift, int floor) {
        if (floor > this.maxFloor) {
            return;
        }

        ArrayList<Label> tempA;
        tempA = (ArrayList<Label>) EleList[lift];
        tempA.get(nowLiftFloor[lift]).setBackground(Color.LIGHT_GRAY);
        tempA.get(floor).setBackground(Color.BLACK);
        nowLiftFloor[lift] = floor;
        this.repaint();
    }

    public void upFloor(int lift) {
        if (nowLiftFloor[lift] != this.maxFloor) {
            setLiftFloor(lift, nowLiftFloor[lift] + 1);
        }

    }

    public void downFloor(int lift) {
        if (nowLiftFloor[lift] != this.maxFloor) {
            setLiftFloor(lift, nowLiftFloor[lift] - 1);
        }

    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        switch (cmd) {
            case "F2":
                setLiftFloor(2, nowLiftFloor[2] + 1);
                break;
            case "F1":

                setLiftFloor(0, 0);
                setLiftFloor(1, 0);
                setLiftFloor(2, 0);
                setLiftFloor(3, 0);
                break;
            default:
                setLiftFloor(1, nowLiftFloor[1] + 1);

        }
        Graphics g = getGraphics();
        paint(g);

    }

}