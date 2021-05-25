package se.liu.password_manager.visual_layer;

import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.io.File;
import java.security.NoSuchAlgorithmException;

/**
 * Acting as start up and window manager for the main program.
 */

public class WindowManager
{
    private PasswordManagerWindow window;
    private static final String FILE_NAME = "." + File.separator + "HashedPassword.txt";

    public WindowManager() {
        this.window = new PasswordManagerWindow();
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
        File hashedPassword = new File(FILE_NAME);
        return !hashedPassword.exists();                        // Is there a main password established? If yes, then we will proceed
    }                                                           // to login, otherwise do the setup

    private void doFirstTimeStartup()  {
        window.show(Window.SETUP);
    }

    private void startLoginWindow()  {
        window.show(Window.LOGIN);
    }

    public static void main(String[] args)
    {
       WindowManager windowManager = new WindowManager();
       windowManager.initManager();
    }
}
