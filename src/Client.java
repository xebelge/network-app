import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client implements Runnable {
    public static void main(String[] args) throws IOException {
        Thread c = new Thread(new Client());
        c.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                String sentence;
                String given_port;
                BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

                Socket clientSocket = new Socket("localhost", 8000);
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                System.out.println("1. TCP CONNECTION FOR DIRECTORY LISTING");
                System.out.println("2. TCP CONNECTION FOR GET FILE");
                System.out.println("3. TCP CONNECTION FOR COMPUTATION (KEEPS SERVER BUSY)");
                System.out.println("4. UDP CONNECTION FOR VIDEO STREAMING (KEEPS SERVER BUSY)");
                System.out.println("5. Exit");

                sentence = inFromUser.readLine();

                if (sentence.equals("1") || sentence.equals("2") || sentence.equals("3") || sentence.equals("4")) {
                    outToServer.writeBytes(sentence + '\n');
                    given_port = inFromServer.readLine();

                    if (sentence.equals("1")) {
                        System.out.println("FROM SERVER: " + given_port);
                        clientSocket = new Socket("localhost", Integer.parseInt(given_port));
                        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        given_port = inFromServer.readLine();
                        System.out.println("FROM SERVER: " + given_port);
                        clientSocket.close();
                    } else if (sentence.equals("2")) {
                        clientSocket = new Socket("localhost", Integer.parseInt(given_port));
                        DataOutputStream dao = new DataOutputStream(clientSocket.getOutputStream());
                        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
                        byte[] b = new byte[200002];
                        System.out.println("Enter File Name (or 'q' to go back to menu)");
                        String fileName = bf.readLine();
                        if (fileName.equals("q")) {
                            continue;
                        }
                        dao.writeBytes(fileName + '\n');

                        InputStream is = clientSocket.getInputStream();
                        FileOutputStream fis = new FileOutputStream("./src/Download/" + fileName + ".txt");
                        is.read(b, 0, b.length);
                        fis.write(b, 0, b.length);
                        clientSocket.close();
                    } else if (sentence.equals("3")) {
                        clientSocket = new Socket("localhost", Integer.parseInt(given_port));

                        DataOutputStream dao = new DataOutputStream(clientSocket.getOutputStream());
                        System.out.println("Enter Number (ms): ");
                        Scanner sc = new Scanner(System.in);
                        int x = sc.nextInt();
                        dao.writeInt(x);
                        clientSocket.close();
                    } else if (sentence.equals("4")) {
                        String hostname = "localhost";
                        int port = Integer.parseInt(given_port);
                        InetAddress address = InetAddress.getByName(hostname);
                        DatagramSocket socket = new DatagramSocket();
                        Scanner sc = new Scanner(System.in);
                        System.out.println("Enter Bit Rate (ms)");
                        String msg = sc.nextLine();
                        byte[] buf = msg.getBytes();
                        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
                        socket.send(packet);
                        clientSocket.close();
                    }
                } else if (sentence.equals("5")) {
                    System.out.println("Exiting...");
                    break;
                } else {
                    System.out.println("Invalid option! Please try again.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
