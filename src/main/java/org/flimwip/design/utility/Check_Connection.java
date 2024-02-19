package org.flimwip.design.utility;

import org.flimwip.design.Views.Kasse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

public class Check_Connection implements Runnable {

    private String nl;
    private String checkout;
    private Kasse k;

    private Semaphore semaphore;
    private String ip_to_look;
    public Check_Connection(String nl, String checkout, String username, String password, Kasse k, Semaphore semaphore){
        this.nl = nl;
        this.checkout = checkout;
        this.k = k;
        this.semaphore = semaphore;
        //nur für windows
        this.ip_to_look = "DE0" + this.nl + "CPOS20" + this.checkout;
        // für mac
        //this.ip_to_look = "172.217.16.206";
    }
    @Override
    public void run() {
        try {
            System.out.println("Starting ping for " + this.k.getId());
            //TODO: Dieser part muss mit der Checkout id und der Niederlassungs id erstellt werden
            //only for Windows, not mac
            semaphore.acquire();
            System.out.println(this.k.getId() + " starting");
            System.out.println("IP to look for is: " + this.ip_to_look);
            List<String> temp = PingIpAddr(this.ip_to_look);
            //System.out.println(temp.get(8).contains("Verloren = 0"));
            for(String s : temp){
                System.out.println(s);
            }
            String to_check_win = "Verloren = 0";
            String to_check_mac = "0.0% packet loss";
            //mac -> 7
            //win -> 8
            if(temp.size() >= 8){
                if(temp.get(8).contains(to_check_win)){
                    //this.k.set_online();
                    this.k.set_clickabel(true);
                    semaphore.release();
                }else{
                    //this.k.set_offline();
                    this.k.set_clickabel(false);
                    semaphore.release();
                }
            }else{
                this.k.set_offline();
                this.k.set_clickabel(false);
                semaphore.release();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    public List<String> PingIpAddr(String ip) throws IOException
    {
        //windows use
        ProcessBuilder pb = new ProcessBuilder("ping", ip);
        System.out.println(pb.command());
        //mac use
        //ProcessBuilder pb = new ProcessBuilder("ping", "-c 4", ip);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(pb.start().getInputStream()));
        this.k.set_searching();
        while (!stdInput.ready())
        {

            // custom timeout handling
            //System.out.println("Looking for ping");
        }

        String line;
        ArrayList<String> output = new ArrayList<>();

        while ((line = stdInput.readLine()) != null)
        {
            output.add(line);
            System.out.println("Line: " + line);
        }

        return output;
    }

    public Future<List<String>> PingIpAddrFuture(String ip) throws IOException
    {
        //windows use
        //ProcessBuilder pb = new ProcessBuilder("ping", ip);
        //mac use
        ProcessBuilder pb = new ProcessBuilder("ping", "-c 5", ip);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(pb.start().getInputStream()));
        this.k.set_searching();
        while (!stdInput.ready())
        {

            // custom timeout handling
            //System.out.println("Looking for ping");
        }

        String line;
        ArrayList<String> output = new ArrayList<>();

        while ((line = stdInput.readLine()) != null)
        {
            output.add(line);
        }

        return (Future<List<String>>) output;
    }

}
