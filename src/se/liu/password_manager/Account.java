package se.liu.password_manager;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 *  Represents an account which contains the encrypted password and the username for said account.
 *  The class is able to change account information.
 */

public class Account
{
    private String username;
    private byte[] password;

    public Account(final String userName, final byte[] plainPassword, final Key key)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException
    {
        this.username = userName;
        Encrypter encrypter = new Encrypter();
        this.password = encrypter.cryptoPassword(plainPassword, key);
    }

    public String getUsername() {
        return username;
    }

    public byte[] getPassword() {
        return password;
    }

    // Ska vi ha det här här?
    public void editPassword(byte[] newPassword, Key key)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException
    {
        Encrypter encrypter = new Encrypter(); // Har den här för att inte den ska sparas i json-filen
        password = encrypter.cryptoPassword(newPassword, key);
    }

    public void editUsername(String newUsername) {
        username = newUsername;
    }
}
