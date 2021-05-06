package se.liu.password_manager;

import com.google.gson.Gson;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 * This is a medium between the visual layer and the logical layer, that can give information
 * when the visual layer needs it.
 */
public class LogicHandler
{
    private static final String ACCOUNTS_FILE_NAME = "." + File.separator + "EncryptedAccounts.json";
    private static final String PASSWORD_FILE_NAME = "." + File.separator + "HashedPassword.txt";
    private AccountList accounts;
    private KeyGen keyGen = new KeyGen();
    private Key key;
    private Decrypter decrypter = new Decrypter();

    public LogicHandler() throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.accounts = readJsonAccountList();
        this.key = getKeyFromMasterPassword();
    }

    private AccountList readJsonAccountList() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(ACCOUNTS_FILE_NAME)) {
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

    public void saveHashToFile(String password) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException,
            FileNotFoundException
    {
        HashEngine hashEngine = new HashEngine();
        try (FileOutputStream stream = new FileOutputStream(PASSWORD_FILE_NAME)) {
            stream.write(hashEngine.generateHash(password));
        }
    }

    public void doAccountAction(ButtonOption buttonOption, Account account, String newUsername, String newPassword)
            throws FileNotFoundException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidParameterSpecException
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

    public String getAccountPassword(Account account)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException
    {
        return new String(decrypter.cryptoPassword(account.getPassword(), key, account.getIv()));
    }
}
