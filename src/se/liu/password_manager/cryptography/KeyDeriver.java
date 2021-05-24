package se.liu.password_manager.cryptography;

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

public class KeyDeriver {
    private static final String HASHING_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final String DERIVING_ALGORITHM = "AES";
    private byte[] salt = null;

    public KeyDeriver(final byte[] salt) {
	this.salt = salt;
    }

    public SecretKey deriveKey(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
	    KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
	    SecretKeyFactory factory = SecretKeyFactory.getInstance(HASHING_ALGORITHM);
	    byte[] key = factory.generateSecret(keySpec).getEncoded();
	    return new SecretKeySpec(key, DERIVING_ALGORITHM);
    }
}
