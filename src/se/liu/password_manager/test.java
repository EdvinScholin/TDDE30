package se.liu.password_manager;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class test
{
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
	SecureRandom random = new SecureRandom();
	byte[] salt = new byte[16];
	random.nextBytes(salt);

	KeySpec spec = new PBEKeySpec("hej".toCharArray(), salt, 65536, 128);
	SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

	byte[] hash = factory.generateSecret(spec).getEncoded();

	System.out.println(new String(hash));




    }
}
