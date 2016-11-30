package ies;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by michaelleung on 30/11/2016.
 */
public class KioskClient {
    private Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;
    private ArrayList<String> stringList = new ArrayList<String>();

    public KioskClient(){
        connectServer();
        String msg = "haha2";
        try {
            send(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String data = null;
        try {
            data = receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stringList.add(data);

    }

    void connectServer(){
        try {
            clientSocket = new Socket(SharedConsts.ServerAddress, SharedConsts.ServerPort);
            System.out.printf("KC Connected to server using local port: %d.\n", clientSocket.getLocalPort());
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
        System.out.println("KC disconnected.");
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException ex) {
        }
    }

    public static void main(String args[]){
        KioskClient kioskClient = new KioskClient();
    } // end of main
}
