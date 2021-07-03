module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires sqlite.jdbc;

    exports org.example.application.view;
    exports org.example.application.controllers;

    opens org.example.application.view to javafx.fxml;
    opens org.example.application.controllers to javafx.fxml;
    opens org.example.domain.entities.taxpayer to javafx.base;
    opens org.example.domain.entities.expenses to javafx.base;
    opens org.example.domain.entities.incometax to javafx.base;
}
