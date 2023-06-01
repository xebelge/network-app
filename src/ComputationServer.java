import java.io.*;
import java.net.*;

public class ComputationServer implements Runnable {
    String clientSentence;
    DataInputStream dot;
    ServerSocket welcomeSocket;

    public ComputationServer() throws Exception {
        welcomeSocket = new ServerSocket(8002);
    }

    public static void main(String[] args) throws Exception {
        Thread cServer = new Thread(new ComputationServer());
        cServer.start();
    }

    @Override
    public void run() {
        Socket connectionSocket = null;

        while (true) {
            try {
                connectionSocket = welcomeSocket.accept();
                dot = new DataInputStream(connectionSocket.getInputStream());
                System.out.println("Client Connected to Computation server");

                int x = dot.readInt();
                try {
                    Thread.sleep(x);
                    System.out.println("Computation completed");
                    connectionSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
