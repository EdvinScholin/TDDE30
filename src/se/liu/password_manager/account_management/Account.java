package se.liu.password_manager.account_management;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

/**
 * The class enables the AccountList class to add or edit an account without the class knowing the specific account
 * type. The methods are implemented in all the subclasses.
 */

public interface Account
{
    abstract byte[] getPassword();

    abstract String getUsername();

    abstract byte[] getInitVector();

    abstract AccountType getAccountType();

    abstract void editPassword(byte[] newPassword, SecretKey key)
	    throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
	    InvalidParameterSpecException;

    abstract void editUsername(String newUsername);
}
