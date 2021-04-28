package se.liu.password_manager;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class LoginWindow
{
    private Login login = new Login();
    private JFrame frame = null;
    private JLabel labelPassword = null;
    private JPasswordField passwordField = null;
    private JButton buttonLogin = null, buttonQuit = null;

    public void show() {
        initFrame();
        initPasswordfield();
        initButtons();
        addListeners();

        frame.pack();
        frame.setVisible(true);
    }

    private void initFrame() {
        frame = new JFrame("Login");
        frame.setLayout(new MigLayout("", "[grow][grow]", "[][]"));
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    }

    private void initPasswordfield() {
        labelPassword = new JLabel("Password: ");
        frame.add(labelPassword);
        passwordField = new JPasswordField(20);
        frame.add(passwordField, "wrap");
    }

    private void initButtons() {
        buttonQuit = new JButton("Quit");
        buttonLogin = new JButton("Login");
        frame.add(buttonQuit);
        frame.add(buttonLogin, "wrap");
    }

    private void addListeners() {
        buttonLogin.addActionListener(new LoginAction(ButtonOption.LOGIN));
        buttonQuit.addActionListener(new LoginAction(ButtonOption.QUIT));
    }

    private class LoginAction extends AbstractAction
    {
        private final ButtonOption button;

        private LoginAction(final ButtonOption button) {
            this.button = button;
        }

        @Override public void actionPerformed(final ActionEvent e) {
            if (button == ButtonOption.LOGIN) {
                if (login.authenticateLogin(new String(passwordField.getPassword()))) {
                    JOptionPane.showMessageDialog(frame, "Correct password");
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Invalid password");
                    passwordField.setText("");
                }
            }
            else if (button == ButtonOption.QUIT) {
                frame.dispose();
            }
        }
    }
}
