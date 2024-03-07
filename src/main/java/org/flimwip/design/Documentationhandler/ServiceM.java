package org.flimwip.design.Documentationhandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ServiceM {
    /**
     * This provides description when generating docs.
     */
    public String desc() default "";
    /**
     * This provides params when generating docs.
     */
    public String[] params();
}

