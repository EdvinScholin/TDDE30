package se.liu.password_manager;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

/**
 * Test class for password manager.
 */

public class PasswordManager
{
    private PasswordManagerViewer pMV;
    private LoginWindow loginWindow;

    public PasswordManager() {
        this.pMV = new PasswordManagerViewer();
        this.loginWindow = new LoginWindow();
    }

    private void startManager() throws NoSuchPaddingException, NoSuchAlgorithmException {
        pMV.show();
    }

    private void startLoginWindow() {
        loginWindow.show();
    }

    public static void main(String[] args)
    {

       PasswordManager pMT = new PasswordManager();
        /*
        try {
            pMT.startManager();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        */

        pMT.startLoginWindow();
    }
}
