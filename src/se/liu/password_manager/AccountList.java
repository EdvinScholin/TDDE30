package se.liu.password_manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains a list of accounts and can manipulate this list by adding and removing an account.
 * The class is also able to save the list on a json-file and edit accounts with the help of methods in Account.
 */

public class AccountList
{
    private List<Account> listOfEncryptedAccounts = new ArrayList<>();
    private static final String FILE_NAME = "." + File.separator + "EncryptedAccounts.json";

    public void addAccount(Key key, String username, String password)
            throws FileNotFoundException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidKeyException
    {
        System.out.println(key);
        byte[] bytePassword = password.getBytes();
        Account account = new Account(username, bytePassword, key);
        listOfEncryptedAccounts.add(account);
        saveOnFile();
    }

    public void removeAccount(Account account) throws FileNotFoundException {
        listOfEncryptedAccounts.remove(account);
        saveOnFile();
    }

    public void saveOnFile() throws FileNotFoundException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (PrintWriter printWriter = new PrintWriter(FILE_NAME)) {
            gson.toJson(this, printWriter);
        }
    }

    public Account getEncryptedAccount(int index) {
        return listOfEncryptedAccounts.get(index);
    }

    public void editAccount(Account account, Key key, String newUsername, String newPassword)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, FileNotFoundException, NoSuchPaddingException,
            NoSuchAlgorithmException
    {
        byte[] bytePassword = newPassword.getBytes();

        if (newUsername == null && newPassword != null) {
            account.editPassword(bytePassword, key);
            saveOnFile();
        }
        if (newUsername != null && newPassword == null) {
            account.editUsername(newUsername);
            saveOnFile();
        }
        if (newUsername != null && newPassword != null) {
            account.editUsername(newUsername);
            account.editPassword(bytePassword, key);
            saveOnFile();
        }
    }

    public DefaultListModel<String> returnListModel() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Account acc : listOfEncryptedAccounts) {
            listModel.addElement(acc.getUsername());
        }
        return listModel;
        /*
        for (int i = 0; i <20 ; i++) {
            listModel.addElement("Account" + i);
        }
        return listModel;

         */
    }
}
