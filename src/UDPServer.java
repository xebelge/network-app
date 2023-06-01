import java.io.*;
import java.net.*;
public class UDPServer implements Runnable {
    public static void main(String[] args) {
        Thread serverThread = new Thread(new UDPServer());
        serverThread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                DatagramSocket serverSocket = new DatagramSocket(8004);

                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                serverSocket.receive(packet);
                String receivedData = new String(packet.getData(), packet.getOffset(), packet.getLength());
                int duration = Integer.parseInt(receivedData);

                try {
                    Thread.sleep(duration);
                    System.out.println("Video Streaming completed");
                    serverSocket.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
