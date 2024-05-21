package org.flimwip.design.Models;

import org.flimwip.design.Controller.UserController;
import org.flimwip.design.Documentationhandler.ServiceATT;
import org.flimwip.design.Documentationhandler.ServiceC;
import org.flimwip.design.Documentationhandler.ServiceCR;
import org.flimwip.design.Documentationhandler.ServiceM;

/**
 * The user class represents the user of the system
 */
@ServiceC(desc = "The user class represents the user of the system. It is used to build up connections to all Checkouts and other systems.")
public class User
{
    /**
     *  Name of the user
     */
    @ServiceATT(desc = "Name of the user", type = "String")
    private String name;

    /**
     * Description of the user
     */
    @ServiceATT(desc="The Description provided for the User", type="String")
    private String description;

    /**
     * Username that the user uses to login into the system
     */
    @ServiceATT(desc="Username that the user uses to login into the system.", type="String")
    private String username;

    /**
     * Password that the user uses to login into the system
     */
    @ServiceATT(desc="Password that the user uses to login into the system.", type="String")
    private String password;

    /**
     * Determines if the user is selected or not
     */
    @ServiceATT(desc="Determines if the user is selected or not.", type="String")
    private boolean selected;

    /**
     * The controller that manages user activities
     */
    @ServiceATT(desc="The UserController used to switch Users for more than one view. If the user gets switched to another this Controller holds the current information about that.", type = "UserController", related = {"UserController"})
    private UserController user_controller;

    /**
     * Constructs a user with the specified name, description, username, password,
     * selected status and the controller.
     */
    @ServiceCR(desc = "Constructor of a single user.",
               params = {"name: String -> The name given for the User",
                         "description: String -> The Description for the user",
                         "username: String -> The official Username for this user",
                         "password: String -> The official Password for this user.",
                         "selected: boolean -> Switch for if the User is selected or not",
                         "user_controller: UserController -> UserController that holds the Users and switches them."})
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
    @ServiceM(desc="Gets the name of the user.",
             category="Getter",
             params={"None"},
             returns="String -> the name of the user",
             thrown={"None"},
             related={"None"})
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user
     */
    @ServiceM(desc="Sets the Username",
             category="Setter",
             params={"name: String -> The Username to set to."},
             returns="void",
             thrown={"None"},
             related={"None"})
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the user
     */
    @ServiceM(desc="Gets the description of the user.",
             category="Getter",
             params={"None"},
             returns="String -> The description of the user.",
             thrown={"None"},
             related={"None"})
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the user
     */
    @ServiceM(desc="Sets the descriptoin of the User",
             category="Setter",
             params={"description: String -> Sets the descriptoin of the User"},
             returns="void",
             thrown={"None"},
             related={"None"})
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the username that the user uses to login into the system
     */
    @ServiceM(desc="Gets the username that the user uses to login into the system",
             category="Getter",
             params={"None"},
             returns="String -> The Username.",
             thrown={"None"},
             related={"None"})
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username that the user uses to login into the system
     */
    @ServiceM(desc="Sets the username that the user uses to login into the system.",
             category="Setter",
             params={"username: String -> The username that the user uses to login into the system."},
             returns="void",
             thrown={"None"},
             related={"None"})
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password that the user uses to login into the system
     */
    @ServiceM(desc="Gets the password that the user uses to login into the system",
             category="Getter",
             params={"None"},
             returns="String -> The password that the user uses to login into the system.",
             thrown={"None"},
             related={"None"})
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password that the user uses to login into the system
     */
    @ServiceM(desc="Sets the password that the user uses to login into the system.",
             category="Setter",
             params={"None"},
             returns="void",
             thrown={"None"},
             related={"None"})
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Checks if the user is selected or not
     */
    @ServiceM(desc="",
             category="Method",
             params={"None"},
             returns="",
             thrown={"None"},
             related={"None"})
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selected status of the user
     */
    @ServiceM(desc="",
             category="Method",
             params={"None"},
             returns="",
             thrown={"None"},
             related={"None"})
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}