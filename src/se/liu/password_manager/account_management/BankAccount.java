package se.liu.password_manager.account_management;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

/**
 * This class represents the bank account type which contains a social security number (sSN) and an encrypted password.
 * This class is a subclass to AbstractAccount
 */

public class BankAccount extends AbstractAccount
{
    protected BankAccount(final String socialSecurityNumber, final byte[] plainPassword, final SecretKey key, AccountType accountType)
	    throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
	    InvalidParameterSpecException
    {
	super(socialSecurityNumber, plainPassword, key, accountType);
    }

    protected BankAccount(final String socialSecurityNumber, final byte[] password, final byte[] initVector, AccountType accountType)
    {
	super(socialSecurityNumber, password, initVector, accountType);
    }
}
