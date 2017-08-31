import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server extends Thread {

    private ServerSocket serverSocket;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                DataInputStream stream = new DataInputStream(socket.getInputStream());
                receiveDiscord(stream);
                stream.close();

            } catch (IOException E) {
                E.printStackTrace();
            }
        }
    }

    private void receiveDiscord(DataInputStream stream) throws IOException {
        File m = new File("discordSW/discord.sql");
        if(!m.exists()) {
            m.getParentFile().mkdirs();
            m.createNewFile();
        }
        FileOutputStream g = new FileOutputStream(m);
        byte[] h = new byte[4096];

        int i = 99999; // Send file size in separate msg
        int j = 0;
        int k = 0;
        int l = i;
        while((j = stream.read(h, 0, Math.min(h.length, l))) > 0) {
            k += j;
            l -= j;
            System.out.println("read " + k + " bytes into " + m.getPath());
            g.write(h, 0, j);
        }

        g.close();
    }

    public static void main(String[] args) {
        Server server = new Server(1988);
        server.start();
    }

}