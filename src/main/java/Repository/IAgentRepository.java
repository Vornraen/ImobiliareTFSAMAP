package Repository;

import Model.Agent;

import java.sql.SQLException;
import java.util.List;

public interface IAgentRepository {
    public int insert(Agent agent) throws SQLException;
    public int update(Agent agent) throws SQLException;
    public int delete(int idAgent) throws SQLException;
    public Agent select(int idAgent) throws SQLException;
    public List<Agent> select() throws SQLException;
}
