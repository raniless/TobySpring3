package springbook.user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.Level;
import springbook.user.domain.User;
import springbook.user.sqlservice.SqlService;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UserDaoJdbc implements UserDao {
    private SqlService sqlService;

    public void setSqlService(SqlService sqlService) {
        this.sqlService = sqlService;
    }

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

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) {
//        this.jdbcTemplate.update("insert into users(id, name, password, level, login, recommend, email) values (?, ?, ?, ?, ?, ?, ?)"
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
//        return this.jdbcTemplate.queryForObject("select * from users where id = ?"
        return this.jdbcTemplate.queryForObject(sqlService.getSql("userGet")
                , new Object[]{id}
                , this.userMapper);
    }

    public List<User> getAll() {
//        return jdbcTemplate.query("select * from users order by id"
        return jdbcTemplate.query(sqlService.getSql("userGetAll")
                , this.userMapper);
    }

    public void deleteAll() {
//        this.jdbcTemplate.update("delete from users");
        this.jdbcTemplate.update(sqlService.getSql("userDeleteAll"));
    }

    public int getCount() {
//        return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
        return this.jdbcTemplate.queryForObject(sqlService.getSql("userGetCount"), Integer.class);
    }

    @Override
    public void update(User user) {
//        this.jdbcTemplate.update("update users set name = ?, password = ?, level = ?, login = ?, recommend = ?, email = ? where id = ? "
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