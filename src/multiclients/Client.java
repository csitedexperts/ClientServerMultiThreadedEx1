package multiclients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        Socket sock;
        BufferedReader from;
        PrintWriter to;
        Scanner kbd = new Scanner(System.in);

        System.out.print("Enter IP address: ");
        String ip = kbd.nextLine().trim();

        Random d = new Random();

        try {
            System.out.println("Attempting connection to "+ip+"...Please Wait!" );

            sock = new Socket(ip, 12346);

            //client successfully connect to server
            System.out.println("Connected to " +
                    sock.getInetAddress()); //hello



            from = new BufferedReader(new InputStreamReader(sock.getInputStream()) //TODO should handle data coming from server
            );
            to = new PrintWriter(sock.getOutputStream(), //TODO should handle data going to server
                    true);



            while (true) {
                System.out.print("Press <Enter> to request a quote: ");
                String enter = kbd.nextLine().trim();
                System.out.println("Requesting Quote ...");

                to.println("Request"); //send request to server



                String quote =from.readLine();
                // System.out.println("Data from server "+quote);

                // String response = from.readLine(); TODO should get response from server

                //should receive a random number for server
                String numbers [] = quote.split(",");  //TODO list should come from server

                String listNum = Arrays.toString(numbers).replace("[","").replace("]","");

                String response = "Finding factors of "+listNum;
                System.out.println(response);


                //  System.out.print("Press Enter "+numbers.length+" factors: ");


                String[] guesss = factorGuess(numbers);

                // user should enter possible responses
                //  int guesss [] = {5,2,3,4}; //TODO should be a list of numbers entered by the user

                if (guesss.length != numbers.length){

                    //should give error that the numbers entered do not correspond to number requested
                    System.out.println("Error please enter "+numbers.length+" numbers");


                }else{
                    String listGuesses = "";

                    for (int i = 0; i < guesss.length;i++ ){

                        if (i == 0) {
                            listGuesses = String.valueOf(guesss[i]);
                        }else {
                            listGuesses = listGuesses + "," + guesss[i];
                        }
                    }

                    to.println(listGuesses);
                    System.out.println("Send factors to server: "+listGuesses); //TODO send the numbers to the server

                    //String quote = from.readLine(); //read quote from server
                    System.out.println("Received \"Correct\" from Server");
                    System.out.println("Received Quote: \""+from.readLine()+"\"");

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String[] factorGuess (String[] numbers){
        ArrayList <String> guesses = new ArrayList<String>();
        for (String n: numbers){
            BigInteger number = new BigInteger(n);
            for (BigInteger bi = BigInteger.valueOf(2); //start from 2
                 bi.compareTo(number) != 0; //stop when the numbers are equal
                 bi = bi.add(BigInteger.ONE)) {
                if (number.mod(bi) == BigInteger.ZERO){
                    // System.out.println("Factor found "+bi);
                    guesses.add(String.valueOf(bi));
                    break;
                }

                if (number.equals(bi)){ //we did not find factors so add itself as a factor

                    System.out.println("no factors found");
                    guesses.add(String.valueOf(bi));
                }
            }
        }
        String result[]=guesses.toArray(new String[guesses.size()]);
        return result ;
    }


}
