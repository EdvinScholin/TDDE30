package se.liu.password_manager;

import net.miginfocom.swing.MigLayout;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class PasswordManagerViewer
{
    private JFrame frame = null;
    private LogicHandler logicHandler = null; //En mellanhand till logiska och visuella planet. Vill visuella planet ha information, frågar den
                                        // logicHandler.
    private JList jList = null;
    private JLabel label = null;
    private int selectedItem = 0;
    private JButton buttonAdd = null, buttonRemove = null, buttonEdit = null;

    public void show() throws NoSuchPaddingException, NoSuchAlgorithmException {
        initializeFrame();
        initializeLogicHandler();
        initializeJList();

        frame.setLayout(new MigLayout("", "[grow][grow][grow]", "[][grow]"));
        frame.setSize(500, 500);

        addJButtons();
        initializeScrollablePane();
        initializeLabel();

        addListeners();

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    }

    private void initializeFrame() {
        frame = new JFrame("Password Manager");
    }

    private void initializeLogicHandler() throws NoSuchPaddingException, NoSuchAlgorithmException {
        logicHandler = new LogicHandler();
    }

    private void initializeJList() {
        new JList(logicHandler.getAccountList().returnListModel());
    }

    private void intializeLabel() {
        label = new JLabel("No account selected");
    }

    private void initializeScrollablePane() {
        frame.add(new JScrollPane(jList), "span 2, grow");
    }

    private void initializeLabel() {
        label.setVerticalAlignment(JLabel.TOP);
        Border border = BorderFactory.createLineBorder(Color.ORANGE);
        label.setBorder(border);
        frame.add(label, "top, grow");
    }

    private void addJButtons() {
        buttonAdd = new JButton("Add account");
        buttonRemove = new JButton("Remove account");
        buttonEdit = new JButton("Edit account");
        frame.add(buttonAdd);
        frame.add(buttonRemove);
        frame.add(buttonEdit, "wrap");
    }

    private void addListeners() {
        jList.addMouseListener(jListMouseListener);
        buttonAdd.addMouseListener(new ButtonMouseListener(ButtonOption.ADD, selectedAccount()));
        buttonRemove.addMouseListener(new ButtonMouseListener(ButtonOption.REMOVE, selectedAccount()));
        buttonEdit.addMouseListener(new ButtonMouseListener(ButtonOption.EDIT, selectedAccount()));
    }

    MouseListener jListMouseListener = new MouseAdapter()
    {
        @Override public void mouseClicked(final MouseEvent e) {
            super.mouseClicked(e);
            selectedItem = jList.getSelectedIndex();
            Account account = selectedAccount();
            label.setText(account.getUsername());
        }
    };

    private class ButtonMouseListener extends MouseAdapter
    {
        private final ButtonOption button;
        private final Account account;

        private ButtonMouseListener(final ButtonOption button, final Account account) {
            this.button = button;
            this.account = account;
        }

        @Override public void mouseClicked(final MouseEvent e) {
            super.mouseClicked(e);
            String[] options = new String[] {"Edit password", "Edit username", "Edit both"};
            int response = JOptionPane.showOptionDialog(null, "What do you want to edit?", "Options",
                                                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                                        null, options, options[0]);

            if (response == 0) {
                String newPassword = askUserAboutAccount("What is your new password?");

                oldAccount.editPassword(newPassword, key);

                saveOnFile();
            }
            if (response == 1) {
                String newUsername = askUserAboutAccount("What is your new username?");
                oldAccount.editUsername(newUsername);

                saveOnFile();
            }
            if (response == 2) {
                String newUsername = askUserAboutAccount("What is your new username?");
                oldAccount.editUsername(newUsername);

                String newPassword = askUserAboutAccount("What is your new password?");
                oldAccount.editPassword(newPassword, key);

                saveOnFile();
            }

            /*
            try {
                logicHandler.buttonAction(button, account);
            } catch (FileNotFoundException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | NoSuchPaddingException
                    | IllegalBlockSizeException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }

             */
        }
    }

    private Account selectedAccount() {
        return logicHandler.getAccountList().getEncryptedAccount(selectedItem);
    }

    private String askUserAboutAccount(String question) {
        return JOptionPane.showInputDialog(null, question);
    }
}
