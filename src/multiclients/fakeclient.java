package multiclients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class fakeclient {

    public static void main(String[] args) {

        Socket sock;
        BufferedReader from;
        PrintWriter to;
        Scanner kbd = new Scanner(System.in);

        String ip = "10.70.20.65"; //TODO should get input from console



        try {
            sock = new Socket(ip, 12346);

            //client successfully connect to server
            System.out.println("Connected to " +
                    sock.getInetAddress()); //hello



            from = new BufferedReader(new InputStreamReader (sock.getInputStream()) //TODO should handle data coming from server
            );
            to = new PrintWriter(sock.getOutputStream(), //TODO should handle data going to server
                    true);



            while (true) {

                System.out.print("Press <Enter> to request a quote: ");
                kbd.nextLine().trim();
                System.out.println("Requesting quote");
                to.println(); //send random request to server, to let it know we want factors

                String numbers [] = from.readLine().split(",");  //read data sent from server //should receive a random list of primes of primes from server


                String listNum = Arrays.toString(numbers).replace("[","").replace("]","");


                String response = "Finding factors of "+listNum;
                System.out.println(response);



                while(true){
                    for (String number : numbers){
                        Scanner scanner = new Scanner(System.in);
                        String inputline = scanner.nextLine();

                        to.println(inputline);


                    }
                    String iscorrect = from.readLine();
                    if (iscorrect.equals("Correct")){
                        break;
                    }

                }

                String quote;
                quote = from.readLine(); //read quote from server
                System.out.println("Received Correct from Server");
                System.out.println("Received Quote: \""+quote+"\"");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}