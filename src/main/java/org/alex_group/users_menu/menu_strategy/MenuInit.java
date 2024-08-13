package org.alex_group.users_menu.menu_strategy;

import org.alex_group.users_menu.application_context.MenuInitContext;
/**
 * The {@code MenuInit} interface provides a static context for initializing and accessing
 * the menu management system within the application. It provides a singleton instance of
 * {@link MenuInitContext} that manages the different menus available for various user roles.
 */
public interface MenuInit {

    static final MenuInitContext context = MenuInitContext.getInstance();
}
