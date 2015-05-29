package org.evosuite.runtime.annotation;

import java.lang.annotation.*;

/**
 * Define a set of constraints for a method when used in a generated test case.
 *
 * Created by Andrea Arcuri on 22/05/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.CONSTRUCTOR})
@Documented
public @interface Constraints {

    /**
     *  Specify that the tagged method can appear in a test case at most once (or never).
     */
    boolean atMostOnce() default false;

    /**
     * Specify that none of the inputs to the tagged method is null
     *
     * @return
     */
    boolean noNullInputs() default false;

    /**
     * If the tagged method is in the test case, none of these other methods specified
     * here can be present in the test at the same time.
     * If any of those methods belongs to another class, then its canonical name should be used,
     * eg 'className'#'methodName'.
     *
     * <p> Note: if a method is overloaded with different input parameters, all of those variants
     * will be excluded.
     *
     * @return
     */
    String[] excludeOthers() default {};

    /**
     * Specify that the tagged method can only be used <i>after</i> this other one.
     * If this other method belongs to another class, then its canonical name should be used,
     * eg 'className'#'methodName'.
     * @return
     */
    String after() default "";


    /**
     * List of properties that should hold to use this tagged method in a test case.
     * The properties are dynamic, and based on previous execution of the test cases.
     *
     * @return
     */
    String[] dependOnProperties() default  {};
}
