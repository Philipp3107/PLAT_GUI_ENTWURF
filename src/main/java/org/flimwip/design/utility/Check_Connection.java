package org.flimwip.design.utility;

import org.flimwip.design.Views.Kasse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Check_Connection implements Runnable {

    private String nl;
    private String checkout;
    private Kasse k;

    public Check_Connection(String nl, String checkout, String username, String password, Kasse k){
        this.nl = nl;
        this.checkout = checkout;
        this.k = k;
    }
    @Override
    public void run() {
        try {
            //TODO: Dieser part muss mit der Checkout id und der Niederlassungs id erstellt werden
            List<String> temp = PingIpAddr("172.217.16.206");
            System.out.println(temp.get(6));
            if(temp.get(6).contains("0.0% packet loss")){
                this.k.set_online();
            }else{
                this.k.set_offline();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> PingIpAddr(String ip) throws IOException
    {
        ProcessBuilder pb = new ProcessBuilder("ping", "-c 3", ip);
        //ProcessBuilder pb = new ProcessBuilder("ping", "-c 5", ip);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(pb.start().getInputStream()));

        while (!stdInput.ready())
        {
            this.k.set_searching();
            // custom timeout handling
            //System.out.println("Looking for ping");
        }

        String line;
        ArrayList<String> output = new ArrayList<>();

        while ((line = stdInput.readLine()) != null)
        {
            output.add(line);
        }

        return output;
    }
}
