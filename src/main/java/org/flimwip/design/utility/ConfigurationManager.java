package org.flimwip.design.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ConfigurationManager {


    private static final String DEFAULT_CONFIG_FILE = "config.properties";
    private static final String DEFAULT_CONFIG_PATH = "H:\\PLAT\\Data\\configurations";

    private static final MyLogger logger = new MyLogger(ConfigurationManager.class);
    public ConfigurationManager(){
        logger.set_Level(LoggingLevels.FINE);
    }


    public static ArrayList<String> fetch_configs(){
        logger.set_Level(LoggingLevels.FINE);
        check_paths();
        return null;
    }

    public static void build_config(HashMap<String, ArrayList<String>> configs){
        logger.set_Level(LoggingLevels.FINE);
        check_paths();
    }

    private static void check_paths(){
        File f = new File(DEFAULT_CONFIG_PATH);
        if(f.exists()){
            logger.log(LoggingLevels.INFO, "Folder: " + DEFAULT_CONFIG_PATH + " already exists.");
        }else{
            f.mkdirs();
            logger.log(LoggingLevels.ERROR, "Folder: " + DEFAULT_CONFIG_PATH + " did not exist. Creating it");
        }
    }


}
