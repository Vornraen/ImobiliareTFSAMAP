package Repository;

import Model.Appointment;

import java.sql.SQLException;
import java.util.List;

public interface IAppointmentRepository {
    public int insert(Appointment appointment) throws SQLException;
    public int update(Appointment appointment) throws SQLException;
    public int delete(int idAppointment) throws SQLException;
    public Appointment select(int idAppointment) throws SQLException;
    public List<Appointment> select() throws SQLException;
}
