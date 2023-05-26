import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer implements Runnable{
    ServerSocket serverSocket;
    String fileName = null;
    public FileServer() throws IOException {
        serverSocket = new ServerSocket(2223);
    }

    public static void main(String[] args) throws Exception {
        Thread fileServer = new Thread(new FileServer());
        fileServer.start();
    }


    @Override
    public void run() {

        while (true){
            try {
        Socket connectionSocket = serverSocket.accept();
        System.out.println("Client Connected File Server.");
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());


            //fileName = inFromClient.readLine();
            //System.out.println(fileName);
            // FileInputStream fr = new FileInputStream("./src/"+fileName);
            FileInputStream fr = new FileInputStream("./src/Test.txt");
            byte [] b = new byte[2000];
            fr.read(b,0,b.length);
            OutputStream os =  connectionSocket.getOutputStream();
            os.write(b, 0, b.length);


        }catch (Exception e){}
    }

    }
}
