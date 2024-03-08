package org.flimwip.design;

import javafx.stage.Stage;
import org.flimwip.design.Documentationhandler.ServiceATT;
import org.flimwip.design.Documentationhandler.ServiceM;
import org.flimwip.design.Models.PopulationFile;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Start {


    public static void main(String[] args) {
        //Main.main(args);
        /*for(Field field : Sampleclass.class.getDeclaredFields()){
            System.out.println(field.getName());
            ServiceATT att = field.getAnnotation(ServiceATT.class);
            System.out.println(att.desc());

        }


        for(Method method: Sampleclass.class.getMethods()){
                ServiceM def = method.getAnnotation(ServiceM.class);
                if(def != null){
                    System.out.println(def.desc());
                    if(def.params().length > 0){
                        System.out.println("Parameters");
                    }
                    for(String s: def.params()){
                        System.out.println(s);
                    }
                }
        }*/
        //TestMainObservable.main(args);
        TesterStart.main(args);
    }


}
