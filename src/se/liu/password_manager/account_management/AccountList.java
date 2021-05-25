package se.liu.password_manager.account_management;

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

    public void addAccount(SecretKey key, String username, String password, AccountType accountType, String accountNumber, String email,
                           String domain)
            throws FileNotFoundException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidParameterSpecException
    {
        byte[] bytePassword = password.getBytes();
        Account account = null;
        switch (accountType) {
            case STANDARD -> account = new StandardAccount(username, bytePassword, key, accountType);
            case EMAIL -> account = new EmailAccount(username, email, domain, bytePassword, key, accountType);
            case BANK -> account = new BankAccount(username, accountNumber, bytePassword, key, accountType);
        }

        if (!encryptedAccounts.isEmpty()) {
            encryptedAccounts.add(0, account); //in order to add account to top of list.
        }
        else {
            encryptedAccounts.add(account); //if encryptedAccounts empty just add to list without index.
        }

        saveOnFile();
    }

    public void removeAccount(Account account) throws FileNotFoundException {
        encryptedAccounts.remove(account);
        saveOnFile();
    }

    public void saveOnFile() throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Account.class, new AccountAdapter());
        Gson gson = builder.setPrettyPrinting().create();

        try (PrintWriter printWriter = new PrintWriter(FILE_NAME)) {
            gson.toJson(this, printWriter);
        }
    }

    public Account getEncryptedAccount(int index) {
        if (encryptedAccounts.isEmpty()) {
            return null;
        }
        else {
            return encryptedAccounts.get(index);
        }

    }

    public void editAccount(Account account, SecretKey key, String newUsername, String newPassword, String newAccountNumber,
                            String newEmail, String newDomain)
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
        if (newAccountNumber != null) {
            ((BankAccount)account).editBankAccountNumber(newAccountNumber);
        }
        if (newEmail != null && newDomain != null) {
            ((EmailAccount)account).editEmail(newEmail);
            ((EmailAccount)account).editDomain(newDomain);
        }

        saveOnFile();
    }


    public DefaultListModel<String> returnListModel() { //returns compatible list format for JList.
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Account account : encryptedAccounts) {
            if (account.getAccountType().equals(AccountType.STANDARD) || account.getAccountType().equals(AccountType.BANK)) {
                listModel.addElement(account.getUsername());
            }
            else {
                listModel.addElement(((EmailAccount)account).getEmail());
            }
        }
        return listModel;
    }
}
