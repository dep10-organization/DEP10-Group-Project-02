package lk.ijse.dep10.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.dep10.app.db.DBConnection;
import lk.ijse.dep10.app.model.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeeViewController {

    public TableView<Employee> tblEmployees;
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnNewEmployee;

    @FXML
    private Button btnSave;


    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    public void initialize() {
        tblEmployees.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblEmployees.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblEmployees.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        loadAllEmployees();

        tblEmployees.getSelectionModel().selectedItemProperty().addListener((ov, previous, current) -> {
            btnDelete.setDisable(current == null);
            if (current == null) return;
            txtId.setText(current.getId());
            txtName.setText(current.getName());
            txtAddress.setText(current.getAddress());
        });

    }

    private void loadAllEmployees() {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Employee");
            while (rst.next()) {
                String id = rst.getString("id");
                String name = rst.getString("name");
                String address = rst.getString("address");

                Employee employee = new Employee(id, name, address);
                tblEmployees.getItems().add(employee);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load employees, try again").showAndWait();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnNewEmployeeOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }


    public void tblEmployeesOnKeyReleased(KeyEvent keyEvent) {

    }
}
