package org.flimwip.design.utility.Runnables;

import org.flimwip.design.Controller.UserController;
import org.flimwip.design.Models.CheckoutModel;
import org.flimwip.design.NetCon;
import org.flimwip.design.Views.Temp.Checkout;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.PKLogger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Semaphore;

/**
 * FetchFiles is a class that represents a task to fetch files from a specific checkout.
 */
public class FetchFiles implements Runnable{


    /**
     * The {@link Semaphore} to manage all Threads running simultaneously
     */
    private final Semaphore semaphore;

    /**
     * The {@link Checkout} of which the Files need to be pulled
     */
    private final CheckoutModel k;

    private final PKLogger logger = new PKLogger(this.getClass());

    private final UserController u_c;

    private final Checkout c;

    /**
     * Constructor
     * @param semaphore Semaphore -> Management for all Runnable, Limitations to 10
     * @param k Checkout -> View to update
     */
    public FetchFiles(Semaphore semaphore,Checkout c, CheckoutModel k, UserController u_c){
        logger.set_Level(LoggingLevels.FINE);
        this.u_c = u_c;
        this.c = c;
        this.semaphore = semaphore;
        this.k = k;
    }


    /**
     * Fetches files from a specific checkout.
     * Implements the Runnable interface for running the task in a separate thread.
     */
    @Override
    public void run(){

        logger.log(LoggingLevels.FINE, STR."Fetching files from checkout: \{k.hostname()}");
        NetCon connection = new NetCon(k, u_c.get_selected_user().getUsername(), u_c.get_selected_user().getPassword());
        try {
            connection.get_connection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try{
            semaphore.acquire();
            if(connection.get_connection()){
                File f = new File(STR."\\\\\{this.k.hostname()}\\c$\\gkretail\\pos-full\\log");
                if(f.listFiles() != null){
                    this.c.set_files(f.listFiles());
                    this.c.set_online();
                }else{
                    this.c.set_offline();
                }
            }else{
                this.c.set_offline();
            }
            semaphore.release();
            System.out.println(STR."Released, Permits: \{semaphore.availablePermits()}");
        }catch(InterruptedException | IOException e){
            logger.log_exception(e);
        }
    }

}
