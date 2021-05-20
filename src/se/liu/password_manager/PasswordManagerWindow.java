package se.liu.password_manager;

import net.miginfocom.swing.MigLayout;

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
    private LogicHandler logicHandler = null;   // En mellanhand till logiska och visuella planet. Vill visuella planet ha information,
                                                // frågar den logicHandler.
    private LoginManager login = null;
    private int selectedIndex = 0;
    private JList<String> accounts = null;
    private JLabel label = null;
    private JPasswordField loginPasswordField = null;
    private JPasswordField setupPasswordField1 = null, setupPasswordField2 = null;
    private JButton buttonAdd = null, buttonRemove = null, buttonEdit = null, buttonQuit = null, buttonLogin = null, continueButton = null;
    private static final int LOCATIONX = 710;
    private static final int LOCATIONY = 290;
    private static final String SETUP_FILE_NAME = "resources" + File.separator + "images" + File.separator + "setup_pic.png";

    public void show(Window window) throws NoSuchPaddingException, NoSuchAlgorithmException {
        initFrame(window);

        if (window == Window.LOGIN) {
            login = new LoginManager();
            initPasswordField(window);
        }

        else if (window == Window.SETUP) {
            initLogo();
            initText();
            initPasswordField(window);
        }

        addJButtons(window);                           // Har den här ordningen på if-satserna för att
                                                        // komponenterna ska hamna på rätt plats i fönstret.
        if (window == Window.PASSWORD_MANAGER) {
            if (logicHandler == null) {
                logicHandler = new LogicHandler(new String(loginPasswordField.getPassword()));
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
        if (accounts.getModel().getSize() != 0) {
            label.setText(logicHandler.getAccountPassword(getSelectedAccount()));
        }
        label.setVerticalAlignment(JLabel.TOP);
        Border border = BorderFactory.createLineBorder(Color.ORANGE);
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
                new JLabel("<html>Welcome, new user!<br>Enter a masterpassword below to<br>begin saving all your passwords.<html>");
        frame.add(welcomeText, "wrap");
    }

    private void initLogo(){
        ImageIcon iconLogo = new ImageIcon(SETUP_FILE_NAME);
        JLabel welcomeMan = new JLabel();
        welcomeMan.setIcon(iconLogo);
        frame.add(welcomeMan, "span 2");
    }

    private void addJButtons(Window window) {
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
            buttonLogin.addActionListener(new ButtonAction(ButtonOption.LOGIN)); // Behöver fixa för action klassen,
            buttonQuit.addActionListener(new ButtonAction(ButtonOption.QUIT));   // göra en public class istället?
        }

        if (window == Window.SETUP) {
            continueButton.addActionListener(new ButtonAction(ButtonOption.CONTINUE)); // Behöver fixa för action klassen
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
                    label.setText(logicHandler.getAccountPassword(account));
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
                doAddAction();
            }
            else if (button == ButtonOption.EDIT) {
                doEditAction();
            }
            else if (button == ButtonOption.REMOVE) {
                doRemoveAction();
            }
            else if (button == ButtonOption.LOGIN) {
                doLoginAction();
            }
            else if (button == ButtonOption.CONTINUE) {
                doContinueAction();
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

    private void doAddAction() {
        String newUsername = null;
        String newPassword = null;
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
        if (response == 1) {
            newUsername = askUserAboutAccount("Email:");
            if (newUsername != null) {
                newPassword = askUserAboutAccount("Password:");
                accountType = AccountType.EMAIL;
            }
        }
        if (response == 2) {
            newUsername = askUserAboutAccount("Social Security Number:");
            if (newUsername != null) {
                newPassword = askUserAboutAccount("Password:");
                accountType = AccountType.BANK;
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
                                          "WARNING", JOptionPane.WARNING_MESSAGE);
            return;
        }

        doAction(ButtonOption.ADD, newUsername, newPassword, accountType);
    }

    private void doEditAction() {
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
                JOptionPane.showMessageDialog(frame, "The password or the username can not be empty!",
                                              "WARNING", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        else if (response == 1) {
            newUsername = askUserAboutAccount("What is your new username?");
            if (newUsername == null) {
                return;
            }
            else if (newUsername.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "The password or the username can not be empty!",
                                             "WARNING", JOptionPane.WARNING_MESSAGE);
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
                                              "WARNING", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        else {
            return;
        }

        doAction(ButtonOption.EDIT, newUsername, newPassword, null);

    }

    private void doRemoveAction() {
        doAction(ButtonOption.REMOVE, null, null, null);
        if (accounts.getModel().getSize() == 0) {
            label.setText("");
        }

    }

    private void doLoginAction() {
        try {
            if (login.authenticateLogin(new String(loginPasswordField.getPassword()))) {
                frame.dispose();
                show(Window.PASSWORD_MANAGER);
            }
            else {
                JOptionPane.showMessageDialog(frame, "Invalid password");
                loginPasswordField.setText("");
            }
        } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException | NoSuchPaddingException exception) {
            exception.printStackTrace();
        }
    }

    private void doContinueAction() {
        if (Arrays.equals(setupPasswordField2.getPassword(), setupPasswordField1.getPassword())) {
            frame.dispose();
            try {
                String stringPassword = new String(setupPasswordField1.getPassword());
                logicHandler = new LogicHandler(stringPassword);
                logicHandler.saveHashToFile(stringPassword);
                String newUsername = askUserAboutAccount("Username:");
                String newPassword = askUserAboutAccount("Password:");
                logicHandler.doAccountAction(ButtonOption.ADD, null, newUsername, newPassword, AccountType.STANDARD);
                show(Window.PASSWORD_MANAGER);
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | IOException | InvalidKeySpecException |
                    IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidParameterSpecException
                    exception) {
                exception.printStackTrace();
            }
        }
        else {
            JOptionPane.showMessageDialog(frame, "Passwords do not match");
            setupPasswordField2.setText("");
        }
    }

    private void doAction(ButtonOption button, String newUsername, String newPassword, AccountType accountType) {
        int editAccountList = JOptionPane.showConfirmDialog(frame, "Are you sure you want to " + button + " this account?");

        if (editAccountList == 0) {
            try {
                logicHandler.doAccountAction(button, getSelectedAccount(), newUsername, newPassword, accountType);
            } catch (FileNotFoundException | IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException |
                    NoSuchAlgorithmException | InvalidKeyException | InvalidParameterSpecException exception) {
                exception.printStackTrace();
            }
        }

        accounts.setModel(logicHandler.getAccounts().returnListModel());
    }
}
