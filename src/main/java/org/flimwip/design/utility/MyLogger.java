package org.flimwip.design.utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyLogger {

    private Class c;
    private LoggingLevels level;
    public MyLogger(Class c){
        this.c = c;
    }

    public void set_Level(LoggingLevels level){
        this.level = level;
    }

    public void log_exception(Exception e){
        log(LoggingLevels.FATAL, "An " + e.getClass().getName() + " occured. With cause: " + e.getCause());
    }

    @SafeVarargs
    public final <T> void log(LoggingLevels level, T... args){
        List<String> args_list = new ArrayList<>();
        for(T t: args){
            if(t instanceof String){
                args_list.add((String) t);
            }else{
                args_list.add(String.valueOf(t));
            }
        }
        String[] args_arr = new String[args_list.size()];
        for(int i = 0; i< args_list.size(); i++){
            args_arr[i] = args_list.get(i);
        }

        log(level, args_arr);
    }

    public void log(LoggingLevels level, String... args){
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
        boolean log = true;
        if(this.level != null){
            if(this.level.get_desc().equals("DEBUG")){
                switch(level){
                    case FINE -> log = false;
                }
            }else if(this.level.get_desc().equals("INFO ")){
                switch(level){
                    case FINE, DEBUG -> log = false;
                }
            }else if(this.level.get_desc().equals("WARN ")){
                switch(level){
                    case FINE, DEBUG, INFO -> log = false;
                }
            }else if(this.level.get_desc().equals("ERROR")){
                switch(level){
                    case FINE, DEBUG, INFO, WARN -> log = false;
                }
            }else if(this.level.get_desc().equals("FATAL")){
                switch(level){
                    case FINE, DEBUG, INFO, WARN, ERROR -> log = false;
                }
            }
        }


        if(log){
            StringBuilder logging = new StringBuilder(level.get_color() + timeStamp + " " + level.get_desc() + " [" + c.getName() + "] " + "{ " +Thread.currentThread().getName() +" } ");
            for(String s : args){
                logging.append(" ").append(s);
            }
            logging.append("\u001B[0m");
            System.out.println(logging);
        }


    }
}