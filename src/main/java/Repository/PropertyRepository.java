package Repository;

import Model.Property;
import Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Database.DatabaseConnection.getConnection;

public class PropertyRepository implements IPropertyRepository {

    private Connection transConnection;

    // Define SQL sentences
    private static final String SQL_SELECT = "SELECT id, price, roomnumber, size, sold, neighbourhood,agentId FROM [Property]";
    private static final String SQL_SELECT_AGENT = "SELECT id, price,roomnumber, size, sold, neighbourhood,agentId FROM [Property] WHERE agentId=?";
    private static final String SQL_SELECT_ONE = "SELECT [ID]\n" +
            "      ,[Price]\n" +
            "      ,[RoomNumber]\n" +
            "      ,[Size]\n" +
            "      ,[Sold]\n" +
            "      ,[Neighbourhood]\n" +
            "      ,[AgentID]\n" +
            "  FROM [Imobiliare].[dbo].[Property]" +
            "Where ID=?";
    private static final String SQL_INSERT = "INSERT INTO [Property](price,size, sold, neighbourhood,agentId,roomnumber) VALUES (?,?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE [Property] SET price=?, size=?, sold=?, neighbourhood=? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM [Property] WHERE id = ?";

    public PropertyRepository(Connection conn) {
        this.transConnection = conn;
    }

    public PropertyRepository() {
        super();
    }

    @Override
    public int insert(Property property) throws SQLException {
        Connection conn = null;
        PreparedStatement pStatement = null;
        int result = 0;
        try {
            conn = this.transConnection != null ? this.transConnection : getConnection();
            pStatement = conn.prepareStatement(SQL_INSERT);
            pStatement.setInt(1, property.getPrice());
            pStatement.setInt(2, property.getSize());
            pStatement.setBoolean(3, property.isSold());
            pStatement.setString(4, property.getNeighbourhood());
            pStatement.setInt(5,property.getAgentId());
            pStatement.setInt(6,property.getRoomNumber());
            result = pStatement.executeUpdate();

        } catch (SQLSyntaxErrorException ex) {
            System.err.println("Error: " + ex.getMessage());
        } finally {
            try {
                if (pStatement != null) pStatement.close();
                if (conn != null) {
                    if (this.transConnection == null) conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return result;
    }



    @Override
    public int update(Property property) throws SQLException {
        Connection conn = null;
        PreparedStatement pStatement = null;
        int result = 0;
        try {
            conn = this.transConnection !=null?this.transConnection:getConnection();
            pStatement = conn.prepareStatement(SQL_UPDATE);
            pStatement.setInt(1,property.getPrice());
            pStatement.setInt(2,property.getSize());
            pStatement.setBoolean(3,property.isSold());
            pStatement.setString(4,property.getNeighbourhood());
            pStatement.setInt(5,property.getId());
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
    public int delete(int idProperty) throws SQLException {
        Connection conn = null;
        PreparedStatement pStatement = null;
        int result = 0;
        try {
            conn = this.transConnection !=null?this.transConnection:getConnection();
            pStatement = conn.prepareStatement(SQL_DELETE);
            pStatement.setInt(1,idProperty);
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
    public Property select(int idProperty) throws SQLException {
        Connection conn = null;
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        Property property = null;
        try {
            conn = this.transConnection !=null?this.transConnection:getConnection();
            pStatement = conn.prepareStatement(SQL_SELECT_ONE);
            pStatement.setInt(1,idProperty);
            rs = pStatement.executeQuery();
            while (rs.next()){
                property = new Property(
                        rs.getInt("ID"),
                        rs.getInt("Price"),
                        rs.getInt("RoomNumber"),
                        rs.getInt("Size"),
                        rs.getBoolean("Sold"),
                        rs.getString("Neighbourhood"),
                        rs.getInt("AgentId")
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
        return property;
    }

    @Override
    public List<Property> select() throws SQLException {
        Connection conn = null;
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        List<Property> properties = new ArrayList<>();

        try {
            conn = this.transConnection;
            pStatement = conn.prepareStatement(SQL_SELECT);
            rs = pStatement.executeQuery();
            while (rs.next()){
                properties.add(new Property(
                        rs.getInt("ID"),
                        rs.getInt("Price"),
                        rs.getInt("RoomNumber"),
                        rs.getInt("Size"),
                        rs.getBoolean("Sold"),
                        rs.getString("Neighbourhood"),
                        rs.getInt("AgentId")
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
        return properties;
    }

    public List<Property> selectWithAgentId(int agentId) throws SQLException {
        Connection conn = null;
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        List<Property> properties = new ArrayList<>();

        try {
            conn = this.transConnection;
            pStatement = conn.prepareStatement(SQL_SELECT_AGENT);
            pStatement.setInt(1,agentId);
            rs = pStatement.executeQuery();
            while (rs.next()){
                properties.add(new Property(
                        rs.getInt("ID"),
                        rs.getInt("Price"),
                        rs.getInt("RoomNumber"),
                        rs.getInt("Size"),
                        rs.getBoolean("Sold"),
                        rs.getString("Neighbourhood"),
                        rs.getInt("AgentId")
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
        return properties;
    }


    }

