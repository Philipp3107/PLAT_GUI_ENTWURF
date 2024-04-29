package org.flimwip.design.utility.Runnables;

import org.flimwip.design.Controller.UserController;
import org.flimwip.design.Views.Temp.Checkout;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.PKLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Semaphore;

public class Check_Connection implements Runnable {
    private final PKLogger logger = new PKLogger(this.getClass());
    private final Checkout k;
    private final Semaphore semaphore;
    private final UserController u_c;
    public Check_Connection(Checkout k, Semaphore semaphore, UserController u_c){
        logger.set_Level(LoggingLevels.FINE);
        this.u_c = u_c;
        this.k = k;
        this.semaphore = semaphore;

    }


    @Override
    public void run(){
        logger.log(LoggingLevels.INFO, "Startet ping for", this.k.get_hostname());
        try{
            this.semaphore.acquire();
        } catch (InterruptedException e) {
            logger.log_exception(e);
        }


        if(ping(k.get_ip())){
            //Fetch Data from Checkout
            semaphore.release();
            Thread t = new Thread(new FetchFiles(this.semaphore, this.k, this.u_c));
            t.setDaemon(true);
            t.start();
        }else{
            this.k.set_offline();
            semaphore.release();
        }
    }


    /**
     * This Method pings the given IP and checks if the Response is okay
     * If the IP is reachable it returns true, otherwise false.
     * @param ip String: IP to ping
     * @return boolean
     */
    public boolean ping(String ip) {

        try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(new ProcessBuilder("ping", ip).start().getInputStream()));) {
            logger.log(LoggingLevels.DEBUG, "Executing command", "ping", ip);
            this.k.set_searching();
            while (!stdInput.ready()) {

            }

            String line;
            while ((line = stdInput.readLine()) != null) {
                if (line.contains("Verloren = 0")) {
                    logger.log(LoggingLevels.INFO, "Ping erflogreich", ip);
                    return true;
                }
            }
            return false;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
