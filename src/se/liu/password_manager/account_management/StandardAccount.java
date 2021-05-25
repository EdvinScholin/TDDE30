package se.liu.password_manager.account_management;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

/**
 * This class represents the standard account type which contains a username and an encrypted password.
 * This class is a subclass to AbstractAccount.
 */

public class StandardAccount extends AbstractAccount
{
    protected StandardAccount(final String userName, final byte[] plainPassword, final SecretKey key, AccountType accountType)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidParameterSpecException
    {
        super(userName, plainPassword, key, accountType);
    }

    protected StandardAccount(final String userName, final byte[] password, final byte[] initVector, AccountType accountType)
    {
        super(userName, password, initVector, accountType);
    }
}
