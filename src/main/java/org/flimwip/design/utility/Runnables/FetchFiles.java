package org.flimwip.design.utility.Runnables;

import org.flimwip.design.Controller.UserController;
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
    private final Checkout k;

    private final PKLogger logger = new PKLogger(this.getClass());

    private final UserController u_c;

    /**
     * Constructor
     * @param semaphore Semaphore -> Management for all Runnable, Limitations to 10
     * @param k Checkout -> View to update
     */
    public FetchFiles(Semaphore semaphore, Checkout k, UserController u_c){
        logger.set_Level(LoggingLevels.FINE);
        this.u_c = u_c;
        this.semaphore = semaphore;
        this.k = k;
    }


    /**
     * Fetches files from a specific checkout.
     * Implements the Runnable interface for running the task in a separate thread.
     */
    @Override
    public void run(){

        logger.log(LoggingLevels.FINE, STR."Fetching files from checkout: \{this.k.get_hostname()}");
        NetCon connection = new NetCon(k.get_ip(), u_c.get_selected_user().getUsername(), u_c.get_selected_user().getPassword());
        try {
            System.out.println(connection.get_connection());
            File f = new File(STR."\\\\\{this.k.get_hostname()}\\c$\\gkretail\\pos-full\\log");
            System.out.println(f.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try{
            semaphore.acquire();
            logger.log(LoggingLevels.INFO, STR."KassenID is: \{this.k.get_hostname()}");
            if(connection.get_connection()){
                System.out.println(this.k.get_hostname());
                File f = new File(STR."\\\\\{this.k.get_hostname()}\\c$\\gkretail\\pos-full\\log");
                System.out.println(f.getName());
                if(f.listFiles() != null){
                    this.k.set_files(f.listFiles());
                    this.k.set_online();
                }else{
                    this.k.set_offline();
                }
            }else{
                this.k.set_offline();
            }
        }catch(InterruptedException | IOException e){
            logger.log_exception(e);
        } finally { semaphore.release();}
    }

}
