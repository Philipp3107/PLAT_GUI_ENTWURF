package org.flimwip.design.utility;

import org.flimwip.design.Models.CheckoutModel;
import org.flimwip.design.Views.Temp.Branch;
import org.flimwip.design.Views.Temp.Checkout;

import java.io.*;
import java.util.*;

/**
 * This class serves as a data storage for {@link Checkout}.
 * It provides methods for initializing data from a file, obtaining checkouts for a branch,
 * getting branch name and region, and listing all keys of the stored data.
 *
 * {@code filename} is used to describe the location of the file used for data initialization.
 *
 * The data is stored in the form of a HashMap, with {@code org.flimwip.design.Views.Temp.Branch} as
 * key and an ArrayList of {@link Checkout}s as value.
 *
 * It depends on {@link CheckoutModel} class for its structure to store checkouts.
 *
 * Fields:
 * private String filename;
 * private HashMap<String, ArrayList<CheckoutModel>> kassen;
 *
 * Methods:
 * public DataStorage(String filename)
 * public void init()
 * public ArrayList<CheckoutModel> getcheckouts(String nl)
 * public Set<String> list_keys()
 * public String get_nl_name(String nl)
 * public String get_nl_region(String nl)
 *
 * Usage:
 * DataStorage ds = new DataStorage("NL_Liste.csv");
 *
 * Dependent classes:
 * Main.java
 * Analyse.java
 */
public class DataStorage {

    private PKLogger logger = new PKLogger(this.getClass());

    /**
     * Represents the filename of a file in the system.
     * The filename should be a string value that uniquely identifies the file.
     */
    private String filename;

    private List<Branch> branches;

    /**
     * HashMap variable used to store checkout data.
     * Key: String - represents the branch ID.
     * Value: ArrayList of CheckoutModel - contains the checkout model objects for a specific branch.
     * The CheckoutModel record contains the following information:
     * - branch: String - the branch ID.
     * - branch_name: String - the name of the branch.
     * - region: String - the region of the branch.
     * - mobil: boolean - indicates whether the checkout is mobile or not.
     * - checkout_id: String - the ID of the checkout.
     * - version: String - the current software version of the checkout.
     *
     * This variable is used to store checkout data in the DataStorage class.
     * The Checkout class uses this variable to display and manage checkout UI elements.
     * The CheckoutView class interacts with this variable to update the display and handle user interactions.
     */
    private HashMap<String, ArrayList<CheckoutModel>> kassen = new HashMap<>();

    /**
     * Constructs a DataStorage object with the given filename.
     * Initializes the data storage by reading the contents of the specified file.
     *
     * @param filename the name of the file to read from
     */
    public DataStorage(String filename){
        this.logger.set_Level(LoggingLevels.FINE);
        this.filename = filename;
        init();

        build_branches();
    }

    public HashMap<String, ArrayList<String>> fetch_hash_map(){
        HashMap<String, ArrayList<String>> new_kassen = new HashMap<>();
        List<String> nls = new ArrayList<>(kassen.keySet());
        Collections.sort(nls);

        nls.forEach(key -> {
            List<CheckoutModel> cm = new ArrayList<>(kassen.get(key));
            ArrayList<String> new_cm = new ArrayList<>();
            cm.forEach(cm_key -> {
                new_cm.add(cm_key.checkout_id());
            });
            new_kassen.put(key, new_cm);
        });
        return new_kassen;
    }

    /**
     * Initializes the data storage by reading the contents of the specified file.
     */
    public void init(){

        ArrayList<CheckoutModel> model = new ArrayList<>();
        String line = "";
        String temp_nl = "102";
        InputStream stream = CredentialManager.class.getClassLoader().getResourceAsStream(filename);
        try(BufferedReader br = new BufferedReader(new InputStreamReader(stream))){
                while((line = br.readLine()) != null){

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
            logger.log(LoggingLevels.ERROR, "Logfile " + this.filename + " could not be found!");
        } catch (IOException e) {
            logger.log(LoggingLevels.FATAL, "An " + e.getClass().getName() + " occured. Cause: " + e.getCause());
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

    public List<Branch> get_branches(){
        return branches;
    }

    private void build_branches(){
        this.branches = new ArrayList<>();
        Comparator<String> c = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };
        ArrayList<String> keys = new ArrayList<>(list_keys());
        keys.sort(c);
        for(String key: keys){
            Branch b = new Branch(key, get_nl_name(key), get_nl_region(key), getcheckouts(key), false, null);
            this.branches.add(b);
        }
    }
}