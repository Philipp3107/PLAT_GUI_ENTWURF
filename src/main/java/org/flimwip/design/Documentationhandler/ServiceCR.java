package org.flimwip.design.Documentationhandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.CONSTRUCTOR)
public @interface ServiceCR {
    /**
     * This provides description when generating docs.
     */
    public String desc();
    /**
     * This provides params when generating docs.
     */
    public String[] params();

    public String[] related() default {"None"};
}
