package se.liu.edvsc779wilse150;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.security.Key;

public class Account
{
    private String username;
    private byte[] password;

    public Account(final String userName, final String plainPassword, final Key key)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException
    {
        this.userName = userName;
        this.password = password;
        this.encrypter = new Encrypter(password, key);
    }

    public String getUsername() {
        return username;
    }

    public byte[] getPassword() {
        return password;
    }

    // Ska vi ha det här här?
    public void editPassword(String newPassword, Key key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Encrypter encrypter = new Encrypter(); // Har den här för att inte den ska sparas i json-filen
        password = encrypter.encryptPassword(newPassword, key);
    }

    public void editUsername(String newUsername) {
        username = newUsername;
    }
}
