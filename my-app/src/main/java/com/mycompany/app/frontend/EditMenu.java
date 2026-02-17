package com.mycompany.app.frontend;

import com.mycompany.app.backend.controller.BoardUI;
import com.mycompany.app.backend.controller.ButtonsUI;
import com.mycompany.app.backend.controller.PaletteUI;
import com.mycompany.app.backend.data.BoardObject;
import com.mycompany.app.backend.data.ColorObject;
import com.mycompany.app.backend.data.SolutionObject;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditMenu {

    public Scene createScene(Stage stage, Scene mainScene, BoardObject board, ColorObject originalColor, SolutionObject sols, BoardUI mainBoardUI) {
        ColorObject newColor = originalColor.copyColorObject();
        ColorObject cacheColor = newColor.copyColorObject();

        PaletteUI paletteUI = new PaletteUI();
        BoardUI editBoardUI = new BoardUI(board, newColor, paletteUI);
        editBoardUI.setInteractable(true);
        editBoardUI.buildBoardGridPane();
        editBoardUI.refresh();

        ButtonsUI buttonsUI = new ButtonsUI(board, newColor, sols, editBoardUI);

        Label title = new Label("Edit Board (" + newColor.getLength() + "x" + newColor.getLength() + ")");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: 900;");

        StackPane boardWrap = new StackPane(editBoardUI.getGrid());
        boardWrap.setAlignment(Pos.CENTER);
        boardWrap.setPadding(new Insets(14));
        boardWrap.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #d9d9d9;" +
            "-fx-border-radius: 12;" +
            "-fx-background-radius: 12;"
        );

        VBox center = new VBox(12, title, boardWrap);
        center.setAlignment(Pos.TOP_CENTER);
        center.setPadding(new Insets(6));

        Label settingsTitle = new Label("Board Settings");
        settingsTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: 900;");

        HBox sizeControl = buttonsUI.boardSizeControl(newColor.getLength(), 1, 26);
        Button clearButton = buttonsUI.resetColorButton();

        VBox settingsBox = new VBox(10, settingsTitle, sizeControl, clearButton);
        settingsBox.setAlignment(Pos.TOP_LEFT);
        settingsBox.setPadding(new Insets(12));
        settingsBox.setStyle(cardStyle());

        Label paletteTitle = new Label("Palette");
        paletteTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: 900;");

        Label paletteHint = new Label("Pick a color, then click tiles to paint.");
        paletteHint.setStyle("-fx-font-size: 12px; -fx-text-fill: rgba(0,0,0,0.7);");

        GridPane paletteGrid = paletteUI.colorPaletteBar();

        VBox paletteBox = new VBox(10, paletteTitle, paletteHint, paletteGrid);
        paletteBox.setAlignment(Pos.TOP_LEFT);
        paletteBox.setPadding(new Insets(12));
        paletteBox.setStyle(cardStyle());

        Button applyButton = buttonsUI.applyButton();
        applyButton.setText("Apply Changes");
        applyButton.setStyle(
            "-fx-font-weight: 900;" +
            "-fx-padding: 10 16;" +
            "-fx-background-radius: 10;" +
            "-fx-border-radius: 10;" +
            "-fx-background-color: #2d6cdf;" +
            "-fx-text-fill: white;"
        );

        applyButton.setOnAction(e -> {
            originalColor.copyBoard(newColor);
            mainBoardUI.buildBoardGridPane();
            mainBoardUI.refresh();
            stage.setScene(mainScene);
        });

        Button cancelButton = buttonsUI.cancelButton();
        cancelButton.setText("Cancel");
        cancelButton.setStyle(
            "-fx-font-weight: 800;" +
            "-fx-padding: 10 16;" +
            "-fx-background-radius: 10;" +
            "-fx-border-radius: 10;"
        );

        cancelButton.setOnAction(e -> {
            newColor.copyBoard(cacheColor);
            editBoardUI.buildBoardGridPane();
            editBoardUI.refresh();
            stage.setScene(mainScene);
        });

        HBox actionRow = new HBox(10, cancelButton, applyButton);
        actionRow.setAlignment(Pos.CENTER_RIGHT);

        VBox sidebar = new VBox(12, settingsBox, paletteBox, actionRow);
        sidebar.setAlignment(Pos.TOP_LEFT);
        sidebar.setPadding(new Insets(6, 0, 0, 12));
        sidebar.setPrefWidth(320);


        BorderPane root = new BorderPane();
        root.setPadding(new Insets(16));
        root.setCenter(center);
        root.setRight(sidebar);

        return new Scene(root, 900, 700);
    }

    private String cardStyle() {
        return ""
            + "-fx-background-color: white;"
            + "-fx-border-color: #d9d9d9;"
            + "-fx-border-radius: 12;"
            + "-fx-background-radius: 12;";
    }
}
