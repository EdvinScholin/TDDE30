package se.liu.password_manager.visual_layer;

import net.miginfocom.swing.MigLayout;
import se.liu.password_manager.account_management.Account;
import se.liu.password_manager.account_management.AccountType;
import se.liu.password_manager.account_management.BankAccount;
import se.liu.password_manager.account_management.EmailAccount;
import se.liu.password_manager.logic_medium.LogicHandler;
import se.liu.password_manager.logic_medium.LoginManager;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

/**
 * A visual class for the main program. This class has inner action classes that help
 * PasswordMangagerWindow when something happens in the frame. The class contains all components of the frame
 * and also a pointer to the middle man between the logic layer and the visual layer.
 */

public class PasswordManagerWindow
{
    private JFrame frame = null;
    private LogicHandler logicHandler = null;
    private LoginManager loginManager = null;
    private int selectedIndex = 0;
    private JList<String> accounts = null;
    private JLabel label = null;
    private JPasswordField loginPasswordField = null;
    private JPasswordField setupPasswordField1 = null, setupPasswordField2 = null;
    private JButton buttonAdd = null, buttonRemove = null, buttonEdit = null, buttonQuit = null, buttonLogin = null, continueButton = null;
    private static final int LOCATIONX = 710;
    private static final int LOCATIONY = 290;
    private static final String SETUP_FILE_NAME = "resources" + File.separator + "images" + File.separator + "setup_pic.png";

    public void show(Window window) {
        initFrame(window);

        if (window == Window.LOGIN) {
            loginManager = new LoginManager();
            initPasswordField(window);
        }

        else if (window == Window.SETUP) {
            initLogo();
            initText();
            initPasswordField(window);
        }

        addButtons(window);                             // The order of when the components is initialized is
                                                        // important for the purpose of the order in which
        if (window == Window.PASSWORD_MANAGER) {        // they appear in the window.
            if (logicHandler == null) {
                try {
                    logicHandler = new LogicHandler(new String(loginPasswordField.getPassword()));
                } catch (IOException | NoSuchPaddingException | InvalidKeySpecException | NoSuchAlgorithmException ioException) {
                    ioException.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Logical error, either with file reading or encryption",
                                                  "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }

            setJList();

            try {
                initLabel();
            } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
        }

        addListeners(window);

        frame.pack();
        frame.setVisible(true);
    }

    private void initFrame(Window window) {
        MigLayout migLayout = null;

        if (window == Window.PASSWORD_MANAGER) {
            frame = new JFrame("Password Manager");
            migLayout = new MigLayout("", "[grow][grow][grow]", "[][grow]");
        }

        if (window == Window.LOGIN) {
            frame = new JFrame("Login to PasswordManager");
            migLayout = new MigLayout("", "[grow][grow]", "[][]");
        }

        if (window == Window.SETUP) {
            frame = new JFrame("Welcome!");
            migLayout = new MigLayout("", "[][][][]", "[][][]");
        }

        frame.setLayout(migLayout);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLocation(LOCATIONX, LOCATIONY);
    }

    private void setJList() {
        accounts = new JList<>(logicHandler.getAccounts().returnListModel());
        JScrollPane jScrollPane = new JScrollPane(accounts);
        frame.add(jScrollPane, "span 2, grow");
    }

    private void initLabel() throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException
    {
        label = new JLabel("");
        label.setVerticalAlignment(JLabel.TOP);
        Border border = BorderFactory.createLineBorder(Color.GRAY);
        label.setBorder(border);
        frame.add(label, "top, grow");

    }

    private void initPasswordField(Window window) {
        JLabel labelPassword = new JLabel("Password: ");
        frame.add(labelPassword);

        if (window == Window.LOGIN) {
            loginPasswordField = new JPasswordField(20);
            frame.add(loginPasswordField, "wrap");
        }

        if (window == Window.SETUP) {
            setupPasswordField1 = new JPasswordField(20);
            frame.add(setupPasswordField1, "span 2, wrap");
            JLabel labelPassword2 = new JLabel("Confirm password: ");
            frame.add(labelPassword2);
            setupPasswordField2 = new JPasswordField(20);
            frame.add(setupPasswordField2, "span 2");
        }
    }

    private void initText(){
        JLabel welcomeText =
                new JLabel("<html>Welcome, new user!<br>Enter a masterpassword below to<br>begin saving all your passwords.<br>" +
                           "The password must be 8 characters or more. <html>");
        frame.add(welcomeText, "wrap");
    }

    private void initLogo(){
        ImageIcon iconLogo = new ImageIcon(SETUP_FILE_NAME);
        JLabel welcomeMan = new JLabel();
        welcomeMan.setIcon(iconLogo);
        frame.add(welcomeMan, "span 2");
    }

    private void addButtons(Window window) {
        if (window == Window.PASSWORD_MANAGER) {
            buttonAdd = new JButton("Add account");
            buttonRemove = new JButton("Remove account");
            buttonEdit = new JButton("Edit account");

            frame.add(buttonAdd);
            frame.add(buttonRemove);
            frame.add(buttonEdit, "wrap");
        }

        else if (window == Window.LOGIN) {
            buttonQuit = new JButton("Quit");
            buttonLogin = new JButton("Login");

            frame.add(buttonQuit);
            frame.add(buttonLogin, "wrap");
        }

        else if (window == Window.SETUP) {
            continueButton = new JButton("Continue");

            frame.add(continueButton);
        }
    }

    private void addListeners(Window window) {
        if (window == Window.PASSWORD_MANAGER) {
            accounts.addMouseListener(jListMouseListener);
            buttonAdd.addActionListener(new ButtonAction(ButtonOption.ADD));
            buttonRemove.addActionListener(new ButtonAction(ButtonOption.REMOVE));
            buttonEdit.addActionListener(new ButtonAction(ButtonOption.EDIT));
        }

        if (window == Window.LOGIN) {
            buttonLogin.addActionListener(new ButtonAction(ButtonOption.LOGIN));
            buttonQuit.addActionListener(new ButtonAction(ButtonOption.QUIT));
        }

        if (window == Window.SETUP) {
            continueButton.addActionListener(new ButtonAction(ButtonOption.CONTINUE));
        }
    }

    private MouseListener jListMouseListener = new MouseAdapter()
    {
        @Override public void mouseClicked(final MouseEvent e) {
            super.mouseClicked(e);
            selectedIndex = accounts.getSelectedIndex();
            if (accounts.getModel().getSize() != 0) {
                Account account = getSelectedAccount();
                try {
                    if (account.getAccountType() == AccountType.STANDARD) {
                        label.setText("Password: " + logicHandler.getAccountPassword(account));
                    }
                    else if (account.getAccountType() == AccountType.EMAIL) {
                        label.setText("<html>Password: " + logicHandler.getAccountPassword(account) + "<br>Email: " +
                                      ((EmailAccount)account).getEmail() + "<br>Domain: " + ((EmailAccount)account).getDomain() + "<html>");
                    }
                    else if (account.getAccountType() == AccountType.BANK) {
                        label.setText("<html>Password: " + logicHandler.getAccountPassword(account) + "<br>Account Number: " + ((BankAccount)account).getBankAccountNumber());
                    }
                } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException |
                        InvalidAlgorithmParameterException exception) {
                    exception.printStackTrace();
                }
            }
        }
    };

    private class ButtonAction extends AbstractAction
    {
        private final ButtonOption button;

        private ButtonAction(final ButtonOption button) {
            this.button = button;
        }

        @Override public void actionPerformed(final ActionEvent e) {
            if (button == ButtonOption.ADD) {
                try {
                    doAddAction();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "File with accounts can not be found!",
                                                  "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (InvalidKeyException | InvalidParameterSpecException | NoSuchAlgorithmException
                    | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Encryption error!",
                                                  "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if (button == ButtonOption.EDIT) {
                try {
                    if (getSelectedAccount().getAccountType() == AccountType.STANDARD) {
                        doEditStandardAccount();
                    }
                    else if (getSelectedAccount().getAccountType() == AccountType.BANK) {
                        doEditBankAccount();
                    }
                    else if (getSelectedAccount().getAccountType() == AccountType.EMAIL) {
                        doEditEmailAccount();
                    }
                } catch (FileNotFoundException | InvalidKeyException | InvalidParameterSpecException | NoSuchAlgorithmException
                        | BadPaddingException | NoSuchPaddingException | IllegalBlockSizeException exception) {
                    exception.printStackTrace();
                }
            }
            else if (button == ButtonOption.REMOVE) {
                try {
                    doRemoveAction();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "File with accounts can not be found!",
                                                  "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                catch (InvalidKeyException | InvalidParameterSpecException | NoSuchAlgorithmException
                    | BadPaddingException | NoSuchPaddingException | IllegalBlockSizeException exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Encryption error!",
                                                  "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if (button == ButtonOption.LOGIN) {
                try {
                    doLoginAction();
                } catch (InvalidKeySpecException | NoSuchAlgorithmException exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Login error!",
                                                  "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (IOException iOException) {
                    iOException.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "File with main password can not be found!",
                                                  "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            else if (button == ButtonOption.CONTINUE) {
                try {
                    doContinueAction();
                } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Login error!",
                                                  "ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (IOException iOException) {
                    iOException.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "File with main password can not be created!",
                                                  "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                frame.dispose();
            }
        }
    }

    private Account getSelectedAccount() {
        return logicHandler.getAccounts().getEncryptedAccount(selectedIndex);
    }

    private String askUserAboutAccount(String question) {
        return JOptionPane.showInputDialog(frame, question);
    }

    private void doAddAction() throws IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidParameterSpecException, InvalidKeyException, FileNotFoundException
    {
        String newUsername = null;
        String newPassword = null;
        String newAccountNumber = null;
        String newEmail = null;
        String newDomain = null;
        AccountType accountType = null;

        String[] options = new String[] { "Standard", "Email", "Bank" };
        int response = JOptionPane.showOptionDialog(null, "What type of account do you want to add?",
                                                    "Options", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                                    null, options, -1);
        if (response == 0) {
            newUsername = askUserAboutAccount("Username:");
            if (newUsername != null) {
                newPassword = askUserAboutAccount("Password:");
                accountType = AccountType.STANDARD;
            }

        }
        else if (response == 1) {
            newUsername = askUserAboutAccount("Username:");
            if (newUsername != null) {
                newEmail = askUserAboutAccount("Email:");
                accountType = AccountType.EMAIL;
                if (newEmail != null) {
                    newDomain = askUserAboutAccount("Domain:");
                    if (newDomain != null) {
                        newPassword = askUserAboutAccount("Password:");
                    }
                }
            }
        }
        else if (response == 2) {
            newUsername = askUserAboutAccount("Social Security Number:");
            if (newUsername != null) {
                newAccountNumber = askUserAboutAccount("Account Number:");
                accountType = AccountType.BANK;
                if (newAccountNumber != null) {
                    newPassword = askUserAboutAccount("Password:");
                }
            }
        }
        else if (response == -1) {
            return;
        }

        if (newUsername == null || newPassword == null ) {
            return;
        }
        else if (newUsername.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "The password or the username can not be empty!",
                                          "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        doAction(ButtonOption.ADD, newUsername, newPassword, newAccountNumber, newEmail, newDomain, accountType);
    }

    private void doEditStandardAccount()
            throws FileNotFoundException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidParameterSpecException, InvalidKeyException
    {
        String newUsername = null;
        String newPassword = null;
        String[] options = new String[] { "Edit password", "Edit username", "Edit both" };
        int response = JOptionPane.showOptionDialog(frame, "What do you want to edit?", "Options",
                                                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                                                    options, -1);
        if (response == 0) {
            newPassword = askUserAboutAccount("What is your new password?");
            if (newPassword == null) {
                return;
            }
            else if (newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "The password can not be empty!",
                                              "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if (response == 1) {
            newUsername = askUserAboutAccount("What is your new username?");
            if (newUsername == null) {
                return;
            }
            else if (newUsername.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "The username can not be empty!",
                                              "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if (response == 2) {
            newUsername = askUserAboutAccount("What is your new username?");
            if (newUsername != null) {
                newPassword = askUserAboutAccount("What is your new password?");
            }
            if (newPassword == null) {
                return;
            }
            else if (newUsername.isEmpty() || newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "The password or the username can not be empty!",
                                              "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else {
            return;
        }

        doAction(ButtonOption.EDIT, newUsername, newPassword, null, null, null, null);
    }

    private void doEditEmailAccount()
            throws FileNotFoundException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidParameterSpecException, InvalidKeyException
    {
        String newUsername = null;
        String newPassword = null;
        String newEmail = null;
        String newDomain = null;

        String[] options = new String[] { "Edit password", "Edit Username", "Edit Email and Domain" };
        int response = JOptionPane.showOptionDialog(frame, "What do you want to edit?", "Options",
                                                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                                                    options, -1);
        if(response == 0) {
            newPassword = askUserAboutAccount("What is your new password?");
            if (newPassword == null) {
                return;
            }
            else if (newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "The password can not be empty!",
                                              "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if (response == 1) {
            newUsername = askUserAboutAccount("What is your new username?");
            if (newUsername == null) {
                return;
            }
            else if (newUsername.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "The username can not be empty!",
                                              "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if (response == 2) {
            newEmail = askUserAboutAccount("What is your new email?");
            if (newEmail == null) {
                return;
            }
            else if (newEmail.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "The email can not be empty!",
                                              "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else {
                newDomain = askUserAboutAccount("What is your new domain?");
                if (newDomain == null) {
                    return;
                }
                else if (newDomain.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "The domain can not be empty!",
                                                  "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        else {
            return;
        }

        doAction(ButtonOption.EDIT, newUsername, newPassword, null, newEmail, newDomain, null);

    }

    private void doEditBankAccount()
            throws FileNotFoundException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidParameterSpecException, InvalidKeyException
    {
        String newUsername = null;
        String newPassword = null;
        String newAccountNumber = null;
        String[] options = new String[] { "Edit password", "Edit Social Security Number", "Edit Account Number" };
        int response = JOptionPane.showOptionDialog(frame, "What do you want to edit?", "Options",
                                                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                                                    options, -1);

        if(response == 0) {
            newPassword = askUserAboutAccount("What is your new password?");
            if (newPassword == null) {
                return;
            }
            else if (newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "The password can not be empty!",
                                              "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        else if (response == 1) {
            newUsername = askUserAboutAccount("What is your new social security number?");
            if (newUsername == null) {
                return;
            }
            else if (newUsername.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "The social security number can not be empty!",
                                              "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else if (response == 2) {
            newAccountNumber = askUserAboutAccount("What is your new account number?");
            if (newAccountNumber == null) {
                return;
            }
            else if (newAccountNumber.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "The account number can not be empty!",
                                              "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        else {
            return;
        }

        doAction(ButtonOption.EDIT, newUsername, newPassword, newAccountNumber, null, null, null);

    }

    private void doRemoveAction()
            throws FileNotFoundException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidParameterSpecException, InvalidKeyException
    {
        doAction(ButtonOption.REMOVE, null, null, null, null, null,
                 null);
        if (accounts.getModel().getSize() == 0) {
            label.setText("");
        }
    }

    private void doLoginAction() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, FileNotFoundException {
        if (loginManager.authenticateLogin(new String(loginPasswordField.getPassword()))) {
            frame.dispose();
            show(Window.PASSWORD_MANAGER);
        }
        else {
            JOptionPane.showMessageDialog(frame, "Invalid password");
            loginPasswordField.setText("");
        }
    }

    private void doContinueAction()
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, FileNotFoundException, NoSuchPaddingException
    {
        if (Arrays.equals(setupPasswordField2.getPassword(), setupPasswordField1.getPassword())) {
            if (setupPasswordField1.getPassword().length < 8) {
                JOptionPane.showMessageDialog(frame, "Password must be 8 characters or more");
                setupPasswordField1.setText("");
                setupPasswordField2.setText("");
            }
            else {
                frame.dispose();
                String stringPassword = new String(setupPasswordField1.getPassword());
                logicHandler = new LogicHandler(stringPassword);
                logicHandler.saveHashToFile(stringPassword);
                show(Window.PASSWORD_MANAGER);
            }
        }
        else {
            JOptionPane.showMessageDialog(frame, "Passwords do not match");
            setupPasswordField2.setText("");
        }
    }

    private void doAction(ButtonOption button, String newUsername, String newPassword, String newAccountNumber,
                          String newEmail, String newDomain, AccountType accountType)
            throws FileNotFoundException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchAlgorithmException,
            InvalidParameterSpecException, InvalidKeyException
    {
        int confirmAction = JOptionPane.showConfirmDialog(frame, "Are you sure you want to " + button + " this account?");

        if (confirmAction == 0) {
            logicHandler.doAccountAction(button, getSelectedAccount(), newUsername, newPassword, newAccountNumber,
                                         newEmail, newDomain, accountType);
        }

        accounts.setModel(logicHandler.getAccounts().returnListModel());
    }
}
