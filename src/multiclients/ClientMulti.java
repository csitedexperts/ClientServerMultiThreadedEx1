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

public class ClientMulti {

    public static void main(String[] args) {

        Socket sock;
        BufferedReader from;
        PrintWriter to;
        Scanner kbd = new Scanner(System.in);

        System.out.print("Enter IP address: ");
        String ip = kbd.nextLine().trim();


        try {
            sock = new Socket(ip, 12346);

            //client successfully connect to server
            System.out.println("Attempting connection to " + ip + "...Please Wait!");

            System.out.println("Connected to " +
                    sock.getInetAddress()); //hello



            from = new BufferedReader(new InputStreamReader (sock.getInputStream()) //handles data coming from server
            );
            to = new PrintWriter(sock.getOutputStream(), // handle data going to server
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
                int threadnumber = 1;
                //start as many threads as there are numbers to be guessed
                for (String number : numbers){
                    Guess guess = new Guess(sock, number, threadnumber); //create object to compute the factors of the numbers provided by server, also used to synchronize

                    Thread t = new Thread(guess); //create new thread
                    t.start();
                    threadnumber ++;

                }

                String iscorrect = from.readLine(); //read quote from server
                if (iscorrect.equals("Correct")){
                    String quote = from.readLine();
                    System.out.println("Received Correct from Server");
                    System.out.println("Received Quote: \""+quote+"\"");
                }


            }




        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}