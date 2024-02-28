package org.flimwip.design.utility;

public enum LoggingLevels {
    FINE("FINE ", "\u001B[32m"),
    DEBUG("DEBUG", "\u001B[34m"),
    INFO("INFO ", "\u001B[37m"),
    WARN("WARN ", "\u001B[33m"),
    ERROR("ERROR", "\u001B[31m"),
    FATAL("FATAL", "\u001B[35m");

    private String desc;
    private String color;

    LoggingLevels(String desc, String color){
        this.desc = desc;
        this.color = color;
    }

    public String get_desc(){
        return this.desc;
    }

    public String get_color(){
        return this.color;
    }
}
