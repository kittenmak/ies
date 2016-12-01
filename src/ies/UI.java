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
        this.add(jPanelButton, br.SOUTH);
        jPanelButton.add(new Button("Floor " + (maxFloor - 3)));//TESTING

        JPanel jPanelLeft = new JPanel();
        jPanelLeft.setLayout((new GridLayout(maxFloor, 1)));
        this.add(jPanelLeft, br.WEST);

        JPanel jPanelRight = new JPanel();
        jPanelRight.setLayout((new GridLayout(maxFloor, (maxLift * 2 + 1))));
        this.add(jPanelRight, br.CENTER);

        for (int n = 0; n < maxFloor; n++) {

            Button tempB = new Button("F"+ (maxFloor - n));
            tempB.setActionCommand("F"+(maxFloor - n)+"");
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
                    System.out.println(((n % (maxLift * 2 + 1) - 1) / 2));
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
        String cmd = e.getActionCommand();
        if (cmd =="F2"){
            setLiftFloor(2, nowLiftFloor[2]+ 1 );
        }else
        if (cmd =="F3"){
            setLiftFloor(2, nowLiftFloor[2]- 1 );
        }
        else{

            setLiftFloor(1, nowLiftFloor[1]+ 1 );
        }

        Graphics g = getGraphics();
        paint(g);

    }

}
