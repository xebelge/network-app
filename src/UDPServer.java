import java.net.DatagramSocket;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;
import java.nio.ByteBuffer;
public class UDPServer implements Runnable{

    public static void main(String[] args) {
        Thread server = new Thread(new UDPServer());
        server.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                DatagramSocket server = new DatagramSocket(4445);

                byte[] buf = new byte[256];
                DatagramPacket p = new DatagramPacket(buf, buf.length);
                server.receive(p);
                String q = new String(p.getData(), p.getOffset(), p.getLength());
                int k = Integer.parseInt(q);

                try {
                    Thread.sleep(k);
                    System.out.println("Video End complated: ");
                    server.close();
                }catch (Exception e){}

            } catch (Exception e) {
            }
        }
    }
    }

