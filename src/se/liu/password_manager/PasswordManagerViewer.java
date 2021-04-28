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

public class PasswordManagerViewer
{
    private JFrame frame = null;
    private LogicHandler logicHandler = null;   //En mellanhand till logiska och visuella planet. Vill visuella planet ha information,
                                                // frågar den logicHandler.
    private int selectedIndex = 0;
    private JList jList = null;
    private JScrollPane jScrollPane = null;
    private JLabel label = null;
    private JButton buttonAdd = null, buttonRemove = null, buttonEdit = null;

    public void show() throws NoSuchPaddingException, NoSuchAlgorithmException {
        initializeFrame();
        initializeLogicHandler();

        addJButtons();
        setJList();
        initializeLabel();

        addListeners();

        frame.pack();
        frame.setVisible(true);
    }

    private void initializeFrame() {
        frame = new JFrame("Password Manager");
        frame.setLayout(new MigLayout("", "[grow][grow][grow]", "[][grow]"));
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    }

    private void initializeLogicHandler() throws NoSuchPaddingException, NoSuchAlgorithmException {
        logicHandler = new LogicHandler();
    }

    private void setJList() {
        jList = new JList(logicHandler.getAccountList().returnListModel());
        jScrollPane = new JScrollPane(jList);
        frame.add(jScrollPane, "span 2, grow");
    }

    private void initializeLabel() {
        label = new JLabel(selectedAccount().getUsername());
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
        buttonAdd.addActionListener(new ButtonAction(ButtonOption.ADD));
        buttonRemove.addActionListener(new ButtonAction(ButtonOption.REMOVE));
        buttonEdit.addActionListener(new ButtonAction(ButtonOption.EDIT));
    }

    MouseListener jListMouseListener = new MouseAdapter()
    {
        @Override public void mouseClicked(final MouseEvent e) {
            super.mouseClicked(e);
            selectedIndex = jList.getSelectedIndex();
            Account account = selectedAccount();
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

            try {
                logicHandler.buttonAction(button, selectedAccount(), newUsername, newPassword);
            } catch (FileNotFoundException | IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException
                    | NoSuchAlgorithmException | InvalidKeyException exception) {
                exception.printStackTrace();
            }

            jList.setModel(logicHandler.getAccountList().returnListModel());
        }
    }

    private Account selectedAccount() {
        return logicHandler.getAccountList().getEncryptedAccount(selectedIndex);
    }

    private String askUserAboutAccount(String question) {
        return JOptionPane.showInputDialog(null, question);
    }
}
