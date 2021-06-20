module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.application.view to javafx.fxml;
    exports org.example.application.view;
    exports org.example.application.controllers;
    opens org.example.application.controllers to javafx.fxml;
}