package org.flimwip.design;

public class Test {
    public static void main(String[] args) {
        String temp = "Werterzu900!&W";
        System.out.println(print(temp.getBytes()));
    }

    public static String print(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
