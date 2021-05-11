package se.liu.password_manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains a list of accounts and can manipulate this list by adding and removing an account.
 * The class is also able to save the list on a json-file and edit accounts with the help of methods in Account.
 */

public class AccountList
{
    private List<Account> encryptedAccounts = new ArrayList<>();
    private static final String FILE_NAME = "." + File.separator + "EncryptedAccounts.json";

    public void addAccount(SecretKey key, String username, String password)
            throws FileNotFoundException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidParameterSpecException
    {
        byte[] bytePassword = password.getBytes();
        Account account = new StandardAccount(username, bytePassword, key);  // HUr fan ska vi göra?

        encryptedAccounts.add(0, account);
        saveOnFile();
    }

    public void removeAccount(Account account) throws FileNotFoundException {
        encryptedAccounts.remove(account);
        saveOnFile();
    }

    public void saveOnFile() throws FileNotFoundException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (PrintWriter printWriter = new PrintWriter(FILE_NAME)) {
            gson.toJson(this, printWriter);
        }
    }

    public StandardAccount getEncryptedAccount(int index) {
        return encryptedAccounts.get(index);
    }

    public void editAccount(StandardAccount account, SecretKey key, String newUsername, String newPassword)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, FileNotFoundException, NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidParameterSpecException
    {
        if (newPassword != null) {
            byte[] bytePassword = newPassword.getBytes();
            account.editPassword(bytePassword, key);
        }
        if (newUsername != null) {
            account.editUsername(newUsername);
        }

        saveOnFile();
    }

    public DefaultListModel<String> returnListModel() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (StandardAccount account : encryptedAccounts) {
            listModel.addElement(account.getUsername());
        }
        return listModel;
    }
}
