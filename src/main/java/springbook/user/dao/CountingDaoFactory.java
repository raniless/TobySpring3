package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class CountingDaoFactory {
    @Bean
    public UserDao userDao(){
        UserDao userDao = new UserDao();
        userDao.setDataSource(dataSource());
        return userDao;
    }

    @Bean
    public DataSource dataSource(){
        return new CountingConnectionMaker(realDataSource());
    }

    @Bean
    public DataSource realDataSource(){
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        try {
            dataSource.setDriver(com.mysql.cj.jdbc.Driver.class.newInstance());
            dataSource.setUrl("jdbc:mysql://localhost/tobydb?serverTimezone=UTC");
            dataSource.setUsername("root");
            dataSource.setPassword("root");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return dataSource;
    }
}
