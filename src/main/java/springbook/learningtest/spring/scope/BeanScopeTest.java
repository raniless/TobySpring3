package springbook.learningtest.spring.scope;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class BeanScopeTest {
    @Test
    public void singletonScope() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class, SingletonClientBean.class);
        Set<SingletonBean> beans = new HashSet<>();

        beans.add(ac.getBean(SingletonBean.class));
        beans.add(ac.getBean(SingletonBean.class));
        assertEquals(1, beans.size());

        beans.add(ac.getBean(SingletonClientBean.class).bean1);
        beans.add(ac.getBean(SingletonClientBean.class).bean2);
        assertEquals(1, beans.size());
    }

    static class SingletonBean {}
    static class SingletonClientBean {
        @Autowired
        SingletonBean bean1;
        @Autowired
        SingletonBean bean2;
    }

    @Test
    public void prototypeScope() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class, PrototypeClientBean.class);
        Set<PrototypeBean> bean = new HashSet<>();

        bean.add(ac.getBean(PrototypeBean.class));
        assertEquals(1, bean.size());
        bean.add(ac.getBean(PrototypeBean.class));
        assertEquals(2, bean.size());

        bean.add(ac.getBean(PrototypeClientBean.class).bean1);
        assertEquals(3, bean.size());
        bean.add(ac.getBean(PrototypeClientBean.class).bean2);
        assertEquals(4, bean.size());
    }

    @Scope("prototype")
    static class PrototypeBean {}
    static class PrototypeClientBean {
        @Autowired
        PrototypeBean bean1;
        @Autowired
        PrototypeBean bean2;
    }
}