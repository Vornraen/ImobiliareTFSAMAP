package Repository;

import Model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserRepository {
    public int insert(User user) throws SQLException;
    public int update(User user) throws SQLException;
    public int delete(int idUser) throws SQLException;
    public User select(int idUser) throws SQLException;
    public List<User> select() throws SQLException;
}
