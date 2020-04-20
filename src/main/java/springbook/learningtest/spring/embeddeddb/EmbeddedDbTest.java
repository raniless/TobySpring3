package springbook.learningtest.spring.embeddeddb;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

public class EmbeddedDbTest {
    EmbeddedDatabase db;
    JdbcTemplate template;

    @Before
    public void setUp() {
        System.out.println(new File("").getAbsolutePath());
        db = new EmbeddedDatabaseBuilder()
                .setType(HSQL)
                .addScript("classpath:/springbook/learningtest/spring/embeddeddb/schema.sql")
                .addScript("classpath:/springbook/learningtest/spring/embeddeddb/data.sql")
                .build();

        template = new JdbcTemplate(db);
    }

    @After
    public void tearDown() {
        db.shutdown();
    }

    @Test
    public void initData() {
        assertEquals(Integer.valueOf(2), template.queryForObject("select count(*) from sqlmap", Integer.class));

        List<Map<String, Object>> list = template.queryForList("select * from sqlmap order by key_");
        assertEquals("KEY1", (String)list.get(0).get("key_"));
        assertEquals("SQL1", (String)list.get(0).get("sql_"));
        assertEquals("KEY2", (String)list.get(1).get("key_"));
        assertEquals("SQL2", (String)list.get(1).get("sql_"));
    }

    @Test
    public void insert() {
        template.update("insert into sqlmap(key_, sql_) values(?, ?)", "KEY3", "SQL3");

        assertEquals(Integer.valueOf(3), template.queryForObject("select count(*) from sqlmap", Integer.class));
    }
}