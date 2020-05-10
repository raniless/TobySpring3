package springbook.learningtest.spring.web.hello;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.web.servlet.ModelAndView;
import springbook.learningtest.spring.web.ConfigurableDispatcherServlet;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class HelloControllerTest {
    @Test
    public void helloController() throws ServletException, IOException {
        ConfigurableDispatcherServlet servlet = new ConfigurableDispatcherServlet();
        servlet.setRelativeLocations(this.getClass(), "spring-servlet.xml");
        servlet.setClasses(HelloSpring.class);

        servlet.init(new MockServletConfig("spring"));

        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/hello");
        req.addParameter("name", "Spring");
        MockHttpServletResponse res = new MockHttpServletResponse();

        servlet.service(req, res);

        ModelAndView mav = servlet.getModelAndView();
        assertEquals("/WEB-INF/view/hello.jsp", mav.getViewName());
        assertEquals("Hello Spring", (String)mav.getModel().get("message"));
    }

    @Test
    public void helloControllerDirect() throws Exception {
        GenericXmlApplicationContext ac = new GenericXmlApplicationContext();
        ac.load(this.getClass(), "spring-servlet.xml");
        ac.registerBean("helloSpring", HelloSpring.class);
        ac.refresh();

//        ApplicationContext ac = new GenericXmlApplicationContext(this.getClass(), "spring-servlet.xml");
        HelloController helloController = ac.getBean(HelloController.class);

        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/hello");
        req.addParameter("name", "Spring");
        MockHttpServletResponse res = new MockHttpServletResponse();

        ModelAndView mav = helloController.handleRequest(req, res);
        assertEquals("/WEB-INF/view/hello.jsp", mav.getViewName());
        assertEquals("Hello Spring", (String)mav.getModel().get("message"));
    }
}