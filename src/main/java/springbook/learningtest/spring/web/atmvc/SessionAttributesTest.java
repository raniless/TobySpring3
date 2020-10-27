package springbook.learningtest.spring.web.atmvc;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import springbook.learningtest.spring.web.AbstractDispatcherServletTest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.Assert.*;

public class SessionAttributesTest extends AbstractDispatcherServletTest {
    @Test
    public void sessionAttr() throws ServletException, IOException {
        setClasses(UserController.class);

        //GET 요청 - form()
        initRequest("/user/edit").addParameter("id", "1");
        runService();

        HttpSession session = request.getSession();
        assertEquals(getModelAndView().getModel().get("user"), session.getAttribute("user"));

        //POST 요청 - submit()
        initRequest("POST", "/user/edit").addParameter("id", "1").addParameter("name", "Spring2");
        request.setSession(session);
        runService();

        assertEquals("mail@spring.com", ((User)getModelAndView().getModel().get("user")).getEmail());
        assertNull(session.getAttribute("user"));
    }

    @Controller
    @SessionAttributes("user")
    static class UserController {
        @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
        public User form(@RequestParam int id) {
            return new User(1, "Spring", "mail@spring.com");
        }

        @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
        public void submit(@ModelAttribute User user, SessionStatus sessionStatus) {
            sessionStatus.setComplete();
        }
    }

    static class User {
        private int id;
        private String name;
        private String email;

        public User() {}

        public User(int id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
