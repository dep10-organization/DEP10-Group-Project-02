package lk.ijse.dep10.app.controller;

import com.mysql.cj.jdbc.IterateBlock;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
    void btnManageCustomersOnAction(ActionEvent event) {

    }

    @FXML
    void btnManageEmployeesOnAction(ActionEvent event) throws IOException {
        Scene scene = new Scene(new FXMLLoader(getClass().getResource("/view/EmployeeView.fxml")).load());
        Stage stage = (Stage) btnManageEmployees.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Manage Employees");
        stage.show();
        stage.sizeToScene();
        stage.centerOnScreen();
    }

    @FXML
    void btnManageStudentsOnAction(ActionEvent event) {

    }

    @FXML
    void btnManageTeachersOnAction(ActionEvent event) {

    }

}
