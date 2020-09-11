package multiclients;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Random;


public class MultiThreadedServer implements Runnable {
    Socket clientsocket;
    String quote = "Thou art so fair!!!";
    String ip;

    MultiThreadedServer(Socket clientsocket) {
        this.clientsocket = clientsocket;
    }
    public static void main(String args[]) throws Exception {
        ServerSocket sock = new ServerSocket(12346);
        System.out.println("Waiting for connection ...");

        while (true) {
            Socket client = sock.accept();
            new Thread(new MultiThreadedServer(client)).start();
        }
    }



    @Override
    public void run() {
        synchronized (this){
            try{
                System.out.println("Connected to " +
                        clientsocket.getInetAddress());
                ip = String.valueOf(clientsocket.getInetAddress());

                BufferedReader from = new BufferedReader(
                        new InputStreamReader(
                                clientsocket.getInputStream()
                        )
                );

                PrintWriter to = new PrintWriter(clientsocket.getOutputStream(),
                        true);

                while(true){

                    String request = from.readLine();
                    if (!request.equals(null)){
                        System.out.println(ip+": Received quote request from client ");
                        Random rand = new Random();

                        int rand1 = rand.nextInt(4) + 2;
                        String stringarray = "";
                        Long[] outputarray = new Long[rand1];

                        for (int i=0; i <rand1; i++){

                            BigInteger rand2 = this.num_generator();
                            stringarray = stringarray.concat(rand2.toString());
                            stringarray = stringarray.concat(",");
                            outputarray[i] = rand2.longValue();
                        }
                        System.out.println(ip+": Sending: " + stringarray + "to client");
                        to.println(stringarray);
                        while(true){
                            boolean iscorrect = true;

                            for (int i = 0; i < rand1; i++){
                                String inputline = from.readLine();
                                String[] inputlist = inputline.split(",");
                                long input = Long.parseLong(inputlist[0]);
                                int index = Integer.parseInt(inputlist[1])-1;
                                System.out.println(ip+": Verifying factor: " + input + "for: " + outputarray[index]);
                                if (outputarray[index] % input == 0){
                                    System.out.println("Correct!");
                                }
                                else{
                                    System.out.println(ip+": Incorrect, please enter factors again");
                                    iscorrect = false;
                                }
                            }
                            if (iscorrect){
                                System.out.println(ip+": Sending Correct!");
                                System.out.println(ip+": Sending Quote: "+"\""+this.quote+"\"");
                                //to.println("Correct");

                                to.println(this.quote);
                                break;
                            }
                            else{
                                to.println("incorrect");
                            }
                        }

                    }
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }

        }

    }

    public static BigInteger num_generator(){
        Random rand1 = new Random();

        BigInteger prime1 = BigInteger.probablePrime(31, rand1);
        BigInteger prime2 = BigInteger.probablePrime(31, rand1);
        BigInteger bigint = prime1.multiply(prime2);
        return bigint ;
    }
}