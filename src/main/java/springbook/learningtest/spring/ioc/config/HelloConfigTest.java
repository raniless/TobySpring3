package springbook.learningtest.spring.ioc.config;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.learningtest.spring.ioc.bean.Hello;

import static org.junit.Assert.*;

public class HelloConfigTest {
    @Test
    public void beanEqualTest() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(HelloConfig.class);
        Hello hello = ctx.getBean("hello", Hello.class);
        Hello hello2 = ctx.getBean("hello2", Hello.class);

        assertEquals(hello.getPrinter(), hello2.getPrinter());
    }
}
