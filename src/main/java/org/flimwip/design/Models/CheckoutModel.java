package org.flimwip.design.Models;

public class CheckoutModel{

private String branch, branch_name, region, checkout_id, betriebsstelle, hostname, ip, modell, os;
    public CheckoutModel(String branch, String branch_name, String region, String checkout_id, String betriebsstelle, String hostname, String ip, String modell, String os) {
    this.branch = branch;
    this.branch_name = branch_name;
    this.region = region;
    this.checkout_id = checkout_id;
    this.betriebsstelle = betriebsstelle;
    this.hostname = hostname;
    this.ip = ip;
    this.modell = modell;
    this.os = os;

}


    public String branch(){
        return this.branch;
    }

    public String branch_name(){
        return this.branch_name;
    }

    public String region(){
        return this.region;
    }

    public String checkout_id(){
        return this.checkout_id;
    }

    public String betriebsstelle(){
        return this.betriebsstelle;
    }

    public String hostname(){
        return this.hostname;
    }

    public String ip(){
        return this.ip;
    }
    public String modell(){
        return this.modell;
    }
    public String os(){
        return this.os;
    }



public static CheckoutModel generate_dummy_model(){
    return new CheckoutModel("", "", "", "", "", "", "", "", "");
}

}