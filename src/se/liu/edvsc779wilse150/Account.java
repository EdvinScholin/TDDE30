package se.liu.edvsc779wilse150;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Account
{
    private String userName;
    private String password;
    private Encrypter encrypter;

    public Account(final String userName, String password)
            throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException
    {
        this.userName = userName;
        this.password = password;
        this.encrypter = new Encrypter(password);
    }

    public byte[] encryptPassword() throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        return encrypter.encryptPassword();
    }

    public String getUserName() {
        return userName;
    }
}
