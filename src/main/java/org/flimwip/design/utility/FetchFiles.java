package org.flimwip.design.utility;

import org.flimwip.design.NetCon;
import org.flimwip.design.Views.Checkout;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class FetchFiles implements Runnable{
    private final String kassenid;
    private final Semaphore semaphore;
    private final Checkout k;
    public FetchFiles(String kassenid, Semaphore semaphore, Checkout k){
        this.kassenid = kassenid;
        this.semaphore = semaphore;
        this.k = k;
    }


    @Override
    public void run(){

        String branch = this.kassenid.substring(3, 6);
        String checkout_id = this.kassenid.substring(12);
        CredentialManager credentialManager = new CredentialManager();

        NetCon connection = new NetCon(branch, checkout_id, credentialManager.get_username(), credentialManager.get_password());

        try{
            semaphore.acquire();
            if(connection.get_connection()){
                File f = new File("\\\\" + this.kassenid + "\\c$\\gkretail\\pos-full\\log");
                if(f.listFiles() != null){
                    this.k.set_files(f.listFiles());
                    this.k.set_online();
                }else{
                    this.k.set_offline();
                }
            }else{
                this.k.set_offline();
            }
        }catch(InterruptedException e){ System.out.println("Interrupted while acquiring Semaphore");
        }catch (IOException e) { System.out.println("IOException Occured: " + e.getLocalizedMessage());
        }finally { semaphore.release();}
    }

}
