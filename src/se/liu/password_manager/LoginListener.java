package se.liu.password_manager;

/**
 * Interface for all listeners to LoginWindow. If something happens in
 * LoginWindow will all SetupListeners be notified.
 */
public interface LoginListener
{
    public void loginConfirmed();
}
