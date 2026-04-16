module com.example.langtonsant {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.langtonsant to javafx.fxml;
    exports com.example.langtonsant;
}