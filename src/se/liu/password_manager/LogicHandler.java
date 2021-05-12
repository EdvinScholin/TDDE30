package se.liu.password_manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;


/**
 * This is a medium between the visual layer and the logical layer, that can give information
 * when the visual layer needs it.
 */
public class LogicHandler
{
    private static final String ACCOUNTS_FILE_NAME = "." + File.separator + "EncryptedAccounts.json";
    private static final String PASSWORD_FILE_NAME = "." + File.separator + "HashedPassword.txt";
    private AccountList accounts;
    private SecretKey key = null;
    private Decrypter decrypter = new Decrypter();

    public LogicHandler(String password) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.accounts = readJsonAccountList();
        try {
            KeyDeriver keyDeriver = new KeyDeriver();
            this.key = keyDeriver.deriveKey(password);
        }
        catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    private AccountList readJsonAccountList() {
        //Gson gson = new Gson();
        Gson gson = new GsonBuilder().registerTypeAdapter(Account.class, new InterfaceAdapter<Account>()).create();
        try (Reader reader = new FileReader(ACCOUNTS_FILE_NAME)) {
            return gson.fromJson(reader, AccountList.class);
        }
        catch (FileNotFoundException ignored) {
            return new AccountList();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;                                                // tryLoadJsonListAgain??
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

    public void doAccountAction(ButtonOption buttonOption, Account account, String newUsername, String newPassword, AccountType accountType)
            throws FileNotFoundException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidParameterSpecException
    {
        switch (buttonOption) {
            case ADD:
                accounts.addAccount(key, newUsername, newPassword, accountType);
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
        return new String(decrypter.decryptPassword(account.getPassword(), key, account.getInitVector()));
    }
}
