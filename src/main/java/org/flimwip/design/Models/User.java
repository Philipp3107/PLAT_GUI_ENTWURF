package org.flimwip.design.Models;

import org.flimwip.design.Controller.UserController;

public class User {
    private String name;
    private String description;
    private String username;
    private String password;
    private boolean selected;

    private UserController user_controller;

    public User(String name, String description, String username, String password, boolean selected, UserController user_controller){
        this.name = name;
        this.user_controller = user_controller;
        this.description = description;
        this.username = username;
        this.password = password;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }






}
