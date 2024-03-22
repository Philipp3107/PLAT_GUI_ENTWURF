package org.flimwip.design.utility;

import org.flimwip.design.Documentationhandler.*;

/**
 * Enumeration that represents different logging levels with their descriptions and colors.
 * Each logging level has a description and a color associated with it.
 */
@ServiceC(desc="Enumeration that represents different logging levels with their descriptions and colors. Each logging level has a description and a color associated with it.",
related={"PKLogger"})
public enum LoggingLevels {
    /**
     * Represents the FINE logging level.
     * It has the description "FINE " and the color code "\u001B[32m".
     */
    FINE("FINE ", "\u001B[32m"),

    /**
     *
     */
    DEBUG("DEBUG", "\u001B[34m"),

    /**
     *
     */
    INFO("INFO ", "\u001B[37m"),

    /**
     * Enumerated value representing the warning logging level.
     * This logging level has a description and a color associated with it.
     */
    WARN("WARN ", "\u001B[33m"),

    /**
     * Enumeration that represents different logging levels with their descriptions and colors.
     * Each logging level has a description and a color associated with it.
     */
    ERROR("ERROR", "\u001B[31m"),

    /**
     * Represents the FATAL logging level.
     * It has the description "FATAL" and the color code "\u001B[35m".
     */
    FATAL("FATAL", "\u001B[35m");

    /**
     * Represents a description.
     */
    @ServiceATT(desc="Represents a description.",
                type="String",
                related={"PKLogger"})
    private String desc;
    /**
     * Represents the color associated with a logging level.
     * This variable is used in the LoggingLevels enumeration to define the color associated with each logging level.
     */
    @ServiceATT(desc="Represents the color associated with a logging level. This variable is used in the LoggingLevels enumeration to define the color associated with each logging level.",
                type="String",
                related={"PKLogger"})
    private String color;

    /**
     * Initializes a new LoggingLevels object with the given description and color.
     *
     * @param desc  the description associated with the logging level
     * @param color the color code associated with the logging level
     */
    @ServiceCR(desc="Initializes a new LoggingLevels object with the given description and color.",
               params={"desc: String -> the description associated with the logging level", "color: Color -> the color code associated with the logging level"},
               related={"PKLogger"})
    LoggingLevels(String desc, String color){
        this.desc = desc;
        this.color = color;
    }

    /**
     * Gets the description associated with a logging level.
     *
     * @return the description associated with the logging level
     */
    @ServiceM(desc="<##>Gets the description associated with a logging level.",
              category="Method",
              params={"None"},
              returns="String -> the description associated with the logging level",
              thrown={"None"},
              related={"None"})
    public String get_desc(){
        return this.desc;
    }

    /**
     * Retrieves the color associated with a logging level.
     *
     * @return the color associated with the logging level
     */
    @ServiceM(desc="<##>Retrieves the color associated with a logging level.",
              category="Method",
              params={"None"},
              returns="Color -> the color associated with the logging level",
              thrown={"None"},
              related={"None"})
    public String get_color(){
        return this.color;
    }
}
