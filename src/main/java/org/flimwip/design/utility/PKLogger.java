package org.flimwip.design.utility;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.flimwip.design.Documentationhandler.*;

/**
 * The PKLogger class provides logging functionality with different logging levels.
 * It allows logging messages, exceptions, and setting the logging level.
 * <p>
 * Sample Usage:
 * // Set the logging level
 * logger.set_Level(LoggingLevels.INFO);
 * <p>
 * // Log a message
 * logger.log(LoggingLevels.INFO, "This is an information message");
 * <p>
 * // Log an exception
 * try {
 *     // code that may throw an exception
 * } catch (Exception e) {
 *     logger.log_exception(e);
 * }
 */
@ServiceC(desc="The PKLogger class provides logging functionality with different logging levels. It allows logging messages, exceptions, and setting the logging level.",
          related={"None"})
public class PKLogger implements Serializable {

    /**
     * Private variable representing a class.
     */
    @ServiceATT(desc="Private variable representing a class.",
                type="Class",
                related={"None"})
    private Class c;

    /**
     * The logging level used for logging messages.
     */
    @ServiceATT(desc="The logging level used for logging messages.",
                type="LoggingLevels",
                related={"LoggingLevels"})
    private LoggingLevels level;

    /**
     * Constructs a PKLogger object with the given class.
     *
     * @param c the class for which logging is done
     */
    @ServiceCR(desc="Constructs a PKLogger object with the given class.",
               params={"c: Class -> the class for which logging is done"},
               related={"None"})
    public PKLogger(Class c){
        this.c = c;
    }

    /**
     * Sets the logging level for the PKLogger object.
     *
     * @param level the logging level to set
     */
    @ServiceM(desc="<##>Sets the logging level for the PKLogger object.",
              category="Setter",
              params={"level: LoggingLevels -> the logging level to set"},
              returns="void",
              thrown={"None"},
              related={"LoggingLevels"})
    public void set_Level(LoggingLevels level){
        this.level = level;
    }

    /**
     * Logs an exception with the specified logging level.
     *
     * @param e the exception to log
     */
    @ServiceM(desc="<##>Logs an exception with the specified logging level.",
              category="Method",
              params={"e: Exception -> the exception to log"},
              returns="void",
              thrown={"None"},
              related={"None"})
    public void log_exception(Exception e){
        log(LoggingLevels.FATAL, "An " + e.getClass().getName() + " occured. With cause: " + e.getCause());
    }

    /**
     * Logs the provided arguments with the specified logging level.
     *
     * @param <T>   the type of the arguments
     * @param level the logging level
     * @param args  the arguments to be logged
     */
    @ServiceM(desc="<##>Logs the provided arguments with the specified logging level.",
              category="Method",
              params={"<T>: T -> the type of the arguments", "level: LoggingLevels -> the logging level", "args: String -> the arguments to be logged"},
              returns="void",
              thrown={"None"},
              related={"LogginLevels"})
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

    /**
     * Logs the provided arguments with the specified logging level.
     *
     * @param level the logging level
     * @param args  the arguments to be logged
     */
    @ServiceM(desc="<##>Logs the provided arguments with the specified logging level.",
              category="Method",
              params={"level: LogginLEvels -> the logging level", "args: String -> the arguments to be logged"},
              returns="void",
              thrown={"None"},
              related={"None"})
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
