package se.liu.password_manager;

public class Login
{
    private String mainPassword = "hej";

    public boolean authenticateLogin(String testedPassword) {
        if (testedPassword.equals(mainPassword)) {
            return true;
	}
        return false;
    }
}
