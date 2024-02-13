package org.flimwip.demo.Controller;

import org.flimwip.demo.Views.Kasse;
import org.flimwip.demo.Views.MainMenuButton;
import org.flimwip.demo.Views.NiederlassungView;

public class CheckoutSelectionController {

    private Kasse[] kassen;

    private Kasse selected = null;
    private NiederlassungView view;

    private MainMenuButton[] mmb;
    public CheckoutSelectionController(NiederlassungView view){
        this.view = view;
    }
    public void set_selected(String id){

        for(Kasse k : kassen){
            if(!k.getId().equals(id)){
                k.unselect();
            }
            if(k.getId().equals(id)){
                this.selected = k;
            }
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
