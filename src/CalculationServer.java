import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CalculationServer implements Runnable{
    String clientSentence;
    DataInputStream dot;
    ServerSocket welcomeSocket;
    public  CalculationServer() throws Exception{
        welcomeSocket = new ServerSocket(3334);

    }

    public static void main(String[] args) throws Exception{

        Thread cServer = new Thread(new CalculationServer());
        cServer.start();
    }


    @Override
    public void run() {
        Socket connectionSocket = null;

        while (true) {
            try {
                connectionSocket = welcomeSocket.accept();
                dot = new DataInputStream(connectionSocket.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Client Connected Calculation server");

                int x = dot.readInt();
                try {
                    Thread.sleep(x);
                    System.out.println("Calculation complated: ");
                    connectionSocket.close();
                }catch (Exception e){}



            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        }

    }
}
