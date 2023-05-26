import java.io.*;
import java.net.*;

public class LoadBalancer implements Runnable{
    String clientSentence;
    String capitalizedSentence;
    ServerSocket welcomeSocket;

    public LoadBalancer()throws Exception{
        welcomeSocket = new ServerSocket(6780);
    }

    public static void main(String[] args) throws Exception{
        Thread server = new Thread(new LoadBalancer());
        server.start();
    }
    @Override
    public void run() {
        while (true){
            try {
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("Client Connected LOAD Balancer");
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream  outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                clientSentence = inFromClient.readLine();

                if(clientSentence.equals("1")){
                    System.out.println("DIRECTORY LISTING");
                    capitalizedSentence = "1112";
                }else if(clientSentence.equals("2")){
                    System.out.println("FILE TRANSFER");
                    capitalizedSentence = "2223";
                }else if(clientSentence.equals("3")) {
                    System.out.println("CALCULATION");
                    capitalizedSentence = "3334";
                }else if(clientSentence.equals("4")) {
                    System.out.println("VIDEO");
                    capitalizedSentence = "4445";
                }else{
                    System.out.println("exit");
                    capitalizedSentence = "BB";
                }
                outToClient.writeBytes(capitalizedSentence);
                connectionSocket.close();

            }catch (Exception e){}
        }

    }
}