package org.flimwip.design.Documentationhandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Types to Fill <br>
 * - desc -> Description of the Method <br>
 * - category -> Category of the Method <br>
 * - params -> Parameter of the Method <br>
 * - returns -> return values of the Method <br>
 * - thrown -> Thrown errors of the Method
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ServiceM {
    /**
     * This provides description when generating docs.
     */
    @ServiceM(desc = "This provides description when generating docs.", params = {"String description"}, returns = "String")
    public String desc();

    @ServiceM(desc = "This provides the category when generating docs. Getter methods get the catergory Getter, Setter methods get the catergory Setter and any other Method gets the category Method", params = {"String description"}, returns = "String")
    public String category() default "Method";

    /**
     * This provides params when generating docs.
     */
    public String[] params();

    public String returns();

    public String[] thrown() default {"None"};
}

