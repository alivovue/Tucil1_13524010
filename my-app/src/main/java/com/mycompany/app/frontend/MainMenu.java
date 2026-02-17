package com.mycompany.app.frontend;

import com.mycompany.app.backend.controller.BoardUI;
import com.mycompany.app.backend.controller.ButtonsUI;
import com.mycompany.app.backend.data.BoardObject;
import com.mycompany.app.backend.data.ColorObject;
import com.mycompany.app.backend.data.SolutionObject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainMenu {

    public Scene createScene(Stage stage) {
        int n = 8;

        BoardObject board = new BoardObject(n);
        ColorObject color = new ColorObject(n);
        SolutionObject sols = new SolutionObject();

        BoardUI boardUI = new BoardUI(board, color, null);
        ButtonsUI buttonsUI = new ButtonsUI(board, color, sols, boardUI);

        Label iterationLabel = new Label("Iteration : 0");
        Label timeLabel = new Label("Time : 0 ms");
        Label successLabel = new Label("Success : False");

        buttonsUI.setStatusLabel(iterationLabel, timeLabel, successLabel);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(16));

        StackPane centerWrap = new StackPane(boardUI.getGrid());
        centerWrap.setAlignment(Pos.CENTER);
        root.setCenter(centerWrap);

        Scene mainScene = new Scene(root, 900, 700);

        Button btnCalc = buttonsUI.calculateButton();
        Button btnFile = buttonsUI.inputFromFile();

        Button btnResetBoard = buttonsUI.resetBoardButton();
        Button btnDownload = buttonsUI.downloadButton();

        Button btnEdit = buttonsUI.editButton();
        btnEdit.setOnAction(e -> {
            EditMenu editMenu = new EditMenu();
            Scene editScene = editMenu.createScene(stage, mainScene, board, color, sols, boardUI);
            stage.setScene(editScene);
        });

        HBox topBar = new HBox(12, btnCalc, btnFile, btnEdit);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(10));

        HBox bottomBar = new HBox(12, btnResetBoard, btnDownload, iterationLabel, timeLabel, successLabel);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(10));

        root.setTop(topBar);
        root.setBottom(bottomBar);

        return mainScene;
    }
}
