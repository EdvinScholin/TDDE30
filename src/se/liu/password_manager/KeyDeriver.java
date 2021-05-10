package se.liu.password_manager;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * This class generates a cryptographic key derived from the users
 * master password, used to encrypt and decrypt saved passwords.
 */

public class KeyDeriver
{
    private static final byte[] SALT = new byte[] {66, 19, 100, 71, -44, -54, -71, -116};

    public SecretKey generateKey(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
	    KeySpec keySpec = new PBEKeySpec(password.toCharArray(), SALT, 65536, 256);
	    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	    byte[] key = factory.generateSecret(keySpec).getEncoded();
	    return new SecretKeySpec(key, "AES");
    }





}
