package springbook.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.sqlservice.SqlService;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDaoJdbc implements UserDao {
    @Autowired
    private SqlService sqlService;

    private RowMapper<User> userMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setLevel(Level.valueOf(rs.getInt("level")));
            user.setLogin(rs.getInt("login"));
            user.setRecommend(rs.getInt("recommend"));
            user.setEmail(rs.getString("email"));

            return user;
        }
    };

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) {
        this.jdbcTemplate.update(sqlService.getSql("userAdd")
                                 , user.getId()
                                 , user.getName()
                                 , user.getPassword()
                                 , user.getLevel().intValue()
                                 , user.getLogin()
                                 , user.getRecommend()
                                 , user.getEmail());
    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject(sqlService.getSql("userGet")
                , new Object[]{id}
                , this.userMapper);
    }

    public List<User> getAll() {
        return jdbcTemplate.query(sqlService.getSql("userGetAll")
                , this.userMapper);
    }

    public void deleteAll() {
        this.jdbcTemplate.update(sqlService.getSql("userDeleteAll"));
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject(sqlService.getSql("userGetCount"), Integer.class);
    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update(sqlService.getSql("userUpdate")
                                 , user.getName()
                                 , user.getPassword()
                                 , user.getLevel().intValue()
                                 , user.getLogin()
                                 , user.getRecommend()
                                 , user.getEmail()
                                 , user.getId());
    }
}