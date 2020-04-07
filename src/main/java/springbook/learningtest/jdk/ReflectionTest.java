package springbook.learningtest.jdk;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ReflectionTest {
    @Test
    public void invokeMethod() throws Exception {
        String name = "Spring";

        //length()
        assertEquals(6, name.length());

        Method lengthMethod = String.class.getMethod("length");
        assertEquals(Integer.valueOf(6), (Integer)lengthMethod.invoke(name));

        //charAt()
        assertEquals('S', name.charAt(0));

        Method charAtMethod = String.class.getMethod("charAt", int.class);
        assertEquals(Character.valueOf('S'), (Character)charAtMethod.invoke(name, 0));
    }
}