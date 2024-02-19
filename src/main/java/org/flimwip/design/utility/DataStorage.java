package org.flimwip.design.utility;

import org.flimwip.design.Models.CheckoutModel;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class DataStorage {

    private String filename;

    private HashMap<String, ArrayList<CheckoutModel>> kassen = new HashMap<>();
    public DataStorage(String filename){

        this.filename = filename;
        init();
    }

    public void init(){

        ArrayList<CheckoutModel> model = new ArrayList<>();
        String line = "";
        String temp_nl = "300";
        InputStream stream = CredentialManager.class.getClassLoader().getResourceAsStream(filename);
        try(BufferedReader br = new BufferedReader(new InputStreamReader(stream))){
                while((line = br.readLine()) != null){
                    //INPUT -> 547;VILLINGEN-SCHWENNINGEN;27 - SUED;DE0547CPOS20002;DE0547CPOS20002;27.10.2020;LIVE
                    String[] splitted = line.split(";");
                    String nl = splitted[0];
                    //System.out.println("NL: " + nl);
                    String nl_name = splitted[1];
                    //System.out.println("NL name: " + nl_name);
                    String region = splitted[2].split(" ")[2];
                    boolean mobil = splitted[3].contains("(Mobil)");
                    //System.out.println(mobil);
                    String checkout = splitted[4].substring(12, 15);
                    //System.out.println("Checkout: " + checkout);
                    String version = splitted[8];
                    //System.out.println("Version: " + version);
                    CheckoutModel k = new CheckoutModel(nl, nl_name, region, mobil, checkout, version);

                                     //MANNHEIM("Mannheim", 666);


                    if(temp_nl.equals(nl)){
                        //System.out.println("Added " + checkout + "to NL " + temp_nl);
                        model.add(k);
                    }else{
                        //System.out.println("NL " + temp_nl + "fertig. Neue wird erzeugt mit id: " + nl);
                    kassen.put(temp_nl, model);
                    String temp = nl_name.replace("-", "_");
                        System.out.println(temp + "( \"" + temp +"\", " + nl + "),");
                    model = new ArrayList<>();
                    model.add(k);
                    temp_nl = nl;
                    }
                }







        } catch (FileNotFoundException e) {
            System.out.println("LogFile " + this.filename + " could not be found.");
        } catch (IOException e) {
            System.out.println("An exception occoured.");
        }
    }

    public ArrayList<CheckoutModel> getcheckouts(String nl){
        if(kassen.containsKey(nl)){
            return kassen.get(nl);
        }else{
            return null;
        }
    }

    public Set<String> list_keys(){
        return kassen.keySet();
    }

    public String get_nl_name(String nl){
        if(kassen.containsKey(nl)){
            return kassen.get(nl).get(0).branch_name();
        }else{
            return null;
        }
    }

    public String get_nl_region(String nl){
        if(kassen.containsKey(nl)){
            return kassen.get(nl).get(0).region();
        }else{
            return null;
        }
    }
}
