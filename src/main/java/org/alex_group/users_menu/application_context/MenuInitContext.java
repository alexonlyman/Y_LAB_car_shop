package org.alex_group.users_menu.application_context;

import org.alex_group.users_menu.menu_strategy.*;

public class MenuInitContext {
    private static final MenuInitContext instance = new MenuInitContext();
    private final MenuForRegister register;
    private final MenuForAuth auth;
    private final MenuForAdmin menuForAdmin;
    private final MenuForUser menuForUser;
    private final MenuForManager menuForManager;


    private MenuInitContext() {
        this.register = new MenuForRegister();
        this.auth = new MenuForAuth();
        this.menuForAdmin = new MenuForAdmin();
        this.menuForUser = new MenuForUser();
        this.menuForManager = new MenuForManager();
    }

    public static MenuInitContext getInstance() {
        return instance;
    }


    public MenuForRegister getRegister() {
        return register;
    }

    public MenuForAuth getAuth() {
        return auth;
    }

    public MenuForAdmin getMenuForAdmin() {
        return menuForAdmin;
    }

    public MenuForUser getMenuForUser() {
        return menuForUser;
    }

    public MenuForManager getMenuForManager() {
        return menuForManager;
    }
}
