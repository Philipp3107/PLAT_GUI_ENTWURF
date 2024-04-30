package org.flimwip.design;

import org.flimwip.design.Documentationhandler.ServiceC;
import org.flimwip.design.Documentationhandler.ServiceM;

@ServiceC(desc="This is the Launcher class.")
public class Start {

    @ServiceM(desc="The Launchers Main Method. It starts the Main method of the class extending Application to start the Applcaication.",
              category="Method",
              params={"String[] args: Arguments"},
              returns="void",
              thrown={"None"})
    public static void main(String[] args) {

        //Kommentar um zu testen dass das hier auf dem richtigen branch läuft

        /*new DokumentationBuilder(NetCon.class);

        Vendor.main(args);*/

        Main.main(args);
    }


}
