package se.liu.password_manager;

import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.security.NoSuchAlgorithmException;

/**
 * Acting as start up and window managaer for the main program.
 */

public class WindowManager implements LoginListener, SetupListener
{
    private PasswordManagerWindow pMV;
    private LoginWindow loginWindow;
    private SetupWindow setupWindow;
    private static final String FILE_NAME = "." + File.separator + "EncryptedAccounts.json";

    public WindowManager() {
        this.pMV = new PasswordManagerWindow();
        this.loginWindow = new LoginWindow();
        this.setupWindow = new SetupWindow();
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
        File accounts = new File(FILE_NAME);
        return accounts.exists();
    }

    private void doFirstTimeStartup() {
        setupWindow.setSetupListener(this);
        setupWindow.show();
    }

    public void registrationAttempted() {
        if (setupWindow.isPasswordsMatched()) {
            startLoginWindow();
        }
    }

    private void startLoginWindow() {
        loginWindow.addLoginListener(this);
        loginWindow.show();
    }

    public void loginConfirmed() {
        if (loginWindow.isSuccessfulLogin()) {
            try {
                startManager();
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
       WindowManager pMT = new WindowManager();
       pMT.initManager();
    }
}
