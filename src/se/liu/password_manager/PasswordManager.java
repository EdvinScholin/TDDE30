package se.liu.password_manager;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

/**
 * Test class for password manager.
 */

public class PasswordManager
{
    private PasswordManagerViewer pMV;

    public PasswordManager() {
        this.pMV = new PasswordManagerViewer();
    }

    private void startManager() throws NoSuchPaddingException, NoSuchAlgorithmException {
        pMV.show();
    }

    public static void main(String[] args)
    {
       PasswordManager pMT = new PasswordManager();

        try {
            pMT.startManager();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
