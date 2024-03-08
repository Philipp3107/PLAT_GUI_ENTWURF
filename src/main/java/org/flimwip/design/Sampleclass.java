package org.flimwip.design;

import org.flimwip.design.Documentationhandler.ServiceATT;
import org.flimwip.design.Documentationhandler.ServiceCR;
import org.flimwip.design.Documentationhandler.ServiceM;

public class Sampleclass {

    @ServiceATT(desc = "name: String")
    private String name;

    @ServiceCR(desc = "Constructor of the Class SampleClass", params = {"name : String"})
    public Sampleclass(String name) {
        this.name = name;
    }

    @ServiceM(desc = "Getter method for name", params = {}, returns = {"String - name : Name der Smapleclass"}, thrown = {})
    public String getName() {
        return name;
    }

    @ServiceM(desc = "Setter method for name", params = {"name: String"}, returns = {}, thrown = {})
    public void setName(String name) {
        this.name = name;
    }
}