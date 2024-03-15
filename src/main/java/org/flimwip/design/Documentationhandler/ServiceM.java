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
    public String desc();

    /**
     * This provides a category when generatin docs.
     */
    public String category() default "Method";

    /**
     * This provides params when generating docs.
     */
    public String[] params();
    /**
     * This provides return values when generating docs.
     */
    public String returns();
    /**
     * This provides thrown Exceptions when generating docs.
     */
    public String[] thrown() default {"None"};

    public String[] related() default {"None"};
}

