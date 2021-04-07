package se.liu.edvsc779wilse150;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Decrypter
{
    private byte[] encryptedPassword;
    private KeyGenerator kg = KeyGenerator.getInstance("AES");
    private Key key; // = kg.generateKey();
    private Cipher cipher = Cipher.getInstance("AES");

    public Decrypter(final byte[] encryptedPassword) throws NoSuchPaddingException, NoSuchAlgorithmException {
	this.encryptedPassword = encryptedPassword;
    }

    public String decryptPassword() throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
	cipher.init(Cipher.DECRYPT_MODE, key);
	byte[] originalPassword = cipher.doFinal(encryptedPassword);
	return new String(originalPassword);
    }
}
