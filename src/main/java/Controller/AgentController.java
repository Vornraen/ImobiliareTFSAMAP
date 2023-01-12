package Controller;

import Model.Agent;
import Model.Appointment;
import Model.Property;
import Model.User;
import Repository.UnitOfWork;
import View.IView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.microsoft.sqlserver.jdbc.StringUtils.isNumeric;
import static java.lang.Integer.parseInt;

public class AgentController implements IController {
    private final UnitOfWork unitOfWork;
    private final IView agentView;
    private final Agent agent;

    public AgentController(UnitOfWork unitOfWork, IView agentView, Agent agent) {
        this.unitOfWork = unitOfWork;
        this.agentView = agentView;
        this.agent = agent;
    }

    public void handleUserInput() throws SQLException {
        while(true)
        {agentView.displayMenu();
            String input = agentView.fetchData();
            switch(input.trim()) {
                case "1":
                    handleGetAgentProperties();
                    break;
                case "2":
                    handleAddProperty();
                    break;
                case "3":
                    handleSeeAppointments();
                    break;
                default:
                    agentView.displayData("Invalid Command");
            }
        }
    }

    public void handleGetAgentProperties() throws SQLException {
        List<Property> properties = unitOfWork.getPropertyRepository().selectWithAgentId(agent.getId());
        for (Property p : properties) {
            if(!p.isSold())
            agentView.displayData(p.toString());
        }
        agentView.displayData("Please select a property or press a letter to go back:");
        String selected = agentView.fetchData();
        if(isNumeric(selected)){
        Property selectedProperty;
        for (Property p : properties) {
            if (parseInt(selected) == (p.getId())) {
                selectedProperty = p;
                agentView.displayData("Selected property: " + selectedProperty);

                agentView.displayData("1.Set as sold\n2.Edit\n");
                String input = agentView.fetchData();
                switch (input.trim()) {
                    case "1":
                        handleSoldProperty(selectedProperty);
                        break;
                    case "2":
                        handleEditProperty(selectedProperty);
                        //Agent agent = unitOfWork.getAgentRepository().select(selectedProperty.getAgentId());
                        //System.out.println(agent.toString());
                        break;
                    default:
                        agentView.displayData("Invalid Command");

                }
            }


            }

        }
        else{
            return;
        }
    }
    private void handleSeeAppointments() throws SQLException {
        List<Appointment> appointments = unitOfWork.getAppointmentRepository().select();
        for(Appointment a : appointments){
            if(a.getAgentId()==agent.getId() && !a.isDone()){
                Property property = unitOfWork.getPropertyRepository().select(a.getPropertyId());
                agentView.displayData(a.getDate() + property.toString() + a.isDone());
            }
        }
    }

    private void handleAddProperty() throws SQLException{
        agentView.displayData("Price: \n");
        int price = Integer.parseInt(agentView.fetchData());

        agentView.displayData("Number of rooms: \n");
        int roomNumber = Integer.parseInt(agentView.fetchData());

        agentView.displayData("Square meters:\n");
        int size = Integer.parseInt(agentView.fetchData());

        agentView.displayData("Neighbourhood:\n");
        String neighbourhood = agentView.fetchData();

        Property property = new Property(price,roomNumber,size,false,neighbourhood,agent.getId());
        unitOfWork.getPropertyRepository().insert(property);

    }


    private void handleSoldProperty(Property selectedProperty) throws SQLException {
        selectedProperty.setSold(true);
        unitOfWork.getPropertyRepository().update(selectedProperty);
        List<Appointment> appointments = unitOfWork.getAppointmentRepository().select();
        for(Appointment a : appointments){
            if(a.getPropertyId()==selectedProperty.getId()){
                handleCancelAppointment(a.getId());
            }
        }

    }
   // handleEditProperty
   private void handleCancelAppointment(int appointmentId) throws SQLException {
        Appointment appointment=unitOfWork.getAppointmentRepository().select(appointmentId);
        appointment.setDone(true);
        unitOfWork.getAppointmentRepository().update(appointment);
   }

   private void handleEditProperty(Property selectedProperty) throws SQLException {
       agentView.displayData("Please give the new data:");
       agentView.displayData("Old Price " + selectedProperty.getPrice() + " New Price: \n");
       int price = Integer.parseInt(agentView.fetchData());
       selectedProperty.setPrice(price);

       agentView.displayData("Old Number Of Rooms " + selectedProperty.getRoomNumber() + " New Number of rooms: \n");
       int roomNumber = Integer.parseInt(agentView.fetchData());
       selectedProperty.setRoomNumber(roomNumber);

       agentView.displayData("Old Square Meters " + selectedProperty.getSize() + " New Square meters:\n");
       int size = Integer.parseInt(agentView.fetchData());
       selectedProperty.setSize(size);

       agentView.displayData("Old Neighbourhood " + selectedProperty.getNeighbourhood() + " New Neighbourhood:\n");
       String neighbourhood = agentView.fetchData();
       selectedProperty.setNeighbourhood(neighbourhood);

       unitOfWork.getPropertyRepository().update(selectedProperty);
   }
}
