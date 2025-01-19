package org.example.aeroport;

import controller.LoginController;
import domain.Client;
import domain.Flight;
import domain.Ticket;
import domain.validator.IValidator;
import domain.validator.ValidatorFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repo.IClientRepo;
import repo.IFlightRepo;
import repo.ITicketRepo;
import repo.database.factory.DataBaseRepoFactory;
import repo.database.factory.EDataBaseStrategy;
import repo.database.utils.AbstractDataBaseRepo;
import repo.database.utils.DataBaseAcces;
import service.AeroportService;

import java.io.IOException;
import java.sql.SQLException;

public class App extends Application {
    private DataBaseAcces data;
    private AbstractDataBaseRepo<Long, Client> clientRepo;
    private AbstractDataBaseRepo<Long, Flight> flightRepo;
    private AbstractDataBaseRepo<Long, Ticket> ticketRepo;
    public AeroportService service;

    @Override
    public void start(Stage stage) throws Exception {
        AeroportService service;

        String url = "jdbc:postgresql://localhost:5432/aeroport";
        String username = "postgres";
        String password = "ivona2004";

        data = new DataBaseAcces(url, username, password);
        try {
            data.createConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DataBaseRepoFactory repoFactory = new DataBaseRepoFactory(data);
        clientRepo = repoFactory.createRepo(EDataBaseStrategy.Client);
        flightRepo = repoFactory.createRepo(EDataBaseStrategy.Flight);
        ticketRepo = repoFactory.createRepo(EDataBaseStrategy.Ticket);
        this.service = new AeroportService((IClientRepo) clientRepo, (IFlightRepo) flightRepo, (ITicketRepo) ticketRepo);
        initView(stage);
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        stage.show();

    }

    private void initView(Stage stage) throws IOException {
        FXMLLoader stageLoader = new FXMLLoader();
        stageLoader.setLocation(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(stageLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);

        LoginController controller = stageLoader.getController();
        controller.setLoginController(service, stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
