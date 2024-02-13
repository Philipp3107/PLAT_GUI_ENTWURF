package org.flimwip.design.utility;

import org.flimwip.design.Models.StandortCase;

public class StandortTranslator {

    public StandortTranslator(){

    }


    public static String getSTANDORT(int id){
        String city = "";
        for(StandortCase s: StandortCase.values()){
            if(s.KENNUMMER == id){
                city = s.STANDORT;
                System.out.println(city);
            }
        }
        if(city.isEmpty()){
            return "NULL";
        }else{
            return city;
        }
    }

}
