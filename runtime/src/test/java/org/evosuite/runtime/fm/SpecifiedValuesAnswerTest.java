package org.evosuite.runtime.fm;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Andrea Arcuri on 27/07/15.
 */
public class SpecifiedValuesAnswerTest {

    public interface BaseString{
        public String getString();
    }

    private static boolean checkString_3different(BaseString s){
        if(s.getString().equals("foo") && s.getString().equals("bar") && s.getString().equals("42")){
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkString_allSame(BaseString s){
        if(s.getString().equals("foo") && s.getString().equals("foo") && s.getString().equals("foo")){
            return true;
        } else {
            return false;
        }
    }

    @Test
    public void testBasicWithString_3different(){

        BaseString s = mock(BaseString.class);
        when(s.getString()).thenAnswer(new SpecifiedValuesAnswer<>("foo"));
        boolean res = checkString_3different(s);
        //should fail
        Assert.assertFalse(res);

        when(s.getString()).thenAnswer(new SpecifiedValuesAnswer<>("foo", "bar", "42"));
        res = checkString_3different(s);
        Assert.assertTrue(res);
    }


    @Test
    public void testBasicWithString_allSame(){

        BaseString s = mock(BaseString.class);
        when(s.getString()).thenAnswer(new SpecifiedValuesAnswer<>("bar"));
        boolean res = checkString_allSame(s);
        //should fail
        Assert.assertFalse(res);

        when(s.getString()).thenAnswer(new SpecifiedValuesAnswer<>("foo")); //1 "foo" should be enough
        res = checkString_allSame(s);
        Assert.assertTrue(res);
    }


    public interface BaseInteger{
        public Integer getInteger();
    }

    private static boolean checkInteger(BaseInteger i){
        Integer v = i.getInteger();
        return v==42;
    }

    @Test
    public void testBasicInteger(){

        BaseInteger i = mock(BaseInteger.class);
        when(i.getInteger()).thenAnswer(new SpecifiedValuesAnswer<>(7));
        boolean res = checkInteger(i);
        Assert.assertFalse(res);

        when(i.getInteger()).thenAnswer(new SpecifiedValuesAnswer<Integer>()); //note: here it is important to specify <Integer>
        res = checkInteger(i);
        Assert.assertFalse(res); //still should fail, as default is 0

        when(i.getInteger()).thenAnswer(new SpecifiedValuesAnswer<>(42));
        res = checkInteger(i);
        Assert.assertTrue(res);
    }

    public interface BaseInt{
        public int getInt();
    }

    private static boolean checkInt(BaseInt i){
        int v = i.getInt();
        return v==42;
    }

    @Test
    public void testBasicInt(){

        BaseInt i = mock(BaseInt.class);
        when(i.getInt()).thenAnswer(new SpecifiedValuesAnswer<>(7));
        boolean res = checkInt(i);
        Assert.assertFalse(res);

        when(i.getInt()).thenAnswer(new SpecifiedValuesAnswer<Integer>()); //note: here it is important to specify <Integer>
        res = checkInt(i);
        Assert.assertFalse(res); //still should fail, as default is 0

        when(i.getInt()).thenAnswer(new SpecifiedValuesAnswer<>(42));
        res = checkInt(i);
        Assert.assertTrue(res);
    }
}