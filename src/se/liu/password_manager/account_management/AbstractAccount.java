package se.liu.password_manager.account_management;

import se.liu.password_manager.cryptography.Encrypter;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

/**
 * The class exists for the purpose of creating a base of all accounts. The class implements Account which is in
 * great use for creating our different Accounts. There are two constructors for this class, one for when the
 * user creates an account and the other for retrieving existing accounts from files. The class are able to
 * share the accounts username, encrypted password initalization vector and account type. There are also
 * possibilities to change the username and password.
 */

public abstract class AbstractAccount implements Account
{
    protected String username;
    protected byte[] password, initVector;
    protected AccountType accountType;

    protected AbstractAccount(final String userName, final byte[] plainPassword, final SecretKey key, AccountType accountType)
	    throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
	    InvalidParameterSpecException
    {
	this.username = userName;
	byte[][] array = initPassword(plainPassword, key);  				// Constructor for creating an account for
	this.password = array[0];							// for the first time
	this.initVector = array[1];
	this.accountType = accountType;
    }

    protected AbstractAccount(final String userName, byte[] password, byte[] initVector, AccountType accountType) {
        this.username = userName;
        this.password = password;							// Constructor for reading of files and creating
        this.initVector = initVector;							// and creating the current accounts from saved data
	this.accountType = accountType;
    }

    private byte[][] initPassword(byte[] password, SecretKey key)
	    throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException,
	    InvalidParameterSpecException, InvalidKeyException
    {
	Encrypter encrypter = new Encrypter();
	byte[][] array = encrypter.encryptPassword(password, key);
	return array;
    }

    @Override public byte[] getPassword() {
	return password;
    }

    @Override public String getUsername() {
	return username;
    }

    @Override public byte[] getInitVector() {
	return initVector;
    }

    @Override public AccountType getAccountType() {
	return accountType;
    }

    @Override public void editPassword(byte[] newPassword, SecretKey key)
	    throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
	    InvalidParameterSpecException
    {
	Encrypter encrypter = new Encrypter();
	byte[][] array = encrypter.encryptPassword(newPassword, key);
	password = array[0];
	initVector = array[1];
    }

    @Override public void editUsername(String newUsername) {
	username = newUsername;
    }

}
