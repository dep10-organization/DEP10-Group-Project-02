package lk.ijse.dep10.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class MainViewController {

    @FXML
    private Button btnManageCustomers;

    @FXML
    private Button btnManageEmployees;

    @FXML
    private Button btnManageStudents;

    @FXML
    private Button btnManageTeachers;

    @FXML
    void btnManageCustomersOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnManageCustomers.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CustomerView.fxml"))));
    }

    @FXML
    void btnManageEmployeesOnAction(ActionEvent event) throws IOException {
        Scene scene = new Scene(new FXMLLoader(getClass().getResource("/view/EmployeeView.fxml")).load());
        Stage stage = (Stage) btnManageEmployees.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Manage Employees");
        stage.sizeToScene();
        stage.setResizable(true);
        stage.show();
        stage.setResizable(false);
        stage.centerOnScreen();
    }

    @FXML
    void btnManageStudentsOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnManageStudents.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/StudentView.fxml"))));
        stage.sizeToScene();
        stage.setResizable(true);
        stage.centerOnScreen();
    }

    @FXML
    void btnManageTeachersOnAction(ActionEvent event) {

    }

}
