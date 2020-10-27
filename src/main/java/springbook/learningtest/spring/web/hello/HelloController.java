package springbook.learningtest.spring.web.hello;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.servlet.mvc.Controller;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.InternalResourceView;
import springbook.learningtest.spring.web.controllers.RequireParams;
import springbook.learningtest.spring.web.controllers.SimpleController;
import springbook.learningtest.spring.web.controllers.ViewName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class HelloController implements Controller {
    @Autowired
    private HelloSpring helloSpring;
    @Autowired
    private HelloPdfView helloPdfView;

    @Override
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String name = req.getParameter("name");

        String message = helloSpring.sayHello(name);

        Map<String, Object> model = new HashMap<>();
        model.put("message", message);

//        return new ModelAndView("/WEB-INF/view/hello.jsp", model);

//        View view = new InternalResourceView("/WEB-INF/view/hello.jsp");
//        return new ModelAndView(view, model);

        return new ModelAndView(helloPdfView, model);
    }
}

//public class HelloController extends SimpleController {
//    public HelloController() {
//        setRequiredParams(new String[]{"name"});
//        setViewName("/WEB-INF/view/hello.jsp");
//    }
//
//    @Override
//    public void control(Map<String, String> params, Map<String, Object> model) {
//        model.put("message", "Hello " + params.get("name"));
//    }
//}

//@Controller
//public class HelloController {
//    @RequestMapping("/hello")
//    public String hello(@RequestParam("name") String name, ModelMap map) {
//        map.put("message", "Hello " + name);
//
//        return "/WEB-INF/view/hello.jsp";
//    }
//}

//@Component("/hello")
//public class HelloController implements SimpleController {
//    @ViewName("/WEB-INF/view/hello.jsp")
//    @RequireParams({"name"})
//    @Override
//    public void control(Map<String, String> params, Map<String, Object> model) {
//        model.put("message", "Hello " + params.get("name"));
//    }
//}