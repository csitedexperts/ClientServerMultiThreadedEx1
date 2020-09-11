package socketmulticlientdemo;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws IOException {
        try {

            // getting localhost ip
            InetAddress ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5678
            Socket s = new Socket(ip, 5678);

            // obtaining input and out streams
            ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream dis = new ObjectInputStream(s.getInputStream());

            // the following loop performs the exchange of
            // information between client and client handler
            while (true) {
                Object obj = dis.readObject();
                MessageObject msg = (MessageObject) obj;
                System.out.println(msg);
                if (msg.message.equals("Exit")) {
                    break;
                }
            }

            // closing resources
            dis.close();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
