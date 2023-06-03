import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client implements Runnable {
    public static void main(String[] args) throws IOException {
        Thread clientThread = new Thread(new Client());
        clientThread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

                Socket clientSocket = new Socket("localhost", 8000);
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                System.out.println("Write 1 for TCP Connection for Directory Listing");
                System.out.println("Write 2 for TCP Connection for Get File");
                System.out.println("Write 3 for TCP Connection for Computation (Keeps Server Busy)");
                System.out.println("Write 4 for UDP Connection for Video Streaming (Keeps Server Busy)");
                System.out.println("Write 5 for Exit");

                String userInput = inFromUser.readLine();

                if (userInput.equals("1") || userInput.equals("2") || userInput.equals("3") || userInput.equals("4")) {
                    outToServer.writeBytes(userInput + '\n');
                    String serverResponse = inFromServer.readLine();

                    if (userInput.equals("1")) {
                        System.out.println("From Server: " + serverResponse);
                        clientSocket = new Socket("localhost", Integer.parseInt(serverResponse));
                        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        serverResponse = inFromServer.readLine();
                        System.out.println("From Server: " + serverResponse);
                        clientSocket.close();
                    } else if (userInput.equals("2")) {
                        clientSocket = new Socket("localhost", Integer.parseInt(serverResponse));
                        DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                        byte[] buffer = new byte[200002];
                        System.out.println("Enter File Name or 'q' to go back to menu.");
                        String fileName = bufferedReader.readLine();
                        if (fileName.equals("q")) {
                            continue;
                        }
                        dataOutputStream.writeBytes(fileName + '\n');

                        InputStream inputStream = clientSocket.getInputStream();
                        FileOutputStream fileOutputStream = new FileOutputStream("./src/Download/" + fileName + ".txt");
                        inputStream.read(buffer, 0, buffer.length);
                        fileOutputStream.write(buffer, 0, buffer.length);
                        clientSocket.close();
                    } else if (userInput.equals("3")) {
                        clientSocket = new Socket("localhost", Integer.parseInt(serverResponse));

                        DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                        System.out.println("Enter Number (ms): ");
                        Scanner scanner = new Scanner(System.in);
                        int duration = scanner.nextInt();
                        dataOutputStream.writeInt(duration);
                        clientSocket.close();
                    } else if (userInput.equals("4")) {
                        String hostname = "localhost";
                        int port = Integer.parseInt(serverResponse);
                        InetAddress address = InetAddress.getByName(hostname);
                        DatagramSocket socket = new DatagramSocket();
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Enter Bit Rate (ms)");
                        String message = scanner.nextLine();
                        byte[] buffer = message.getBytes();
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
                        socket.send(packet);
                        clientSocket.close();
                    }
                } else if (userInput.equals("5")) {
                    System.out.println("Exiting...");
                    break;
                } else {
                    System.out.println("Invalid option! Please try again.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
