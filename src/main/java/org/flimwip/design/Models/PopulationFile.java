/**
 * The PopulationFile class represents the data of a population
 * file in the application.
 * <p>
 * This class holds the attributes name, from, to, from_absolute,
 * and to_absolute which represent details about the population.
 */
package org.flimwip.design.Models;

/**
 * The PopulationFile class represents a population file.
 */

import org.flimwip.design.Documentationhandler.ServiceATT;
import org.flimwip.design.Documentationhandler.ServiceC;
import org.flimwip.design.Documentationhandler.ServiceCR;
import org.flimwip.design.Documentationhandler.ServiceM;

@ServiceC(desc = "The PopulationFile class represents the data of a population file in the application. This class holds the attributes name, from, to, from_absolute, and to_absolute which represent details about the population.")
public class PopulationFile {

    @ServiceATT(desc = "The name of the population file.", type = "String")
    private String name;

    @ServiceATT(desc = "The attribute 'from' represents the starting point of the population file.", type = "String")
    private String from;

    @ServiceATT(desc = "The attribute 'to' represents the end point of the population file.", type = "String")
    private String to;

    @ServiceATT(desc = "The attribute 'from_absolute' is a flag that indicates whether the 'from' attribute is absolute or not.", type = "boolean")
    private boolean from_absolute;


    @ServiceATT(desc = "The attribute 'to_absolute' is a flag that indicates whether the 'to' attribute is absolute or not.", type = "boolean")
    private boolean to_absolute;

    @ServiceCR(desc = "Constructor of the PopulationFile class", params = {"None"})
    public PopulationFile() {

    }

    @ServiceM(desc = "Retrieves the name of the population file.", category = "Getter", params = {"None"}, returns = "String: name", thrown = {"None"})
    public String get_name() {
        return this.name;
    }

    @ServiceM(desc = "Retrieves the 'from' value of the population file.", category = "Getter", params = {"None"}, returns = "String: from", thrown = {"None"})
    public String get_from() {
        return this.from;
    }

    @ServiceM(desc = "This method is used to check if the 'from' value is absolute.", category = "Getter", params = {"None"}, returns = "boolean: from_absolute", thrown = {"None"})
    public boolean get_from_absolut(){
        return this.from_absolute;
    }

    @ServiceM(desc = "This method is used to check if the 'to' value is absolute.", category = "Getter", params = {"None"}, returns = "boolean: to_absolute", thrown = {"None"})
    public boolean get_to_absolute() {
        return to_absolute;
    }

    @ServiceM(desc = "This method is used to get the 'to' value of the population file.", category = "Getter", params = {"None"}, returns = "String: to", thrown = {"None"})
    public String get_to(){
        return this.to;
    }

    @ServiceM(desc = "This method is used to set the 'to' value as absolute.", category = "Setter", params = {"boolean"}, returns = "void", thrown = {"None"})
    public void set_to_absolute(boolean to_absolute) {
        this.to_absolute = to_absolute;
    }

    @ServiceM(desc = "This method is used to set the 'from' value of the population file.", category = "Setter", params = {"String: from"}, returns = "void", thrown = {"None"})
    public void set_from(String from){
        this.from = from;
    }

    @ServiceM(desc = "This method is used to set the 'from' value as absolute.", category = "Setter", params = {"boolean: from_absolute"}, returns = "void", thrown = {"None"})
    public void set_from_absolute(boolean absolut){
        this.from_absolute = absolut;
    }

    @ServiceM(desc = "This method is used to set the 'to' value of the population file.", category = "Setter", params = {"String: to"}, returns = "void", thrown = {"None"})
    public void set_to(String to){
        this.to = to;
    }

    @ServiceM(desc = "This method is used to set the name of the population file.", category = "Setter", params = {"String: name"}, returns = "void", thrown = {"None"})
    public void set_name(String name){
        this.name = name;
    }


    @Override
    @ServiceM(desc = "This method is used to get the string representation of the population file.", category = "Method", params = {"None"}, returns = "String: description of this class in Text", thrown = {"None"})
    public String toString() {
        return "[name: " + this.name + ", von: " + this.from + ", abs:  " + this.from_absolute + ", zu: " + this.to + ", abs: " /* + this.to_absolute*/ + "]";
    }
}