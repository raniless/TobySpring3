package springbook.learningtest.spring.web.hello;

import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import springbook.learningtest.spring.web.AbstractDispatcherServletTest;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SimpleHelloControllerTest extends AbstractDispatcherServletTest {
    @Test
    public void helloController() throws ServletException, IOException {
        ModelAndView mav = setRelativeLocations("spring-servlet.xml")
                            .setClasses(HelloSpring.class)
                            .initRequest(RequestMethod.GET, "/hello")
                            .addParameter("name", "Spring")
                            .runService()
                            .getModelAndView();

        assertEquals("/WEB-INF/view/hello.jsp", mav.getViewName());
        assertEquals("Hello Spring", (String)mav.getModel().get("message"));

        setRelativeLocations("spring-servlet.xml")
            .setClasses(HelloSpring.class)
            .initRequest(RequestMethod.GET, "/hello")
            .addParameter("name", "Spring")
            .runService()
            .assertModel("message", "Hello Spring")
            .assertViewName("/WEB-INF/view/hello.jsp");
    }
}