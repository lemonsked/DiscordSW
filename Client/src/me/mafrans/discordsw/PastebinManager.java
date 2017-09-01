package me.mafrans.discordsw;

import org.jpaste.exceptions.PasteException;
import org.jpaste.pastebin.PastebinLink;
import org.jpaste.pastebin.PastebinPaste;
import org.jpaste.pastebin.account.PastebinAccount;
import org.jpaste.pastebin.exceptions.LoginException;

/**
 * Created by malte on 2017-09-01.
 */
public class PastebinManager {
    private static String devKey;
    private static PastebinAccount account = new PastebinAccount();

    public static void signin(String username, String password) throws LoginException {
        account = new PastebinAccount(devKey, username, password);
        account.login();
    }

    public static void authenticateUser(String devKey, String username, String password) throws LoginException {
        setDevKey(devKey);
        account = new PastebinAccount(devKey, username, password);
        System.out.println("Logging in with " + account.getDeveloperKey() + ", " + account.getUsername() + ", " + account.getPassword());
        account.login();
    }

    public static void setDevKey(String devKey) {
        PastebinManager.devKey = devKey;
    }

    public static PastebinLink postPrivate(String title, String contents) throws PasteException {
        int visibility = PastebinPaste.VISIBILITY_PRIVATE; // makes paste private

        // create paste
        PastebinPaste paste = new PastebinPaste(account);
        paste.setContents(contents);
        paste.setPasteTitle(title);
        paste.setVisibility(visibility);

        // push paste
        PastebinLink link = paste.paste();

        return link;
    }
}
