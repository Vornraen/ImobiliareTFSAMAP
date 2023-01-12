package Main;

import Controller.AgentController;
import Controller.DefaultController;
import Controller.IController;
import Controller.UserController;
import Database.DatabaseConnection;
import Model.Agent;
import Model.User;
import Repository.PropertyRepository;
import Repository.UnitOfWork;
import Repository.UserRepository;
import View.AgentView;
import View.DefaultView;
import View.IView;
import View.UserView;

import java.sql.*;

import static Database.DatabaseConnection.getConnection;

public class Main {

    public static void main(String[] args) throws SQLException {
        UnitOfWork unitOfWork = new UnitOfWork(getConnection());
        IView defaultView = new DefaultView();
        IController defaultController = new DefaultController(unitOfWork,defaultView);
        defaultController.handleUserInput();
    }
}