package com.gui.test;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListViewWithTextAndButtons extends Application {

    @Override
    public void start(Stage primaryStage) {
        ListView<String> listView = new ListView<>();
        ObservableList<String> items = FXCollections.observableArrayList(
                "Item 1", "Item 2", "Item 3");
        listView.setItems(items);

        // Set a custom cell factory to display text and buttons
        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    private final HBox hbox = new HBox();
                    private final Text text = new Text();
                    private final Button button = new Button("Click");

                    {
                        button.setOnAction(event -> {
                            System.out.println("Button clicked: " + getItem());
                        });

                        hbox.getChildren().addAll(text, button);
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            text.setText(item);
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });

        Scene scene = new Scene(listView, 200, 200);
        primaryStage.setTitle("ListView with Text and Buttons");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

