package org.flimwip.design;

import org.flimwip.design.Models.CheckoutModel;
import org.flimwip.design.utility.DataStorage;
import java.io.*;

public class Listenfilter {

    public static void main(String[] args) throws FileNotFoundException {
        DataStorage ds = new DataStorage("NL_Liste.csv");
        int count = 0;
        for(String s : ds.list_keys()){
            for(CheckoutModel acm: ds.getcheckouts(s)){
                if(acm.betriebsstelle().equals("Reservieren und Abholen")){
                    count++;
                }
            }
        }

        System.out.println(STR."Reservieren und Abholen: \{count}");

    }
}
