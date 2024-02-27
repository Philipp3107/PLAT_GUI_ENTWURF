package org.flimwip.design.utility;

import org.flimwip.design.NetCon;
import org.flimwip.design.Views.Checkout;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Semaphore;

/**
 * FetchFiles is a class that represents a task to fetch files from a specific checkout.
 */
public class FetchFiles implements Runnable{

    /**
     * Represents the identifier of a checkout.
     */
    private final String kassenid;

    /**
     * The {@link Semaphore} to manage all Threads running simultaniously
     */
    private final Semaphore semaphore;

    /**
     * The {@link Checkout} of which the Files need to be pulled
     */
    private final Checkout k;

    /**
     * Contstructor
     * @param kassenid String -> Id of the Checkout
     * @param semaphore Semaphore -> Management for all Runnables, Limitations to 10
     * @param k Checkout -> View to update
     */
    public FetchFiles(String kassenid, Semaphore semaphore, Checkout k){
        this.kassenid = kassenid;
        this.semaphore = semaphore;
        this.k = k;
    }


    /**
     * Fetches files from a specific checkout.
     * Implements the Runnable interface for running the task in a separate thread.
     */
    @Override
    public void run(){
        String branch = null;
        String checkout_id = null;
        if(this.kassenid.length() >= 12){
            branch = this.kassenid.substring(3, 6);
            checkout_id = this.kassenid.substring(12);
        }

        System.out.println(branch);
        System.out.println(checkout_id);
        NetCon connection = new NetCon(branch, checkout_id, CredentialManager.get_username(), CredentialManager.get_password());

        try{
            semaphore.acquire();
            System.out.println("KassenID ist: " + this.kassenid);
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
