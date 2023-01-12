package Controller;

import Model.Agent;
import Model.User;
import Repository.UnitOfWork;
import View.AgentView;
import View.IView;
import View.UserView;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class DefaultController implements IController {
    private UnitOfWork unitOfWork;
    private IView defaultView;

    private List<User> users;

    private List<Agent> agents;


    public DefaultController(UnitOfWork unitOfWork,IView defaultView) throws SQLException {
        this.unitOfWork = unitOfWork;
        this.defaultView=defaultView;

    }

    @Override
    public void handleUserInput() throws SQLException {

        while(true)
        {
            defaultView.displayMenu();
            String input = defaultView.fetchData();
            switch(input.trim()) {
                case "1":
                    handleLogin();
                    break;
                case "2":
                    handleRegister();
                default:
                    defaultView.displayData("Invalid Command");

            }
        }
    }

    private void handleLogin() throws SQLException {
        List<User> users = unitOfWork.getUserRepository().select();
        List<Agent> agents = unitOfWork.getAgentRepository().select();

        boolean isUser=false;
        boolean isAgent=false;

        defaultView.displayData("Email: \n");
        String email = defaultView.fetchData();

        defaultView.displayData("Password: \n");
        String password =  defaultView.fetchData();

        for(User u : users){
            if(email.equals(u.getEmail()) && password.equals(u.getPassword())){
                IView userView = new UserView();
                UserController userController = new UserController(unitOfWork,userView,u);
                userController.handleUserInput();
            }
        }

        for(Agent a: agents){
            if(email.equals(a.getEmail()) && password.equals(a.getPassword())){
                IView agentView = new AgentView();
                AgentController agentController = new AgentController(unitOfWork,agentView,a);
                agentController.handleUserInput();
            }
        }
        defaultView.displayData("Please create an account or try again");


    }

    private void handleRegister() throws SQLException{
        List<User> users = unitOfWork.getUserRepository().select();
        List<Agent> agents = unitOfWork.getAgentRepository().select();
        boolean alreadyExists=false;

        defaultView.displayData("Please enter the following data to create an account:\nEmail: ");
        String email = defaultView.fetchData();

        for(User u : users){
            if(email.equals(u.getEmail())){
                alreadyExists=true;
            }
        }
        for(Agent a : agents){
            if(Objects.equals(email, a.getEmail())){
                alreadyExists=true;
            }
        }
        if(alreadyExists){
            defaultView.displayData("User already exists");
        }
        else {
            defaultView.displayData("\nPassword: ");
            String password = defaultView.fetchData();

            defaultView.displayData("\nName: ");
            String name = defaultView.fetchData();

            User newUser = new User(name,email,password);
            unitOfWork.getUserRepository().insert(newUser);

            defaultView.displayData("User created successfully");
        }


    }

}
