package ies;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by michaelleung on 30/11/2016.
 */

//======================================================================
// Thread
public class SocketServer implements Runnable {
    Socket clientSocket = null;
    DataInputStream in;
    DataOutputStream out;

    protected String receivedMsg;
    protected String id;
    protected ControlPanel newControlPanelNew;
//    protected newMBox mbox = null;
//    protected Logger log = null;

    //------------------------------------------------------------
    // AppThread
    public SocketServer(Socket clientSocket, String id, ControlPanel ControlPanelNew) {
        this.clientSocket = clientSocket;
        this.id = id;
        this.newControlPanelNew = ControlPanelNew;
//        log = newControlPanel.getLogger();
//        mbox = new newMBox(id, log);
//        ControlPanelNew.regThread(this);
    } // AppThread


    //------------------------------------------------------------
    // getters
//    public newMBox getMBox() { return mbox; }
    public String getID() { return id; }

    public String getReceivedMsg(){
        return receivedMsg;
    }
    @Override
    public void run() {
        System.out.println("[SocketServer] Established connection to client " +
                clientSocket.getInetAddress().getHostAddress() + ":" +
                clientSocket.getPort());

        try {
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
            process();
        } catch (Exception e) {
            //System.out.println("[SocketServer] Connection terminated!");
        } finally {
            disconnect();
        }
    }

    public synchronized void sendMsg(String msg) throws IOException {
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

    private void process() throws IOException {
        String send_msg;
        while (true) {
            String line = receive();

            if (line.equals("QUIT")) {
                break;
            }
            //System.out.println("TN Client: " + line);
            receivedMsg = line;
            sendMsg(line);
            ControlPanel.regThread(this);
        }

//        send_msg = aes.encrypt("QUIT");
        send_msg = "QUIT";
        sendMsg(send_msg);
    }

    private void disconnect() {
        System.out.println("[SocketServer] disconnected.");
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException ex) {
        }
    }
} // AppThread


