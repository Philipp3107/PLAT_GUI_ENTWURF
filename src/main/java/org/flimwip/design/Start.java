package org.flimwip.design;

import org.flimwip.design.Controller.*;
import org.flimwip.design.Documentationhandler.ServiceC;
import org.flimwip.design.Documentationhandler.ServiceM;
import org.flimwip.design.Models.AppUser;
import org.flimwip.design.Models.JobHistoryItem;
import org.flimwip.design.Models.PopulationFile;
import org.flimwip.design.Models.User;
import org.flimwip.design.Views.MainViews.Dashboard;
import org.flimwip.design.Views.Temp.*;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.PKLogger;
import org.flimwip.design.utility.PersitenzManager;

@ServiceC(desc="This is the Launcher class.")
public class Start {

    @ServiceM(desc="The Launchers Main Method. It starts the Main method of the class extending Application to start the Applcaication.",
              category="Method",
              params={"String[] args: Arguments"},
              returns="void",
              thrown={"None"})
    public static void main(String[] args) {
        //Main.main(args);
        //ArrayList<String> realted = new ArrayList<>();

        Class[] c = {FileController.class, AppUser.class, PopulationFile.class, DashboardStatsController.class, MainController.class, UserController.class, NetCon.class, Checkout.class, Dashboard.class, BackButton.class, Branch.class, MainMenuButton.class, Cryptographer.class, LoggingLevels.class, PersitenzManager.class, PKLogger.class, JobHistoryItem.class};
        for(Class cl : c){
            new DokumentationBuilder(cl);
        }

        //Terminal.main(args);
    }


}
