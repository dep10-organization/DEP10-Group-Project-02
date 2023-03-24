package lk.ijse.dep10.app.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.dep10.app.db.DBConnection;
import lk.ijse.dep10.app.model.Employee;

import java.sql.*;

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
        txtId.setEditable(false);

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

        Platform.runLater(btnNewEmployee::fire);
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
        Employee selectedEmployee = tblEmployees.getSelectionModel().getSelectedItem();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement stm = connection.prepareStatement("DELETE FROM Employee WHERE id=?");
            stm.setString(1, selectedEmployee.getId());
            stm.executeUpdate();
            tblEmployees.getItems().remove(selectedEmployee);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to delete the employee, try again").show();
            throw new RuntimeException(e);
        }
        tblEmployees.refresh();
        btnNewEmployee.fire();
    }

    @FXML
    void btnNewEmployeeOnAction(ActionEvent event) {
        txtId.setText(generatedID());
        txtName.clear();
        txtAddress.clear();
        tblEmployees.getSelectionModel().clearSelection();
        txtName.requestFocus();
    }

    private String generatedID() {
        if (tblEmployees.getItems().isEmpty()) return "E001";
        int newId = Integer.parseInt(tblEmployees.getItems().
                get(tblEmployees.getItems().size() - 1).getId().substring(1)) + 1;
        return String.format("E%03d", newId);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (!iDataValid()) return;
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();

        Employee selectedEmployee = tblEmployees.getSelectionModel().getSelectedItem();

        if (selectedEmployee == null) {
            Connection connection = DBConnection.getInstance().getConnection();
            try {
                PreparedStatement stm = connection.prepareStatement("INSERT INTO Employee (id, name, address) VALUES (?,?,?)");
                stm.setString(1, id);
                stm.setString(2, name);
                stm.setString(3, address);
                stm.executeUpdate();

                Employee employee = new Employee(id, name, address);
                tblEmployees.getItems().add(employee);
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Failed to save the employee, try again").show();
                throw new RuntimeException(e);
            }

        } else {
            Connection connection = DBConnection.getInstance().getConnection();
            try {
                PreparedStatement stm = connection.prepareStatement("UPDATE Employee SET name=?, address=? WHERE id=?");
                stm.setString(1, txtName.getText());
                stm.setString(2, txtAddress.getText());
                stm.setString(3, txtId.getText());
                stm.executeUpdate();

                selectedEmployee.setId(txtId.getText());
                selectedEmployee.setName(txtName.getText());
                selectedEmployee.setAddress(txtAddress.getText());
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Failed to update the employee, try again").show();
                throw new RuntimeException(e);
            }
        }
        tblEmployees.refresh();
        btnNewEmployee.fire();

    }

    private boolean iDataValid() {
        boolean dataValid = true;
        String name = txtName.getText();
        String address = txtAddress.getText();

        if (address.length() < 3 || address.strip().isEmpty()) {
            txtAddress.requestFocus();
            txtAddress.selectAll();
            dataValid = false;
        }
        if (!name.matches("[A-Za-z ]+") || name.strip().isEmpty()) {
            txtName.requestFocus();
            txtName.selectAll();
            dataValid = false;
        }
        return dataValid;
    }


    public void tblEmployeesOnKeyReleased(KeyEvent keyEvent) {

    }
}
