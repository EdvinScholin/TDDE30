package se.liu.password_manager;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * This class generates a cryptographic key derived from the users
 * master password, used to encrypt and decrypt saved passwords.
 */

public class KeyGen
{
    private KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

    public KeyGen() throws NoSuchAlgorithmException {
    }

    public Key generateKey(String password) {
        return keyGenerator.generateKey();
    }
}
