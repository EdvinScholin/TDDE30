package se.liu.password_manager;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

public class BankAccount extends AbstractAccount
{
    protected BankAccount(final String ssn, final byte[] plainPassword, final SecretKey key, AccountType accountType)
	    throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
	    InvalidParameterSpecException
    {
	super(ssn, plainPassword, key, accountType);
    }

    protected BankAccount(final String ssn, final byte[] password, final byte[] initVector, AccountType accountType)
    {
	super(ssn, password, initVector, accountType);
    }
}
