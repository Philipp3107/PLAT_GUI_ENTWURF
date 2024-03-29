package org.flimwip.design.utility;

import java.io.*;

public class CredentialManager {

    private static final String CREDENTIALS = "H:\\PLAT\\Data\\Credentials.csv";
    private static final String DIR = "H:\\PLAT\\Data\\";



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

    /**
     * Fetches the Password from the Credentials File and provides it statically
     * @return String: Password either "" or provided by user
     */
    public static String get_password(){
            String password = null;
            try {
                check_files();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try(BufferedReader br = new BufferedReader(new FileReader(CREDENTIALS))){
                password = br.readLine().split(";")[1];
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return password;

        }


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
            try(BufferedWriter br = new BufferedWriter(new FileWriter(CREDENTIALS))){
                br.write("username;password");
            }
        }
    }

    /**
     * Sets a new Password and Username if the User enters it
     * @param username Username for the application and for fetching Data from the Checkout
     * @param password Password for the application and for fetching Data from the Checkout
     * @throws IOException
     */
    public static void set_new_credentials(String username, String password) throws IOException {
        File f = new File(CREDENTIALS);
    try(BufferedWriter bw = new BufferedWriter(new FileWriter(f))){
        bw.write(username + ";" + password);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    }



}
