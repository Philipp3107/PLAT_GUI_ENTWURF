package org.flimwip.design.Controller;

import javafx.beans.property.Property;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;
import org.flimwip.design.Models.User;
import org.flimwip.design.Views.UserView;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class UserController {

    private ArrayList<UserView> user_views_dashboard;

    private ArrayList<UserView> user_views_settings;

    private ArrayList<User> pos_user;
    public UserController(){
        System.out.println("UserController -> Initializing Users");
        load_users();
        System.out.println("UserController -> loaded users");
        this.user_views_dashboard = new ArrayList<>();
        this.user_views_settings = new ArrayList<>();
        for(User user : pos_user){
            this.user_views_dashboard.add(new UserView(user, this));
            this.user_views_settings.add(new UserView(user, this));
            System.out.println("UserController -> Build user: " + user.getName());
        }
        System.out.println("UserController -> builded user views");
    }

    public ArrayList<UserView> get_user_views_dashboard(){
        System.out.println("returned user views to dashbaord");
        return this.user_views_dashboard;
    }

    public ArrayList<UserView> get_user_views_settings(){
        System.out.println("returned user views to Settings");
        return this.user_views_settings;
    }



    public void change_selected(String name){
        for(User user: pos_user){
            if(!user.getName().equals(name)){
                user.setSelected(false);
                for(UserView view : user_views_settings){
                    if(view.get_users_name().equals(user.getName())){
                        view.set_deselected();
                    }
                }
                System.out.println("user " + user.getName() + " set to " + user.isSelected());
            }else{
                user.setSelected(true);
                for(UserView view : user_views_settings){
                    if(view.get_users_name().equals(user.getName())){
                        view.set_selected();
                    }
                }
                System.out.println("user " + user.getName() + " set to " + user.isSelected());
            }
        }
        //Dashboard
        for(User user: pos_user){
            if(!user.getName().equals(name)){
                user.setSelected(false);
                for(UserView view : user_views_dashboard){
                    if(view.get_users_name().equals(user.getName())){
                        view.set_deselected();
                    }
                }
                System.out.println("user " + user.getName() + " set to " + user.isSelected());
            }else{
                user.setSelected(true);
                for(UserView view : user_views_dashboard){
                    if(view.get_users_name().equals(user.getName())){
                        view.set_selected();
                    }
                }
                System.out.println("user " + user.getName() + " set to " + user.isSelected());
            }
        }
        rewrite_users();
    }


    private void load_users(){
        String file = "H:\\PLAT\\Data\\Users";
        File user_dir = new File(file);
        pos_user = new ArrayList<>();
        if(user_dir.exists()){
            for(File user_file: user_dir.listFiles()){
                try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(user_file.getAbsolutePath()))){
                    Properties props = new Properties();
                    props.load(bis);
                    //name, description, username, password, selected, usercontroller
                    User user = new User(props.getProperty("name"), props.getProperty("description"), props.getProperty("username"), props.getProperty("password"), props.getProperty("default").equals("false") ? false : true, this );
                    this.pos_user.add(user);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }





    private void rewrite_users(){
        String file = "H:\\PLAT\\Data\\Users";
        for(User user : pos_user){
            File f = new File(file + "\\" + user.getUsername() + ".properties");
            System.out.println(f.exists());
            Properties properties;
            try(BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()))){
                properties = new Properties();
                properties.load(br);
                System.out.println(properties.getProperty("name"));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsolutePath()))){
                properties.setProperty("default", user.isSelected() ? "true" : "false");
                properties.store(bw, null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
