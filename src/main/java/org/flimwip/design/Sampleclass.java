package org.flimwip.design;

import org.flimwip.design.Documentationhandler.ServiceATT;
import org.flimwip.design.Documentationhandler.ServiceCR;
import org.flimwip.design.Documentationhandler.ServiceM;

public class Sampleclass {

    @ServiceATT(desc = "name: String", type = "String")
    private String name;

    @ServiceCR(desc = "Constructor of the Class SampleClass", params = {"name : String"})
    public Sampleclass(String name) {
        this.name = name;
    }

    @ServiceM(desc = "Getter method for name", category = "", params = {}, returns = "String - name : Name der Smapleclass", thrown = {})
    public String getName() {
        return name;
    }

    @ServiceM(desc = "Setter method for name", category = "", params = {"name: String"}, returns = "", thrown = {})
    public void setName(String name) {
        this.name = name;
    }


    public void nonsense(String iwas, String iwas_w){

    }
}