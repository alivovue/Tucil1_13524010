package com.mycompany.app.backend.controller;
import com.mycompany.app.backend.data.*;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class BoardUI {
    private BoardObject boardObject;
    private ColorObject colorObject;
    private PaletteUI paletteUI;

    private final GridPane grid = new GridPane();
    private ImageView[][] queenIcons;
    private StackPane[][] colorCells;
    private boolean interactable;

    private static final String[] colorPalette = new String[] {
        "#1f77b4", "#ff7f0e", "#2ca02c", "#d62728", "#9467bd", "#8c564b",
        "#e377c2", "#7f7f7f", "#bcbd22", "#17becf", "#0066cc", "#ff9900",
        "#00cc66", "#cc0000", "#6600cc", "#996633", "#cc3399", "#5a5a5a",
        "#99cc00", "#00cccc", "#3366ff", "#ff3366", "#33ccff", "#ffcc00",
        "#66ff66", "#ff6666"
    };

    private final Image queenCrownImage = new Image(getClass().getResourceAsStream("/queen_png.png"));

    public BoardUI(BoardObject boardObject, ColorObject colorObject, PaletteUI paletteUI) {
        this.boardObject = boardObject;
        this.colorObject = colorObject;
        this.paletteUI = paletteUI;

        int n = colorObject.getLength();
        queenIcons = new ImageView[n][n];
        colorCells = new StackPane[n][n];

        this.interactable = false;

        buildBoardGridPane();
        refresh();
    }

    public GridPane getGrid() {
        return grid;
    }

    public void setInteractable(boolean set) {
        interactable = set;
    }

    public void buildBoardGridPane() {
        grid.getChildren().clear();
        int n = colorObject.getLength();
        double maxBoardPx = 520;
        double size = Math.floor(maxBoardPx / n);
        size = Math.max(14, Math.min(size, 40));

        queenIcons = new ImageView[colorObject.getLength()][colorObject.getLength()];

        for (int i = 0 ; i < colorObject.getLength() ; i++) {
            for (int j = 0 ; j < colorObject.getLength() ; j++) {

                StackPane cell = new StackPane();
                cell.setMinSize(size, size);
                cell.setAlignment(Pos.CENTER);

                int code = colorObject.getColorTile(i, j);

                String background;
                if (code == 0) {
                    background = "#bdbdbd";
                }
                else {
                    background = colorPalette[(code - 1) % colorPalette.length];
                }

                cell.setStyle(
                    "-fx-background-color: " + background + ";" +
                    "-fx-border-color: #333333;" +
                    "-fx-border-width: 0.6;"
                );

                ImageView icon = new ImageView(queenCrownImage);
                icon.setPreserveRatio(true);
                icon.setFitWidth(size * 0.75);
                icon.setFitHeight(size * 0.75);
                icon.setVisible(false);

                queenIcons[i][j] = icon;
                cell.getChildren().add(icon);

                if (interactable) {
                    final int row = i;
                    final int col = j;
                    cell.setOnMouseClicked(e -> {
                        if (paletteUI == null) return;
                        int selectedColor = paletteUI.getSelectedColor();
                        colorObject.setColor(row, col, selectedColor);
                        String bg;
                        if (selectedColor == 0) {
                            bg = "#bdbdbd";
                        }
                        else {
                            bg = colorPalette[(selectedColor - 1) % colorPalette.length];
                        }
                        cell.setStyle(
                            "-fx-background-color: " + bg + ";" +
                            "-fx-border-color: #333333;" +
                            "-fx-border-width: 0.6;"
                        );
                    });
                }
                grid.add(cell, j, i);
            }
        }
    }

    public void refresh() {
        for (int i = 0 ; i < colorObject.getLength() ; i++) {
            for (int j = 0 ; j < colorObject.getLength() ; j++) {
                queenIcons[i][j].setVisible(boardObject.getTile(i, j) == 1);
            }
        }
    }
}
