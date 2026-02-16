package com.mycompany.app.frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu {
    // build and return the scene (clean separation)
    public Scene createScene(Stage stage) {
        Label title = new Label("Queens Solver");

        Button btnSolve = new Button("Open Solver");
        btnSolve.setOnAction(e -> {
            // Later: navigate to another screen
            // stage.setScene(new SolverView().createScene(stage));
            title.setText("TODO: open solver view");
        });

        VBox root = new VBox(12, title, btnSolve);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        return new Scene(root, 800, 500);
    }
}
