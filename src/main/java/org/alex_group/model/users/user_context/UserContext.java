package org.alex_group.model.users.user_context;

import org.alex_group.model.users.User;
/**
 * UserContext maintains the current authenticated user for the current thread.
 */
public class UserContext {

    /**
     * Gets the current user for the current thread.
     *
     * @return the current user
     */
    private static User currentUser;

    /**
     * Sets the current user for the current thread.
     *
     *
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Clears the current user for the current thread.
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
