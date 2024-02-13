package org.flimwip.design;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Helper {

    public static void create_dummy_dashboard_data(int max, int min, int size){
        Random rand = new Random();

        File f = new File("src/main/java/org/flimwip/design/resources/dummy_data_errors.csv");
        int[] dummy_data = new int[size*3];
        for(int i = 0; i < size*3; i++){
            int random = rand.nextInt(max - min) + min;
            dummy_data[i] = random;
        }


            try(BufferedWriter bw = new BufferedWriter(new FileWriter(f))){
                int i = 0;
                for(int d: dummy_data){
                    if(i == 3){
                        bw.newLine();
                        i = 0;
                    }else{
                        bw.write(String.valueOf(d)  + ";");
                        i++;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }


    public static void main(String[] args) {
        Helper.create_dummy_dashboard_data(1000, 23, 1000);
    }

}
