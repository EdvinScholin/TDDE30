package se.liu.password_manager;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class test
{
    test(){
	JFrame f= new JFrame();

	KeyGen keyGen = null;
	try {
	    keyGen = new KeyGen();
	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	}

	Key key = keyGen.generateKey();

	Account account1 = null;
	Account account2 = null;
	try {
	    account1 = new Account("WilmerS", "mupp", key);
	    account2 = new Account("EdvinS", "balle", key);
	} catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
	    e.printStackTrace();
	}

	DefaultListModel<String> l1 = new DefaultListModel<>();
	l1.addElement(account1.getUsername());
	l1.addElement(account2.getUsername());
	/*
	l1.addElement("Item3");
	l1.addElement("Item4");

	 */
	JList<String> list = new JList<>(l1);
	list.setBounds(100,100, 75,300);
	f.add(list);
	f.setSize(400,400);
	f.setLayout(null);
	f.setVisible(true);
    }
    public static void main(String args[])
    {
	new test();
    }
}
