package springbook.learningtest.spring.web.controllers;

import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;
import springbook.learningtest.spring.web.AbstractDispatcherServletTest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ServletControllerTest extends AbstractDispatcherServletTest {
    @Test
    public void helloServletController() throws ServletException, IOException {
        setClasses(SimpleServletHandlerAdapter.class, HelloServlet.class)
                .initRequest("/hello")
                .addParameter("name", "Spring");

        assertEquals("Hello Spring", runService().getContentAsString());
    }

    @Component("/hello")
    static class HelloServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String name = req.getParameter("name");
            resp.getWriter().print("Hello " + name);
        }
    }
}
