package se.liu.edvsc779wilse150;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.security.Key;

public class Account
{
    private String userName;
    private byte[] password;

    public Account(final String userName, final String plainPassword, final Key key)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException
    {
        this.userName = userName;
        this.password = password;
        this.encrypter = new Encrypter(password, key);
    }

    public String getUserName() {
        return userName;
    }

    public byte[] getPassword() {
        return password;
    }
}
