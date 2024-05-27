package org.flimwip.design;

import org.flimwip.design.Controller.DashboardStatsController;
import org.flimwip.design.Documentationhandler.ServiceC;
import org.flimwip.design.Documentationhandler.ServiceM;
import org.flimwip.design.Models.PopulationFile;
import org.flimwip.design.utility.terminal.BMP;

@ServiceC(desc="This is the Launcher class.")
public class Start {

    @ServiceM(desc="The Launchers Main Method. It starts the Main method of the class extending Application to start the Applcaication.",
              category="Method",
              params={"String[] args: Arguments"},
              returns="void",
              thrown={"None"})
    public static void main(String[] args) {

        //Terminal_Parser tp = new Terminal_Parser();
        //String line = "01 02 25 82 02 86 07 00 07 00 09 01 40 07 15 2a 20 2a 20 20 4b 75 6e 64 65 6e 62 65 6c 65 67 20 20 2a 20 2a 07 15 42 61 75 68 61 75 73 20 47 6d 62 48 20 26 20 43 6f 2e 20 4b 47 07 06 4e 4c 20 35 33 31 07 13 49 67 67 65 6c 68 65 69 6d 65 72 20 53 74 72 2e 20 33 30 07 05 36 37 33 34 36 07 06 53 70 65 79 65 72 09 01 00 07 00 07 00 07 18 44 61 74 75 6d 3a 20 20 20 20 20 20 20 20 31 32 2e 30 33 2e 32 30 32 34 07 18 55 68 72 7a 65 69 74 3a 20 20 20 20 31 37 3a 34 30 3a 34 34 20 55 68 72 07 18 42 65 6c 65 67 2d 4e 72 2e 20 20 20 20 20 20 20 20 20 20 20 37 34 39 31 07 18 54 72 61 63 65 2d 4e 72 2e 20 20 20 20 20 20 20 20 20 31 36 32 34 33 32 07 00 09 01 40 07 d 4b 61 72 74 65 6e 7a 61 68 6c 75 6e 67 07 10 53 45 50 41 20 4c 61 73 74 73 63 68 72 69 66 74 07 06 4f 6e 6c 69 6e 65 09 01 00 07 04 43 44 47 4d 07 18 4b 75 72 7a 2d 42 4c 5a 20 20 20 20 20 20 20 20 20 20 20 23 23 23 23 23 07 18 4b 74 6f 2e 20 20 20 20 20 20 20 20 20 20 23 23 23 23 23 23 38 30 31 35 07 18 4b 61 72 74 65 20 37 20 67 81 6c 74 69 67 20 62 69 73 20 31 32 2f 32 37 07 04 49 42 41 4e 09 01 02 07 16 44 45 35 32 35 34 23 23 23 23 23 23 23 23 23 23 23 23 38 30 31 35 09 01 00 07 18 47 2d 49 44 20 20 44 45 31 36 5a 5a 5a 30 30 30 30 30 30 32 30 32 34 35 07 04 4d 2d 49 44 09 01 02 07 16 36 35 34 33 30 32 39 36 37 34 39 31 32 34 30 33 31 32 31 37 34 30 09 01 00 07 18 54 65 72 6d 69 6e 61 6c 2d 49 44 20 20 20 20 20 36 35 34 33 30 32 39 36 07 00 07 18 42 65 74 72 61 67 20 45 55 52 20 20 20 20 20 20 20 31 31 36 35 2c 36 36 07 00 07 00 09 01 50 07 17 54 72 61 6e 73 61 6b 74 69 6f 6e 20 65 72 66 6f 6c 67 72 65 69 63 68 09 01 00 07 00 07 00 09 01 40 07 17 42 69 74 74 65 20 42 65 6c 65 67 20 61 75 66 62 65 77 61 68 72 65 6e 09 01 00 07 00 07 00 09 01 40 07 15 44 69 65 20 42 65 6c 61 73 74 75 6e 67 20 65 72 66 6f 6c 67 74 07 14 7a 75 6d 20 6e 84 63 68 73 74 6d 94 67 6c 69 63 68 65 6e 20 07 f 42 61 6e 6b 61 72 62 65 69 74 73 74 61 67 2e 09 01 00 07 00 07 00 07 00 07 00 09 01 f0";
        //tp.parse(line, 00);

        //Main.main(args);


        //BMP current_type = BMP.get_type("42");
        //System.out.println(current_type);
        //ArrayList<String> realted = new ArrayList<>();

        /*Class[] c = {FileController.class, AppUser.class, PopulationFile.class, DashboardStatsController.class, MainController.class, UserController.class, NetCon.class, Checkout.class, Dashboard.class, BackButton.class, Branch.class, MainMenuButton.class, Cryptographer.class, LoggingLevels.class, PersitenzManager.class, PKLogger.class, JobHistoryItem.class};
        for(Class cl : c){
            new DokumentationBuilder(cl);
        }*/

        //Terminal.main(args);
    }


}
