package com.mycompany.app.backend.controller;

import java.io.File;

import com.mycompany.app.backend.data.*;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.stage.Window;

import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import javafx.application.Platform;
import javafx.concurrent.Task;

public class ButtonsUI {
    private BoardObject boardObject;
    private ColorObject colorObject;
    private BruteSolver bruteSolver;
    private SolutionObject solutionObject;
    private BoardUI boardUI;

    private ColorObject cacheColorObject;
    private Label iterationLabel;
    private Label timeLabel;
    private Label successLabel;

    public ButtonsUI(BoardObject boardObject, ColorObject colorObject, SolutionObject solutionObject, BoardUI boardUI) {
        this.boardObject = boardObject;
        this.colorObject = colorObject;
        this.solutionObject = solutionObject;
        this.boardUI = boardUI;
        this.bruteSolver = new BruteSolver(boardObject, colorObject, solutionObject);
        this.cacheColorObject = new ColorObject(colorObject.getLength());
    }

    public void setStatusLabel(Label iterationLabel, Label timeLabel, Label successLabel) {
        this.iterationLabel = iterationLabel;
        this.timeLabel = timeLabel;
        this.successLabel = successLabel;
    }

    public Button calculateButton() {
        Button b = new Button("Calculate");
        b.setOnAction(e -> {
            if (!colorObject.isValidColor()) {
                errorMessage("Invalid Color", "Board is not fully colored or more than 26 colors");
                return;
            }
            
            // check color number == n, ambil dari file reader
            boolean[] seen = new boolean[27];
            int jumlahWarna = 0;

            for (int i = 0 ; i < colorObject.getLength() ; i++) {
                for (int j = 0 ; j < colorObject.getLength() ; j++) {
                    if (!seen[colorObject.getColorTile(i, j)]) {
                        seen[colorObject.getColorTile(i, j)] = true;
                        jumlahWarna++;
                    }
                }
            }

            if (jumlahWarna != colorObject.getLength()) {
                errorMessage("Invalid Color", "Board size and color are not the same amount");
                return;
            }

            
            solutionObject.resetSolutions();
            boardObject.resetBoard();
            boardUI.refresh();
            b.setDisable(true);

            Task<Void> task = new Task<>() {
                protected Void call() {
                    bruteSolver.exhaustiveBrute(it -> {
                        Platform.runLater(() -> {
                            boardUI.refresh();
                            if (iterationLabel != null) iterationLabel.setText("Iteration : " + solutionObject.getIteration());
                            if (timeLabel != null) timeLabel.setText("Time : " + solutionObject.getTime() + " ms");
                            if (successLabel != null) successLabel.setText("Success : " + (solutionObject.getSolutions().isEmpty() ? "False" : "True"));
                        });
                    }, 5000);
                    Platform.runLater(() -> boardUI.refresh());
                    return null;
                }
            };

            task.setOnSucceeded(ev -> b.setDisable(false));
            task.setOnFailed(ev -> {
                b.setDisable(false);
                Throwable ex = task.getException();
                errorMessage("Brute force crashed", ex == null ? "Unknown error" : ex.getMessage());
            });
            
            new Thread(task, "BruteThread").start();
        
        });
        return b;
    }

    public Button inputFromFile() {
        Button b = new Button("Input from file");
        b.setOnAction(e -> {
            Window window = b.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose .txt file");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt")
            );
            File file = fileChooser.showOpenDialog(window);
            if (file == null) return;
            try {
                String path = file.getAbsolutePath();
                ColorObject temp = FileReader.loadColorBoard(path);
                this.colorObject.copyBoard(temp);
                this.boardObject.setSize(temp.getLength());
                boardUI.buildBoardGridPane();
            }
            catch (Exception ex) {
                errorMessage("File load error", ex.getMessage());
            }
            
        });
        return b;
    }

    public Button saveToFile() {
        Button b = new Button("Save to file");
        b.setOnAction(e -> {
            Window window = b.getScene().getWindow();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save .txt file");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt")
            );
            fileChooser.setInitialFileName("hasil.txt");

            File file = fileChooser.showSaveDialog(window);
            if (file == null) return;

            try {
                String path = file.getAbsolutePath();
                if (!path.toLowerCase().endsWith(".txt")) {
                    path += ".txt";
                }

                int n = this.colorObject.getLength();

                try (java.io.BufferedWriter out =
                        java.nio.file.Files.newBufferedWriter(
                            java.nio.file.Path.of(path),
                            java.nio.charset.StandardCharsets.UTF_8
                        )) {

                    for (int i = 0; i < n; i++) {
                        StringBuilder row = new StringBuilder(n);
                        for (int j = 0; j < n; j++) {

                            int number = this.colorObject.getColorTile(i, j);

                            if (number < 1 || number > 26) {
                                throw new IllegalArgumentException("invalid color number at (" + i + "," + j + "): " + number);
                            }

                            char c;
                            if (boardObject.getTile(i, j) == 1) {
                                c = '#';
                            }
                            else {
                                c = (char) ('A' + (number - 1));
                            }
                            row.append(c);
                        }
                        out.write(row.toString());
                        out.newLine();
                    }
                }

            } catch (Exception ex) {
                errorMessage("File save error", ex.getMessage());
            }
        });
        return b;
    }



    public Button resetBoardButton() {
        Button b = new Button("Reset board");
        b.setOnAction(e -> {
            boardObject.resetBoard();
            boardUI.refresh();
        });
        return b;
    }

    public Button resetColorButton() {
        Button b = new Button("Reset color");
        b.setOnAction(e -> {
            colorObject.resetColor();
            boardUI.buildBoardGridPane();
        });
        return b;
    }

    public Button downloadButton() {
        Button b = new Button("Download Result");
        b.setOnAction(e -> {
            if (solutionObject.getSolutions().isEmpty()) {
                errorMessage("No solution", "There is no solution yet, please run the program first");
                return;
            }

            Window window = b.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image");
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PNG Image (*.png)", "*.png")
            );
            fileChooser.setInitialFileName("board.png");

            File file = fileChooser.showSaveDialog(window);
            if (file == null) return;
            try {
                SnapshotParameters snapshotParameters = new SnapshotParameters();
                WritableImage writableImage = boardUI.getGrid().snapshot(snapshotParameters, null);
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
                File result = file.getName().toLowerCase().endsWith(".png") ? file : new File(file.getAbsolutePath() + ".png");
                ImageIO.write(bufferedImage, "png", result);
            }
            catch (Exception ex) {
                errorMessage("Save failed", ex.getMessage());
            }
        });
        return b;
        
    }

    public HBox boardSizeControl(int initialSize, int minSize, int maxSize) {
        Label label = new Label("Board size:");

        Spinner<Integer> spinner = new Spinner<>();
        spinner.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(minSize, maxSize, initialSize)
        );
        spinner.setEditable(true);
        spinner.setPrefWidth(90);

        Button apply = new Button("Apply Size");
        apply.setOnAction(e -> {
            int n = spinner.getValue();
            boardObject.setSize(n);
            colorObject.setSize(n);
            boardUI.buildBoardGridPane();
        });

        HBox box = new HBox(8, label, spinner, apply);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private void errorMessage(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // for edit UI

    public Button applyButton() {
        Button b = new Button("Apply Changes");
        b.setOnAction(e -> {
            System.out.println("changes applied");
        });
        return b;
    }

    public Button cancelButton() {
        Button b = new Button("Cancel Changes");
        b.setOnAction(e -> {
            System.out.println("changes canceled");
        });
        return b;
    }

    public Button editButton() {
        Button b = new Button("Edit Board");
        b.setOnAction(e -> {
            System.out.println("entering edit page");
        });
        return b;
    }
}
