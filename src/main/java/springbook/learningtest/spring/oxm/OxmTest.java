package springbook.learningtest.spring.oxm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Unmarshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.sqlservice.jaxb.SqlType;
import springbook.user.sqlservice.jaxb.Sqlmap;

import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/OxmTest-context.xml")
public class OxmTest {
    @Autowired
    Unmarshaller unmarshaller;

    @Test
    public void unmarshallSqlMap() throws JAXBException, IOException {
        Source xmlSource = new StreamSource(getClass().getResourceAsStream("sqlmap.xml"));
        Sqlmap sqlmap = (Sqlmap)unmarshaller.unmarshal(xmlSource);

        List<SqlType> sqlList = sqlmap.getSql();

        assertEquals(3, sqlList.size());
        assertEquals("add", sqlList.get(0).getKey());
        assertEquals("insert", sqlList.get(0).getValue());
        assertEquals("get", sqlList.get(1).getKey());
        assertEquals("select", sqlList.get(1).getValue());
        assertEquals("delete", sqlList.get(2).getKey());
        assertEquals("delete", sqlList.get(2).getValue());
    }
}