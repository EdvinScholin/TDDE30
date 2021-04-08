package se.liu.edvsc779wilse150;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Decrypter
{
    /*
    private byte[] encryptedPassword;
    private Key key;
    private Cipher cipher = Cipher.getInstance("AES");

    public Decrypter(final byte[] encryptedPassword, final Key key) throws NoSuchPaddingException, NoSuchAlgorithmException {
	this.encryptedPassword = encryptedPassword;
	this.key = key;
    }

     */
    private Cipher cipher = null;
    {
	try {
	    cipher = Cipher.getInstance("AES");
	} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
	    e.printStackTrace();
	}
    }

    public String decryptPassword(byte[] encryptedPassword, Key key)
	    throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {
	cipher.init(Cipher.DECRYPT_MODE, key);
	byte[] originalPassword = cipher.doFinal(encryptedPassword);
	return new String(originalPassword);
    }
}
