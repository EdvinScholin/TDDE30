package se.liu.password_manager;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

public interface Account
{
    /*
    abstract byte[][] initPassword(byte[] password, SecretKey key)
	    throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException,
	    InvalidParameterSpecException, InvalidKeyException;

     */

    abstract byte[] getPassword();

    abstract String getUsername();

    abstract byte[] getInitVector();

    abstract void editPassword(byte[] newPassword, SecretKey key)
	    throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
	    InvalidParameterSpecException;

    abstract void editUsername(String newUsername);
}
