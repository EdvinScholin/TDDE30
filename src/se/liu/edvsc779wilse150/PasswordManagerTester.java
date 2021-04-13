package se.liu.edvsc779wilse150;

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
            throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, FileNotFoundException
    {
        PasswordManagerTester pMT = new PasswordManagerTester();
        KeyGen keyGen = new KeyGen();
        Key key = keyGen.generateKey();

        // Ska decrypter verkligen ta in ett password o key i konstruktorn? Detta ger isf att vi måste varje gång
        // skapa en ny decrypter, hade varit nice o inte skapa en ny hela tiden
        Decrypter decrypter = new Decrypter();

        Account account1 = new Account("WilmerS", "mupp", key);
        Account account2 = new Account("EdvinS", "balle", key);

        AccountList accountList = new AccountList();
        accountList.addAccount(account1);
        accountList.addAccount(account2);

        Account account = accountList.getEncryptedAccount(0);
        System.out.println(account.getUsername());
        System.out.println("Password: " + decrypter.decryptPassword(account.getPassword(), key));

        accountList.removeAccount(account1);

        accountList.editAccount(account2, key);
        System.out.println("Username" + account2.getUsername());
        System.out.println( "Password: " + decrypter.decryptPassword(account2.getPassword(), key));




        /*
        System.out.println("EncryptedAccount" + encryptedAccount1.getUserName() + "\n" + new String(encryptedAccount1.getEncryptedPassword()));
        Decrypter decrypter = new Decrypter(encryptedAccount1.getEncryptedPassword(), account1.getKey());
        System.out.println("Decrypted: " + decrypter.decryptPassword());
         */
    }
}
