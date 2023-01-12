package Controller;

import Model.Agent;
import Model.Appointment;
import Model.Property;
import Model.User;
import Repository.UnitOfWork;
import View.IView;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static com.microsoft.sqlserver.jdbc.StringUtils.isNumeric;
import static java.lang.Integer.parseInt;

public class UserController implements IController{

    private UnitOfWork unitOfWork;
    private IView userView;

    private User user;


    public UserController(UnitOfWork unitOfWork,IView userView,User user) {
        this.unitOfWork = unitOfWork;
        this.userView=userView;
        this.user=user;
    }

    public void handleUserInput() throws SQLException {
        while(true)
            {userView.displayMenu();
            String input = userView.fetchData();
            switch(input.trim()) {
                case "1":
                    handleGetUserProperties();
                    break;
                case "2":
                    handleSeeAppointments();
                    break;
                default:
                    userView.displayData("Invalid Command");
            }
            }
    }

    private void handleGetUserProperties() throws SQLException {
           List<Property> properties = unitOfWork.getPropertyRepository().select();
           for(Property p : properties){
               if(!p.isSold()) {
                   userView.displayData(p.toString());
               }
           }
           userView.displayData("Please select a property or press any letter to go back");
           String selected = userView.fetchData();
           if(isNumeric(selected)){
        Property selectedProperty;
        for(Property p : properties){
            if(parseInt(selected)==(p.getId())){
                selectedProperty=p;
                userView.displayData("Selected property: " + selectedProperty.toString());

                userView.displayData("1.Create an appointment\n2.See agent details\n");

                String input = userView.fetchData();
                switch(input.trim()) {
                    case "1":
                        handleTimeOfDay(selectedProperty);
                        break;
                    case "2":
                        Agent agent = unitOfWork.getAgentRepository().select(selectedProperty.getAgentId());
                        userView.displayData(agent.toString());
                        break;
                    default:
                        userView.displayData("Invalid Command");

                }
            }


            }

        }
           else{
               return;
           }


    }

    private void handleTimeOfDay(Property selectedProperty) throws SQLException {
        userView.displayData("Select when do you want the appointment tomorrow:\n1.Morning\n2.Midday\n3.Evening");
        String input = userView.fetchData();
        List<Appointment> appointments = unitOfWork.getAppointmentRepository().select();
        switch(input.trim()){
            case "1":
                handleCreateAppointment(appointments,selectedProperty,"Morning");
                break;
            case "2":
                handleCreateAppointment(appointments,selectedProperty,"Noon");
                break;
            case "3":
                handleCreateAppointment(appointments,selectedProperty,"Evening");
                break;
            default:
                userView.displayData("Invalid");
        }
    }
    private void handleCreateAppointment(List<Appointment> appointments,Property selectedProperty,String timeOfDay) throws SQLException {
        boolean taken=false;
        Appointment appointment;
        boolean already=false;
        for(Appointment a : appointments){
            if((Objects.equals(a.getDate(), timeOfDay) && a.getPropertyId()==selectedProperty.getId() && !a.isDone() ) ){
                taken=true;
            }
            if( (a.getPropertyId()== selectedProperty.getId() && a.getUserId()==user.getId())){
                already=true;
            }
        }
        if(!taken) {
            appointment = new Appointment(selectedProperty.getId(), user.getId(),selectedProperty.getAgentId(), timeOfDay,false);
            unitOfWork.getAppointmentRepository().insert(appointment);
        }
        else{
            if(!already)
                userView.displayData("Not available during "+ timeOfDay);
            else{
                userView.displayData("Already made an appointment on this property");
            }
        }
    }

    private void handleSeeAppointments() throws SQLException {
        List<Appointment> appointments = unitOfWork.getAppointmentRepository().select();
        for(Appointment a : appointments){
            if(a.getUserId()==user.getId() && !a.isDone()){
                Property property = unitOfWork.getPropertyRepository().select(a.getPropertyId());
                userView.displayData("During " + a.getDate() + " " + property.toString());
            }
        }
    }
}
