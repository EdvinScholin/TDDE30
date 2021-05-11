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

public class StandardAccount extends AbstractAccount
{
    protected StandardAccount(final String userName, final byte[] plainPassword, final SecretKey key)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidParameterSpecException
    {
        super(userName, plainPassword, key);
    }
}
