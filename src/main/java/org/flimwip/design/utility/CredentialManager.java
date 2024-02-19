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

        try(BufferedReader br = new BufferedReader(new InputStreamReader(stream))){
            String line = "";
            while((line = br.readLine()) != null){
                String[] splitted = line.split(";");
                this.username = splitted[0];
                this.password = splitted[1];
            }
        }catch(FileNotFoundException e){
            System.out.println("Couldn't find credentials " );
        }catch(IOException e){
            System.out.println("An Error occured." + e.getLocalizedMessage());
        }
    }

    public String get_password(){
        return this.password;
    }


    public String get_username(){
    return this.username;
    }

    public void set_new_credentials(String username, String password){
    try(BufferedWriter bw = new BufferedWriter(new FileWriter(CREDENTIALS))){
        bw.write(username + ";" + password);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

    init();
    }



}
