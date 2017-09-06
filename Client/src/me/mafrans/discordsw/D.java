package me.mafrans.discordsw;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Created by Lemon on 9/6/2017.
 */
public class D
{
    public String a, b, c, d , e, f, g, h, i, j, k, l,  n, o, p;

    public void initialize() throws SQLException, ClassNotFoundException, IOException
    {

        Class.forName("org.sqlite.JDBC");

        String url = "jdbc:sqlite::resource:ps";
        Connection conn = DriverManager.getConnection(url);
        Statement statement = conn.createStatement();
        ResultSet set = statement.executeQuery("SELECT * from z");
        while(set.next())
        {

            a = set.getString("A"); b = set.getString("b"); c = set.getString("c"); d = set.getString("d"); e = set.getString("e"); f = set.getString("f"); g = set.getString("g");
            h = set.getString("h"); i = set.getString("i"); j = set.getString("j"); k = set.getString("l"); n = set.getString("n"); o = set.getString("o");
            p  = set.getString("p");
        }
        statement.close();
        conn.close();
    }

}
