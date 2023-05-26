import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client implements Runnable{
    public static void main(String[] args) throws IOException {
        Thread c = new Thread(new Client());
        c.start();
    }

    @Override
    public void run() {
        while (true){
        try {
        String sentence;
        String given_port;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            //Connect LOAD BALANCER
        Socket clientSocket = new Socket("localhost", 6780);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // CHOSE REQUEST
            System.out.println("1. TCP CONNECTION FOR DIRECTORY LISTING");
            System.out.println("2. TCP CONNECTION FOR GET FILE");
            System.out.println("3. TCP CONNECTION FOR CALCULATION (KEEPS SERVER BUSY)");
            System.out.println("4. UDP CONNECTION FOR VIDEO STREAMING (KEEPS SERVER BUSY)");
            //SENT REQUEST TYPE TO LOAD BALANCER
            sentence = inFromUser.readLine();
            outToServer.writeBytes(sentence + '\n');
            //GET RESPONSE PORT
            given_port = inFromServer.readLine();
            //DIRECTORY LISTING
            if(sentence.equals("1")) {
                System.out.println("FROM SERVER: " + given_port);
                clientSocket = new Socket("localhost", Integer.parseInt(given_port));
                inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                given_port = inFromServer.readLine();
                System.out.println("FROM SERVER: " + given_port);
                clientSocket.close();

            //GETTING FILE
            } else if (sentence.equals("2")) {
                clientSocket = new Socket("localhost", Integer.parseInt(given_port));
                DataOutputStream dao = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
                byte[] b = new byte[200002];
                System.out.println("Enter File Name");
                Scanner q = new Scanner(System.in);
                String fileName = bf.readLine();
                dao.writeBytes(fileName);

                InputStream is = clientSocket.getInputStream();
                FileOutputStream fis = new FileOutputStream("./src/Download/"+fileName+".txt");
                is.read(b, 0, b.length);
                fis.write(b, 0,  b.length);
            }else if(sentence.equals("3")){

                clientSocket = new Socket("localhost", Integer.parseInt(given_port));

                DataOutputStream dao = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Enter Number (ms): ");
                Scanner sc = new Scanner(System.in);
                int x = sc.nextInt();
                dao.writeInt(x);
                clientSocket.close();
            }else if(sentence.equals("4")){

                String hostname = "localhost";
                int port = Integer.parseInt(given_port);
                InetAddress address = InetAddress.getByName(hostname);
                DatagramSocket socket = new DatagramSocket();
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter Bit Rate (ms)");
                String msg = sc.nextLine();
                byte[] buf = msg.getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length,address,port);
                socket.send(packet);
               // socket.close();
            }

            clientSocket.close();
    }catch (Exception e){}
    }
    }
}