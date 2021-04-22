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

    public void addAccount(Key key)
            throws FileNotFoundException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidKeyException
    {
        System.out.println(key);
        String username = askUserAboutAccount("Username:");
        String password = askUserAboutAccount("Password:");
        Account account = new Account(username, password, key);
        listOfEncryptedAccounts.add(account);
        saveOnFile();
    }

    public void removeAccount(Account account) {
        listOfEncryptedAccounts.remove(account);
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

    public void editAccount(Account oldAccount, Key key)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, FileNotFoundException, NoSuchPaddingException,
            NoSuchAlgorithmException
    {
        String[] options = new String[] {"Edit password", "Edit username", "Edit both"};
        int response = JOptionPane.showOptionDialog(null, "What do you want to edit?", "Options",
                                                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                                    null, options, options[0]);
        if (response == 0) {
            String newPassword = askUserAboutAccount("What is your new password?");
            oldAccount.editPassword(newPassword, key);

            saveOnFile();
        }
        if (response == 1) {
            String newUsername = askUserAboutAccount("What is your new username?");
            oldAccount.editUsername(newUsername);

            saveOnFile();
        }
        if (response == 2) {
            String newUsername = askUserAboutAccount("What is your new username?");
            oldAccount.editUsername(newUsername);

            String newPassword = askUserAboutAccount("What is your new password?");
            oldAccount.editPassword(newPassword, key);

            saveOnFile();
        }
    }

    public DefaultListModel<String> returnListModel() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Account acc : listOfEncryptedAccounts) {
            listModel.addElement(acc.getUsername());
        }
        return listModel;
    }
}
