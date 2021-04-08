package se.liu.edvsc779wilse150;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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

    public void saveOnFile() throws FileNotFoundException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (PrintWriter printWriter = new PrintWriter(FILE_NAME)) {
            gson.toJson(this, printWriter);
        }
    }

    public Account getEncryptedAccount(int index) {
        return listOfEncryptedAccounts.get(index);
    }

    public Account editAccount(Account oldAccount){
        String[] options = new String[] {"Edit password", "Edit username", "Edit both"};
        int response = JOptionPane.showOptionDialog(null, "Message", "Title",
                                                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                                    null, options, options[0]);
        if (response == 0) {
            JOptionPane.showInputDialog(null, "What is your new password?");
            System.out.println("passw");
        }
        if (response == 1) {
            JOptionPane.showInputDialog(null, "What is your new username?");
            System.out.println("account");
        }
        if (response == 2) {
            JOptionPane.showInputDialog(null, "What is your new username?");
            JOptionPane.showInputDialog(null, "What is your new password?");
            System.out.println("both");
        }
        return null;

    }
}
