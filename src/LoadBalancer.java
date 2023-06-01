import java.io.*;
import java.net.*;

public class LoadBalancer implements Runnable {
    String clientSentence;
    String capitalizedSentence;
    ServerSocket welcomeSocket;

    public LoadBalancer() throws Exception {
        welcomeSocket = new ServerSocket(8000);
    }

    public static void main(String[] args) throws Exception {
        Thread server = new Thread(new LoadBalancer());
        server.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("Client Connected to Load Balancer");

                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                clientSentence = inFromClient.readLine();

                if (clientSentence.equals("1")) {
                    System.out.println("Directory Listing");
                    capitalizedSentence = "8001";
                } else if (clientSentence.equals("2")) {
                    System.out.println("File Transfer");
                    capitalizedSentence = "8003";
                } else if (clientSentence.equals("3")) {
                    System.out.println("Computation");
                    capitalizedSentence = "8002";
                } else if (clientSentence.equals("4")) {
                    System.out.println("Video Streaming");
                    capitalizedSentence = "8004";
                } else {
                    System.out.println("Exit");
                    capitalizedSentence = "exit";
                }

                outToClient.writeBytes(capitalizedSentence);
                connectionSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
