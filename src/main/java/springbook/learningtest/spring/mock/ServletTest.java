package springbook.learningtest.spring.mock;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.*;

public class ServletTest {
    @Test
    public void getMethodServlet() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/hello");
        req.addParameter("name", "Spring");

        MockHttpServletResponse res = new MockHttpServletResponse();

        SimpleGetServlet servlet = new SimpleGetServlet();
        servlet.service(req, res);

        assertEquals("<HTML><BODY>Hello Spring</BODY></HTML>", res.getContentAsString());
        assertTrue(res.getContentAsString().contains("Hello Spring"));
    }
}
