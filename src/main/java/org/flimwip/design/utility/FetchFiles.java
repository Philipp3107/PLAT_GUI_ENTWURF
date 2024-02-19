package org.flimwip.design.utility;

import org.flimwip.design.NetCon;
import org.flimwip.design.Views.Checkout;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Semaphore;

/**
 * Sets the Files found in the {@link Checkout Checkouts} directory as Files to the {@link Checkout}
 * <br>
 * If the {@link NetCon Connection} could be to the {@link Checkout} could be established, the found Files will be set to {@link Checkout#files Files} for later use.
 */
public class FetchFiles implements Runnable{
    /**
     * The id of the Checkout in its complete Form {@code DE0XXXCPOS20XXX}
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
    public FetchFiles(String kassenid, Semaphore semaphore, Checkout k){
        this.kassenid = kassenid;
        this.semaphore = semaphore;
        this.k = k;
    }


    @Override
    public void run(){

        String branch = this.kassenid.substring(3, 6);
        String checkout_id = this.kassenid.substring(12);

        NetCon connection = new NetCon(branch, checkout_id, CredentialManager.get_username(), CredentialManager.get_password());

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
