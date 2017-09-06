import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {
    private static Socket socket;

    public static void main(String[] args) {
        try {

            int port = 25000;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server Started and listening to the port 25000");

            //Server is running always. This is done using this while(true) loop
            while(true) {
                //Reading the message from the client
                socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String input = br.readLine();
                Random rand = new Random();

                File outFile = new File("/discordSW/" + rand.nextInt(9999) + ".dump");
                if(!outFile.exists()) {
                    outFile.getParentFile().mkdirs();
                    outFile.createNewFile();
                }
                PrintWriter writer = new PrintWriter(outFile);
                writer.println(input.replace("||", "\n"));
                System.out.println(input.replace("||", "\n"));
                writer.close();
                System.out.println("\nDumped info to " + outFile.getAbsolutePath().toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return;
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e){}
        }
    }
}