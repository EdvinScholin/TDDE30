package se.liu.password_manager.cryptography;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * This class is responsible for the hash generation. The class depends on a static salt for the
 * generation of the salt.
 */

public class HashEngine
{
    private static final byte[] SALT = new byte[] {-64, 105, 16, -52, 88, 109, 3, 57, -79, 46, -58, 125, 101, -19, -81, -108};

    public byte[] generateHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec specification = new PBEKeySpec(password.toCharArray(), SALT, 65536, 128);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = keyFactory.generateSecret(specification).getEncoded();
        return hash;
    }
}
