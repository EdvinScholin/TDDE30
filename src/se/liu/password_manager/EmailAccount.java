package se.liu.password_manager;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

public class EmailAccount extends AbstractAccount
{
    protected EmailAccount(final String email, final byte[] plainPassword, final SecretKey key, AccountType accountType)
	    throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
	    InvalidParameterSpecException
    {
	super(email, plainPassword, key, accountType);
    }

    protected EmailAccount(final String email, final byte[] password, final byte[] initVector, AccountType accountType)
    {
	super(email, password, initVector, accountType);
    }

}
