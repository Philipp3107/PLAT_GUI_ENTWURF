package org.flimwip.design.utility.ascii;

public class ASCII_Translator {

    public static String translate(String ... tokens){
        String complete = "";
        for(String s : tokens){
            char c = (char) get_num_from_hex(s);
            complete += String.valueOf(c);
        }
        return complete;
    }

    public static String translate(String token){
        String complete = "";
        String[] tokens = token.split(" ");
        for(String s : tokens){
            char c = (char) get_num_from_hex(s);
            complete += String.valueOf(c);
        }
        return complete;
    }

    public static int get_num_from_hex(String hex){
        String[] splitted = hex.split("");
        int sum = 0;
        int count = splitted.length-1;
        for(String s : splitted){
            try{
                int a = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                s = s.toUpperCase();
            }
            switch(s){
                case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9":
                    sum += (Integer.parseInt(s) * (int)Math.pow(16, count));
                    count--;
                    break;
                case "A":
                    sum += (int) (10 * Math.pow(16, count));
                    count--;
                    break;
                case "B":
                    sum += (int) (11 * Math.pow(16, count));
                    count--;
                    break;
                case "C":
                    sum += (int) (12 * Math.pow(16, count));
                    count--;
                    break;
                case "D":
                    sum += (int) (13 * Math.pow(16, count));
                    count--;
                    break;
                case "E":
                    sum += (int) (14 * Math.pow(16, count));
                    count--;
                    break;
                case "F":
                    sum += (int) (15 * Math.pow(16, count));
                    count--;
                    break;
            }
        }
        return sum;
    }
    public static void main(String[] args) {
        String token = "53 45 50 41 20 4c 61 73 74 73 63 68 72 69 66 74";
        System.out.println(translate(token));

        /*String[] ascii = {"20", "31", "32", "33", "34"};
        for(String s : ascii){
            int i = get_num_from_hex(s);
            System.out.println(i);
            System.out.println("Hallo"+(char)(i-10)+"DU");
        }*/

    }
}
