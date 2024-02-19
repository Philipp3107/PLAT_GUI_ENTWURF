package org.flimwip.design.utility;

import java.io.*;
import java.net.URL;

public class CredentialManager {

    private static final String CREDENTIALS = "resources/Credentials.csv";
    private String password = null;
    private String username = null;
    public CredentialManager(){
        init();
    }

    private void init(){

        InputStream stream = CredentialManager.class.getClassLoader().getResourceAsStream("Credentials.csv");

        try(BufferedReader br = new BufferedReader(new FileReader(CREDENTIALS))){
            password = br.readLine().split(";")[1];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Fetches the Username from the Credentials File and provieds it statically
     * @return String: Username either "" or provided by user
     */
    public static String get_username(){
        String username = null;
        try {
            check_files();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try(BufferedReader br = new BufferedReader(new FileReader(CREDENTIALS))){
            username = br.readLine().split(";")[0];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return username;

    }
        return password;



    /**
     * Checks if the File with the Credentials Exists and if not creates it
     * @throws IOException
     */
    private static void check_files() throws IOException {
        File cred = new File(CREDENTIALS);
        File dir = new File(DIR);
        if(!cred.exists()){
            if(!dir.exists()){
                dir.mkdirs();
                cred.createNewFile();
            }else{
                cred.createNewFile();
            }
        }
    }

    /**
     * Sets a new Password and Username if the User enters it
     * @param username Username for the application and for fetching Data from the Checkout
     * @param password Password for the application and for fetching Data from the Checkout
     * @throws IOException
     */
    public void set_new_credentials(String username, String password) throws IOException {
        File f = new File(CREDENTIALS);
    try(BufferedWriter bw = new BufferedWriter(new FileWriter(f))){
        bw.write(username + ";" + password);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

    }



}
