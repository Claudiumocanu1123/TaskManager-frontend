module com.claudiu.taskmanager.taskmanagerjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.claudiu.taskmanager.taskmanagerjavafx to javafx.fxml;
    exports com.claudiu.taskmanager.taskmanagerjavafx;
}