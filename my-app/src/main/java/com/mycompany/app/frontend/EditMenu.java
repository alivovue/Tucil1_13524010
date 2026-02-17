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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class EditMenu {
    public Scene createScene(
            Stage stage,
            Scene mainScene,
            BoardObject board,
            ColorObject originalColor,
            SolutionObject sols,
            BoardUI mainBoardUI
    ) {
        ColorObject draftColor = originalColor.copyColorObject();
        ColorObject snapshot = draftColor.copyColorObject();

        PaletteUI paletteUI = new PaletteUI();
        BoardUI editBoardUI = new BoardUI(board, draftColor, paletteUI);
        editBoardUI.setInteractable(true);
        editBoardUI.buildBoardGridPane();
        editBoardUI.refresh();

        ButtonsUI editButtons = new ButtonsUI(board, draftColor, sols, editBoardUI);

        HBox sizeControl = editButtons.boardSizeControl(draftColor.getLength(), 1, 26);
        Button btnClearColor = editButtons.resetColorButton();

        HBox topBar = new HBox(12, sizeControl, btnClearColor);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(10));

        GridPane paletteBar = paletteUI.colorPaletteBar();

        Button btnApply = editButtons.applyButton();
        btnApply.setOnAction(e -> {
            originalColor.copyBoard(draftColor);
            mainBoardUI.buildBoardGridPane();
            mainBoardUI.refresh();

            stage.setScene(mainScene);
        });

        Button btnCancel = editButtons.cancelButton();
        btnCancel.setOnAction(e -> {
            draftColor.copyBoard(snapshot);
            editBoardUI.buildBoardGridPane();
            editBoardUI.refresh();
            stage.setScene(mainScene);
        });

        HBox bottomBar = new HBox(12, paletteBar, btnCancel, btnApply);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(16));

        StackPane centerWrap = new StackPane(editBoardUI.getGrid());
        centerWrap.setAlignment(Pos.CENTER);

        root.setTop(topBar);
        root.setCenter(centerWrap);
        root.setBottom(bottomBar);

        return new Scene(root, 900, 700);
    }
}
