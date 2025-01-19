module org.example.aeroport {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.aeroport to javafx.fxml;
    exports org.example.aeroport;
}