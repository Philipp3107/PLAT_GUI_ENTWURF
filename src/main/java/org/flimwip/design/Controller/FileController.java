package org.flimwip.design.Controller;

import org.flimwip.design.Views.CheckoutFile;
import org.flimwip.design.Views.NiederlassungView;

import java.util.ArrayList;

public class FileController {

    private ArrayList<CheckoutFile> files;

    private ArrayList<CheckoutFile> selected = new ArrayList<>();
    private NiederlassungView nv;
    public FileController(NiederlassungView nv){
        this.files = new ArrayList<>();
        this.selected = new ArrayList<>();
        this.nv = nv;
    }


    public void add_file(CheckoutFile file){
        files.add(file);
        System.out.println(file.getId() + " added");
    }

    public void set_selected(CheckoutFile file){
        System.out.println("Set selected " + file.getId());
        for(CheckoutFile cf : files){
            System.out.println(cf.getId());
            System.out.println(file.getId());
            if(!cf.getId().equals(file.getId())){
                cf.deselect();
            }
        }
        selected.clear();
        this.selected.add(file);
    }


    public void multi_select(CheckoutFile file) {

        if (selected.isEmpty()) {
            this.selected.add(file);
        } else {
            this.selected.add(file);
            int index_first = 0;
            int index_new = 0;
            for(int i = 0; i < files.size(); i++){
                if(files.get(i).getId().equals(selected.get(0).getId())){
                    index_first = i;
                }
                if(files.get(i).getId().equals(file.getId())){
                    index_new = i;
                }
            }

            if(index_first < index_new) {
                boolean select = false;
                CheckoutFile f = selected.get(0);
                for (CheckoutFile cf : files) {
                    if (select) {
                        cf.select();
                        add_to_selected(cf);
                    } else {
                        if (!cf.getId().equals(f.getId()) && !cf.getId().equals(file.getId())) {
                            cf.deselect();
                            this.selected.remove(cf);
                        }

                    }
                    if (cf.getId().equals(f.getId())) {
                        select = true;
                    }
                    if (cf.getId().equals(file.getId())) {
                        select = false;
                    }
                }
            }else{
                boolean select = false;
                CheckoutFile f = selected.get(0);
                for(int i = files.size() -1; i >= 0; i--){
                    if (select) {
                        files.get(i).select();
                        add_to_selected(files.get(i));
                    } else {
                        if (!files.get(i).getId().equals(f.getId()) && !files.get(i).getId().equals(file.getId())) {
                            files.get(i).deselect();
                            this.selected.remove(files.get(i));
                        }

                    }
                    if (files.get(i).getId().equals(f.getId())) {
                        select = true;
                    }
                    if (files.get(i).getId().equals(file.getId())) {
                        select = false;
                    }
                }
            }
        }
    }

    public void add_to_selected(CheckoutFile file){
        boolean add = true;
        for(CheckoutFile cf : selected){
            if(cf.getId().equals(file.getId())){
                add = false;
            }
        }
        if(add){
            this.selected.add(file);
        }

    }

    public int get_selected_size(){
        return this.selected.size();
    }

    public void handle_secondary_click(){
        for(CheckoutFile f : selected){
            System.out.println(f.getId());

        }
        nv.show_menu();
    }

    public void deselect_all(){
        for(CheckoutFile cf : selected){
            cf.deselect();
        }
        this.selected.clear();

    }
}
