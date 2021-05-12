package se.liu.password_manager;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

public abstract class AbstractAccount implements Account
{
    protected String username;
    protected byte[] password, initVector;

    protected AbstractAccount(final String userName, final byte[] plainPassword, final SecretKey key)
	    throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
	    InvalidParameterSpecException
    {
	this.username = userName;
	byte[][] array = initPassword(plainPassword, key);  	// Detta måste vi göra för att om encrypter.cryptoPassword(password, key);
								// körs flera gånger ändras IV:n och kan inte decrypta lösenorden.
	this.password = array[0];
	this.initVector = array[1];
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
