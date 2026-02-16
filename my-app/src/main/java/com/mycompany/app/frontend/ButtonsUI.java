package com.mycompany.app.frontend;

import javafx.scene.control.Button;

public class ButtonsUI {
    public ButtonsUI() {}

    public Button calculateButton() {
        Button b = new Button("Calculate");
        b.setOnAction(e -> {
            System.out.println("calculated");
        });
        return b;
    }

    public Button inputFromFile() {
        Button b = new Button("Input from file");
        b.setOnAction(e -> {
            System.out.println("inputted from file");
        });
        return b;
    }
}
