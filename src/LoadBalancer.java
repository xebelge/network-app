import java.io.*;
import java.net.*;

public class LoadBalancer implements Runnable {
    private ServerSocket welcomeSocket;

    public LoadBalancer() throws IOException {
        welcomeSocket = new ServerSocket(8000);
    }

    public static void main(String[] args) throws IOException {
        Thread serverThread = new Thread(new LoadBalancer());
        serverThread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("Client connected to Load Balancer");

                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                String clientRequest = inFromClient.readLine();

                String response;
                switch (clientRequest) {
                    case "1":
                        System.out.println("Directory Listing");
                        response = "8001";
                        break;
                    case "2":
                        System.out.println("File Transfer");
                        response = "8003";
                        break;
                    case "3":
                        System.out.println("Computation");
                        response = "8002";
                        break;
                    case "4":
                        System.out.println("Video Streaming");
                        response = "8004";
                        break;
                    default:
                        System.out.println("Exit");
                        response = "exit";
                        break;
                }

                outToClient.writeBytes(response);
                connectionSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
