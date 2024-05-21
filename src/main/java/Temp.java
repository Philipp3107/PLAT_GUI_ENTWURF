import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Temp {
    public static void main(String[] args) throws IOException {
        File f1 = new File("C:\\Users\\KotteP\\Downloads\\export (1).csv");


        try(BufferedReader br1 = new BufferedReader(new FileReader(f1))){

            String t1 = "";

            while((t1 = br1.readLine()) != null ){
               String[] array = t1.split(",");
               if(array[3].contains("PC - ") && array[7].isEmpty()){
                   System.out.println(t1);
               }

            }
        }
    }
}