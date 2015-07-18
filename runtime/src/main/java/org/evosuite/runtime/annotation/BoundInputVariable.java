package org.evosuite.runtime.annotation;

import java.lang.annotation.*;

/**
 * Specify that the given method is bound to the given input variable.
 * The method cannot be removed as long as that variable exists.
 * If that variables is removed, then this method should be removed as well.
 *
 * Created by Andrea Arcuri on 30/05/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Documented
public @interface BoundInputVariable {

    /**
     * Specify that the given method, where this annotation is used, is an
     * initializer for the bound variable.
     * This means it can be used only directly after a "new" and before
     * any other method
     *
     * @return
     */
    boolean initializer() default false;


    /**
     * Specify that the given method can only be called once on the bounded variable
     *
     * @return
     */
    boolean atMostOnce() default false;

    /**
     * Specify that the given method can only be called once with same
     * parameters on the bounded variable.
     * In other words, the method can be called more than once, but then
     * there should be at least on parameter which is different
     *
     * @return
     */
    boolean atMostOnceWithSameParameters() default false;

}
