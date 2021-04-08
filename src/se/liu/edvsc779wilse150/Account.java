package se.liu.edvsc779wilse150;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Account
{
    private String userName;
    private String password;
    private Encrypter encrypter;
    private KeyGen keyGen = new KeyGen();
    private Key key = keyGen.getKey();

    public Account(final String userName, String password)
            throws NoSuchAlgorithmException, NoSuchPaddingException
    {
        this.userName = userName;
        this.password = password;
        this.encrypter = new Encrypter(password, key);
    }

    public byte[] encryptPassword() throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        return encrypter.encryptPassword();
    }

    public String getUserName() {
        return userName;
    }

    public Key getKey() {
        return key;
    }
}
