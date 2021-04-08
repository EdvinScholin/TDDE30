package se.liu.edvsc779wilse150;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Encrypter
{
    /*
    private String password;
    private Key key;

    public Encrypter(final String password, final Key key) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.password = password;
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

    public byte[] encryptPassword(String password, Key key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(password.getBytes());
        return result;
    }
}
