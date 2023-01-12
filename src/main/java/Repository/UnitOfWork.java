package Repository;

import java.sql.Connection;

public class UnitOfWork {

    private Connection connection;
    private AgentRepository agentRepository;
    private AppointmentRepository appointmentRepository;
    private PropertyRepository propertyRepository;
    private UserRepository userRepository;

    public AgentRepository getAgentRepository() {
        if(agentRepository==null){
            agentRepository=new AgentRepository(connection);
        }
        return agentRepository;
    }

    public AppointmentRepository getAppointmentRepository() {
        if(appointmentRepository==null){
            appointmentRepository=new AppointmentRepository(connection);
        }
        return appointmentRepository;
    }

    public PropertyRepository getPropertyRepository() {
        if(propertyRepository==null){
            propertyRepository=new PropertyRepository(connection);
        }
        return propertyRepository;
    }

    public UnitOfWork(Connection connection) {
        this.connection = connection;
    }

    public UserRepository getUserRepository() {
        if(userRepository==null){
            userRepository=new UserRepository(connection);
        }
        return userRepository;
    }
}
