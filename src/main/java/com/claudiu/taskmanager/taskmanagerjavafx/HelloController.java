package com.claudiu.taskmanager.taskmanagerjavafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class HelloController {

    @FXML
    private Label messageLabel;
    @FXML
    private TextField textField;
    @FXML
    private ListView<String> listView;
    @FXML
    private Button DeleteTaskButton;
    @FXML
    private Button EditTaskButton;

    private ObservableList<String> list = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        listView.setItems(list);
        textField.setOnAction(e -> handleGoToTask());
        listView.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2) {
                handleGoToEdit();
            }
        });
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                textField.setText(newValue);
            }
        });
        DeleteTaskButton.disableProperty().bind(
                listView.getSelectionModel().selectedItemProperty().isNull()
        );
        EditTaskButton.disableProperty().bind(
                listView.getSelectionModel().selectedItemProperty().isNull()
        );

    }
    @FXML
    private void handleGoToTask() {
        String task = textField.getText().trim();
        if(!task.isEmpty()) {
            list.add(task);
            textField.clear();
        }
    }
    @FXML
    private void handleGoToDelete() {
        String task = listView.getSelectionModel().getSelectedItem();
        if(task != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Task");
            alert.setHeaderText("Are you sure you want to delete this task?");
            alert.setContentText("This action cannot be undone");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                list.remove(task);
            }
        }
        else
        {
            textField.clear();
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("No task selected");

        }

    }
    @FXML
    private void handleGoToEdit() {

        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if(selectedIndex != -1)
        {
            String newTask = textField.getText().trim();
            if(!newTask.isEmpty()) {
                listView.getItems().set(selectedIndex,newTask);
                textField.clear();
            }
            else
            {
                messageLabel.setStyle("-fx-text-fill: red;");
                messageLabel.setText("textField is empty");
            }
        }
        else
        {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText("List is empty");
        }

    }
}