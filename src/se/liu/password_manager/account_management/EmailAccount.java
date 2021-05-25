package se.liu.password_manager.account_management;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

/**
 * This class represents the email account type which contains an email adress and an encrypted password.
 * This class is a subclass to AbstractAccount
 */

public class EmailAccount extends AbstractAccount
{
    private String email;
    private String domain;

    protected EmailAccount(final String userName, final String email, final String domain, final byte[] plainPassword, final SecretKey key,
			   AccountType accountType)
	    throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
	    InvalidParameterSpecException
    {
	super(email, plainPassword, key, accountType);
	this.email = email;
	this.domain = domain;
    }

    protected EmailAccount(final String userName, final String email, final String domain, final byte[] password, final byte[] initVector, AccountType accountType)
    {
	super(email, password, initVector, accountType);
	this.email = email;
	this.domain = domain;
    }

    public String getEmail() {
	return email;
    }

    public String getDomain() {
	return domain;
    }

    public void editEmail(String newEmail) {
	email = newEmail;
    }

    public void editDomain(String newDomain) {
	domain = newDomain;
    }
}
