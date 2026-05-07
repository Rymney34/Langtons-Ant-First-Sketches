package com.example.langtonsant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("testAnt.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Langton's ant");
        stage.setScene(scene);
        stage.show();
    }
}
