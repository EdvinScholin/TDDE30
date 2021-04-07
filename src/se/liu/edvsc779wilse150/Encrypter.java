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

    public static void main(String[] args) {
        try {
            String password = "Hej jag heter wilmer och jag har ett supersvårt lösenord";
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            Key key = kg.generateKey();
            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] data = password.getBytes();
            byte[] result = cipher.doFinal(data);
            System.out.println(new String(result));
            System.out.println(new String(data));

            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] original = cipher.doFinal(result);
            System.out.println("Decrypted data: " + new String(original));

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }
}
