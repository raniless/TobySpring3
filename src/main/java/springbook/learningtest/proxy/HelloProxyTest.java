package springbook.learningtest.proxy;

import org.junit.Test;

import java.lang.reflect.Proxy;

import static org.junit.Assert.*;

public class HelloProxyTest {
    @Test
    public void simpleProxy() {
        Hello hello = new HelloTarget();
        assertEquals("Hello Toby", hello.sayHello("Toby"));
        assertEquals("Hi Toby", hello.sayHi("Toby"));
        assertEquals("Thank You Toby", hello.sayThankYou("Toby"));

        Hello proxyHello = new HelloUppercase(new HelloTarget());
        assertEquals("HELLO TOBY", proxyHello.sayHello("Toby"));
        assertEquals("HI TOBY", proxyHello.sayHi("Toby"));
        assertEquals("THANK YOU TOBY", proxyHello.sayThankYou("Toby"));

        Hello proxiedHello = (Hello) Proxy.newProxyInstance(getClass().getClassLoader()
                , new Class[]{Hello.class}
                , new UppercaseHandler(new HelloTarget()));
        assertEquals("HELLO TOBY", proxiedHello.sayHello("Toby"));
        assertEquals("HI TOBY", proxiedHello.sayHi("Toby"));
        assertEquals("THANK YOU TOBY", proxiedHello.sayThankYou("Toby"));

    }
}
