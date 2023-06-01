import java.io.*;
import java.net.*;

public class TCPServer implements Runnable {
    ServerSocket serverSocket;
    String sentence;
    String clientSentence;

    public TCPServer() throws IOException {
        serverSocket = new ServerSocket(8001);
    }

    public static void main(String[] args) throws Exception {
        Thread listingServer = new Thread(new TCPServer());
        listingServer.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket connectionSocket = serverSocket.accept();
                System.out.println("Client Connected");

                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

                String[] pathnames;
                File f = new File("./src");
                pathnames = f.list();
                for (String pathname : pathnames) {
                    sentence = pathname + ", ";
                    outToClient.writeBytes(sentence);
                }

                connectionSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
