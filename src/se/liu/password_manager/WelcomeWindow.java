package se.liu.password_manager;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class WelcomeWindow
{
    private JFrame frame = null;
    private JLabel welcomeText = null;
    private JLabel welcomeMan = null;
    private JLabel labelPassword1 = null;
    private JPasswordField passwordField1 = null;
    private JLabel labelPassword2 = null;
    private JPasswordField passwordField2 = null;
    private JButton okButton = null;



    public void show() {
        initFrame();
        initWelcomeMan();
	initText();
	initPasswordfield();
	initButton();


	frame.pack(); //om man kommenterar bort denna så blir fönstret vad .setSize säger.
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
        //welcomeText = new JLabel("<html>Welcome new user!<br>Enter a masterpassword below to begin saving all your passwords.<html>");
	welcomeText = new JLabel("<html>Howdy new user!<br>Enter a masterpassword below to<br>begin saving all your passwords.<html>");
        frame.add(welcomeText, "wrap");
    }

    private void initWelcomeMan(){
	ImageIcon iconLogo = new ImageIcon("resources/images/welcome_man.png");
	welcomeMan = new JLabel();
	welcomeMan.setIcon(iconLogo);
	frame.add(welcomeMan, "span 2");
    }

    private void initPasswordfield() {
	labelPassword1 = new JLabel("New masterpassword: ");
	frame.add(labelPassword1);
	passwordField1 = new JPasswordField(20);
	frame.add(passwordField1, "span 2, wrap");
	labelPassword2 = new JLabel("Confirm password: ");
	frame.add(labelPassword2);
	passwordField2 = new JPasswordField(20);
	frame.add(passwordField2, "span 2");

    }

    private void initButton() {
	okButton = new JButton("OK");
        frame.add(okButton);
    }






    public static void main(String[] args) {
	WelcomeWindow wW = new WelcomeWindow();
	wW.show();
    }
}

