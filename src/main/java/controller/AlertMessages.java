package controller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class AlertMessages {
    public static void showMessage(Stage stage, Alert.AlertType type, String header, String message) {
        Alert alert = new Alert(type);
        alert.initStyle(stage.getStyle());
        alert.initOwner(stage);
        alert.setTitle(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
