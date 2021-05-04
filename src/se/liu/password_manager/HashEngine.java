package se.liu.password_manager;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class HashEngine
{
    byte[] salt = new byte[] {-64, 105, 16, -52, 88, 109, 3, 57, -79, 46, -58, 125, 101, -19, -81, -108};

    /*
    SecureRandom random = new SecureRandom();
	byte[] salt = new byte[16];
	random.nextBytes(salt);
	*/

    public HashEngine() {
    }

    public byte[] generateHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec specification = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = keyFactory.generateSecret(specification).getEncoded();
        return hash;
    }
}
