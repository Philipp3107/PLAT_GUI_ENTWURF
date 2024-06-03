package org.flimwip.design.utility.terminal.commands;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

class Command{
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean in_commands = true;
        File commands = new File("C:\\Users\\KotteP\\IdeaProjects\\design\\src\\main\\java\\org\\flimwip\\design\\utility\\terminal\\commands\\commands.json");

        String line;
        try(BufferedReader br = new BufferedReader(new FileReader(commands))){

            while((line = br.readLine()) != null){

                sc.nextLine();
                System.out.println(line);


                if(line.contains("\"commands\": [")){
                    in_commands = true;
                }else if(line.contains("\"tables\": [")){
                    in_commands = false;
                }

                if(in_commands){
                    System.out.println("Verarbeitung in commands");
                    if(Pattern.compile("\s{6}\"\\w{2,4}\": \\{").matcher(line).matches()){
                        System.out.println("Line start found");
                        String commandNumber = line.split("\"")[1];
                        System.out.println(commandNumber);
                        line = br.readLine();
                        String section = line.split(":")[1].split("\"")[1];
                        System.out.println(section);
                        line = br.readLine();
                        String name = line.split(":")[1].split("\"")[1];
                        System.out.println(name);
                        System.out.println(br.readLine());
                        System.out.println(br.readLine());
                        System.out.println(br.readLine());
                        System.out.println(br.readLine());
                        System.out.println(br.readLine());
                        System.out.println(br.readLine());
                    }



                }else{
                    System.out.println("Verarbeitung in tables");
                }

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /*
    "commands": [
    {
      "01": {
        "section": "Miscellaneous",
        "name": "reversal-id",
        "fixed_length": true,
        "length": "8-byte",
        "encoding": "binary",
        "description": "none",
        "subcommands": ["none"],
        "parentcommands": ["none"]
      }
    },
     */


    }

