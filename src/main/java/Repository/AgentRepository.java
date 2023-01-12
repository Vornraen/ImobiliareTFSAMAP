package Repository;

import Model.Agent;
import Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Database.DatabaseConnection.getConnection;

public class AgentRepository implements IAgentRepository{

    private Connection transConnection;

    // Define SQL sentences
    private static final String SQL_SELECT = "SELECT ID, Name, Email, Password,PhoneNumber FROM [Agent]";
    private static final String SQL_SELECT_ONE = "SELECT id, name, email, password, phonenumber FROM [Agent] WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO [Agent] (name, email, password,phonenumber) VALUES (?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE [Agent] SET name=?,email=?,password=?,phonenumber=? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM [Agent] WHERE id = ?";

    public AgentRepository(Connection conn){
        this.transConnection = conn;
    }

    public AgentRepository(){
        super();
    }

    @Override
    public int insert(Agent agent) throws SQLException {
        Connection conn = null;
        PreparedStatement pStatement = null;
        int result = 0;
        try {
            conn = this.transConnection !=null?this.transConnection:getConnection();
            pStatement = conn.prepareStatement(SQL_INSERT);
            pStatement.setString(1,agent.getName());
            pStatement.setString(2,agent.getEmail());
            pStatement.setString(3,agent.getPassword());
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
    public int update(Agent agent) throws SQLException {
        Connection conn = null;
        PreparedStatement pStatement = null;
        int result = 0;
        try {
            conn = this.transConnection !=null?this.transConnection:getConnection();
            pStatement = conn.prepareStatement(SQL_UPDATE);
            pStatement.setString(1,agent.getName());
            pStatement.setString(2,agent.getEmail());
            pStatement.setString(3,agent.getPassword());
            pStatement.setInt(4,agent.getId());
            pStatement.setString(5,agent.getPhoneNumber());
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
    public int delete(int idAgent) throws SQLException {
        Connection conn = null;
        PreparedStatement pStatement = null;
        int result = 0;
        try {
            conn = this.transConnection !=null?this.transConnection:getConnection();
            pStatement = conn.prepareStatement(SQL_DELETE);
            pStatement.setInt(1,idAgent);
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
    public Agent select(int idAgent) throws SQLException {
        Connection conn = null;
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        Agent agent = null;
        try {
            conn = this.transConnection !=null?this.transConnection:getConnection();
            pStatement = conn.prepareStatement(SQL_SELECT_ONE);
            pStatement.setInt(1,idAgent);
            rs = pStatement.executeQuery();
            while (rs.next()){
                agent = new Agent(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getString("PhoneNumber")
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
        return agent;
    }

    @Override
    public List<Agent> select() throws SQLException {
        Connection conn = null;
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        List<Agent> agents = new ArrayList<>();

        try {
            conn = this.transConnection;
            pStatement = conn.prepareStatement(SQL_SELECT);
            rs = pStatement.executeQuery();
            while (rs.next()){
                agents.add(new Agent(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getString("PhoneNumber")
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
        return agents;
    }
}
