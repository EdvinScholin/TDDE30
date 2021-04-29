package se.liu.password_manager;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * This class is responsible for decrypting passwords with a given algorithm and key.
 */

public class Decrypter extends CrypthographyObject
{
    public Decrypter() throws NoSuchPaddingException, NoSuchAlgorithmException {
        super(Cipher.getInstance("AES"));
    }

    public byte[] cryptoPassword(byte[] encryptedPassword, Key key)
	    throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {
	cipher.init(Cipher.DECRYPT_MODE, key);
	byte[] originalPassword = cipher.doFinal(encryptedPassword);
	return originalPassword;
    }
}
