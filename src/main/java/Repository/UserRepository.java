package Repository;

import Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Database.DatabaseConnection.getConnection;

public class UserRepository implements IUserRepository{
    private Connection transConnection;

    // Define SQL sentences
    private static final String SQL_SELECT = "SELECT ID, Name, Email, Password FROM [User]";
    private static final String SQL_SELECT_ONE = "SELECT id, name, email, password FROM [User] WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO [User] (name, email, password) VALUES (?,?,?)";
    private static final String SQL_UPDATE = "UPDATE [User] SET name=?,email=?,password=? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM [User] WHERE id = ?";

    public UserRepository(Connection conn){
        this.transConnection = conn;
    }

    public UserRepository(){
        super();
    }

    @Override
    public int insert(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement pStatement = null;
        int result = 0;
        try {
            conn = this.transConnection !=null?this.transConnection:getConnection();
            pStatement = conn.prepareStatement(SQL_INSERT);
            pStatement.setString(1,user.getName());
            pStatement.setString(2,user.getEmail());
            pStatement.setString(3,user.getPassword());
            result = pStatement.executeUpdate();

        }catch (SQLSyntaxErrorException ex){
            System.err.println("Error: "+ex.getMessage());
        }
        finally {
            try {
                if(pStatement != null)pStatement.close();
                if(conn != null) {
                    if(this.transConnection == null) conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return result;
    }

    @Override
    public int update(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement pStatement = null;
        int result = 0;
        try {
            conn = this.transConnection !=null?this.transConnection:getConnection();
            pStatement = conn.prepareStatement(SQL_UPDATE);
            pStatement.setString(1,user.getName());
            pStatement.setString(2,user.getEmail());
            pStatement.setString(3,user.getPassword());
            pStatement.setInt(4,user.getId());
            result = pStatement.executeUpdate();
        }catch (SQLSyntaxErrorException ex){
            System.err.println("Error: "+ex.getMessage());
        }
        finally {
            try {
                if(pStatement != null)pStatement.close();
                if(conn != null) {
                    if(this.transConnection == null) conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return result;
    }

    @Override
    public int delete(int idUser) throws SQLException {
        Connection conn = null;
        PreparedStatement pStatement = null;
        int result = 0;
        try {
            conn = this.transConnection !=null?this.transConnection:getConnection();
            pStatement = conn.prepareStatement(SQL_DELETE);
            pStatement.setInt(1,idUser);
            result = pStatement.executeUpdate();
        }catch (SQLSyntaxErrorException ex){
            System.err.println("Error: "+ex.getMessage());
        }
        finally {
            try {
                if(pStatement != null)pStatement.close();
                if(conn != null) {
                    if(this.transConnection == null) conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return result;
    }

    @Override
    public User select(int idUser) throws SQLException {
        Connection conn = null;
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = this.transConnection !=null?this.transConnection:getConnection();
            pStatement = conn.prepareStatement(SQL_SELECT_ONE);
            pStatement.setInt(1,idUser);
            rs = pStatement.executeQuery();
            while (rs.next()){
                user = new User(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Password")
                );
            }
        }catch (SQLSyntaxErrorException ex){
            System.err.println("Error: "+ex.getMessage());
        }finally {
            try {
                if(rs != null)rs.close();
                if(pStatement != null)pStatement.close();
                if(conn != null) {
                    if(this.transConnection == null )conn.close();
                }
            } catch (SQLException throwables) {
                //throwables.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public List<User> select() throws SQLException {
        Connection conn = null;
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            conn = this.transConnection;
            pStatement = conn.prepareStatement(SQL_SELECT);
            rs = pStatement.executeQuery();
            while (rs.next()){
                users.add(new User(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Password")
                ));
            }
        }catch (SQLSyntaxErrorException ex){
            System.err.println("Error: "+ex.getMessage());
        }
        finally {
            try {
                if(rs != null)rs.close();
                if(pStatement != null)pStatement.close();
                if(conn != null) {
                    if(this.transConnection == null )conn.close();
                }
            } catch (SQLException throwables) {
                //throwables.printStackTrace();
            }
        }
        return users;
    }
}
