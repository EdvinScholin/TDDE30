package se.liu.password_manager;


import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.security.Key;

public interface CrypthographyManager
{
    public byte[] cryptoPassword(byte[] password, Key key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException;
}
