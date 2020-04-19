package springbook.user.sqlservice;

import org.junit.Before;
import org.junit.Test;
import springbook.issuetracker.exception.SqlUpdateFailureException;
import springbook.issuetracker.sqlservice.UpdatableSqlRegistry;
import springbook.user.exception.SqlNotFoundException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ConcurrentHashMapSqlRegistryTest {
    UpdatableSqlRegistry sqlRegistry;

    @Before
    public void setUp() {
        sqlRegistry = new ConcurrentHashMapSqlRegistry();
        sqlRegistry.registerSql("KEY1", "SQL1");
        sqlRegistry.registerSql("KEY2", "SQL2");
        sqlRegistry.registerSql("KEY3", "SQL3");
    }

    @Test
    public void find() {
        checkFindResult("SQL1", "SQL2", "SQL3");
    }

    private void checkFindResult(String expected1, String expected2, String expected3) {
        assertEquals(expected1, sqlRegistry.findSql("KEY1"));
        assertEquals(expected2, sqlRegistry.findSql("KEY2"));
        assertEquals(expected3, sqlRegistry.findSql("KEY3"));
    }

    @Test(expected = SqlNotFoundException.class)
    public void unknownKey(){
        sqlRegistry.findSql("SQL9999!@#$");
    }

    @Test
    public void updateSingle() {
        sqlRegistry.updateSql("KEY2", "Modified2");
        checkFindResult("SQL1", "Modified2", "SQL3");
    }

    @Test
    public void updateMulti() {
        Map<String, String> sqlmap = new HashMap<>();
        sqlmap.put("KEY1", "Modified1");
        sqlmap.put("KEY3", "Modified3");

        sqlRegistry.updateSql(sqlmap);
        checkFindResult("Modified1", "SQL2", "Modified3");
    }

    @Test(expected = SqlUpdateFailureException.class)
    public void updateWithNoExistingKey() {
        sqlRegistry.updateSql("SQL9999!@#$", "Modified2");
    }
}
