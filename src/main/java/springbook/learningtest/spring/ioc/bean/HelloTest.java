package springbook.learningtest.spring.ioc.bean;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import springbook.learningtest.spring.ioc.config.AnnotatedHelloConfig;

import static org.junit.Assert.*;

public class HelloTest {
    @Test
    public void registerBeanWithManual() {
        StaticApplicationContext ac = new StaticApplicationContext();
        ac.registerSingleton("hello1", Hello.class);

        Hello hello1 = ac.getBean("hello1", Hello.class);
        assertNotNull(hello1);

        BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
        helloDef.getPropertyValues().addPropertyValue("name", "Spring");

        ac.registerBeanDefinition("hello2", helloDef);

        Hello hello2 = ac.getBean("hello2", Hello.class);
        assertNotNull(hello2);
        assertEquals("Hello Spring", hello2.sayHello());
        assertNotEquals(hello1, hello2);
        assertEquals(2, ac.getBeanFactory().getBeanDefinitionCount());
    }

    @Test
    public void registerBeanWithDependency(){
        StaticApplicationContext ac = new StaticApplicationContext();

        ac.registerBeanDefinition("printer", new RootBeanDefinition(StringPrinter.class));

        BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
        helloDef.getPropertyValues().addPropertyValue("name", "Spring");
        helloDef.getPropertyValues().addPropertyValue("printer", new RuntimeBeanReference("printer"));

        ac.registerBeanDefinition("hello", helloDef);

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();

        assertEquals("Hello Spring", ac.getBean("printer").toString());
    }

    @Test
    public void genericApplicationContextForXml() {
        GenericApplicationContext ac = new GenericApplicationContext();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ac);
        reader.loadBeanDefinitions("genericApplicationContext.xml");

        ac.refresh();

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();

        assertEquals("Hello Spring", ac.getBean("printer").toString());
    }

    @Test
    public void genericApplicationContextForProperties() {
        GenericApplicationContext ac = new GenericApplicationContext();
        PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(ac);
        reader.loadBeanDefinitions("genericApplicationContext.properties");

        ac.refresh();

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();

        assertEquals("Hello Spring", ac.getBean("printer").toString());
    }

    @Test
    public void genericXmlApplicationContext() {
        GenericXmlApplicationContext ac = new GenericXmlApplicationContext("genericApplicationContext.xml");

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();

        assertEquals("Hello Spring", ac.getBean("printer").toString());
    }

    @Test
    public void contextTree(){
        ApplicationContext parent = new GenericXmlApplicationContext("parentContext.xml");

        GenericApplicationContext child = new GenericApplicationContext(parent);
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(child);
        reader.loadBeanDefinitions("childContext.xml");
        child.refresh();

        Printer printer = child.getBean("printer", Printer.class);
        assertNotNull(printer);

        Hello hello = child.getBean("hello", Hello.class);
        assertNotNull(hello);

        hello.print();
        assertEquals("Hello Child", printer.toString());
    }

    @Test
    public void simpleBeanScanning() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("springbook.learningtest.spring.ioc.bean");
        AnnotatedHello hello = ctx.getBean("myAnnotatedHello", AnnotatedHello.class);

        assertNotNull(hello);

        ApplicationContext ctx2 = new AnnotationConfigApplicationContext(AnnotatedHelloConfig.class);
        AnnotatedHello hello2 = ctx2.getBean("annotatedHello", AnnotatedHello.class);;

        assertNotNull(hello2);

        AnnotatedHelloConfig config = ctx2.getBean("annotatedHelloConfig", AnnotatedHelloConfig.class);

        assertNotNull(config);

        assertEquals(config.annotatedHello(), config.annotatedHello());
    }
}
