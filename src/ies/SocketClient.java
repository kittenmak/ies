package ies;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by michaelleung on 1/12/2016.
 */
public class SocketClient implements Runnable{
    Socket clientSocket = null;
    DataInputStream in;
    DataOutputStream out;

    protected String id;
    protected String receivedMsg;
    protected Elevator elevator;

    public SocketClient(String id, Elevator elevator){
//        this.clientSocket = clientSocket;
        this.id = id;
        this.elevator = elevator;
    }

    public String getID() { return id; }

    public String getReceivedMsg(){
        return receivedMsg;
    }

    void connectServer() {
        try {
            clientSocket = new Socket(SharedConsts.ServerAddress, SharedConsts.ServerPort);
            System.out.printf("Lift Connected to server using local port: %d.\n", clientSocket.getLocalPort());
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
        System.out.println("Lift disconnected.");
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException ex) {
        }
    }

    @Override
    public void run() {
        connectServer();

        try {
            send("test send to cp");
            String msg = receive();
            System.out.print("sc msg = " + msg);
            Elevator.regThread(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
