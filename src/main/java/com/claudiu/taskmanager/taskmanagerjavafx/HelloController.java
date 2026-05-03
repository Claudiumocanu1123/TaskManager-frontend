package com.claudiu.taskmanager.taskmanagerjavafx;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.nio.charset.StandardCharsets;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

public class HelloController {

    @FXML
    private Label messageLabel;
    @FXML
    private TextField textField;
    @FXML
    private ListView<Task> listView;
    @FXML
    private Button DeleteTaskButton;
    @FXML
    private Button EditTaskButton;
    @FXML
    private CheckBox cb1;
    @FXML
    private CheckBox cb2;
    @FXML
    private CheckBox cb3;
    @FXML
    private CheckBox cb4;
    @FXML
    private CheckBox cb5;
    @FXML
    private CheckBox cb6;

    private ObservableList<Task> list = FXCollections.observableArrayList();
    private HttpClient httpClient = HttpClient.newHttpClient();
    private static final String API_URL = "http://localhost:8080/api/tasks";

    @FXML
    private void initialize() {
        listView.setItems(list);
        textField.setOnAction(e -> handleGoToTask());
        listView.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2) {
                textField.clear();
            }
        });
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                textField.setText(newValue.getTitle());
            }
        });
        DeleteTaskButton.disableProperty().bind(
                listView.getSelectionModel().selectedItemProperty().isNull()
        );
        EditTaskButton.disableProperty().bind(
                listView.getSelectionModel().selectedItemProperty().isNull()
        );
        loadTasksFromBackend();

    }
    private void loadTasksFromBackend() {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .GET()
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(json -> {
                    System.out.println("JSON de la server: " + json);
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

                        List<Task> tasksFromBackend =
                                mapper.readValue(json, new TypeReference<List<Task>>() {});

                        Platform.runLater(() -> list.setAll(tasksFromBackend));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
    @FXML
    private void handleGoToTask() {
        String task = textField.getText().trim();
        String title = textField.getText();
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
        if(task.isEmpty())
            return;
        if(!task.isEmpty()) {
            list.add(new Task(title,""));
            textField.clear();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + "?title=" + encodedTitle))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenRun(() -> loadTasksFromBackend());
        }
    }
    @FXML
    private void handleGoToDelete() {
        Task task = listView.getSelectionModel().getSelectedItem();
        System.out.println("Task selectat: " + task.getTitle() + " | ID: " + task.getId());
        if(task == null)
        {
            messageLabel.setText("Please select a task");
            return;
        }
        Long id = task.getId();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Task");
            alert.setHeaderText("Are you sure you want to delete this task?");
            alert.setContentText("This action cannot be undone");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .uri(URI.create(API_URL + "/" + task.getId()))
                        .DELETE()
                        .build();
                System.out.println("URL delete: " + httpRequest.uri());
                httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                        .thenAccept(response ->
                                Platform.runLater(() -> {
                                    System.out.println("Status delete: " + response.statusCode());
                                    list.remove(task);
                                    listView.getSelectionModel().clearSelection();
                                    textField.clear();
                                })
                        );
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

        Task task = listView.getSelectionModel().getSelectedItem();

        if (task == null) {
            messageLabel.setText("List is empty");
            return;
        }

        String newTask = textField.getText().trim();

        if (newTask.isEmpty()) {
            messageLabel.setText("textField is empty");
            return;
        }

        String encodedTask = URLEncoder.encode(newTask, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + task.getId() + "?title=" + encodedTask))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response ->
                        Platform.runLater(() -> {
                            task.setTitle(newTask);
                            listView.refresh();
                            textField.clear();
                        })
                );
    }


}