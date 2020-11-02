package springbook.learningtest.spring.ioc.config;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.learningtest.spring.ioc.bean.Hello;

import static org.junit.Assert.*;

public class HelloServiceTest {
    @Test
    public void beanEqualTest() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(HelloService.class);
        Hello hello = ctx.getBean("hello", Hello.class);
        Hello hello2 = ctx.getBean("hello2", Hello.class);

        assertNotEquals(hello.getPrinter(), hello2.getPrinter());
    }

    @Test
    public void beanSingletonTest() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(HelloServiceSingleton.class);
        Hello hello = ctx.getBean("hello", Hello.class);
        Hello hello2 = ctx.getBean("hello2", Hello.class);

        assertNotNull(hello.getPrinter());
        assertNotNull(hello2.getPrinter());
        assertEquals(hello.getPrinter(), hello2.getPrinter());
    }
}
