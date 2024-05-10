package org.flimwip.design.utility;

import org.flimwip.design.Models.CheckoutModel;
import org.flimwip.design.Views.Temp.Branch;
import org.flimwip.design.Views.Temp.Checkout;
import org.flimwip.design.Documentationhandler.*;

import java.io.*;
import java.util.*;

/**Note:  <p>
 * hallo meine damen und herren, wenn sie das lesen, dann müssen sie dem
 * herrn dr philipp kotte the first dringend eine gehaltserhöhung geben denn wenn wir
 * alle mal radikal ehrlich sind macht er wahrscheinlich viel aufwendigere sachen
 * als bereits fertig studierte informaktiker. was ich sagen will, ist, dass ihr
 * seine arbeit fast geschenkt bekommt und deshalb eine (mindestens) 50% erhöhung des
 * gehalts von nöten ist MFG
 * <p>
 * This class serves as a data storage for {@link Checkout}.
 * It provides methods for initializing data from a file, obtaining checkouts for a branch,
 * getting branch name and region, and listing all keys of the stored data.
 * {@code filename} is used to describe the location of the file used for data initialization.
 * <p>
 * The data is stored in the form of a HashMap, with {@code org.flimwip.design.Views.Temp.Branch} as
 * key and an ArrayList of {@link Checkout}s as value.
 * <p>
 * It depends on {@link CheckoutModel} class for its structure to store checkouts.
 * <p>
 * Fields:
 * private String filename;
 * private HashMap<String, ArrayList<CheckoutModel>> kassen;
 * <p>
 * Methods:
 * public DataStorage(String filename)
 * public void init()
 * public ArrayList<CheckoutModel> getcheckouts(String nl)
 * public Set<String> list_keys()
 * public String get_nl_name(String nl)
 * public String get_nl_region(String nl)
 * <p>
 * Usage:
 * DataStorage ds = new DataStorage("NL_Liste.csv");
 * <p>
 * Dependent classes:
 * Main.java
 * Analyse.java
 */
@ServiceC(desc="This class serves as a data storage for {@link Checkout}. It provides methods for initializing data from a file, obtaining checkouts for a branch, getting branch name and region, and listing all keys of the stored data.",
related={"None"})
public class DataStorage {

    /**
     * The logger variable is an instance of the PKLogger class. It is used for logging
     * messages and exceptions at different logging levels.
     *<p>
     * Sample Usage:
     * // Set the logging level
     * logger.set_Level(LoggingLevels.INFO);
     * <p>
     * // Log a message
     * logger.log(LoggingLevels.INFO, "This is an information message");
     * <p>
     * // Log an exception
     * try {
     *     // code that may throw an exception
     * } catch (Exception e) {
     *     logger.log_exception(e);
     * }
     */
    @ServiceATT(desc="The logger variable is an instance of the PKLogger class. It is used for logging messages and exceptions at different logging levels.",
                type="PKLogger",
                related={"PKLogger"})
    private PKLogger logger = new PKLogger(this.getClass());

    /**
     * Represents the filename of a file in the system.
     * The filename should be a string value that uniquely identifies the file.
     */
    @ServiceATT(desc="Represents the filename of a file in the system. The filename should be a string value that uniquely identifies the file.",
                type="String",
                related={"None"})
    private String filename;

    /**
     * Represents a list of branches.
     */
    @ServiceATT(desc="Represents a list of branches.",
                type="List<Branch>",
                related={"Branch"})
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
    @ServiceATT(desc="HashMap variable used to store checkout data. Key: String - represents the branch ID. Value: ArrayList of CheckoutModel - contains the checkout model objects for a specific branch.",
                type="HashMap<String, ArrayList<String>>",
                related={"None"})
    private HashMap<String, ArrayList<CheckoutModel>> kassen = new HashMap<>();


    // Key = NL-nr , arraylist[0] = Stadt, arraylist[1] = region
    private HashMap<String, ArrayList<String>> branches_new = new HashMap<>();
    // key = nl_nr, arraylist<Checkoutmodel>
    private HashMap<String, ArrayList<CheckoutModel>> checkouts_new = new HashMap<>();


    /**
     * Constructs a DataStorage object with the given filename.
     * Initializes the data storage by reading the contents of the specified file.
     *
     * @param filename the name of the file to read from
     */
    public DataStorage(String filename){
        this.logger.set_Level(LoggingLevels.FINE);
        this.filename = filename;

        fetch_seperate_branches();
        init();
        build_branches();
    }

    public void fetch_seperate_branches(){

        InputStream stream = CredentialManager.class.getClassLoader().getResourceAsStream("NL.csv");
        try(BufferedReader br = new BufferedReader(new InputStreamReader(stream))){
            String s = "";
            while((s=br.readLine()) != null){
                String[] temp = s.split(",");
                ArrayList<String> info = new ArrayList<>();
                info.add(temp[1]);
                info.add(temp[2]);
                branches_new.put(temp[0], info);
                checkouts_new.put(temp[0], new ArrayList<>());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * fetch_hash_map method retrieves a HashMap of type String key and ArrayList of Strings values.
     *
     * @return HashMap<String, ArrayList < String>> - A HashMap with keys representing the branch names and values as an ArrayList of corresponding checkout IDs.
     */
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


    public CheckoutModel get_checkout_codel(String nl, String checkout){
        ArrayList<CheckoutModel> cms = this.checkouts_new.get(nl);
        for (CheckoutModel cm : cms){
            if(cm.hostname().equals(STR."DE0\{nl}CPOS20\{checkout}")){
                return cm;
            }
        }
        return null;
    }
    /**
     * Initializes the data storage by reading the contents of the specified file.
     */
    public void init(){

        ArrayList<CheckoutModel> model = new ArrayList<>();
        String line = "";
        InputStream stream = CredentialManager.class.getClassLoader().getResourceAsStream("export (1).csv");
        try(BufferedReader br = new BufferedReader(new InputStreamReader(stream))){
            int index = 0;
                while((line = br.readLine()) != null){
                    if (index != 0){
                        String[] splitted = line.split(",");
                        String nl = splitted[6];
                        String checkout_id = splitted[4];
                        String betriebsstelle = splitted[7];
                        String ip = splitted[9];
                        String modell = splitted[10];
                        String os = splitted[12];
                        String hostname = splitted[8];
                        String branch_name= "";
                        String region= "";
                        if(!hostname.contains("CGL")) {
                            if (branches_new.containsKey(nl)) {
                                branch_name = branches_new.get(nl).get(0);
                                region = branches_new.get(nl).get(1);
                            }

                            CheckoutModel k = new CheckoutModel(nl, branch_name, region, checkout_id, betriebsstelle, hostname, ip, modell, os);
                            if (checkouts_new.containsKey(nl)) {
                                checkouts_new.get(nl).add(k);
                            } else {
                                checkouts_new.put(nl, new ArrayList<>());
                                checkouts_new.get(nl).add(k);
                            }
                        }
                    }

                    ++index;
                }

        } catch (FileNotFoundException e) {
            logger.log(LoggingLevels.ERROR, "Logfile " + this.filename + " could not be found!");
        } catch (IOException e) {
            logger.log(LoggingLevels.FATAL, "An " + e.getClass().getName() + " occured. Cause: " + e.getCause());
        }
    }

    /**
     * Retrieves the list of checkouts for a specific branch.
     *
     * @param nl the ID of the branch
     * @return ArrayList<CheckoutModel> - the list of checkouts for the specified branch,
     * or null if the branch ID is not found
     */
    public ArrayList<CheckoutModel> getcheckouts(String nl){
        if(checkouts_new.containsKey(nl)){
            return checkouts_new.get(nl);
        }else{
            return null;
        }
    }

    /**
     * Retrieves the set of keys in the kassen HashMap.
     *
     * @return Set<String> - the set of keys in the kassen HashMap
     */
    public Set<String> list_keys(){
        return branches_new.keySet();
    }

    /**
     * Retrieves the name of a branch based on its NL ID.
     *
     * @param nl the NL ID of the branch
     * @return the name of the branch with the specified NL ID, or null if the branch ID is not found
     */
    public String get_nl_name(String nl){
        if(branches_new.containsKey(nl)){
            return branches_new.get(nl).get(0);
        }else{
            return null;
        }
    }

    /**
     * Retrieves the region of a branch based on its NL ID.
     *
     * @param nl the NL ID of the branch
     * @return the region of the branch with the specified NL ID, or null if the branch ID is not found
     */
    public String get_nl_region(String nl){
        if(branches_new.containsKey(nl)){
            return branches_new.get(nl).get(1);
        }else{
            return null;
        }
    }

    /**
     * Retrieves the list of branches.
     *
     * @return List<Branch> - the list of branches.
     */
    public List<Branch> get_branches(){
        return branches;
    }

    /**
     * Builds the branches by sorting the keys and creating Branch objects.
     * Each Branch object is constructed with the corresponding key, name, region, checkouts,
     * ignore flag set to false, and no parent branch.
     */
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
