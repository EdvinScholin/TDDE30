package se.liu.password_manager;

import com.google.gson.Gson;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
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

public class PasswordManager
{
    private PasswordManagerViewer pMV;

    public PasswordManager() throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.pMV = new PasswordManagerViewer();
    }

    private void startManager() throws NoSuchPaddingException, NoSuchAlgorithmException {
        pMV.show();
    }

    public static void main(String[] args)
    {
        PasswordManager pMT = null;
        try {
            pMT = new PasswordManager();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            pMT.startManager();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
