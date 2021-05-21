package se.liu.password_manager.logic_medium;

import com.google.gson.Gson;
import se.liu.password_manager.cryptography.HashEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

/**
 * This class is resposible for the login by authenticating the password.
 * The class reads the correct password hash from a file and compares it to the users inputted password.
 */

public class LoginManager
{
    private static final String FILE_NAME = "." + File.separator + "HashedPassword.txt";
    private HashEngine hashEngine = new HashEngine();

    public boolean authenticateLogin(String testedPassword) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException,
            FileNotFoundException
    {
        byte[] hashSalt = readHashPasswordFile()[1];
        byte[] hashedTestedPassword = getHashFromTestedPassword(testedPassword, hashSalt);
        byte[] correctPassword = readHashPasswordFile()[0];


        if (Arrays.equals(hashedTestedPassword, correctPassword)) {
            return true;
	}
        return false;
    }

    private byte[][] readHashPasswordFile() throws IOException, FileNotFoundException {
        Gson gson = new Gson();

        try (Reader reader = new FileReader(FILE_NAME)) {
            return gson.fromJson(reader, byte[][].class);

        }
    }

    private byte[] getHashFromTestedPassword(String testedPassword, byte[] hashSalt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return hashEngine.generateHash(testedPassword, hashSalt);
    }
}
