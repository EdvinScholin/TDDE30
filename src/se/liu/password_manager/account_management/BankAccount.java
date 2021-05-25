package se.liu.password_manager.account_management;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

/**
 * This class represents the bank account type which contains a social security number and an encrypted password.
 * This class is a subclass to AbstractAccount
 */

public class BankAccount extends AbstractAccount
{
    private String bankAccountNumber;

    protected BankAccount(final String socialSecurityNumber, final String accountNumber, final byte[] plainPassword,
			  final SecretKey key, AccountType accountType)
	    throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
	    InvalidParameterSpecException
    {
	super(socialSecurityNumber, plainPassword, key, accountType);
	this.bankAccountNumber = accountNumber;
    }

    protected BankAccount(final String socialSecurityNumber, final String accountNumber, final byte[] password,
			  final byte[] initVector, AccountType accountType)
    {
	super(socialSecurityNumber, password, initVector, accountType);
	this.bankAccountNumber = accountNumber;
    }

    public String getBankAccountNumber() {
	return bankAccountNumber;
    }

    public void editBankAccountNumber(String newAccountNumber) {
        bankAccountNumber = newAccountNumber;
    }
}
