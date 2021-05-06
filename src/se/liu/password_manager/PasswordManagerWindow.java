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
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * A visual class for the main program. This class has inner action classes that help
 * PasswordMangagerWindow when something happens in the frame. The class contains all components of the frame
 * and also a pointer to the middle man between the logic layer and the visual layer.
 */
public class PasswordManagerWindow
{
    private JFrame frame = null;
    private LogicHandler logicHandler = null;   //En mellanhand till logiska och visuella planet. Vill visuella planet ha information,
                                                // frågar den logicHandler.
    private int selectedIndex = 0;
    private JList<String> jList = null;
    private JLabel label = null;
    private JButton buttonAdd = null, buttonRemove = null, buttonEdit = null, buttonQuit = null, buttonLogin = null, continueButton = null;
    private List<JButton> jButtons = null;
    private static final int LOCATIONX = 710;
    private static final int LOCATIONY = 290;

    public void showPasswordManager() throws NoSuchPaddingException, NoSuchAlgorithmException {
        Window window = Window.PASSWORD_MANAGER;

        initFrame(window);
        initLogicHandler();

        addJButtons(window); // fixat
        setJList();
        initLabel();

        addListeners();

        frame.pack();
        frame.setVisible(true);
    }

    public void showLoginWindow() {
        Window window = Window.LOGIN;

        initLogin();
        initFrame(window);
        initPasswordField();
        addJButtons(window);
        addListeners();

        frame.pack();
        frame.setVisible(true);
    }

    public void showSetupWindow() {
        Window window = Window.SETUP;

        initFrame(window);
        initLogo();
        initText();
        initPasswordField();
        addJButtons(window);
        addListeners();

        frame.pack();
        frame.setVisible(true);
    }

    private void initFrame(Window window) {
        if (window == Window.PASSWORD_MANAGER) {
            frame = new JFrame("Password Manager");
            frame.setLayout(new MigLayout("", "[grow][grow][grow]", "[][grow]"));
        }

        if (window == Window.LOGIN) {
            frame = new JFrame("Login to PasswordManager");
            frame.setLayout(new MigLayout("", "[grow][grow]", "[][]"));
        }

        if (window == Window.SETUP) {
            frame = new JFrame("Welcome!");
            frame.setLayout(new MigLayout("", "[][][][]", "[][][]"));
        }

        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLocation(LOCATIONX, LOCATIONY);
    }

    private void initLogicHandler() throws NoSuchPaddingException, NoSuchAlgorithmException {
        logicHandler = new LogicHandler();
    }

    private void setJList() {
        jList = new JList<>(logicHandler.getAccounts().returnListModel());
        JScrollPane jScrollPane = new JScrollPane(jList);
        frame.add(jScrollPane, "span 2, grow");
    }

    private void initLabel() {
        label = new JLabel(getSelectedAccount().getUsername());
        label.setVerticalAlignment(JLabel.TOP);
        Border border = BorderFactory.createLineBorder(Color.ORANGE);
        label.setBorder(border);
        frame.add(label, "top, grow");
    }

    private void addJButtons(Window window) {
        jButtons = new ArrayList<>();

        if (window == Window.PASSWORD_MANAGER) {
            buttonAdd = new JButton("Add account");
            buttonRemove = new JButton("Remove account");
            buttonEdit = new JButton("Edit account");

            jButtons.add(buttonAdd);
            jButtons.add(buttonRemove);
            jButtons.add(buttonEdit);

            frame.add(buttonAdd);
            frame.add(buttonRemove);
            frame.add(buttonEdit, "wrap");
        }

        else if (window == Window.LOGIN) {
            buttonQuit = new JButton("Quit");
            buttonLogin = new JButton("Login");

            jButtons.add(buttonQuit);
            jButtons.add(buttonLogin);

            frame.add(buttonQuit);
            frame.add(buttonLogin, "wrap");
        }

        else if (window == Window.SETUP) {
            continueButton = new JButton("Continue");

            jButtons.add(continueButton);

            frame.add(continueButton);
        }

        int size = jButtons.size();
        for (JButton jButton : jButtons) {
            if (jButton.) // inte klar
            frame.add(jButton);
        }
    }

    private void addListeners(Window window) {
        if (window == Window.PASSWORD_MANAGER) {
            jList.addMouseListener(jListMouseListener);
            buttonAdd.addActionListener(new ButtonAction(ButtonOption.ADD));
            buttonRemove.addActionListener(new ButtonAction(ButtonOption.REMOVE));
            buttonEdit.addActionListener(new ButtonAction(ButtonOption.EDIT));
        }

        if (window == Window.LOGIN) {
            buttonLogin.addActionListener(new LoginWindow.LoginAction(ButtonOption.LOGIN)); // Behöver fixa för action klassen,
            buttonQuit.addActionListener(new LoginWindow.LoginAction(ButtonOption.QUIT));   // göra en public class istället?
        }

        if (window == Window.SETUP) {
            continueButton.addActionListener(new SetupWindow.SetupAction(ButtonOption.CONTINUE)); // Behöver fixa för action klassen
        }
    }

    MouseListener jListMouseListener = new MouseAdapter()
    {
        @Override public void mouseClicked(final MouseEvent e) {
            super.mouseClicked(e);
            selectedIndex = jList.getSelectedIndex();
            Account account = getSelectedAccount();
            label.setText(account.getUsername());
        }
    };

    private class ButtonAction extends AbstractAction
    {
        private final ButtonOption button;

        private ButtonAction(final ButtonOption button) {
            this.button = button;
        }

        @Override public void actionPerformed(final ActionEvent e) {
            String newUsername = null;
            String newPassword = null;
            int removeAccount = 0;

            if (button == ButtonOption.EDIT) {
                String[] options = new String[] {"Edit password", "Edit username", "Edit both"};
                int response = JOptionPane.showOptionDialog(null, "What do you want to edit?", "Options",
                                                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                                            null, options, options[0]);
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

            else if (button == ButtonOption.REMOVE) {
                removeAccount = JOptionPane.showConfirmDialog(frame, "Are you sure you want to remove this account?");
            }

            try {
                if (removeAccount == 0) {
                    logicHandler.doButtonAction(button, getSelectedAccount(), newUsername, newPassword);
                }
            } catch (FileNotFoundException | IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException
                    | NoSuchAlgorithmException | InvalidKeyException exception) {
                exception.printStackTrace();
            }

            jList.setModel(logicHandler.getAccounts().returnListModel());
        }
    }

    private Account getSelectedAccount() {
        return logicHandler.getAccounts().getEncryptedAccount(selectedIndex);
    }

    private String askUserAboutAccount(String question) {
        return JOptionPane.showInputDialog(frame, question);
    }
}
