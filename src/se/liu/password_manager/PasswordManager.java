package se.liu.password_manager;

import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.security.NoSuchAlgorithmException;

/**
 * Acting as start up and window managaer for the main program.
 */

public class PasswordManager implements LoginListener, WelcomeListener
{
    private PasswordManagerViewer pMV;
    private LoginWindow loginWindow;
    private WelcomeWindow welcomeWindow;
    private static final String FILE_NAME = "." + File.separator + "EncryptedAccounts.json";

    public PasswordManager() {
        this.pMV = new PasswordManagerViewer();
        this.loginWindow = new LoginWindow();
        this.welcomeWindow = new WelcomeWindow();
    }

    private void initManager() {
        if (isFirstTimeStartup()) {
            doFirstTimeStartup();
        }
        else {
            startLoginWindow();
        }
    }

    private boolean isFirstTimeStartup() {
        File acclist = new File(FILE_NAME);
        return acclist.exists();
    }

    private void doFirstTimeStartup() {
        welcomeWindow.setWelcomeListener(this);
        welcomeWindow.show();
    }

    public void registrationAttempted() {
        if (welcomeWindow.isPasswordsMatched()) {
            startLoginWindow();
        }
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

    private void startManager() throws NoSuchPaddingException, NoSuchAlgorithmException {
        pMV.show();
    }


    public static void main(String[] args)
    {
       PasswordManager pMT = new PasswordManager();
       pMT.initManager();
        //pMT.startLoginWindow();
        //System.out.println(pMT.isFirstTimeStartup());
    }
}
