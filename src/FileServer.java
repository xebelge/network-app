import java.io.*;
import java.net.*;

public class FileServer implements Runnable {
    private ServerSocket serverSocket;

    public FileServer() throws IOException {
        serverSocket = new ServerSocket(8003);
    }

    public static void main(String[] args) throws IOException {
        Thread serverThread = new Thread(new FileServer());
        serverThread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket connectionSocket = serverSocket.accept();
                System.out.println("Client connected to File Server");

                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                String fileName = inFromClient.readLine();
                FileInputStream fileInputStream = new FileInputStream("./src/Download/" + fileName + ".txt");
                byte[] buffer = new byte[2000];
                fileInputStream.read(buffer, 0, buffer.length);
                OutputStream outputStream = connectionSocket.getOutputStream();
                outputStream.write(buffer, 0, buffer.length);

                connectionSocket.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
