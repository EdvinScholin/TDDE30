package se.liu.password_manager;

import com.google.gson.Gson;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Test class for password manager.
 */

public class PasswordManagerTester
{
    private static final String FILE_NAME = "./EncryptedAccounts.json";

    private AccountList readJsonAccountList() {
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

    public static void main(String[] args)
    {
        PasswordManagerTester pMT = new PasswordManagerTester();

        KeyGen keyGen = null;
        try {
            keyGen = new KeyGen();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Key key = keyGen.generateKey();

        // Ska decrypter verkligen ta in ett password o key i konstruktorn? Detta ger isf att vi måste varje gång
        // skapa en ny decrypter, hade varit nice o inte skapa en ny hela tiden

        Decrypter decrypter = null;
        try {
            decrypter = new Decrypter();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Account account1 = null;
        Account account2 = null;
        try {
            account1 = new Account("WilmerS", "mupp", key);
            account2 = new Account("EdvinS", "balle", key);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException |
                InvalidKeyException e) {
            e.printStackTrace();
        }

        AccountList accountList = new AccountList();
        try {
            accountList.addAccount(account1);
            accountList.addAccount(account2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Account account = accountList.getEncryptedAccount(0);
        System.out.println(account.getUsername());
        try {
            System.out.println("Password: " + decrypter.decryptPassword(account.getPassword(), key));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        accountList.removeAccount(account1);

        try {
            accountList.editAccount(account2, key);
        } catch (IllegalBlockSizeException | FileNotFoundException | InvalidKeyException | BadPaddingException | NoSuchPaddingException |
                NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        System.out.println("Username" + account2.getUsername());

        try {
            System.out.println( "Password: " + decrypter.decryptPassword(account2.getPassword(), key));
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }


        /*
        System.out.println("EncryptedAccount" + encryptedAccount1.getUserName() + "\n" + new String(encryptedAccount1.getEncryptedPassword()));
        Decrypter decrypter = new Decrypter(encryptedAccount1.getEncryptedPassword(), account1.getKey());
        System.out.println("Decrypted: " + decrypter.decryptPassword());
         */
    }
}
