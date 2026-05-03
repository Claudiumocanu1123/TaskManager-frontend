module com.claudiu.taskmanager.taskmanagerjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;


    opens com.claudiu.taskmanager.taskmanagerjavafx to javafx.fxml;
    exports com.claudiu.taskmanager.taskmanagerjavafx;
}