package springbook.user.sqlservice.updatable;

import springbook.user.exception.SqlUpdateFailureException;
import springbook.user.sqlservice.SqlRegistry;

import java.util.Map;

public interface UpdatableSqlRegistry extends SqlRegistry {
    void updateSql(String key, String sql) throws SqlUpdateFailureException;

    void updateSql(Map<String, String> sqlmap) throws SqlUpdateFailureException;
}
