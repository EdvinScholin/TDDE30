package se.liu.password_manager;

import com.google.gson.Gson;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;


/**
 * This is a medium between the visual layer and the logical layer, that can give information
 * when the visual layer needs it.
 */
public class LogicHandler
{
    private static final String FILE_NAME = "." + File.separator + "EncryptedAccounts.json";
    private AccountList accounts;
    private KeyGen keyGen = new KeyGen();
    private Key key;
    private Decrypter decrypter = new Decrypter();

    public LogicHandler() throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.accounts = readJsonAccountList();
        this.key = getKeyFromMasterPassword();
    }

    private Key getKeyFromMasterPassword() {
        return keyGen.generateKey();
    }

    private AccountList readJsonAccountList() {
        //System.out.println(key);
        Gson gson = new Gson();
        try (Reader reader = new FileReader(FILE_NAME)) {
            return gson.fromJson(reader, AccountList.class);
        }
        catch (FileNotFoundException ignored) {
            return new AccountList();
        }
        catch (IOException ignored) {
            //return tryLoadAgain("Could not load HighScores, try again?");
            return new AccountList();
        }
    }

    public void doButtonAction(ButtonOption buttonOption, Account account, String newUsername, String newPassword)
            throws FileNotFoundException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidKeyException
    {
        switch (buttonOption) {
            case ADD:
                accounts.addAccount(key, newUsername, newPassword);
                break;
            case REMOVE:
                accounts.removeAccount(account);
                break;
            case EDIT:
                accounts.editAccount(account, key, newUsername, newPassword);
                break;
        }
    }

    public AccountList getAccounts() {
        return accounts;
    }
}
