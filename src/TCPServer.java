import java.io.*;
import java.net.*;

public class TCPServer implements Runnable {
    private ServerSocket serverSocket;

    public TCPServer() throws IOException {
        serverSocket = new ServerSocket(8001);
    }

    public static void main(String[] args) throws IOException {
        Thread serverThread = new Thread(new TCPServer());
        serverThread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket connectionSocket = serverSocket.accept();
                System.out.println("Client Connected");

                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                File directory = new File("./src");
                String[] filenames = directory.list();
                StringBuilder response = new StringBuilder();
                for (String filename : filenames) {
                    response.append(filename).append(", ");
                }

                outToClient.writeBytes(response.toString());

                connectionSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
