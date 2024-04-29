import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Temp {
    public static void main(String[] args) throws IOException {
        File f = new File("C:\\Users\\KotteP\\IdeaProjects\\design\\src\\main\\resources\\TERMINAL_CODES.md");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = "";
        while( (line = br.readLine()) != null){
            if(line.contains("##")){
                String temp = line.split("## ")[1];

                System.out.println(STR."- [\{temp}](#\{temp.replace(" ", "-").replace("/", "").replace("(", "").replace(")", "").toLowerCase()})");
            }
        }
    }
}