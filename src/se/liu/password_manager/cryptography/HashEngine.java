package se.liu.password_manager.cryptography;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * This class is responsible for the hash generation. The class depends on a static salt for the
 * generation of the salt.
 */

public class HashEngine
{
    private static final String HASHING_ALGORITHM = "PBKDF2WithHmacSHA1";

    public byte[] generateHash(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec specification = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(HASHING_ALGORITHM);
        byte[] hash = keyFactory.generateSecret(specification).getEncoded();
        return hash;
    }
}
