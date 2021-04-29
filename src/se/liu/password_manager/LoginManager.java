package se.liu.password_manager;

/**
 * This class is resposible for the login by authenticating the password.
 * The class contains the correct password.
 */
public class LoginManager
{
    private String mainPassword = "hej";

    public boolean authenticateLogin(String testedPassword) {
        if (testedPassword.equals(mainPassword)) {
            return true;
	}
        return false;
    }
}
