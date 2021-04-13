package se.liu.edvsc779wilse150;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class AccountList
{
    private List<Account> listOfEncryptedAccounts = new ArrayList<>();
    private static final String FILE_NAME = "./EncryptedAccounts.json";

    public void addAccount(Account account) throws FileNotFoundException {
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
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, FileNotFoundException
    {
        String[] options = new String[] {"Edit password", "Edit username", "Edit both"};
        int response = JOptionPane.showOptionDialog(null, "What do you want to edit?", "Options",
                                                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                                    null, options, options[0]);
        if (response == 0) {
            String newPassword = JOptionPane.showInputDialog(null, "What is your new password?");
            oldAccount.editPassword(newPassword, key);

            saveOnFile();
        }
        if (response == 1) {
            String newUsername = JOptionPane.showInputDialog(null, "What is your new username?");
            oldAccount.editUsername(newUsername);

            saveOnFile();
        }
        if (response == 2) {
            String newUsername = JOptionPane.showInputDialog(null, "What is your new username?");
            oldAccount.editUsername(newUsername);

            String newPassword = JOptionPane.showInputDialog(null, "What is your new password?");
            oldAccount.editPassword(newPassword, key);

            saveOnFile();
        }
    }
}
