package com.gui.test;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveFileExample extends Application {

    private Stage primaryStage;
    private ProgressBar progressBar;
    private Label progressLabel;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        Button saveButton = new Button("Save File");
        saveButton.setOnAction(e -> saveFileWithProgressBar());

        VBox root = new VBox(saveButton);
        Scene scene = new Scene(root, 200, 100);

        primaryStage.setTitle("Save File With ProgressBar");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveFileWithProgressBar() {
        FileChooser fileChooser = new FileChooser();

        // Set initial file name (default file name)
        fileChooser.setInitialFileName("default_filename.txt");

        // Show save file dialog
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            // Create a new Stage for the progress dialog
            Stage progressStage = new Stage();
            progressBar = new ProgressBar(0);
            progressLabel = new Label("Saving...");
            VBox progressLayout = new VBox(progressLabel, progressBar);
            Scene progressScene = new Scene(progressLayout, 200, 100);
            progressStage.setScene(progressScene);
            progressStage.setTitle("Saving File...");
            progressStage.show();

            // Create a Task for saving file in background
            Task<Void> saveTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    // Sample byte array, replace this with your actual byte array
                    byte[] byteArray = {72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100};

                    // Create FileOutputStream for the file
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        for (int i = 0; i < byteArray.length; i++) {
                            fos.write(byteArray[i]);
                            double progress = (double) (i + 1) / byteArray.length;
                            updateProgress(progress, 1);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return null;
                }
            };

            // Bind progress bar properties to task properties
            progressBar.progressProperty().bind(saveTask.progressProperty());

            // When the task is finished, close the progress dialog
            saveTask.setOnSucceeded(e -> {
                // progressStage.close();
                System.out.println("File saved successfully.");
            });

            // When the task has an exception, close the progress dialog and show error
            saveTask.setOnFailed(e -> {
                // progressStage.close();
                System.out.println("An error occurred while saving file.");
                saveTask.getException().printStackTrace();
            });

            // Start the task in a new thread
            Thread thread = new Thread(saveTask);
            thread.start();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
