package se.liu.password_manager;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

public class ButtonAction2
{
    /*
    private final ButtonOption button;

    public ButtonAction(final ButtonOption button) {
	this.button = button;
    }

    @Override public void actionPerformed(final ActionEvent e) {
	if (button == ButtonOption.ADD || button == ButtonOption.REMOVE || button == ButtonOption.EDIT) {
	    String newUsername = null;
	    String newPassword = null;
	    int removeAccount = 0;

	    if (button == ButtonOption.EDIT) {
		String[] options = new String[] { "Edit password", "Edit username", "Edit both" };
		int response = JOptionPane.showOptionDialog(null, "What do you want to edit?", "Options",
							    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
							    options, options[0]);
		if (response == 0) {
		    newPassword = askUserAboutAccount("What is your new password?");
		}
		if (response == 1) {
		    newUsername = askUserAboutAccount("What is your new username?");
		}
		if (response == 2) {
		    newUsername = askUserAboutAccount("What is your new username?");
		    newPassword = askUserAboutAccount("What is your new password?");
		}
	    }
	    else if (button == ButtonOption.ADD) {
		newUsername = askUserAboutAccount("Username:");
		newPassword = askUserAboutAccount("Password:");
	    }
	    else {
		removeAccount = JOptionPane.showConfirmDialog(frame, "Are you sure you want to remove this account?");
	    }

	    try {
		if (removeAccount == 0) {
		    logicHandler.doAccountAction(button, getSelectedAccount(), newUsername, newPassword);
		}
		removeAccount = 0;
	    }
	    catch (FileNotFoundException | IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException | NoSuchAlgorithmException | InvalidKeyException | InvalidParameterSpecException exception) {
		exception.printStackTrace();
	    }

	    jList.setModel(logicHandler.getAccounts().returnListModel());
	}

	else if (button == ButtonOption.LOGIN) {
	    if (login.authenticateLogin(new String(loginPasswordField.getPassword()))) {
		frame.dispose();
		try {
		    show(Window.PASSWORD_MANAGER);
		}
		catch (NoSuchPaddingException | NoSuchAlgorithmException noSuchPaddingException) {
		    noSuchPaddingException.printStackTrace();
		}
	    }
	    else {
		JOptionPane.showMessageDialog(frame, "Invalid password");
		loginPasswordField.setText("");
	    }
	}

	else if (button == ButtonOption.CONTINUE) {
	    if (Arrays.equals(setupPasswordField2.getPassword(), setupPasswordField1.getPassword())) {
		frame.dispose();
		try {
		    logicHandler = new LogicHandler(new String(setupPasswordField1.getPassword()));
		    logicHandler.saveHashToFile(new String(setupPasswordField1.getPassword()));
		    String newUsername = askUserAboutAccount("Username:");
		    String newPassword = askUserAboutAccount("Password:");
		    logicHandler.doAccountAction(ButtonOption.ADD, null, newUsername, newPassword);
		    show(Window.PASSWORD_MANAGER);

		} catch (NoSuchPaddingException | NoSuchAlgorithmException | IOException | InvalidKeySpecException |
			IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidParameterSpecException
			noSuchPaddingException) {
		    noSuchPaddingException.printStackTrace();
		}

	    }
	    else {
		JOptionPane.showMessageDialog(frame, "Passwords do not match");
		setupPasswordField2.setText("");
	    }

	}

	else {
	    frame.dispose();
	}
    }

     */
}
