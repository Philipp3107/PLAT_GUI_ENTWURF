package org.flimwip.design.Controller;

import org.flimwip.design.Views.Dashboard;
import org.flimwip.design.Views.DashboardButton;

public class DashboardStatsController {

    private Dashboard dashboard;

    private DashboardButton[] buttons = new DashboardButton[3];


    public DashboardStatsController(Dashboard dashboard){
    this.dashboard = dashboard;
    }

    public void set_dash_buttons(DashboardButton error, DashboardButton warn, DashboardButton critical){
        buttons[0] = error;
        buttons[1] = warn;
        buttons[2] = critical;

    }

    public void deselect(String name){
        for(DashboardButton d: buttons){
            if(!d.getId().equals(name)){
                d.deselect();
            }
        }
    }
    public void change(String name){
        this.dashboard.change_stats(name);
    }
}
