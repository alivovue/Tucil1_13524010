package com.mycompany.app.backend.controller;

import javafx.geometry.Pos;
import javafx.scene.control.Button;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class PaletteUI {
    private int selectedColor;
    public PaletteUI () {
        this.selectedColor = 1;
    }
    // for color palette menu 

    public int getSelectedColor() {
        return selectedColor;
    }

    public GridPane colorPaletteBar() {
        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.setAlignment(Pos.CENTER);
        for (int i = 1; i <= 26; i++) {
            String colorHex = getPaletteHex(i);
            Button b = createPaletteButton(colorHex, i);

            int index = i - 1;
            int col = index % 13;
            int row = index / 13;

            grid.add(b, col, row);
        }

        return grid;
    }

    private String getPaletteHex(int code) {
        String[] palette = new String[] {
            "#1f77b4", "#ff7f0e", "#2ca02c", "#d62728", "#9467bd", "#8c564b",
            "#e377c2", "#7f7f7f", "#bcbd22", "#17becf", "#0066cc", "#ff9900",
            "#00cc66", "#cc0000", "#6600cc", "#996633", "#cc3399", "#5a5a5a",
            "#99cc00", "#00cccc", "#3366ff", "#ff3366", "#33ccff", "#ffcc00",
            "#66ff66", "#ff6666"
        };
        return palette[(code - 1) % palette.length];
    }

    private Button createPaletteButton(String colorHex, int code) {
        Button b = new Button();
        b.setMinSize(24, 24);
        b.setPrefSize(24, 24);
        b.setMaxSize(24, 24);

        b.setStyle(
            "-fx-background-color: " + colorHex + ";" +
            "-fx-border-color: #333333;" +
            "-fx-border-width: 1;" +
            "-fx-background-radius: 0;" +
            "-fx-padding: 0;"
        );

        b.setOnAction(e ->{
            selectedColor = code;
        });

        return b;
    }
}
