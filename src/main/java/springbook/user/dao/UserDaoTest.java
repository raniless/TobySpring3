package springbook.user.dao;


import context.AppContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
//@ContextConfiguration(locations = "/test-applicationContext.xml")
@ContextConfiguration(classes = AppContext.class)
@Transactional
public class UserDaoTest {
    @Autowired
    private UserDao dao;
    @Autowired
    DataSource dataSource;

    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setUp(){
        this.user1 = new User("gyumee"  , "박성철", "springno1", Level.BASIC, 1, 0, "gyumee@email.com");
        this.user2 = new User("leegw700", "이길원", "springno2", Level.SILVER, 55, 10, "leegw700@email.com");
        this.user3 = new User("bumjin"  , "박범진", "springno3", Level.GOLD, 100, 40, "bumjin@email.com");
    }

    @Test
    public void addAndGet() {
        dao.deleteAll();
        assertEquals(0, dao.getCount());

        dao.add(user1);
        dao.add(user2);
        assertEquals(2, dao.getCount());

        User userGet1 = dao.get(user1.getId());
        checkSameUser(userGet1, user1);

        User userGet2 = dao.get(user2.getId());
        checkSameUser(userGet2, user2);
    }

    @Test
    public void count() {
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
    public void getUserFailure() {
        dao.deleteAll();
        assertEquals(0, dao.getCount());

        dao.get("unknown_id");
    }

    @Test
    public void getAll() {
        dao.deleteAll();

        dao.add(user1);
        List<User> users1 = dao.getAll();
        assertEquals(1, users1.size());
        checkSameUser(user1, users1.get(0));

        dao.add(user2);
        List<User> users2 = dao.getAll();
        assertEquals(2, users2.size());
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        dao.add(user3);
        List<User> users3 = dao.getAll();
        assertEquals(3, users3.size());
        checkSameUser(user3, users3.get(0));
        checkSameUser(user1, users3.get(1));
        checkSameUser(user2, users3.get(2));
    }

    @Test
    public void getAllNoneData() {
        dao.deleteAll();

        List<User> user0 = dao.getAll();
        assertEquals(0, user0.size());
    }

    @Test(expected = DataAccessException.class)
    public void duplicateKey(){
        dao.deleteAll();

        dao.add(user1);
        dao.add(user1);
    }

    @Test
    public void sqlExceptionTransfer(){
        dao.deleteAll();

        try {
            dao.add(user1);
            dao.add(user1);
        }
        catch(DuplicateKeyException ex){
            SQLException sqlEx = (SQLException)ex.getRootCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
            assertEquals(set.translate(null, null, sqlEx).getClass(), DuplicateKeyException.class);
        }
    }

    @Test
    public void update() {
        dao.deleteAll();

        dao.add(user1);
        dao.add(user2);

        user1.setName("오민규");
        user1.setPassword("springno6");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);
        user1.setEmail("mk@email.com");

        dao.update(user1);

        User user1Update = dao.get(user1.getId());
        checkSameUser(user1, user1Update);
        User user2Update = dao.get(user2.getId());
        checkSameUser(user2, user2Update);
    }

    private void checkSameUser(User user1, User user2){
        assertEquals(user2.getId()       , user1.getId());
        assertEquals(user2.getName()     , user1.getName());
        assertEquals(user2.getPassword() , user1.getPassword());
        assertEquals(user2.getLevel()    , user1.getLevel());
        assertEquals(user2.getLogin()    , user1.getLogin());
        assertEquals(user2.getRecommend(), user1.getRecommend());
    }
}