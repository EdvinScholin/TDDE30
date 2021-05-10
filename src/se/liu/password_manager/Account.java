package se.liu.password_manager;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

/**
 *  Represents an account which contains the encrypted password and the username for said account.
 *  The class is able to change account information.
 */

public class Account
{
    private String username;
    private byte[] password, initVector;

    public Account(final String userName, final byte[] plainPassword, final SecretKey key)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidParameterSpecException
    {
        this.username = userName;
        byte[][] array = initPassword(plainPassword, key);  // Detta måste vi göra för att om encrypter.cryptoPassword(password, key);
                                                            // körs flera gånger ändras IV:n och kan inte decrypta lösenorden.
        this.password = array[0];
        this.initVector = array[1];
    }

    private byte[][] initPassword(byte[] password, SecretKey key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException,
            InvalidParameterSpecException, InvalidKeyException
    {
        Encrypter encrypter = new Encrypter();
        byte[][] array = encrypter.cryptoPassword(password, key);
        return array;
    }

    public String getUsername() {
        return username;
    }

    public byte[] getPassword() {
        return password;
    }

    public byte[] getInitVector() {
        return initVector;
    }

    public void editPassword(byte[] newPassword, SecretKey key)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidParameterSpecException
    {
        Encrypter encrypter = new Encrypter();
        byte[][] array = encrypter.cryptoPassword(newPassword, key);
        password = array[0];
        initVector = array[1];
    }

    public void editUsername(String newUsername) {
        username = newUsername;
    }
}
