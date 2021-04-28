package se.liu.password_manager;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

/**
 * Acting as start up and window managaer for the main program.
 */

public class PasswordManager implements LoginListener
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
        loginWindow.addLoginListener(this);
        loginWindow.show();
    }

    public void loginAttempted() {
        if (loginWindow.isSuccessfulLogin()) {
            try {
                pMV.show();
            } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }



    public static void main(String[] args)
    {
       PasswordManager pMT = new PasswordManager();
       pMT.startLoginWindow();
    }
}
