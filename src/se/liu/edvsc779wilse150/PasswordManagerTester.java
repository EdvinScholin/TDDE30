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
import java.security.NoSuchAlgorithmException;

public class PasswordManagerTester
{
    private String FILE_NAME = "./EncryptedAccounts";


    private AccountList readJsonHighScoreList() {
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
	    throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        Account account = new Account("WilmerS", "mupp");
        EncryptedAccount encryptedAccount = new EncryptedAccount(account);
        System.out.println(encryptedAccount.getUserName() + "\n" + new String(encryptedAccount.getEncryptedPassword()));
        Decrypter decrypter = new Decrypter(encryptedAccount.getEncryptedPassword(), account.getKey());
        System.out.println("Decrypted: " + decrypter.decryptPassword());
    }
}
