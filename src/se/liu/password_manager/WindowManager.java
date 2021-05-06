package se.liu.password_manager;

import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.security.NoSuchAlgorithmException;

/**
 * Acting as start up and window managaer for the main program.
 */

public class WindowManager
{
    private PasswordManagerWindow pMV;
    private static final String FILE_NAME = "." + File.separator + "HashedPassword.txt";

    public WindowManager() {
        this.pMV = new PasswordManagerWindow();
    }

    private void initManager() throws NoSuchPaddingException, NoSuchAlgorithmException {
        if (isFirstTimeStartup()) {
            doFirstTimeStartup();
        }
        else {
            startLoginWindow();
        }
    }

    private boolean isFirstTimeStartup() {
        File hashedPassword = new File(FILE_NAME);
        return !hashedPassword.exists();
    }

    private void doFirstTimeStartup() throws NoSuchPaddingException, NoSuchAlgorithmException {
        pMV.show(Window.SETUP);
    }

    private void startLoginWindow() throws NoSuchPaddingException, NoSuchAlgorithmException {
        pMV.show(Window.LOGIN);
    }

    public static void main(String[] args)
    {
       WindowManager windowManager = new WindowManager();
        try {
            windowManager.initManager();
        }
        catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
