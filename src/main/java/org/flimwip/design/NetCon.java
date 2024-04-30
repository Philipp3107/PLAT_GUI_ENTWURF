package org.flimwip.design;

import org.flimwip.design.Documentationhandler.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


@ServiceC(desc="This class builds and closes the connection to specific checkout. Needed for that is the Branch, the checkout, the password and the username.")
public class NetCon{
    private String ip;
    @ServiceATT(desc="Holds the password with which the connections will be established and closed",
                type="String")
    private String password;
    @ServiceATT(desc="Holds the username with which the connections will be established and closed",
                type="String")
    private String username;

    @ServiceCR(desc="The Constructor of the NetCon-Class",
               params={"String: nl -> Branch for the Connection", "String: checkout -> Checkout for the Connection", "String: password -> Password for the Connection", "String: username -> Username for the Connection"})
    public NetCon(String ip, String username, String password){
        this.ip = ip;
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
            String[] command = new String[]{"net", "use", STR."\\\\\{this.ip}", STR."/u:fc.de.bauhaus.intra\\\{this.username}", this.password};
            ProcessBuilder pb = new ProcessBuilder(command);
            System.out.println(pb.command());
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
                System.out.println("Line in NetCon: " + line);

            }

            /*for(String s : output){
                System.out.println("output: " + s);
            }*/
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
        String[] command = new String[]{"net", "use", "\\\\" ,this.ip , "/d"};
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

    /**
     * Pings the given IP address to check if it is reachable.
     *
     * @param ip the IP address to ping
     * @return true if the IP address is reachable, false otherwise
     * @throws IOException if an I/O error occurs
     */
    @ServiceM(desc = "Pings the given IP address to check if it is reachable.",
              category = "Method",
              params = {"ip: String -> the IP address to ping"},
              returns = "boolean -> true if the IP address is reachable, false otherwise",
              thrown = {"IOException if an I/O error occurs"})
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
