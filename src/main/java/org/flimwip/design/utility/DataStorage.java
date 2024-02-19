package org.flimwip.design.utility;

import org.flimwip.design.Models.CheckoutModel;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class DataStorage {

    private String filename;

    /**
     * Here are all {@link org.flimwip.design.Views.Checkout}s stored for their {@link org.flimwip.design.Views.Branch}
     */
    private HashMap<String, ArrayList<CheckoutModel>> kassen = new HashMap<>();
    public DataStorage(String filename){

        this.filename = filename;
        init();
    }

    /**
     * Splits all the Checkouts given in the File into its key areas and puts them into the HashMap as Key: Branch number and ArrayList of CheckoutModels
     */
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
                    String nl_name = splitted[1];
                    String region = splitted[2].split(" ")[2];
                    boolean mobil = splitted[3].contains("(Mobil)");
                    String checkout = splitted[4].substring(12, 15);
                    String version = splitted[8];
                    CheckoutModel k = new CheckoutModel(nl, nl_name, region, mobil, checkout, version);

                    if(temp_nl.equals(nl)){
                        model.add(k);
                    }else{
                    kassen.put(temp_nl, model);
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

    /**
     * Returns all the Checkouts for the given Branch
     * @param nl String: Branchnumber
     * @return {@code AraryList<CheckoutModels>}
     */
    public ArrayList<CheckoutModel> getcheckouts(String nl){
        if(kassen.containsKey(nl)){
            return kassen.get(nl);
        }else{
            return null;
        }
    }

    /**
     * Provides all the Keys from the HasMap
     * @return {@code Set<String>}
     */
    public Set<String> list_keys(){
        return kassen.keySet();
    }

    /**
     * Returns the Branches name by providing the Branchnumber
     * @param nl the Branch number
     * @return the name of the Branch
     */
    public String get_nl_name(String nl){
        if(kassen.containsKey(nl)){
            return kassen.get(nl).get(0).branch_name();
        }else{
            return null;
        }
    }
    /**
     * Returns the Branches region by providing the Branchnumber
     * @param nl the Branch number
     * @return the region of the Branch
     */
    public String get_nl_region(String nl){
        if(kassen.containsKey(nl)){
            return kassen.get(nl).get(0).region();
        }else{
            return null;
        }
    }
}
