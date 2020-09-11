/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketmulticlientdemo;

// ClientHandler class
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

class ClientHandler extends Thread {

    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
    ObjectInputStream dis;
    ObjectOutputStream dos;
    final Socket socket;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.dis = new ObjectInputStream(socket.getInputStream());
        this.dos = new ObjectOutputStream(socket.getOutputStream());
    }

    public void sendMessage(MessageObject msg) {
        try {
            if (socket.isClosed()) {
                Server.clients.remove(this);
                return;
            }
            dos.writeObject(msg);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        Server.avisaTodos();
        while (true) {
            try {
                Object obj = dis.readObject();
                if (obj instanceof MessageObject) {
                    MessageObject msg = (MessageObject) obj;
                    System.out.println("RECEIVED: " + msg.message);
                    if (msg.message.equals("Exit")) {
                        System.out.println("Client " + this.socket + " sends exit...");
                        System.out.println("Closing this connection.");
                        this.socket.close();
                        System.out.println("Connection closed");
                        break;
                    }
                } else {
                    System.out.println(obj.toString());
                }
            } catch (SocketException se) {
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Server.clients.remove(this);
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
