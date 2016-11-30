package ies;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Kitten on 30/11/2016.
 */
public class KioskClient {

    private static Socket clientSocket;
    private static DataInputStream in;
    private static DataOutputStream out;
    private String assignedLift;


    public KioskClient() {
        connect("0,0,2,4");

    }

    public void connect(String request){
        try {
            connectServer();
            send(request);
            assignedLift = receive();
            System.out.println(assignedLift);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
    }

    static void connectServer() throws IOException {
        clientSocket = new Socket(SharedConsts.ServerAddress, SharedConsts.ServerPort);
        System.out.printf("Connected to server using local port: %d.\n", clientSocket.getLocalPort());

        in = new DataInputStream(clientSocket.getInputStream());
        out = new DataOutputStream(clientSocket.getOutputStream());

    }

    public static void send(String sendMsg) throws IOException {
        AES aes = new AES(SharedConsts.Key);
        String msg, encrypt_msg, decrypt_msg;
        encrypt_msg = aes.encrypt(sendMsg);//format("InputType, currentFloor, destination/RFID, timestamp")


        out.writeInt(encrypt_msg.length());
        out.write(encrypt_msg.getBytes(), 0, encrypt_msg.length());
        out.flush();
    }

    public static String receive() throws IOException {
        AES aes = new AES(SharedConsts.Key);
        byte[] data = new byte[4];
        int size;
        int len;

        size = in.readInt();
        data = new byte[size];
        do {
            len = in.read(data, data.length - size, size);
            size -= len;
        } while (size > 0);

        return aes.decrypt(new String(data));
    }

    public static void disconnect() {
        System.out.println("sent");
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException ex) {
        }
    }








    public static void main(String args[]) {
        new KioskClient();
    }


}
