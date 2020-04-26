package context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import springbook.user.sqlservice.OxmSqlService;
import springbook.user.sqlservice.SqlMapConfig;
import springbook.user.sqlservice.SqlService;
import springbook.user.sqlservice.updatable.EmbeddedDbSqlRegistry;
import springbook.user.sqlservice.updatable.UpdatableSqlRegistry;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

@Configuration
public class SqlServiceContext {
    @Autowired
    SqlMapConfig sqlMapConfig;

    @Bean
    public SqlService sqlService() {
        OxmSqlService sqlService = new OxmSqlService();

        sqlService.setUnmarshaller(unmarshaller());
        sqlService.setSqlRegistry(sqlRegistry());
        sqlService.setSqlmap(sqlMapConfig.getSqlMapResource());

        return sqlService;
    }

    @Bean
    public Unmarshaller unmarshaller() {
        Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
        unmarshaller.setContextPath("springbook.user.sqlservice.jaxb");

        return unmarshaller;
    }

    @Bean
    public UpdatableSqlRegistry sqlRegistry() {
        EmbeddedDbSqlRegistry sqlRegistry = new EmbeddedDbSqlRegistry();
        sqlRegistry.setDataSource(embeddedDatabase());

        return sqlRegistry;
    }

    @Bean
    public DataSource embeddedDatabase() {
        return new EmbeddedDatabaseBuilder()
                .setName("embeddedDatabase")
                .setType(HSQL)
                .addScript("classpath:springbook/user/sqlservice/updatable/sqlRegistrySchema.sql")
                .build();
    }
}