package org.flimwip.design.utility.terminal.commands;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

class Command{
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean in_commands = true;
        File commands = new File("src/main/java/org/flimwip/design/utility/terminal/commands/commands.json");

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
                        line = br.readLine();
                        boolean fixed_length;
                        String alias_bool = line.split(":")[1].replace(" ", "").replace(",", "");
                        if(alias_bool.equals("true")){
                            fixed_length = true;
                            System.out.println("true");
                        }else{
                            fixed_length = false;
                            System.out.println("false");
                        }
                        line = br.readLine();
                        String length = line.split(":")[1].split("\"")[1];
                        System.out.println(length);
                        line = br.readLine();
                        String encoding = line.split(":")[1].split("\"")[1];
                        System.out.println(encoding);
                        line = br.readLine();
                        String description = line.split(":")[1].split("\"")[1];
                        System.out.println(description);
                        line = br.readLine();
                        ArrayList<String> subcommands = new ArrayList<>();
                        String temp = line.split(": ")[1];
                        temp = temp.replace("[", "").replace("]", "");
                        String[] subc = temp.split(", ");
                        for(String s : subc){
                            subcommands.add(s.split("\"")[1]);
                        }
                        for(String s : subcommands){
                            System.out.print(s);
                        }

                        System.out.println();


                        line = br.readLine();
                        ArrayList<String> parentcommands = new ArrayList<>();
                        temp = line.split(": ")[1];
                        temp = temp.replace("[", "").replace("]", "");
                        String[] parc = temp.split(", ");
                        for(String p : parc){
                            parentcommands.add(p.split("\"")[1]);
                        }
                        for(String p : parentcommands){
                            System.out.print(p);
                        }

                        System.out.println();
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

