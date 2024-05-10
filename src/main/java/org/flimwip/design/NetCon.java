package org.flimwip.design;

import org.flimwip.design.Documentationhandler.*;
import org.flimwip.design.Models.CheckoutModel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


@ServiceC(desc="This class builds and closes the connection to specific checkout. Needed for that is the Branch, the checkout, the password and the username.")
public class NetCon{
    private CheckoutModel k;
    @ServiceATT(desc="Holds the password with which the connections will be established and closed",
                type="String")
    private String password;
    @ServiceATT(desc="Holds the username with which the connections will be established and closed",
                type="String")
    private String username;

    @ServiceCR(desc="The Constructor of the NetCon-Class",
               params={"String: nl -> Branch for the Connection", "String: checkout -> Checkout for the Connection", "String: password -> Password for the Connection", "String: username -> Username for the Connection"})
    public NetCon(CheckoutModel k, String username, String password){
        this.k = k;
        this.password = password;
        this.username = username;
    }


    /**
     * Retrieves a connection to a remote server using the net use command.
     *
     * @return true if the connection to the server was successfully established, false otherwise.
     * @throws IOException if an I/O error occurs.
     */
    @ServiceM(desc = "Retrieves a connection to a remote server using the net use command.",
              category = "Method",
              params = {},
              returns = "",
              thrown = {"None"})
    public boolean get_connection() throws IOException{
            String[] command = new String[]{"net", "use", STR."\\\\\{k.hostname()}", STR."/u:fc.de.bauhaus.intra\\\{this.username}", this.password};
            ProcessBuilder pb = new ProcessBuilder(command);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(pb.start().getInputStream()));
            int i = 0;
            while(!stdInput.ready()){
                i++;
                if(i >= 2500000){
                    return false;
                }
            }

            String line;
            ArrayList<String> output = new ArrayList<>();

            while((line = stdInput.readLine()) != null){
                output.add(line);
            }
            return output.get(0).contains("Der Befehl wurde erfolgreich");
    }


    /**
     * Closes the connection to a remote server.
     *
     * @throws IOException if an I/O error occurs.
     */
    //  net use 10.250.172.141\c$
    //10.49.243.101
    //net use \\10.49.243.101\c$ /u:fc.de.bauhaus.intra\pos-intall M6kUVm3T && explorer \\10.49.243.101\c$
    @ServiceM(desc = "Closes the connection to a remote server.",
              category = "Method",
              params = {"None"},
              returns = "void",
              thrown = {"IOException -> if an I/O error occurs."})
    public void close_connection() throws IOException{
        String[] command = new String[]{"net", "use", STR."\\\\\{k.hostname()}", "/d"};
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
}
