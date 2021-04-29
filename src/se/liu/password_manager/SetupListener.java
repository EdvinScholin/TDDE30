package se.liu.password_manager;

/**
 * Interface for all listeners to SetupWindow. If something happens in
 * SetupWindow will all SetupListeners be notified.
 */
public interface SetupListener
{
    public void registrationAttempted();
}
