package org.flimwip.design.Controller;

import javafx.beans.property.Property;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;
import org.flimwip.design.Models.User;
import org.flimwip.design.Views.UserView;
import org.flimwip.design.Views.Vendor;
import org.flimwip.design.utility.LoggingLevels;
import org.flimwip.design.utility.MyLogger;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class UserController {

    private User selected;
    private ArrayList<UserView> user_views_dashboard;

    private ArrayList<UserView> user_views_settings;

    private MyLogger logger = new MyLogger(this.getClass());

    private Vendor vendor = null;

    private ArrayList<User> pos_user;
    public UserController(){
        logger.set_Level(LoggingLevels.FINE);
        load_users();
        logger.log(LoggingLevels.INFO, "Loaded Users");
        this.user_views_dashboard = new ArrayList<>();
        this.user_views_settings = new ArrayList<>();
        for(User user : pos_user){
            this.user_views_dashboard.add(new UserView(user, this));
            this.user_views_settings.add(new UserView(user, this));

            if(user.isSelected()){
                this.selected = user;
                logger.log(LoggingLevels.INFO, "Selected User is: " + user.getName());
            }
            logger.log(LoggingLevels.INFO, "Build User: " + user.getName());
        }
    }

    public ArrayList<UserView> get_user_views_dashboard(){
        return this.user_views_dashboard;
    }

    public ArrayList<UserView> get_user_views_settings(){
        return this.user_views_settings;
    }

    public User get_selected_user(){
        return this.selected;
    }

    public void set_vendor(Vendor vendor){
        this.vendor = vendor;
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
                logger.log(LoggingLevels.INFO, "User:", user.getName(),"is", (user.isSelected() ? "selected": "deselected"));
            }else{
                user.setSelected(true);
                //this.vendor.update_user(user);
                this.selected = user;
                logger.log(LoggingLevels.DEBUG, "New selected User is: " + user.getName());
                for(UserView view : user_views_settings){
                    if(view.get_users_name().equals(user.getName())){
                        view.set_selected();
                    }
                }
                logger.log(LoggingLevels.INFO, "User:", user.getName(),"is", (user.isSelected() ? "selected": "deselected"));
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
                logger.log(LoggingLevels.INFO, "User:", user.getName(),"is", (user.isSelected() ? "selected": "deselected"));
            }else{
                user.setSelected(true);
                for(UserView view : user_views_dashboard){
                    if(view.get_users_name().equals(user.getName())){
                        view.set_selected();
                    }
                }
                logger.log(LoggingLevels.INFO, "User:", user.getName(),"is", (user.isSelected() ? "selected": "deselected"));
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
            logger.log(LoggingLevels.INFO, "File", f.getName(), "exists");
            Properties properties;
            try(BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()))){
                properties = new Properties();
                properties.load(br);
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
