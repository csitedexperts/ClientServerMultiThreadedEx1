/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketmulticlientdemo;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

// Server class
public class Server {

    public static final List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        // server is listening on port 5678
        ServerSocket serverSocket = new ServerSocket(5678);
        System.out.println("Server running on port 5678");

        // running infinite loop for getting
        // client request
        while (true) {
            Socket socket = null;

            try {
                // socket object to receive incoming client requests
                socket = serverSocket.accept();

                System.out.println("A new client is connected : " + socket);

                // obtaining input and out streams
                System.out.println("Assigning new thread for this client");

                // create a new thread object
                ClientHandler client = new ClientHandler(socket);
                clients.add(client);
                // Invoking the start() method
                client.start();
            } catch (Exception e) {
                socket.close();
                e.printStackTrace();
            }
        }
    }

    public static void avisaTodos() {
        for (ClientHandler c : clients) {
            c.sendMessage(new MessageObject("Miranha"));
        }
    }
}