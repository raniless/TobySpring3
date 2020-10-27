package springbook.learningtest.spring.web;

import org.junit.After;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public abstract class AbstractDispatcherServletTest implements AfterRunService {
    protected MockHttpServletRequest request;
    protected MockHttpServletResponse response;
    protected MockServletConfig config = new MockServletConfig("Spring");
    protected MockHttpSession session;

    private ConfigurableDispatcherServlet dispatcherServlet;
    private Class<?>[] classes;
    private String[] locations;
    private String[] relativeLocations;
    private String servletPath;

    public AbstractDispatcherServletTest setLocations(String ... locations) {
        this.locations = locations;
        return this;
    }

    public AbstractDispatcherServletTest setRelativeLocations(String ... relativeLocations) {
        this.relativeLocations = relativeLocations;
        return this;
    }

    public AbstractDispatcherServletTest setClasses(Class<?> ... classes) {
        this.classes = classes;
        return this;
    }

    public AbstractDispatcherServletTest setServletPath(String servletPath) {
        if(this.request == null) {
            this.servletPath = servletPath;
        }
        else {
            this.request.setServletPath(servletPath);
        }

        return this;
    }

    public AbstractDispatcherServletTest initRequest(String method, String requestURI) {
        request = new MockHttpServletRequest(method, requestURI);
        response = new MockHttpServletResponse();

        if(servletPath != null) {
           setServletPath(servletPath);
        }

        return this;
    }

    public AbstractDispatcherServletTest initRequest(RequestMethod method, String requestURI) {
        return initRequest(method.toString(), requestURI);
    }

    public AbstractDispatcherServletTest initRequest(String requestURI) {
        return initRequest(RequestMethod.GET, requestURI);
    }

    public AbstractDispatcherServletTest addParameter(String name, String value) {
        if(request == null) {
          throw new IllegalArgumentException("request가 초기화되지 않았습니다.");
        }

        request.addParameter(name, value);
        return this;
    }

    public AbstractDispatcherServletTest buildDispatcherServlet() throws ServletException {
        if (classes == null && locations == null && relativeLocations == null) {
            throw new IllegalArgumentException("classes와 locations 중 하나는 설정해야 합니다.");
        }

        dispatcherServlet = new ConfigurableDispatcherServlet();
        dispatcherServlet.setClasses(classes);
        dispatcherServlet.setLocations(locations);
        if(relativeLocations != null) {
            dispatcherServlet.setRelativeLocations(getClass(), relativeLocations);
        }
        dispatcherServlet.init(config);

        return this;
    }

    public AfterRunService runService() throws ServletException, IOException {
        if(dispatcherServlet == null) {
            buildDispatcherServlet();
        }

        if(request == null) {
            throw new IllegalStateException("request가 준비되지 않았습니다.");
        }

        dispatcherServlet.service(request, response);

        return this;
    }

    @Override
    public String getContentAsString() throws UnsupportedEncodingException {
        return response.getContentAsString();
    }

    @Override
    public WebApplicationContext getContext() {
        if(dispatcherServlet == null) {
            throw new IllegalStateException("DispatcherServlet이 준비되지 않았습니다.");
        }

        return dispatcherServlet.getWebApplicationContext();
    }

    @Override
    public <T> T getBean(Class<T> beanType) {
        if(dispatcherServlet == null) {
            throw new IllegalStateException("DispatcherServlet이 준비되지 않았습니다.");
        }

        return getContext().getBean(beanType);
    }

    @Override
    public ModelAndView getModelAndView() {
        return dispatcherServlet.getModelAndView();
    }

    @Override
    public AfterRunService assertViewName(String viewName) {
        assertEquals(viewName, getModelAndView().getViewName());
        return this;
    }

    @Override
    public AfterRunService assertModel(String name, Object value) {
        assertEquals(value, getModelAndView().getModel().get(name));
        return this;
    }

    @After
    public void closeServletContext() {
        if(dispatcherServlet != null) {
            ((ConfigurableApplicationContext)dispatcherServlet.getWebApplicationContext()).close();
        }
    }
}
