package org.flimwip.design.utility;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class CredentialManager {

    private static final String CREDENTIALS = "H:\\PLAT\\Data\\Credentials.csv";
    private static final String DIR = "H:\\PLAT\\Data\\";
    private String password = null;
    private String username = null;
    public CredentialManager(){

            File f = new File(DIR);
            System.out.println(f.getAbsoluteFile());
            if(f.mkdir()){
                System.out.println("Directory");
            }else{
                System.out.println("Datei wurde nicht erstellt");
            }
            init();
    }

    private void init() {
        File f = new File(CREDENTIALS);
        try {
            if(f.createNewFile()){

                System.out.println("Created file");
            }else{
                System.out.println("File already Existed");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try(BufferedReader br = new BufferedReader(new FileReader(new File(CREDENTIALS)))){
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

    public void set_new_credentials(String username, String password) throws IOException {
        File f = new File(CREDENTIALS);
    try(BufferedWriter bw = new BufferedWriter(new FileWriter(f))){
        bw.write(username + ";" + password);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

        init();
    }



}
