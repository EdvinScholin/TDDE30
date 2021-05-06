package se.liu.password_manager;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;


/**
 *  This class is responsible for handling the AES encryption of submitted passwords.
 */

public class Encrypter
{
    Cipher cipher;

    public Encrypter() throws NoSuchPaddingException, NoSuchAlgorithmException {
        super(Cipher.getInstance("AES"));
    }

    public byte[] cryptoPassword(byte[] password, Key key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(password);
        return result;
    }
}
