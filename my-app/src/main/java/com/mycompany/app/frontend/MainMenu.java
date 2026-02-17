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
import javafx.scene.layout.VBox;
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

        String statusStyle = "-fx-font-size: 16px; -fx-font-weight: 600;";
        iterationLabel.setStyle(statusStyle);
        timeLabel.setStyle(statusStyle);
        successLabel.setStyle(statusStyle);

        buttonsUI.setStatusLabel(iterationLabel, timeLabel, successLabel);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(16));

        Scene mainScene = new Scene(root, 900, 700);

        Button calculateButton = buttonsUI.calculateButton();
        Button inputFileButton = buttonsUI.inputFromFile();
        Button editButton = buttonsUI.editButton();
        Button resetBoardButton = buttonsUI.resetBoardButton();
        Button downloadButton = buttonsUI.downloadButton();
        Button saveToFileButton = buttonsUI.saveToFile();

        editButton.setOnAction(e -> {
            EditMenu editMenu = new EditMenu();
            Scene editScene = editMenu.createScene(stage, mainScene, board, color, sols, boardUI);
            stage.setScene(editScene);
        });

        calculateButton.setStyle(
            "-fx-font-weight: 900;" +
            "-fx-padding: 10 18;" +
            "-fx-background-radius: 8;" +
            "-fx-border-radius: 8;" +
            "-fx-background-color: #2d6cdf;" +
            "-fx-text-fill: white;"
        );

        String secondaryStyle =
            "-fx-font-weight: 700;" +
            "-fx-padding: 10 16;" +
            "-fx-background-radius: 8;" +
            "-fx-border-radius: 8;";

        inputFileButton.setStyle(secondaryStyle);
        editButton.setStyle(secondaryStyle);

        resetBoardButton.setPrefWidth(150);
        downloadButton.setPrefWidth(150);
        saveToFileButton.setPrefWidth(150);

        HBox topBar = new HBox(12, calculateButton, inputFileButton, editButton);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10));
        root.setTop(topBar);

        Label boardTitle = new Label("Board");
        boardTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: 900;");

        StackPane boardWrap = new StackPane(boardUI.getGrid());
        boardWrap.setAlignment(Pos.CENTER);
        boardWrap.setPadding(new Insets(12));
        boardWrap.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #d9d9d9;" +
            "-fx-border-radius: 12;" +
            "-fx-background-radius: 12;"
        );

        boardUI.getGrid().setHgap(0);
        boardUI.getGrid().setVgap(0);

        VBox centerCard = new VBox(12, boardTitle, boardWrap);
        centerCard.setAlignment(Pos.TOP_CENTER);
        centerCard.setPadding(new Insets(6));

        root.setCenter(centerCard);

        Label statusTitle = new Label("Status");
        statusTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: 900;");

        Label actionsTitle = new Label("Actions");
        actionsTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: 900;");

        VBox actionsBox = new VBox(10, resetBoardButton, downloadButton, saveToFileButton);
        actionsBox.setAlignment(Pos.TOP_LEFT);

        VBox rightPanel = new VBox(
            12,
            statusTitle,
            iterationLabel,
            timeLabel,
            successLabel,
            actionsTitle,
            actionsBox
        );
        rightPanel.setAlignment(Pos.TOP_LEFT);
        rightPanel.setPadding(new Insets(12));
        rightPanel.setPrefWidth(210);
        rightPanel.setMaxHeight(320);

        rightPanel.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #d9d9d9;" +
            "-fx-border-radius: 12;" +
            "-fx-background-radius: 12;"
        );

        BorderPane.setAlignment(rightPanel, Pos.TOP_RIGHT);
        BorderPane.setMargin(rightPanel, new Insets(6, 0, 0, 12));

        root.setRight(rightPanel);

        return mainScene;
    }
}
