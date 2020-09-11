package multiclients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Random;
import java.lang.Math;

public class Server {

    public static void main(String[] args) {
        ServerSocket sock;
        Socket client;
        BufferedReader from;
        PrintWriter to;


        String quote = "Thou art so fair!!!";

        Scanner kbd = new Scanner(System.in);

        try {
            sock = new ServerSocket(12346);
            System.out.println("Waiting for connection ...");
            client = sock.accept();
            System.out.println("Connected to " +
                    client.getInetAddress());
            from = new BufferedReader(
                    new InputStreamReader(
                            client.getInputStream()
                    )
            );

            to = new PrintWriter(client.getOutputStream(),
                    true);

            while (true) {

                String request = from.readLine();
                if (!request.equals(null)){
                    System.out.println("Received quote request from client ");
                    Random rand = new Random();
                    int rand1 = rand.nextInt(4) + 2;
                    int[] outputarray = new int[rand1+1];
                    String stringarray = "";
                    for (int i=0; i <=rand1; i++){
                        int rand2 = rand.nextInt(5000000) + 5000000;
                        stringarray = stringarray.concat(Integer.toString(rand2));
                        outputarray[i] = rand2;
                        if (i != rand1){
                            stringarray = stringarray.concat(",");
                        }
                    }
                    System.out.println("Sending: " + stringarray + ": to client");
                    to.println(stringarray);
                    String inputline = from.readLine();
                    //System.out.println(inputline);
                    System.out.println("Verifying factors");
                    String[] inputarray = inputline.split(",");
                    boolean Isvalid = true;
                    for (int i = 0; i < inputarray.length; i++){
                        if (outputarray[i] % Integer.parseInt(inputarray[i]) != 0){
                            Isvalid = false;

                        }
                    }
                    if (Isvalid) {
                        System.out.println("Sending \"Correct\"");
                        System.out.println("Sending quote: \"" + quote+"\"");
                        to.println(quote);
                    }




                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
