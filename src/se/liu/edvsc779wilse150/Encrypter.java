package se.liu.edvsc779wilse150;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Encrypter
{
    private String password;
    private KeyGenerator kg = KeyGenerator.getInstance("AES");
    private Key key = kg.generateKey();
    private Cipher cipher = Cipher.getInstance("AES");

    public Encrypter(final String password) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.password = password;
    }

    private byte[] getPasswordBytes() {
        byte[] data = password.getBytes();
        return data;
    }

    public byte[] encryptPassword() throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(getPasswordBytes());
        return result;
    }
}
