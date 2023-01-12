package Repository;

import Model.Property;

import java.sql.SQLException;
import java.util.List;

public interface IPropertyRepository {
    public int insert(Property property) throws SQLException;
    public int update(Property property) throws SQLException;
    public int delete(int idProperty) throws SQLException;
    public Property select(int idProperty) throws SQLException;
    public List<Property> select() throws SQLException;
}
