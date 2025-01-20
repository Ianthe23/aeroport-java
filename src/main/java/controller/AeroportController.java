package controller;

import domain.Client;
import domain.Flight;
import domain.Ticket;
import exceptions.ServiceException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.AeroportService;
import utils.events.AeroportEvent;
import utils.observer.IObserver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AeroportController implements IObserver<AeroportEvent> {
    AeroportService service;
    Stage primaryStage;
    Client loggedClient;

    @FXML
    private ObservableList<Ticket> modelPurchasedTickets = FXCollections.observableArrayList();

    @FXML
    private ObservableList<Ticket> modelTicketsFromDate = FXCollections.observableArrayList();

    @FXML
    private ObservableList<Flight> modelFlightsByDate = FXCollections.observableArrayList();

    @FXML
    Button buyTicketBtn;

    @FXML
    ComboBox<String> comboBoxFrom;

    @FXML
    ComboBox<String> comboBoxTo;

    @FXML
    DatePicker datePicker;

    @FXML
    Button showFlights_btn;

    @FXML
    Label loggedUserNameLabel;

    @FXML
    TableView<Ticket> purchasedTicketsTableView;

    @FXML
    TableColumn<Ticket, String> flightIdColumn;

    @FXML
    TableColumn<Ticket, String> purchaseTimeColumn;

    @FXML
    TableView<Ticket> ticketsFromDateTableView;

    @FXML
    TableColumn<Ticket, String> flightIdFromDateColumn;

    @FXML
    TableColumn<Ticket, String> purchaseTimeFromDateColumn;

    @FXML
    TableView<FlightRow> flightsByDateTableView;

    @FXML
    TableColumn<FlightRow, String> flightIdByDateColumn;

    @FXML
    TableColumn<FlightRow, String> seatsByDateColumn;

    public void setAeroportController(AeroportService service, Stage primaryStage, Client loggedClient) {
        this.service = service;
        this.primaryStage = primaryStage;
        this.loggedClient = loggedClient;

        loggedUserNameLabel.setText(loggedClient.getName());
        service.addObserver(this);
        initPurchasedTickets();
        initializeTable();

        initTicketsFromDate();
        initializeTableFromDate();

        setFields();
    }

    @FXML
    public void initialize() {
        initializeTable();
        initializeTableFromDate();
    }

    private void initializeTable() {
        flightIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlightId().toString()));
        purchaseTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPurchaseTime().toString()));

        purchasedTicketsTableView.setItems(modelPurchasedTickets);
    }

    private void initializeTableFromDate() {
        flightIdFromDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlightId().toString()));
        purchaseTimeFromDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPurchaseTime().toString()));

        ticketsFromDateTableView.setItems(modelTicketsFromDate);
    }

    private void initializeTableFromDateFromTo() {
        flightIdByDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlightId().toString()));
        seatsByDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlight().getSeats().toString()));
    }

    private void initPurchasedTickets() {
        modelPurchasedTickets.clear();
        Iterable<Ticket> purchasedTickets = service.getTicketsByUsername(loggedClient.getUsername());
        purchasedTickets.forEach(modelPurchasedTickets::add);
    }

    private void initTicketsFromDate() {
        modelTicketsFromDate.clear();
        LocalDate date = LocalDate.of(2024, 1, 24);
        Iterable<Ticket> ticketsFromDate = service.getAllTicketsByDate(date);
        ticketsFromDate.forEach(modelTicketsFromDate::add);
    }

    private void setFields() {
        comboBoxFrom.getItems().addAll(service.getAllFromFlights());
        comboBoxTo.getItems().addAll(service.getAllToFlights());
    }

    @FXML
    private void handleShowFlights() {
        modelFlightsByDate.clear();
        LocalDate date = datePicker.getValue();
        String from = comboBoxFrom.getValue();
        String to = comboBoxTo.getValue();

        List<Flight> flights = service.getFlightsByDateAndFromTo(date, from, to);
        flights.forEach(flight -> {
            modelFlightsByDate.add(flight);
        });

        List<Long> flightIds = modelFlightsByDate.stream().map(flight -> service.getIds(flight.getDepartureTime(), flight.getLandingTime(), flight.getFrom(), flight.getTo())).toList();

        List<FlightRow> rows = IntStream.range(0, modelFlightsByDate.size())
                .mapToObj(i -> new FlightRow(flightIds.get(i), modelFlightsByDate.get(i)))
                .collect(Collectors.toList());

        flightsByDateTableView.setItems(FXCollections.observableArrayList(rows));
        initializeTableFromDateFromTo();
    }

    @FXML
    public void handleBuyTicket() {
        FlightRow selectedFlightRow = flightsByDateTableView.getSelectionModel().getSelectedItem();
        if (selectedFlightRow == null) {
            AlertMessages.showMessage(primaryStage, Alert.AlertType.ERROR, "Error", "No flight selected");
        }

        try {
            Long flightId = selectedFlightRow.getFlightId();
            Ticket ticket = new Ticket(loggedClient.getUsername(), flightId, LocalDateTime.now());
            service.addTicket(ticket);
        } catch (ServiceException e) {
            AlertMessages.showMessage(primaryStage, Alert.AlertType.ERROR, "Error", e.getMessage());
        }

    }

    @Override
    public void update(AeroportEvent aeroportEvent) {
        initPurchasedTickets();
        initTicketsFromDate();
    }
}
