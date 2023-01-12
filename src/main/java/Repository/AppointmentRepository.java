package Repository;

import Model.Appointment;
import Model.Property;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Database.DatabaseConnection.getConnection;

public class AppointmentRepository implements IAppointmentRepository{

    private Connection transConnection;

    private static final String SQL_SELECT = "SELECT id, propertyId, userId, agentid, date,done FROM [Appointment]";
    private static final String SQL_SELECT_ONE = "SELECT id, propertyId, userId, agentid, date,done FROM [Appointment] WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO [Appointment](propertyId, userId, agentid, date,done) VALUES (?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE [Appointment] SET done=? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM [Appointment] WHERE id = ?";

    public AppointmentRepository(Connection conn){
        this.transConnection = conn;
    }

    public AppointmentRepository(){
        super();
    }

    @Override
    public int insert(Appointment appointment) throws SQLException {
        Connection conn = null;
        PreparedStatement pStatement = null;
        int result = 0;
        try {
            conn = this.transConnection != null ? this.transConnection : getConnection();
            pStatement = conn.prepareStatement(SQL_INSERT);
            pStatement.setInt(1, appointment.getPropertyId());
            pStatement.setInt(2, appointment.getUserId());
            pStatement.setInt(3, appointment.getAgentId());
            pStatement.setString(4, appointment.getDate());
            pStatement.setBoolean(5,appointment.isDone());
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
    public int update(Appointment appointment) throws SQLException {
        Connection conn = null;
        PreparedStatement pStatement = null;
        int result = 0;
        try {
            conn = this.transConnection !=null?this.transConnection:getConnection();
            pStatement = conn.prepareStatement(SQL_UPDATE);
            pStatement.setBoolean(1,appointment.isDone());
            pStatement.setInt(2, appointment.getId());
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
    public int delete(int idAppointment) throws SQLException {
        return 0;
    }

    @Override
    public Appointment select(int idAppointment) throws SQLException {
        Connection conn = null;
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        Appointment appointment = null;
        try {
            conn = this.transConnection !=null?this.transConnection:getConnection();
            pStatement = conn.prepareStatement(SQL_SELECT_ONE);
            pStatement.setInt(1,idAppointment);
            rs = pStatement.executeQuery();
            while (rs.next()){
                appointment = new Appointment(
                        rs.getInt("ID"),
                        rs.getInt("PropertyId"),
                        rs.getInt("UserId"),
                        rs.getInt("AgentId"),
                        rs.getString("Date"),
                        rs.getBoolean("Done")
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
        return appointment;
    }

    @Override
    public List<Appointment> select() throws SQLException {
        Connection conn = null;
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        List<Appointment> appointments = new ArrayList<>();

        try {
            conn = this.transConnection;
            pStatement = conn.prepareStatement(SQL_SELECT); //propertyId, userId, agentid, date
            rs = pStatement.executeQuery();
            while (rs.next()){
                appointments.add(new Appointment(
                        rs.getInt("ID"),
                        rs.getInt("PropertyId"),
                        rs.getInt("UserId"),
                        rs.getInt("AgentId"),
                        rs.getString("Date"),
                        rs.getBoolean("Done")
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
        return appointments;
    }
}
