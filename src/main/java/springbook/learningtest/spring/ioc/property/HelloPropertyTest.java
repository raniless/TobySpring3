package springbook.learningtest.spring.ioc.property;

import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HelloPropertyTest {
    @Test
    public void valueAnnotationTest() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.registerBean("helloProperty", HelloProperty.class);
        ctx.refresh();

        HelloProperty helloProperty = ctx.getBean("helloProperty", HelloProperty.class);
        assertEquals("Everyone", helloProperty.getName());
    }

    @Test
    public void valueNotRunningTest() {
        //스프링 컨테이너 밖에서 사용되는 경우 @Value 애노테이션은 무시
        HelloProperty helloProperty2 = new HelloProperty();
        assertNull(helloProperty2.getName());
    }

    @Test
    public void outerResourceValueTest() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ctx);
        reader.loadBeanDefinitions("./springbook/learningtest/spring/ioc/property/helloContext.xml");
        ctx.registerBean("helloProperty", HelloProperty.class);
        ctx.refresh();

        HelloProperty helloProperty = ctx.getBean("helloProperty", HelloProperty.class);
        //System Properties
        assertEquals("Windows 10", helloProperty.getOs());
        //외부 Resource(Property)
        assertEquals("root", helloProperty.getUserName());
    }
}