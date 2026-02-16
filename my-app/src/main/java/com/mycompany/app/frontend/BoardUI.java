package com.mycompany.app.frontend;
import com.mycompany.app.backend.data.*;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.*;

public class BoardUI {
    private BoardObject boardObject;
    private ColorObject colorObject;
    private SolutionObject solutionObject;

    private final GridPane grid = new GridPane();
    private final Label[][] queenLabels;

    private static final String[] colorPalette = new String[] {
        "#1f77b4", "#ff7f0e", "#2ca02c", "#d62728", "#9467bd", "#8c564b",
        "#e377c2", "#7f7f7f", "#bcbd22", "#17becf", "#0066cc", "#ff9900",
        "#00cc66", "#cc0000", "#6600cc", "#996633", "#cc3399", "#5a5a5a",
        "#99cc00", "#00cccc", "#3366ff", "#ff3366", "#33ccff", "#ffcc00",
        "#66ff66", "#ff6666"
    };

    public BoardUI(BoardObject boardObject, ColorObject colorObject, SolutionObject solutionObject) {
        this.boardObject = boardObject;
        this.colorObject = colorObject;
        this.solutionObject = solutionObject;

        int n = colorObject.getLength();
        queenLabels = new Label[n][n];

        buildBoardGridPane();
        refresh();
    }

    public GridPane getGrid() {
        return grid;
    }

    public void buildBoardGridPane() {
        grid.getChildren().clear();
        double size = 40;

        for (int i = 0 ; i < colorObject.getLength() ; i++) {
            for (int j = 0 ; j < colorObject.getLength() ; j++) {
                // minimum requirement
                StackPane cell = new StackPane();
                cell.setMinSize(size, size);
                cell.setAlignment(Pos.CENTER);

                // set color to bg

                String background = colorPalette[colorObject.getColorTile(i, j) % colorPalette.length];
                cell.setStyle(
                    "-fx-background-color: " + background + ";" +
                    "-fx-border-color: #333333;" +
                    "-fx-border-width: 0.6;"
                );

                // mark queen

                Label q = new Label("");
                queenLabels[i][j] = q;
                cell.getChildren().add(q);

                grid.add(cell, j, i);
            }
        }
    }

    public void refresh() {
        for (int i = 0 ; i < colorObject.getLength() ; i++) {
            for (int j = 0 ; j < colorObject.getLength() ; j++) {
                queenLabels[i][j].setText(boardObject.getTile(i, j) == 1 ? "Q" : "");
            }
        }
    }
}
