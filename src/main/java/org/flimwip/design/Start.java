package org.flimwip.design;

import javafx.stage.Stage;
import org.flimwip.design.Controller.CheckoutSelectionController;
import org.flimwip.design.Controller.DashboardStatsController;
import org.flimwip.design.Documentationhandler.ServiceATT;
import org.flimwip.design.Documentationhandler.ServiceC;
import org.flimwip.design.Documentationhandler.ServiceM;
import org.flimwip.design.Models.AppUser;
import org.flimwip.design.Models.PopulationFile;
import org.flimwip.design.Views.Temp.BranchView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

        //Building documentation
        Class c = BranchView.class;


        ServiceC class_defeinition = (ServiceC) c.getAnnotation(ServiceC.class);

        String folder = c.getPackageName().split("design.")[1];
        System.out.println("<card-summary> " + class_defeinition.desc() + " </card-summary>");

        System.out.println("<available-only-for> Folder "+ folder + " </available-only-for>");

        System.out.println("<chapter title=\"Description\" id=\"description\">");
        System.out.println("<p>" + class_defeinition.desc() + " </p>");
        System.out.println("</chapter>");



        //<chapter title="Attributes" id="attributes">
        System.out.println("<chapter title=\"Attributes\" id=\"attributes\">");
        System.out.println("<available-only-for> since v.1.2</available-only-for>");
        System.out.println("<deflist type=\"wide\">");
        for(Field field : c.getDeclaredFields()){

            ServiceATT att = field.getAnnotation(ServiceATT.class);
            if(att != null){
                System.out.println(field);
                System.out.println(att.desc());
                System.out.println("<def title=\"" + field.getName() + ": " + att.type() + "\">");
                System.out.println(att.desc());
                System.out.println("</def>");
            }
        }
        System.out.println("</deflist>");
        System.out.println("</chapter>");

        System.out.println("<chapter title=\"Methods\" id=\"methods\">");
        System.out.println("<available-only-for> since v.1.2</available-only-for>");
        System.out.println("<deflist type=\"wide\">");
        boolean build_methods = false;
        for(Method method: c.getDeclaredMethods()){
                ServiceM def = method.getAnnotation(ServiceM.class);
                if(def != null && def.category().equals("Method")){
                    build_methods = true;
                    StringBuilder throwing = new StringBuilder();
                    for(String s : def.thrown()){
                        throwing.append(s).append(", ");
                    }

                    System.out.println("<def title=\"" + method.getName() + ": " + def.returns() + "\">");
                    System.out.println("<p>" + def.desc() + "</p>");
                    System.out.println("<p> <u><b>Parameters:</b></u></p>");
                    for(String s : def.params()){
                        System.out.println("<p> - " + s + "</p>");
                    }
                    System.out.println("<p> <u><b>Thrown:</b></u></p>");
                    for(String s : def.thrown()){
                        System.out.println("<p> - " + s + " </p>");
                    }
                    System.out.println("<p> <u><b>Access:</b></u> " + method.accessFlags() +"</p>");
                    System.out.println("</def>");
                }
        }
        if(!build_methods){
            System.out.println("<def title=\"Wow so leer hier\">");
            System.out.println("Ich glaub hier steht nichts");
            System.out.println("</dev>");
        }
        System.out.println("</deflist>");
        System.out.println("</chapter>");


        System.out.println("<chapter title=\"Getter\" id=\"getter\">");
        System.out.println("<available-only-for> since v.1.2</available-only-for>");
        System.out.println("<deflist type=\"wide\">");
        boolean build_getter = false;
        for(Method method: c.getDeclaredMethods()){
            ServiceM def = method.getAnnotation(ServiceM.class);
            if(def != null && def.category().equals("Getter")){
                build_getter = true;
                System.out.println("<def title=\"" + method.getName() + ": " + def.returns() + "\">");
                System.out.println("<p>" + def.desc() + "</p>");
                System.out.println("<p> <u><b>Parameters:</b></u></p>");
                for(String s : def.params()){
                    System.out.println("<p> - " + s + "</p>");
                }
                System.out.println("<p> <u><b>Thrown:</b></u></p>");
                for(String s : def.thrown()){
                    System.out.println("<p> - " + s + " </p>");
                }

                System.out.println("<p> <u><b>Access:</b></u> " + method.accessFlags() +"</p>");
                System.out.println("</def>");
            }
        }
        if(!build_getter){
            System.out.println("<def title=\"Die unendlichen Weiten\">");
            System.out.println("Hier könnten ihre Getter stehen");
            System.out.println("</def>");
        }
        System.out.println("</deflist>");
        System.out.println("</chapter>");

        System.out.println("<chapter title=\"Setter\" id=\"setter\">");
        System.out.println("<available-only-for> since v.1.2</available-only-for>");
        System.out.println("<deflist type=\"wide\">");
        boolean build_setter = false;
        for(Method method: c.getDeclaredMethods()){
            ServiceM def = method.getAnnotation(ServiceM.class);
            if(def != null && def.category().equals("Setter")){
                build_setter = true;
                System.out.println("<def title=\"" + method.getName() + ": " + def.returns() + "\">");
                System.out.println("<p>" + def.desc() + "</p>");
                System.out.println("<p> <u><b>Parameters:</b></u></p>");
                for(String s : def.params()){
                    System.out.println("<p> - " + s + "</p>");
                }
                System.out.println("<p> <u><b>Thrown:</b></u></p>");
                for(String s : def.thrown()){
                    System.out.println("<p> - " + s + " </p>");
                }
                System.out.println("<p> <u><b>Access:</b></u> " + method.accessFlags() +"</p>");
                System.out.println("</def>");
            }
        }
        if(!build_setter){
            System.out.println("<def title=\"Ich glaub hier is nix drin\">");
            System.out.println("Setter, Setter, ... Oh, hier steht ja gar nichts.");
            System.out.println("</def>");
        }
        System.out.println("</deflist>");
        System.out.println("</chapter>");


        // TesterStart.main(args);
    }


}
