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
 * This is a medium between the visual layer and the info
 */
public class LogicHandler
{
    private static final String FILE_NAME = "." + File.separator + "EncryptedAccounts.json";
    private AccountList accountList;
    private KeyGen keyGen = new KeyGen();
    private Key key;
    private Encrypter encrypter = new Encrypter();
    private Decrypter decrypter = new Decrypter();

    public LogicHandler() throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.accountList = readJsonAccountList();
        this.key = keyFromMasterPassword();
    }

    private Key keyFromMasterPassword() {
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

    public void buttonAction(ButtonOption buttonOption, Account account)
            throws FileNotFoundException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidKeyException
    {
        switch (buttonOption) {
            case ADD:
                accountList.addAccount(key);
                break;
            case REMOVE:
                accountList.removeAccount(account);
                break;
            case EDIT:
                accountList.editAccount(account, key);
                break;
        }
    }

    public AccountList getAccountList() {
        return accountList;
    }
}
