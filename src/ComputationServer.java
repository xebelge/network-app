import java.io.*;
import java.net.*;

public class ComputationServer implements Runnable {
    private ServerSocket serverSocket;

    public ComputationServer() throws IOException {
        serverSocket = new ServerSocket(8002);
    }

    public static void main(String[] args) throws IOException {
        Thread serverThread = new Thread(new ComputationServer());
        serverThread.start();
    }

    @Override
    public void run() {
        Socket connectionSocket;

        while (true) {
            try {
                connectionSocket = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(connectionSocket.getInputStream());
                System.out.println("Client connected to Computation server");

                int duration = dataInputStream.readInt();
                try {
                    Thread.sleep(duration);
                    System.out.println("Computation completed");
                    connectionSocket.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
