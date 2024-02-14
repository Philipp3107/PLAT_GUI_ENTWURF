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

    private String ip_to_look;
    public Check_Connection(String nl, String checkout, String username, String password, Kasse k){
        this.nl = nl;
        this.checkout = checkout;
        this.k = k;
        this.ip_to_look = "DE0" + this.nl + "CPOS20" + this.checkout;
    }
    @Override
    public void run() {
        try {
            //TODO: Dieser part muss mit der Checkout id und der Niederlassungs id erstellt werden
            //only for Windows, not mac
            System.out.println(this.ip_to_look);
            List<String> temp = PingIpAddr(this.ip_to_look);
            //System.out.println(temp.get(8).contains("Verloren = 0"));
            if(temp.size() >= 8){
                if(temp.get(8).contains("Verloren = 0")){
                    this.k.set_online();
                }else{
                    this.k.set_offline();
                }
            }else{
                this.k.set_offline();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> PingIpAddr(String ip) throws IOException
    {
        ProcessBuilder pb = new ProcessBuilder("ping", ip);
        //ProcessBuilder pb = new ProcessBuilder("ping", "-c 5", ip);
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

        return output;
    }
}
