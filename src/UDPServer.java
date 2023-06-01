import java.net.*;

public class UDPServer implements Runnable {
    public static void main(String[] args) {
        Thread server = new Thread(new UDPServer());
        server.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                DatagramSocket serverSocket = new DatagramSocket(8004);

                byte[] buf = new byte[256];
                DatagramPacket p = new DatagramPacket(buf, buf.length);
                serverSocket.receive(p);
                String q = new String(p.getData(), p.getOffset(), p.getLength());
                int k = Integer.parseInt(q);

                try {
                    Thread.sleep(k);
                    System.out.println("Video Streaming completed");
                    serverSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
