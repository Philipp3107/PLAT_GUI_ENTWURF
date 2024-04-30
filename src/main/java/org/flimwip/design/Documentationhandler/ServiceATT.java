package org.flimwip.design.Documentationhandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation used to provide additional information about a field in a class.
 * The information includes a description and the type of the field.<br>
 * Elements to fill: <br>
 * - desc -> This provides description when generating docs. <br>
 * - type -> This provides the Type when generating docs.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ServiceATT {
    /**
     * This provides description when generating docs.
     */
    public String desc() ;
    /**
     * This provides the Type when generating docs.
     */
    public String type();

    public String[] related() default {"None"};
}

