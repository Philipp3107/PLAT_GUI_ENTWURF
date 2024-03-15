package org.flimwip.design;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.flimwip.design.Documentationhandler.*;

@ServiceC(desc="This class generates RandomArtImages in \"jpg\" format that can be used as profile Image")
public class RandomArtGenerator {

    @ServiceATT(desc="Holds the Path for where the RandomArtImages are stored",
                type="String")
    public static final String RANDOM_ART_PATH = "/Users/philippkotte/Desktop/profile_picture/image";
    
    public static void main(String[] args) throws IOException {
        for(int k = 0;k < 300; k++){
        int[][] seeds = new int[4][4];
        for(int i = 0 ; i < 4; i++){
            for(int j = 0; j < 4; j++){
                seeds[i][j] = new Random().nextInt(3);
                //System.out.println(seeds[i][j]);
            }
        }

        int width = 800;
        int height = 800;
        BufferedImage buffered_image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            int seed_1;
            int seed_2;
            if(k%2 == 0){
                seed_1 = getRandomHighSeed();
                seed_2 = getRandomLowSeed();
            }else{
                seed_1= getRandomLowSeed();
                seed_2 = getRandomHighSeed();
            }

            //System.out.println("Seed: " + seed_1 + " and " + seed_2 + " for: " + k);
        for(int x = 0; x < width; x++){

            int r = getRandomNumber();

            for(int y = 0; y < height; y++){
                int g = getRandomNumber();
                int b = getRandomNumber();
                Color c = null;

                if(seeds[x / 200][y/200] == 0){
                    c = new Color(seed_1, seed_2, b);
                }else if(seeds[x / 200][y/200] == 1){
                    c = new Color(r, seed_1, seed_2);
                }else if(seeds[x / 200][y/200] == 2) {
                    c = new Color(seed_2, g, seed_1);
                }
                buffered_image.setRGB(x, y, c.getRGB());
                //System.out.println(buffered_image.getRGB(x, y));
            }
        }

        File outputfile = new File(RANDOM_ART_PATH + k + ".jpg");
        ImageIO.write(buffered_image, "jpg", outputfile);
        }
    }
    
    @ServiceM(desc="Used to generate a random Number between 1 and 255",
              category="Method",
              params={"None"},
              returns="int -> Random number generated",
              thrown={"None"})
    public static int getRandomNumber() {

        Random rand = new Random();
        int n = rand.nextInt(254);
        //System.out.println(n += 1);
        return n +=1;
    }
    @ServiceM(desc="Used to generate a random Number as seed between 20 and 94",
              category="Method",
              params={"None"},
              returns="int -> Random number generated",
              thrown={"None"})
    public static int getRandomLowSeed() {

        Random rand = new Random();
        int n = rand.nextInt(74);
        //System.out.println(n += 1);
        return n +=20;
    }
    
    @ServiceM(desc="Used to generate a random Number as seed between 150 and 220",
              category="Method",
              params={"None"},
              returns="int -> Random number generated",
              thrown={"None"})
    public static int getRandomHighSeed() {

        Random rand = new Random();
        int n = rand.nextInt(70);
        //System.out.println(n += 1);
        return n +=150;
    }

    @ServiceM(desc="Converts the given Numbers in anm RGC - Color to a Hex-String",
              category="Method",
              params={"int: red -> Red-parameter of a color", "int: green -> Green-parameter of a color", "int: blue -> Blue-parameter of a color"},
              returns="String -> converted Hex-String",
              thrown={"None"})
    public static String convert_to_hex(int red, int green, int blue){
        return String.format("#%02X%02X%02X", red, green, blue);
    }
}

