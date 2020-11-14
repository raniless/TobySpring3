package springbook.learningtest.spring.ioc.property;

import com.sun.javafx.runtime.SystemProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Properties;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "helloContext.xml")
public class SystemPropertiesTest {
    @Resource
    private Properties systemProperties;
    @Resource
    private Map<String, String> systemEnvironment;

    @Test
    public void systemPropertiesTest() {
        System.out.println(systemProperties.getProperty("os.name"));
        System.out.println(systemEnvironment.get("Path"));
    }
}
