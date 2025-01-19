package controller;

import domain.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.AeroportService;

public class LoginController {
    AeroportService service;
    Client loggedClient;
    Stage stage;

    @FXML
    Button login_btn;

    @FXML
    TextField usernameField;

    @FXML
    public void initialize() {

    }

    public void setLoginController(AeroportService service, Stage stage) {
        this.service = service;
        this.stage = stage;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        try {
            loggedClient = service.logClient(username);
            if (loggedClient == null) {
                AlertMessages.showMessage(null, Alert.AlertType.ERROR, "Error", "Invalid username");
            } else {
                usernameField.clear();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/org/example/aeroport/views/aeroport-view.fxml"));

                AnchorPane root = loader.load();

                Stage mainStage = new Stage();
                Scene scene = new Scene(root);
                mainStage.setTitle("Bun venit la aeroport!");
                mainStage.setScene(scene);
                mainStage.setMinHeight(400);
                mainStage.setMinWidth(700);

                AeroportController controller = loader.getController();
                controller.setAeroportController(service, mainStage, loggedClient);

                mainStage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
