package org.flimwip.design;

import javafx.stage.Stage;
import org.flimwip.design.Controller.*;
import org.flimwip.design.Documentationhandler.ServiceATT;
import org.flimwip.design.Documentationhandler.ServiceC;
import org.flimwip.design.Documentationhandler.ServiceCR;
import org.flimwip.design.Documentationhandler.ServiceM;
import org.flimwip.design.Models.AppUser;
import org.flimwip.design.Models.PopulationFile;
import org.flimwip.design.Views.Temp.BackButton;
import org.flimwip.design.Views.Temp.BranchView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Array;
import java.util.ArrayList;
import java.util.jar.Attributes;

@ServiceC(desc="This is the Launcher class.")
public class Start {


    @ServiceM(desc="The Launchers Main Method. It starts the Main method of the class extending Application to start the Applcaication.",
              category="Method",
              params={"String[] args: Arguments"},
              returns="void",
              thrown={"None"})
    public static void main(String[] args) {
        //Main.main(args);


        ArrayList<String> realted = new ArrayList<>();

        //Building documentation
        Class c = Cryptographer.class;


        ServiceC class_defeinition = (ServiceC) c.getAnnotation(ServiceC.class);

        //String folder = c.getPackageName().split("design.")[1];
        System.out.println("<card-summary> " + class_defeinition.desc() + " </card-summary>");

        //System.out.println("<available-only-for> Folder "+ folder + " </available-only-for>");

        System.out.println("<chapter title=\"Description\" id=\"description\">");
        System.out.println("<p>" + class_defeinition.desc() + " </p>");
        System.out.println("</chapter>");

        if(!class_defeinition.related()[0].equals("None")){
            System.out.println("<p> <u><b>Related:</b></u></p>");
            for(String s : class_defeinition.related()){
                System.out.println("<a href=\""+s +".topic\"/>");
            }
        }
        for(String s: class_defeinition.related()){
            if(!realted.contains(s)){
                realted.add(s);
            }
        }



        //<chapter title="Attributes" id="attributes">
        System.out.println("<chapter title=\"Attributes\" id=\"attributes\">");
        System.out.println("<available-only-for> since v.1.2</available-only-for>");
        System.out.println("<deflist type=\"full\">");
        for(Field field : c.getDeclaredFields()){

            ServiceATT att = field.getAnnotation(ServiceATT.class);
            if(att != null){
                System.out.println("<def title=\"" + field.getName() + ": " + att.type() + "\">");
                System.out.println("<p>" + att.desc() + "</p>");
                if(!att.related()[0].equals("None")){
                    System.out.println("<p> <u><b>Related:</b></u></p>");
                    for(String s : att.related()){
                        System.out.println("<a href=\""+s +".topic\"/>");
                    }
                }
                System.out.println("</def>");
            }
            for(String s : att.related()){
                if(!realted.contains(s)){
                    realted.add(s);
                }
            }
        }

        System.out.println("</deflist>");
        System.out.println("</chapter>");

        //Konstrukot
        System.out.println("<chapter title=\"Constructor\" id=\"constructor\">");
        System.out.println("<available-only-for> since v.1.2</available-only-for>");
        System.out.println("<deflist type=\"full\">");
        for(Constructor constr : c.getConstructors()){

            ServiceCR cr = (ServiceCR) constr.getAnnotation(ServiceCR.class);
            if(cr != null){
                int pos = constr.getName().split("\\.").length -1;
                System.out.println("<def title=\" "+ constr.getName().split("\\.")[pos] +" \">");
                System.out.println(cr.desc());
                if(!cr.related()[0].equals("None")){
                    System.out.println("<p> <u><b>Related:</b></u></p>");
                    for(String s : cr.related()){
                        System.out.println("<a href=\""+s +".topic\"/>");
                    }
                }
                System.out.println("</def>");
            }
            for(String s : cr.related()){
                if(!realted.contains(s)){
                    realted.add(s);
                }
            }
        }
        System.out.println("</deflist>");
        System.out.println("</chapter>");

        System.out.println("<chapter title=\"Methods\" id=\"methods\">");
        System.out.println("<available-only-for> since v.1.2</available-only-for>");
        boolean build_methods = false;
        for(Method method: c.getDeclaredMethods()){
                ServiceM def = method.getAnnotation(ServiceM.class);
                if(def != null && def.category().equals("Method")){
                    build_methods = true;

                    System.out.println(STR."<chapter title=\"\{method.getName()}: \{def.returns()} \">");
                    System.out.println(STR."<p>\{def.desc()}</p>");
                    System.out.println(STR."<deflist type=\"full\">");
                    System.out.println(STR."<def title=\"Paramaters:\">");
                    for(String s : def.params()){
                        System.out.println(STR."<p> - \{s} </p>");
                    }
                    System.out.println(STR."</def>");
                    System.out.println(STR."<def title=\"Thrown:\">");
                    for(String s : def.thrown()){
                        System.out.println(STR."<p> - " + s + " </p>");
                    }
                    System.out.println(STR."</def>");

                    System.out.println(STR."<def title=\"Access:\">");
                    System.out.println(STR."<p>\{method.accessFlags()}</p>");
                    System.out.println(STR."</def>");
                    System.out.println(STR."</deflist>");
                    System.out.println(STR."</chapter>");

                    for(String s : def.related()){
                        if(!realted.contains(s)){
                            realted.add(s);
                        }
                    }
                }

        }
        if(!build_methods){
            System.out.println("<def title=\"Wow so leer hier\">");
            System.out.println("Ich glaub hier steht nichts");
            System.out.println("</dev>");
        }
        System.out.println("</chapter>");


        System.out.println("<chapter title=\"Getter\" id=\"getter\">");
        System.out.println("<available-only-for> since v.1.2</available-only-for>");
        boolean build_getter = false;
        for(Method method: c.getDeclaredMethods()){
            ServiceM def = method.getAnnotation(ServiceM.class);
            if(def != null && def.category().equals("Getter")){
                System.out.println(STR."<chapter title=\"\{method.getName()}: \{def.returns()} \">");
                System.out.println(STR."<p>\{def.desc()}</p>");
                System.out.println(STR."<deflist type=\"full\">");
                System.out.println(STR."<def title=\"Paramaters:\">");
                for(String s : def.params()){
                    System.out.println(STR."<p> - \{s} </p>");
                }
                System.out.println(STR."</def>");
                System.out.println(STR."<def title=\"Thrown:\">");
                for(String s : def.thrown()){
                    System.out.println(STR."<p> - " + s + " </p>");
                }
                System.out.println(STR."</def>");

                System.out.println(STR."<def title=\"Access:\">");
                System.out.println(STR."<p>\{method.accessFlags()}</p>");
                System.out.println(STR."</def>");
                System.out.println(STR."</deflist>");
                System.out.println(STR."</chapter>");

                build_getter = true;

                for(String s : def.related()){
                    if(!realted.contains(s)){
                        realted.add(s);
                    }
                }
            }
        }
        if(!build_getter){
            System.out.println("<deflist>");
            System.out.println("<def title=\"Die unendlichen Weiten\">");
            System.out.println("Hier könnten ihre Getter stehen");
            System.out.println("</def>");
            System.out.println("</deflist>");
        }
        System.out.println("</chapter>");

        System.out.println("<chapter title=\"Setter\" id=\"setter\">");
        System.out.println("<available-only-for> since v.1.2</available-only-for>");

        boolean build_setter = false;
        for(Method method: c.getDeclaredMethods()){
            ServiceM def = method.getAnnotation(ServiceM.class);
            if(def != null && def.category().equals("Setter")){
                build_setter = true;
                System.out.println("<chapter title=\" "+ method.getName() +": " + def.returns() + "\">");
                System.out.println("<p>" + def.desc() + "</p>");

                System.out.println("<deflist type=\"full\">");
                System.out.println("<def title=\"Paramaters:\">");
                for(String s : def.params()){
                    System.out.println("<p> - " + s + "</p>");
                }
                System.out.println("</def>");
                System.out.println("<def title=\"Thrown:\">");
                for(String s : def.thrown()){
                    System.out.println("<p> - " + s + " </p>");
                }
                System.out.println("</def>");
                //ut.println(STR."<p> <u><b>Access:</b></u> \{method.accessFlags()}</p>");
                System.out.println("<def title=\"Access:\">");
                System.out.println(STR."<p>\{method.accessFlags()}</p>");
                System.out.println("</def>");
                if(!def.related()[0].equals("None")){
                    //System.out.println("<p> <u><b>Related:</b></u></p>");
                    System.out.println("<def title=\"Related:\">");
                    for(String s : def.related()){
                        System.out.println("<a href=\""+s +".topic\"/>");
                    }
                    System.out.println("</def>");
                }
                System.out.println("</deflist>");
                System.out.println("</chapter>");

                for(String s : def.related()){
                    if(!realted.contains(s)){
                        realted.add(s);
                    }
                }
            }
        }
        if(!build_setter){
            System.out.println("<def title=\"Ich glaub hier is nix drin\">");
            System.out.println("Setter, Setter, ... Oh, hier steht ja gar nichts.");
            System.out.println("</def>");
        }
        System.out.println("</chapter>");


        System.out.println("<seealso>");
        System.out.println("<category ref=\"related\">");
        int count = 1;
        for(String s : realted){
            if (count % 5 == 0) {
                System.out.println("</category>");
                System.out.println("<category ref=\"related\">");
            }else{
                System.out.println("<a href=\""+ s + ".topic\"/>");
            }
            count++;

        }

        System.out.println("</category>");
        System.out.println("</seealso>");

        // Vendor.main(args);
    }


}
