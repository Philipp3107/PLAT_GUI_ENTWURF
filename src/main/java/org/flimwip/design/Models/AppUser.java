package org.flimwip.design.Models;

import org.flimwip.design.Documentationhandler.ServiceATT;
import org.flimwip.design.Documentationhandler.ServiceC;
import org.flimwip.design.Documentationhandler.ServiceCR;
import org.flimwip.design.Documentationhandler.ServiceM;

import java.io.Serializable;

@ServiceC(desc = "This class represents an App user. It encapsulates the user first name, last name, username, and password. The class uses annotations such as ServiceC, ServiceATT, and ServiceM for describing the class, attributes, and methods respectively. It includes a constructor, getters and setters for each of the attributes, and a static method used for testing.")
public class AppUser implements Serializable {
    @ServiceATT(desc = "The first name of the User using this App. This is also used for the Job history", type = "String")
    private String first_name;

    @ServiceATT(desc = "The last name of the User using this App. This is also used for the Job History", type="String")
    private String last_name;

    @ServiceATT(desc = "The username of the User using this App. This is also used for Login.", type="String")
    private String username;

    @ServiceATT(desc = "The password the User set themselves", type="String")
    private String password;

    @ServiceCR(desc = "Constructor of the AppUser class", params = {"String: firstname", "String: last_name", "String: username", "String: password"})
    public AppUser(String first_name, String last_name, String username, String password){
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
    }


    @ServiceM(desc = "Method to get first name", category = "Getter", params = {"None"}, returns = "String: first_name", thrown = "None")
    public String get_first_name() {
        return first_name;
    }

    @ServiceM(desc = "Method to set first name",category = "Setter", params = {"String: first_name"}, returns = "None", thrown = "None")
    public void set_first_name(String first_name) {
        this.first_name = first_name;
    }

    @ServiceM(desc = "Method to get last name",category = "Getter", params = {"None"}, returns = "String: last_name", thrown = "None")
    public String get_last_name() {
        return last_name;
    }

    @ServiceM(desc = "Method to set last name",category = "Setter", params = {"String: last_name"}, returns = "None", thrown = "None")
    public void set_last_name(String last_name) {
        this.last_name = last_name;
    }

    @ServiceM(desc = "Method to get username",category = "Getter", params = {"None"}, returns = "String: username", thrown = "None")
    public String get_username() {
        return username;
    }

    @ServiceM(desc = "Method to set username",category = "Setter", params = {"String: username"}, returns = "None", thrown = "None")
    public void set_username(String username) {
        this.username = username;
    }

    @ServiceM(desc = "Method to get password",category = "Getter", params = {"None"}, returns = "String: password", thrown = "None")
    public String get_password() {
        return password;
    }

    @ServiceM(desc = "Method to set password",category = "Setter", params = {"String: password"}, returns = "None", thrown = "None")
    public void set_password(String password) {
        this.password = password;
    }


    @Override
    @ServiceM(desc = "Overriding the \"toString\" method to provide readability for the User", category = "Method", params = {"None"}, returns = "String: all Elements of the user concatinated as String", thrown = {"None"})
    public String toString(){
        return "[" + this.first_name + " " + this.last_name + "],  Username: " + this.username + ", Password: " + this.password;
    }
}