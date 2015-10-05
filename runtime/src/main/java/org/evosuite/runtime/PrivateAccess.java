/**
 * Copyright (C) 2010-2015 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser Public License as published by the
 * Free Software Foundation, either version 3.0 of the License, or (at your
 * option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser Public License along
 * with EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package org.evosuite.runtime;

import org.evosuite.runtime.javaee.injection.InjectionList;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to access private fields/methods by reflection.
 * If the accessed fields/methods do not exist any more, than
 * the tests would gracefully stop
 *
 * Created by Andrea on 20/02/15.
 */
public class PrivateAccess {

    /**
     * flag to specify to throw AssumptionViolatedException when fields/methods do not
     * exist any more. this should bet set to false iff during experiments
     */
    private static boolean shouldNotFailTest = true;

    public static void setShouldNotFailTest(boolean b){
        shouldNotFailTest = b;
    }


    /**
     * Use reflection to set the given field
     *
     * @param klass
     * @param instance  null if field is static
     * @param fieldName
     * @param value
     * @param <T>  the class type
     * @throws IllegalArgumentException if klass or fieldName are null
     * @throws FalsePositiveException  if the the field does not exist anymore (eg due to refactoring)
     */
    public  static <T> void setVariable(Class<T> klass, T instance, String fieldName, Object value)
            throws IllegalArgumentException, FalsePositiveException {
        setVariable(klass,instance,fieldName,value,null);
    }

    /**
     * Use reflection to set the given field
     *
     * @param klass
     * @param instance  null if field is static
     * @param fieldName
     * @param value
     * @param <T>  the class type
     * @param tagsToCheck if not null, then the field has to have at least one the tags in such list
     * @throws IllegalArgumentException if klass or fieldName are null
     * @throws FalsePositiveException  if the the field does not exist anymore (eg due to refactoring)
     */
    public  static <T> void setVariable(Class<?> klass, T instance, String fieldName, Object value,
                                        List<Class<? extends Annotation>> tagsToCheck)
            throws IllegalArgumentException, FalsePositiveException {

        if(klass == null){
            throw new IllegalArgumentException("No specified class");
        }
        if(fieldName == null){
            throw new IllegalArgumentException("No specified field name");
        }
        if(fieldName.equals("serialVersionUID")){
            throw new IllegalArgumentException("It is not allowed to set serialVersionUID by reflection");
        }
        // note: 'instance' can be null (ie, for static variables), and of course "value"

        Field field = null;
        try {
            field = klass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            String message = "Field '"+fieldName+"' does not exist any more in class "+klass;

            if(shouldNotFailTest) {
                // force the throwing of a JUnit AssumptionViolatedException
                throw new FalsePositiveException(message);
                //it is equivalent to calling
                //org.junit.Assume.assumeTrue(message,false);
            } else {
                throw new IllegalArgumentException(message);
            }
        }
        assert field != null;
        field.setAccessible(true);

        if(tagsToCheck != null){
            boolean match = false;
            for(Annotation ann : field.getDeclaredAnnotations()){
                Class<? extends Annotation> tag = ann.annotationType();
                if(InjectionList.isValidForInjection(tag, tagsToCheck)){
                    match = true;
                    break;
                }
            }

            if(!match){
                throw new IllegalArgumentException("The field "+fieldName+" in class "+klass.getName()+
                        "does not have any valid annotation");
            }
        }

        try {
            field.set(instance,value);
        } catch (IllegalAccessException e) {
            //should never happen, due to setAccessible(true);
            throw new FalsePositiveException("Failed to set field "+fieldName+": "+e.toString());
        }
    }


    /**
     * Use reflection to call the given method
     *
     * @param klass
     * @param instance  null for static methods
     * @param methodName
     * @param inputs  arrays of inputs
     * @param types   types for the inputs
     * @param <T>
     * @return the result of calling the method
     * @throws IllegalArgumentException if either klass or methodName are null
     * @throws FalsePositiveException  if method does not exist any more (eg, refactoring)
     * @throws Throwable the method might throw an internal exception
     */
    public static <T> Object callMethod(Class<T> klass, T instance, String methodName, Object[] inputs, Class<?>[] types)
            throws  Throwable {

        if(klass == null){
            throw new IllegalArgumentException("No specified class");
        }
        if(methodName == null){
            throw new IllegalArgumentException("No specified method name");
        }
        // note: 'instance' can be null (ie, for static methods), and of course "inputs"

        if( (types==null && inputs!=null) || (types!=null && inputs==null) ||(types!=null && inputs!=null && types.length!=inputs.length)){
            throw new IllegalArgumentException("Mismatch between input parameters and their type description");
        }

        Method method = null;
        try {
            method = klass.getDeclaredMethod(methodName,types);
        } catch (NoSuchMethodException e) {
            String message = "Method "+methodName+" does not exist anymore";
            if(shouldNotFailTest){
                throw new FalsePositiveException(message);
            } else {
                throw new IllegalArgumentException(message);
            }
        }
        assert method != null;
        method.setAccessible(true);

        Object result = null;

        try {
            result = method.invoke(instance,inputs);
        } catch (IllegalAccessException e) {
           //shouldn't really happen
            throw new FalsePositiveException("Failed to call "+methodName+": "+e.toString());
        } catch (InvocationTargetException e) {
            //we need to propagate the real cause to the test
            throw e.getTargetException();
        }

        return result;
    }

    /*
        TODO likely need one method per number of inputs
     */

    public static <T> Object callMethod(Class<T> klass, T instance, String methodName)
            throws Throwable {
        return callMethod(klass,instance,methodName,new Object[0], new Class<?>[0]);
    }

    public static <T> Object callMethod(Class<T> klass, T instance, String methodName, Object input, Class<?> type)
            throws Throwable {
        return callMethod(klass,instance,methodName,new Object[]{input}, new Class<?>[]{type});
    }

    public static <T> Object callMethod(Class<T> klass, T instance, String methodName
            , Object i0, Class<?> t0, Object i1, Class<?> t1)
            throws  Throwable {
        return callMethod(klass,instance,methodName,new Object[]{i0,i1}, new Class<?>[]{t0,t1});
    }

    public static <T> Object callMethod(Class<T> klass, T instance, String methodName
            , Object i0, Class<?> t0, Object i1, Class<?> t1, Object i2, Class<?> t2)
            throws  Throwable {
        return callMethod(klass,instance,methodName,new Object[]{i0,i1,i2}, new Class<?>[]{t0,t1,t2});
    }

    public static <T> Object callMethod(Class<T> klass, T instance, String methodName
            , Object i0, Class<?> t0, Object i1, Class<?> t1, Object i2, Class<?> t2,Object i3, Class<?> t3)
            throws  Throwable {
        return callMethod(klass,instance,methodName,new Object[]{i0,i1,i2,i3}, new Class<?>[]{t0,t1,t2,t3});
    }

    public static <T> Object callMethod(Class<T> klass, T instance, String methodName
            , Object i0, Class<?> t0, Object i1, Class<?> t1, Object i2, Class<?> t2,Object i3, Class<?> t3,Object i4, Class<?> t4)
            throws  Throwable {
        return callMethod(klass,instance,methodName,new Object[]{i0,i1,i2,i3,i4}, new Class<?>[]{t0,t1,t2,t3,t4});
    }


    public static Method getCallMethod(int nParameters){
        if(nParameters<0 || nParameters>5){ //TODO might consider have more
            return null;
        }

        List<Class<?>> types = new ArrayList<>();
        types.add(Class.class);//klass
        types.add(Object.class);//T
        types.add(String.class);//methodName

        for(int i=0; i<nParameters; i++){
            types.add(Object.class);
            types.add(Class.class);
        }

        try {
            return PrivateAccess.class.getDeclaredMethod("callMethod",types.toArray(new Class[0]));
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
