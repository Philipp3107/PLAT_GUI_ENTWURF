package org.flimwip.design;

import org.flimwip.design.Models.CheckoutModel;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class HelperFailureData {
    public static void main(String[] args) throws IOException {
        List<String> checkouts = new ArrayList<String>();
        File checkout_list = new File("L:\\POS-Systeme\\TeamPOS_INTERN\\02 Mitarbeiter\\Philipp Kotte\\PLAT 0.2.24\\Helper\\resources\\NL_mini_Liste.csv");
        String reading = "";
        try(BufferedReader br = new BufferedReader(new FileReader(checkout_list))){
            while((reading = br.readLine()) != null){
                String[] splitted = reading.split(";");
                if(splitted[2].equals("LIVE")){
                    //System.out.println(reading);
                    checkouts.add(splitted[0]);       
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //for(String s : checkouts){
        // System.out.println(s);
        // }
        System.out.println("Alle kassen gefunden!");

        HashMap<String, Integer> warns = new HashMap<>();
        HashMap<String, Integer> err = new HashMap<>();
        HashMap<String, Integer> criticals = new HashMap<>();


        for(String c: checkouts){
            String nl = c.substring(3, 6);
            String checkout = c.substring(11);
            System.out.println("NL: " + nl + ", Checkout: " + checkout);
            NetCon net_con = new NetCon(CheckoutModel.generate_dummy_model(), "pos-install", "M6kUVm3T");
            if(net_con.get_connection()){
                System.out.println("Connection established");
                //\\DE0300CPOS20001\c$\gkretail\pos-full\log
                File f = new File("\\\\" + c+ "\\c$\\gkretail\\pos-full\\log\\");
                System.out.println(f);
                System.out.println(f.isDirectory());
                if(f.listFiles() != null){
                    for(File file : f.listFiles()){
                        if(file.getName().contains(".zip") && !file.getName().contains("debug") && !file.getName().contains("terminal") && !file.getName().contains("performance")){
                            System.out.println(file.getName());
                            ZipFile zip = new ZipFile(file.getAbsolutePath());
                            Enumeration<? extends ZipEntry> entries = zip.entries();
                            while(entries.hasMoreElements()){
                                ZipEntry entry = entries.nextElement();
                                try(InputStream is = zip.getInputStream(entry)){
                                    Scanner sc = new Scanner(is);
                                    String line = "";
                                    while(sc.hasNext()){
                                        line = sc.nextLine();
                                        String key = line.split(" ")[0];
                                        if(line.contains("WARN")){
                                            if(warns.containsKey(key)){
                                                warns.replace(key, warns.get(key) + 1);
                                            }else{
                                                if(!err.containsKey(key)){
                                                    err.put(key, 0);
                                                }
                                                if(!criticals.containsKey(key)){
                                                    criticals.put(key, 0);
                                                }
                                                warns.put(key, 1);
                                            }

                                        }else if(line.contains("ERROR")){
                                            if(line.contains("RuntimeException")){
                                                if(criticals.containsKey(key)){
                                                    criticals.replace(key, warns.get(key) + 1);
                                                }else{
                                                    if(!err.containsKey(key)){
                                                        err.put(key, 0);
                                                    }
                                                    if(!warns.containsKey(key)){
                                                        warns.put(key, 0);
                                                    }
                                                    criticals.put(key, 1);
                                                }
                                            }else{
                                                if(err.containsKey(key)){
                                                    err.replace(key, warns.get(key) + 1);
                                                }else{
                                                    if(!warns.containsKey(key)){
                                                        warns.put(key, 0);
                                                    }
                                                    if(!criticals.containsKey(key)){
                                                        criticals.put(key, 0);
                                                    }
                                                    err.put(key, 1);
                                                }
                                            }

                                        }else{

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                System.out.println("Files an log sind angeblich null");
                net_con.close_connection();
            }else{
                System.out.println("Couldn't establish connection");
            }
        }

       /*  System.out.println("Warnings");
        for(String s : warnings){
            System.out.println(s);
        }
        System.out.println("ERRORS");
        for(String s : errors){
            System.out.println(s);
        } */

        ArrayList<String> err_arr = new ArrayList<>();
        System.out.println("errors");
        for(String s: err.keySet()){
            err_arr.add(s + " " + err.get(s));
        }

        Collections.sort(err_arr);
        for(String s : err_arr){
            System.out.println(s);
        }

        ArrayList<String> warn_arr = new ArrayList<>();

        for(String s: warns.keySet()){
            warn_arr.add(s + " " + warns.get(s));
        }
        System.out.println("Warns");
        Collections.sort(warn_arr);
        for(String s : warn_arr){
            System.out.println(s);
        }

        ArrayList<String> critical_arr = new ArrayList<>();
        System.out.println("Criticals");
        for(String s: criticals.keySet()){
            critical_arr.add(s + " " + criticals.get(s));
        }

        Collections.sort(critical_arr);
        for(String s : critical_arr){
            System.out.println(s);
        }


        try(BufferedWriter bw = new BufferedWriter(new FileWriter("L:\\POS-Systeme\\TeamPOS_INTERN\\02 Mitarbeiter\\Philipp Kotte\\PLAT 0.2.24\\Helper\\resources\\failure_data.csv"))){
            String line = "";
            for(int i = 0; i < warn_arr.size(); i++){
                String warn = warn_arr.get(i).split(" ")[1];
                String error = err_arr.get(i).split(" ")[1];
                String critical = critical_arr.get(i).split(" ")[1];
                line = warn + ";" + error + ";" + critical + "\n";
                bw.write(line);
            }

        }


    }
}
