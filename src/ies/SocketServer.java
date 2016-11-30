package ies;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Kitten on 30/11/2016.
 */
public class SocketServer extends Thread{
    Socket clientSocket = null;
    DataInputStream in;
    DataOutputStream out;
    AES aes = new AES(SharedConsts.Key);

    public SocketServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        System.out.println("Established connection to client " +
                clientSocket.getInetAddress().getHostAddress() + ":" +
                clientSocket.getPort());

        try {
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
            process();
        } catch (Exception e) {
            System.out.println("Connection terminated!");
        } finally {
            disconnect();
        }
    }

    public void sendMsg(byte[] data, int len) throws IOException {
        out.writeInt(len);
        out.write(data, 0, len);
        out.flush();
    }


    public byte[] receive() throws IOException {
        byte[] data;
        int size;
        int len;

        // TODO: TASK 4 - Receive data and store them in the byte array

        // get the size of the message
        size = in.readInt();

        // receive the message content
        data = new byte[size];
        do {
            len = in.read(data, data.length - size, size);
            size -= len;
        } while (size > 0);

        return data;
    }

    private void process() throws IOException {
        String send_msg;
        while (true) {
            String line = aes.decrypt(new String(receive()));
            if (line.equals("QUIT")) {
                break;
            }

            System.out.println("Client: " + line);
            send_msg = aes.encrypt(line);
            sendMsg(send_msg.getBytes(), send_msg.length());

            send_msg = aes.encrypt("QUIT");
            sendMsg(send_msg.getBytes(), send_msg.length());
        }
    }


    private void disconnect() {
        System.out.println("disconnected.");
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException ex) {
        }
    }


}
