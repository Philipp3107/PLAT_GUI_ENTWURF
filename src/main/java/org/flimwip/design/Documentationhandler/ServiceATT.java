package org.flimwip.design.Documentationhandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ServiceATT {
    /**
     * This provides description when generating docs.
     */
    public String desc() ;

    public String type();
}

