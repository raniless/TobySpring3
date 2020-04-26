package springbook.user.sqlservice;

import context.SqlServiceContext;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Import(SqlServiceContext.class)
public @interface EnableSqlService {
}
