package se.liu.password_manager;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Arrays;

public class SetupWindow
{
    private JFrame frame = null;
    private JPasswordField passwordField1 = null, passwordField2 = null;
    private JButton continueButton = null;
    private static final String FILE_NAME = "resources" + File.separator + "images" + File.separator + "setup_pic.png";
    private boolean passwordsMatched = false;
    private SetupListener setupListener = null;


    public void show() {
        initFrame();
        initLogo();
	initText();
	initPasswordfield();
	initButton();
	addListeners();

	frame.pack();
	frame.setVisible(true);
    }


    private void initFrame() {
        frame = new JFrame("Welcome!");
	frame.setLayout(new MigLayout("", "[][][][]", "[][][]"));
	frame.setSize(500, 500);
	frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	frame.setLocation(960-250, 540-250); //för att få fönstert i mitten av en 1920x1080p skärm hehe.
    }


    private void initText(){
	JLabel welcomeText =
		new JLabel("<html>Welcome, new user!<br>Enter a masterpassword below to<br>begin saving all your passwords.<html>");
        frame.add(welcomeText, "wrap");
    }

    private void initLogo(){
	ImageIcon iconLogo = new ImageIcon(FILE_NAME);
	JLabel welcomeMan = new JLabel();
	welcomeMan.setIcon(iconLogo);
	frame.add(welcomeMan, "span 2");
    }

    private void initPasswordfield() {
	JLabel labelPassword1 = new JLabel("New masterpassword: ");
	frame.add(labelPassword1);
	passwordField1 = new JPasswordField(20);
	frame.add(passwordField1, "span 2, wrap");
	JLabel labelPassword2 = new JLabel("Confirm password: ");
	frame.add(labelPassword2);
	passwordField2 = new JPasswordField(20);
	frame.add(passwordField2, "span 2");

    }

    private void initButton() {
	continueButton = new JButton("Continue");
        frame.add(continueButton);
    }

    private void addListeners() {
	continueButton.addActionListener(new SetupAction(ButtonOption.CONTINUE));
    }

    private class SetupAction extends AbstractAction
    {
	private final ButtonOption button;

	private SetupAction(final ButtonOption button) {
	    this.button = button;
	}

	@Override public void actionPerformed(final ActionEvent e) {
	    if (button == ButtonOption.CONTINUE) {
	        if (Arrays.equals(passwordField2.getPassword(), passwordField1.getPassword())) {
		    passwordsMatched = true;
		    notifyListeners();
		    frame.dispose();
		}
	        else {
		    JOptionPane.showMessageDialog(frame, "Passwords do not match");
		    passwordField2.setText("");
		}

	    }
	}
    }

    public boolean isPasswordsMatched() {
	return passwordsMatched;
    }

    public void setSetupListener(SetupListener wL) {
	setupListener = wL;
    }

    public void notifyListeners() {
	setupListener.registrationAttempted();
    }
}

