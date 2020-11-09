package springbook.learningtest.spring.ioc.property;

import org.springframework.beans.factory.annotation.Value;

public class HelloProperty {
    private String name;
    private String os;
    private String userName;

    @Value("Everyone")
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Value("#{systemProperties['os.name']}")
    public void setOs(String os) {
        this.os = os;
    }

    public String getOs() {
        return os;
    }

    @Value("${db.username}")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}