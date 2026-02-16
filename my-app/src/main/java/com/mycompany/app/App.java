package com.mycompany.app;

import com.mycompany.app.frontend.MainMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        MainMenu menu = new MainMenu();
        Scene scene = menu.createScene(stage);
        stage.setTitle("Queens Solver");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
