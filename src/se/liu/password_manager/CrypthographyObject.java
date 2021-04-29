package se.liu.password_manager;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.security.Key;

public abstract class CrypthographyObject
{
    protected Cipher cipher;

    protected CrypthographyObject(Cipher cipher) {
         this.cipher = cipher;
    }

    abstract byte[] cryptoPassword(byte[] password, Key key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException;
}
