package org.flimwip.design.Controller;

import org.flimwip.design.Views.Kasse;
import org.flimwip.design.Views.MainMenuButton;
import org.flimwip.design.Views.NiederlassungView;

public class CheckoutSelectionController {

    private Kasse[] kassen;

    private Kasse selected = null;
    private NiederlassungView view;

    private MainMenuButton[] mmb;
    public CheckoutSelectionController(NiederlassungView view){
        this.view = view;
    }
    public void set_selected(String id){
        boolean selected = false;
        for(Kasse k : kassen){
            if(!k.getId().equals(id)){
                k.unselect();
                this.view.set_center("");
            }
            if(k.getId().equals(id)){
                this.selected = k;
                selected = true;
            }
        }

        if(selected){
            this.view.set_center(id);
        }else{
            this.view.set_center("");
        }
    }



    public void set_version(String s){
            this.view.setVersion("Version: " + s);


    }

    public void set_city(String s){
        this.view.set_city("Standort: " + s);

    }

    public void set_mouse_focus(String id){
        for(Kasse k : kassen){
            if(!k.getId().equals(id)){
                k.remove_focus();
            }
        }
    }

    public void set_checkouts(Kasse[] kassen){
    this.kassen = kassen;
    }


    /**Return the current selected Checkout from the controller
     *
     * @return Kasse selected
     */
    public Kasse getSelected() {
        return selected;
    }
}
