/**
 * The PopulationFile class represents the data of a population
 * file in the application.
 * <p>
 * This class holds the attributes name, from, to, from_absolute,
 * and to_absolute which represent details about the population.
 */
package org.flimwip.design.Models;

public class PopulationFile {
    /**
     * The name of the population file.
     */
    private String name;

    /**
     * The attribute 'from' represents the starting point of the population file.
     */
    private String from;

    /**
     * The attribute 'to' represents the end point of the population file.
    */
    private String to;

    /**
     * The attribute 'from_absolute' is a flag that indicates whether
     * the 'from' attribute is absolute or not.
     */
    private boolean from_absolute;

    /**
     * The attribute 'to_absolute' is a flag that indicates whether
     * the 'to' attribute is absolute or not.
     */
    private boolean to_absolute;

    /**
    * Default constructor.
    */
    public PopulationFile(){

    }

    /**
    * Getter for the name attribute.
    *
    * @return String The name of the population file.
    */
    public String get_name(){
        return this.name;
    }

    /**
    * Getter for the 'from' attribute.
    *
    * @return String The starting point of the population file.
    */
    public String get_from(){
        return this.from;
    }

    /**
    * Getter for the 'from_absolute' attribute.
    *
    * @return boolean Whether 'from' is absolute or not.
    */
    public boolean get_from_absolut(){
        return this.from_absolute;
    }

    /**
    * Getter for the 'to' attribute.
    *
    * @return String The end point of the population file.
    */
    public String get_to(){
        return this.to;
    }

    /**
    * Getter for the 'to_absolute' attribute.
    *
    * @return boolean Whether 'to' is absolute or not.
    */
    public boolean get_to_absolute(){
        return this.to_absolute;
    }

    /**
    * Setter for the 'from' attribute.
    *
    * @param from The starting point of the population file.
    */
    public void set_from(String from){
        this.from = from;
    }

    /**
    * Setter for the 'from_absolute' attribute.
    *
    * @param absolut Whether 'from' is absolute or not.
    */
    public void set_from_absolute(boolean absolut){
        this.from_absolute = absolut;
    }

    /**
    * Setter for the 'to' attribute.
    *
    * @param to The end point of the population file.
    */
    public void set_to(String to){
        this.to = to;
    }

    /**
    * Setter for the 'to_absolute' attribute.
    *
    * @param absolut Whether 'to' is absolute or not.
    */
    public void set_to_absolute(boolean absolut){
        this.to_absolute = absolut;
    }

    /**
    * Setter for the 'name' attribute.
    *
    * @param name The name of the population file.
    */
    public void set_name(String name){
        this.name = name;
    }

    /**
    * Converts the PopulationFile object to a String.
    *
    * @return A String representation of the PopulationFile object.
    */
    @Override
    public String toString(){
        return "[name: " + this.name + ", von: " + this.from +", abs:  "+ this.from_absolute + ", zu: " + this.to + ", abs: "  + this.to_absolute +"]";
    }
}