package org.flimwip.design.Models;

public class PopulationFile {

    private String name;
    private String from;
    private String to;
    private boolean from_absolute;
    private boolean to_absolute;


    public PopulationFile(){

    }

    public String get_name(){
        return this.name;
    }

    public String get_from(){
        return this.from;
    }

    public boolean get_from_absolut(){
        return this.from_absolute;
    }

    public String get_to(){
        return this.to;
    }

    public boolean get_to_absolute(){
        return this.to_absolute;
    }


    public void set_from(String from){
        this.from = from;
    }

    public void set_from_absolute(boolean absolut){
        this.from_absolute = absolut;
    }

    public void set_to(String to){
        this.to = to;
    }

    public void set_to_absolute(boolean absolut){
        this.to_absolute = absolut;
    }

    public void set_name(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return "[name: " + this.name + ", von: " + this.from +", abs:  " + this.from_absolute + ", zu: " + this.to + ", abs: "  + this.to_absolute +"]";
    }




}