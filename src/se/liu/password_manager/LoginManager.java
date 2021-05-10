package se.liu.password_manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

/**
 * This class is resposible for the login by authenticating the password.
 * The class contains the correct password.
 */
public class LoginManager
{
    private static final String FILE_NAME = "." + File.separator + "HashedPassword.txt";
    private HashEngine hashEngine = new HashEngine();

    public boolean authenticateLogin(String testedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        byte[] hashedTestedPassword = getHashFromTestedPassword(testedPassword);
        byte[] correctPassword = readHashPasswordFile();

        if (Arrays.equals(hashedTestedPassword, correctPassword)) {
            return true;
	}
        return false;
    }

    private byte[] readHashPasswordFile() throws IOException {
        File file = new File(FILE_NAME);
        return Files.readAllBytes(file.toPath());
    }

    private byte[] getHashFromTestedPassword(String testedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return hashEngine.generateHash(testedPassword);
    }
}
