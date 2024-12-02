module com.example.employeedatabasesystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Add this to enable access to java.sql

    opens com.example.employeedatabasesystem to javafx.fxml;
    exports com.example.employeedatabasesystem;
}