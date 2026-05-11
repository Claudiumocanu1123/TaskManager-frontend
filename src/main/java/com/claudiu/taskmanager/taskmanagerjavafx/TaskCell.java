package com.claudiu.taskmanager.taskmanagerjavafx;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TaskCell extends ListCell<Task> {

    private final CheckBox checkBox = new CheckBox();
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private static final String API_URL = "http://localhost:8080/api/tasks";

    @Override
    protected void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);
        if (empty || task == null) {
            setGraphic(null);
            setText(null);
            return;
        }
        checkBox.setText(task.getTitle() + " - " + task.getDescription());
        checkBox.setSelected(task.isCompleted());
        if(task.isCompleted()) {
            checkBox.setStyle("-fx-text-fill: #888888; -fx-strikethrough: true;");
        }
        else {
            checkBox.setStyle("-fx-text-fill: white; -fx-strikethrough: false;");
        }
        checkBox.setOnAction(event -> {
            String url = API_URL + "/" + task.getId() + "/complete";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .method("PATCH", HttpRequest.BodyPublishers.noBody())
                    .build();
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        });
        setGraphic(checkBox);
        setText(null);
    }
}
