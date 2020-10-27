package springbook.learningtest.spring.web.adapters;

import com.sun.org.apache.xpath.internal.objects.XObject;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import springbook.learningtest.spring.web.controllers.RequireParams;
import springbook.learningtest.spring.web.controllers.SimpleController;
import springbook.learningtest.spring.web.controllers.ViewName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SimpleHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof SimpleController);
    }

    @Override
    public ModelAndView handle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        Method m = ReflectionUtils.findMethod(handler.getClass(), "control", Map.class, Map.class);
        ViewName viewName = AnnotationUtils.getAnnotation(m, ViewName.class);
        RequireParams requireParams = AnnotationUtils.getAnnotation(m, RequireParams.class);

        Map<String, String> params = new HashMap<>();
        for(String param : requireParams.value()) {
            String value = req.getParameter(param);
            if(value == null) {
                throw new IllegalStateException();
            }
            params.put(param, value);
        }

        Map<String, Object> model = new HashMap<>();
        ((SimpleController)handler).control(params, model);

        return new ModelAndView(viewName.value(), model);
    }

    @Override
    public long getLastModified(HttpServletRequest httpServletRequest, Object o) {
        return -1;
    }
}
