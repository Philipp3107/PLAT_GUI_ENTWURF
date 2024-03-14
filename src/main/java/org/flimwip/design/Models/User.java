package org.flimwip.design.Models;

import org.flimwip.design.Controller.UserController;

/**
 * The user class represents the user of the system
 */
public class User
{
    /**
     *  Name of the user
     */
    private String name;

    /**
     * Description of the user
     */
    private String description;

    /**
     * Username that the user uses to login into the system
     */
    private String username;

    /**
     * Password that the user uses to login into the system
     */
    private String password;

    /**
     * Determines if the user is selected or not
     */
    private boolean selected;

    /**
     * The controller that manages user activities
     */
    private UserController user_controller;

    /**
     * Constructs a user with the specified name, description, username, password,
     * selected status and the controller.
     */
    public User(String name, String description, String username, String password, boolean selected, UserController user_controller){
        this.name = name;
        this.user_controller = user_controller;
        this.description = description;
        this.username = username;
        this.password = password;
        this.selected = selected;
    }

    /**
     * Gets the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the user
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the user
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the username that the user uses to login into the system
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username that the user uses to login into the system
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password that the user uses to login into the system
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password that the user uses to login into the system
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Checks if the user is selected or not
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selected status of the user
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}