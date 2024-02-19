package org.flimwip.design;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class NetCon{

    private String nl;
    private String checkout;
    private String password;
    private String username;

    public NetCon(String nl, String checkout, String username, String password){
        this.nl = nl;
        this.checkout = checkout;
        this.password = password;
        this.username = username;
    }

    /*public static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        Progress_Bar_Printer pbp = new Progress_Bar_Printer();
        long size = Files.size(Paths.get(source.getAbsolutePath()));

        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[16384];
            int length;
            long dest_size = 0;
            while ((length = is.read(buffer)) > 0) {
                dest_size += length;
                pbp.print_and_compute(size, dest_size);
                os.write(buffer, 0, length);
                // System.out.println(buffer);
                // System.out.print("\r" + String.valueOf(length));
            }
            is.close();
            os.close();
        } finally {
            System.out.println(dest.getAbsolutePath());
        }
    }*/

    public boolean get_connection() throws IOException{
            String[] command = new String[]{"net", "use", "\\\\" + "DE0" + this.nl + "CPOS20" + this.checkout + "\\c$" , "/u:fc.de.bauhaus.intra\\" + this.username , this.password};
            ProcessBuilder pb = new ProcessBuilder(command);
            //net use \\DE0666CPOS20002\c$ /u:fc.de.bauhaus.intra\pos-install M6kUVm3T && explorer \\DE0666CPOS20002\c$\gkretail\pos-full\log
            //Der Befehl wurde erfolgreich ausgeführt.
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(pb.start().getInputStream()));
            //System.out.println(pb.command());
            //int i = 0;
            while(!stdInput.ready()){
                /*custom timeout handling
                System.out.print("Watingin: " + i + "\r");
                i++;
                if(i >= 500000){
                    return false;
                }*/
            }

            String line;
            ArrayList<String> output = new ArrayList<>();

            while((line = stdInput.readLine()) != null){
                output.add(line);
                System.out.println("Line in NetCon: " + line);
            }

            /*for(String s : output){
                System.out.println("output: " + s);
            }*/
            return output.get(0).contains("Der Befehl wurde erfolgreich");
    }


    public void close_connection() throws IOException{
        String[] command = new String[]{"net", "use", "\\\\" + "DE0" + this.nl + "CPOS20" + this.checkout + "\\c$" , "/d"};
        ProcessBuilder pb = new ProcessBuilder(command);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(pb.start().getInputStream()));

        while(!stdInput.ready()){
            //custom timeout handling
        }

        String line;
        ArrayList<String> output = new ArrayList<>();

        while((line = stdInput.readLine()) != null){
            output.add(line);
        }
    }

    public boolean PingIpAddr(String ip) throws IOException{
        ProcessBuilder pb = new ProcessBuilder("ping", ip);
        //ProcessBuilder pb = new ProcessBuilder("ping", "-c 5", ip);
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(pb.start().getInputStream()));
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
        //System.out.println(output.get(9));

        if(output.size() >= 9){
            System.out.println(output.get(9).contains("0% Verlust"));
            return output.get(9).contains("0% Verlust");
        }else{
            return false;
        }
    }
}