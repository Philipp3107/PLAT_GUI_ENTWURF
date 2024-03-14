package org.flimwip.design.utility.Runnables;

import org.flimwip.design.Views.Temp.Checkout;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.MyLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Semaphore;

public class Check_Connection implements Runnable {

    private String checkout;
    private MyLogger logger = new MyLogger(this.getClass());
    private Checkout k;
    private String nl;
    private Semaphore semaphore;
    private String ip_to_look;
    public Check_Connection(String nl, String checkout, Checkout k, Semaphore semaphore){
        logger.set_Level(LoggingLevels.FINE);
        this.checkout = checkout;
        this.k = k;
        this.nl = nl;
        this.semaphore = semaphore;
        //nur für windows
        this.ip_to_look = "DE0" + this.nl + "CPOS20" + this.checkout;
        // für mac
        //this.ip_to_look = "172.217.16.206";
    }


    @Override
    public void run(){
        logger.log(LoggingLevels.INFO, "Startet ping for", this.k.getId());
        try{
            this.semaphore.acquire();
        } catch (InterruptedException e) {
            logger.log_exception(e);
        }


        if(ping(this.ip_to_look)){
            //Fetch Data from Checkout
            semaphore.release();
            Thread t = new Thread(new FetchFiles(this.ip_to_look, this.semaphore, this.k));
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
     * @throws IOException
     */
    public boolean ping(String ip) {

        try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(new ProcessBuilder("ping", ip).start().getInputStream()));) {
            logger.log(LoggingLevels.DEBUG, "Executing command", "ping", ip);
            this.k.set_searching();
            while (!stdInput.ready()) {
                //while the Process
            }

            String line;
            while ((line = stdInput.readLine()) != null) {
                if (line.contains("Verloren = 0")) {
                    logger.log(LoggingLevels.INFO, "Ping erflogreich");
                    return true;
                }
            }
            return false;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
