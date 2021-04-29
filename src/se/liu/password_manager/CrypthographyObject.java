package se.liu.password_manager;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.security.Key;

/**
 * The abstract class for every Crypthography object. Each of these objects has a cryptoPassword method and can use
 * the protected field for encryption or decryption.
 */
public abstract class CrypthographyObject
{
    protected Cipher cipher;

    protected CrypthographyObject(Cipher cipher) {
         this.cipher = cipher;
    }

    abstract byte[] cryptoPassword(byte[] password, Key key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException;
}
