package com.mycompany.app.backend.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class PaletteUI {
    private int selectedColor;
    private final List<Button> paletteButtons = new ArrayList<>(26);

    public PaletteUI() {
        this.selectedColor = 1;
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public GridPane colorPaletteBar() {
        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(8);
        grid.setAlignment(Pos.TOP_LEFT);

        paletteButtons.clear();

        int columns = 6;

        for (int i = 1; i <= 26; i++) {
            String colorHex = getPaletteHex(i);
            Button b = createPaletteButton(colorHex, i);
            paletteButtons.add(b);

            int index = i - 1;
            int col = index % columns;
            int row = index / columns;

            grid.add(b, col, row);
        }

        updateHighlight();
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
        b.setMinSize(32, 32);
        b.setPrefSize(32, 32);
        b.setMaxSize(32, 32);
        b.setStyle(baseStyle(colorHex, false));

        b.setOnAction(e -> {
            selectedColor = code;
            updateHighlight();
        });

        return b;
    }

    private void updateHighlight() {
        for (int i = 0; i < paletteButtons.size(); i++) {
            int code = i + 1;
            String hex = getPaletteHex(code);
            boolean selected = (code == selectedColor);
            paletteButtons.get(i).setStyle(baseStyle(hex, selected));
        }
    }

    private String baseStyle(String colorHex, boolean selected) {
        if (selected) {
            return ""
                + "-fx-background-color: " + colorHex + ";"
                + "-fx-border-color: black;"
                + "-fx-border-width: 3;"
                + "-fx-background-radius: 6;"
                + "-fx-border-radius: 6;"
                + "-fx-padding: 0;"
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.35), 8, 0.25, 0, 0);";
        }

        return ""
            + "-fx-background-color: " + colorHex + ";"
            + "-fx-border-color: rgba(0,0,0,0.45);"
            + "-fx-border-width: 1;"
            + "-fx-background-radius: 6;"
            + "-fx-border-radius: 6;"
            + "-fx-padding: 0;";
    }
}
