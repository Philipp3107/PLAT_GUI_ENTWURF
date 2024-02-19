package org.flimwip.design.Controller;

import org.flimwip.design.Main;
import org.flimwip.design.Views.MainMenuButton;
import org.flimwip.design.Views.BranchView;

public class MainController {

  private Main main;
  private MainMenuButton[] mmb;
    public MainController(Main main){
        this.main = main;
    }

    public void set_main_center(String dest){
            this.main.set_center(dest);
    }

    public void set_main_menu_buttons(MainMenuButton[] mmb){
        this.mmb = mmb;
    }

    public void deselect_main_menu_buttons(String id){
        for(MainMenuButton mmb : mmb){
            if(mmb.getId().equals(id)){
                System.out.println(id+ " was clicked");
            }else{
                mmb.deselect();
            }
        }
    }

    public void set_center_to_nl(BranchView view){
        this.main.set_center_to_nl(view);
    }
}
