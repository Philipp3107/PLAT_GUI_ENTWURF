package org.flimwip.design.utility;

import org.flimwip.design.NetCon;
import org.flimwip.design.Views.Kasse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class FetchFiles implements Runnable{

    private String kassenid;
    private Semaphore semaphore;
    private Kasse k;
    public FetchFiles(String kassenid, Semaphore semaphore, Kasse k){
        this.kassenid = kassenid;
        this.semaphore = semaphore;
        this.k = k;
    }
    @Override
    public void run() {
        try {
            semaphore.acquire();

            String username = new CredentialManager().get_username();
            String password = new CredentialManager().get_password();
            String nl = this.kassenid.substring(0, 3);
            String checkout = this.kassenid.substring(3);
            System.out.println(this.kassenid);
            System.out.println(this.kassenid.substring(0, 3));
            System.out.println(this.kassenid.substring(3));
            NetCon connection = new NetCon(this.kassenid.substring(0, 3), "0" + this.kassenid.substring(3),username ,password );
            if(connection.get_connection()){
                System.out.println("Connection established for: " + k.getId());
                File f = new File("\\\\DE0" + nl + "CPOS20" + checkout + "\\c$\\gkretail\\pos-full\\log");
                //System.out.println("Sleeping for 500 millis");
                //Thread.sleep(5000);
                this.k.set_files(f.listFiles());
                this.k.set_online();
                this.k.set_clickabel(true);
                semaphore.release();
                System.out.println("Releasing semaphore");
                connection.close_connection();
            }else{
                System.out.println("Connection could not be established");
            }


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
