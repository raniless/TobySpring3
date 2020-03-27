package springbook.user.dao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        try(Connection c = dataSource.getConnection();
            PreparedStatement ps = stmt.makePreparedStatement(c))
        {
            ps.executeUpdate();
        }catch(SQLException e){
            throw e;
        }
    }

    public void executeSql(final String query) throws SQLException {
        workWithStatementStrategy(
                new StatementStrategy(){
                    @Override
                    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                        return c.prepareStatement(query);
                    }
                }
        );
    }

    public void executeSql(final String query, final Object... objects) throws SQLException {
        workWithStatementStrategy(
                new StatementStrategy(){
                    @Override
                    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                        PreparedStatement ps = c.prepareStatement(query);

                        int idx = 1;
                        for(Object object : objects){
                            ps.setObject(idx++, object);
                        }

                        return ps;
                    }
                }
        );
    }
}