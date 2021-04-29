package se.liu.password_manager;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class LoginWindow
{
    private Login login = null;
    private JFrame frame = null;
    private JPasswordField passwordField = null;
    private JButton buttonLogin = null, buttonQuit = null;
    private boolean successfulLogin = false;
    private List<LoginListener> loginListeners = new ArrayList<>();

    public void show() {
        initLogin();
        initFrame();
        initPasswordfield();
        initButtons();
        addListeners();

        frame.pack();
        frame.setVisible(true);
    }

    private void initLogin() {
        login = new Login();
    }

    private void initFrame() {
        frame = new JFrame("Login to PasswordManager");
        frame.setLayout(new MigLayout("", "[grow][grow]", "[][]"));
        frame.setLocation(960-250, 540-250);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    }

    private void initPasswordfield() {
        JLabel labelPassword = new JLabel("Password: ");
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
                    successfulLogin = true;
                    notifyListeners();
                    frame.dispose();
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

    public boolean isSuccessfulLogin() {
        return successfulLogin;
    }

    public void addLoginListener(LoginListener ll) {
        loginListeners.add(ll);
    }

    public void notifyListeners() {
        for (LoginListener listener : loginListeners) {
            listener.loginAttempted();
        }
    }
}
