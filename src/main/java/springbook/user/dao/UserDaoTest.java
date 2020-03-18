package springbook.user.dao;



import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.domain.User;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserDaoTest {
//    @Test
    public void addAndGet() throws SQLException {
        //ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

        UserDao dao = context.getBean("userDao", UserDao.class);

        User user1 = new User("gyumee"  , "박성철", "springno1");
        User user2 = new User("leegw700", "이길원", "springno2");

        dao.deleteAll();
        assertEquals(0, dao.getCount());

        dao.add(user1);
        dao.add(user2);
        assertEquals(2, dao.getCount());

        User userGet1 = dao.get(user1.getId());
        assertEquals(user1.getName(), userGet1.getName());
        assertEquals(user1.getPassword(), userGet1.getPassword());

        User userGet2 = dao.get(user2.getId());
        assertEquals(userGet2.getName(), user2.getName());
        assertEquals(userGet2.getPassword(), user2.getPassword());
    }

//    @Test
    public void count() throws SQLException {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        UserDao dao = context.getBean("userDao", UserDao.class);

        User user1 = new User("gyumee"  , "박성철", "springno1");
        User user2 = new User("leegw700", "이길원", "springno2");
        User user3 = new User("bumjin"  , "박범진", "springno3");

        dao.deleteAll();
        assertEquals(0, dao.getCount());

        dao.add(user1);
        assertEquals(1, dao.getCount());

        dao.add(user2);
        assertEquals(2, dao.getCount());

        dao.add(user3);
        assertEquals(3, dao.getCount());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure() throws SQLException {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        UserDao dao = context.getBean("userDao", UserDao.class);

        dao.deleteAll();
        assertEquals(0, dao.getCount());

        dao.get("unknown_id");
    }
}