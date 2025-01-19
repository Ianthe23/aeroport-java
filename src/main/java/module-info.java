module org.example.aeroport {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens org.example.aeroport to javafx.fxml;
    exports org.example.aeroport;
}