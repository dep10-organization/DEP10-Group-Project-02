package lk.ijse.dep10.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lk.ijse.dep10.app.db.DBConnection;
import lk.ijse.dep10.app.model.Customer;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.*;

public class CustomerViewController {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnNewCustomer;

    @FXML
    private Button btnSave;

    @FXML
    private TableView<Customer> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    public void initialize() {
        btnDelete.setDisable(true);

        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        loadAllCustomers();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((ov, prev, current) -> {
            if (current == null) return;

            btnDelete.setDisable(false);
            txtId.setText(current.getId() + "");
            txtName.setText(current.getName());
            txtAddress.setText(current.getAddress());
        });
    }

    private void loadAllCustomers() {

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Customer");

            while (rst.next()) {
                String id = rst.getString("id");
                String name = rst.getString("name");
                String address = rst.getString("address");

                tblCustomer.getItems().add(new Customer(id, name, address));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Customer selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement stm = connection
                    .prepareStatement("DELETE FROM Customer WHERE id = ?");

            stm.setString(1, selectedCustomer.getId());
            stm.executeUpdate();

            tblCustomer.getItems().remove(selectedCustomer);
            if (tblCustomer.getItems().isEmpty()) btnNewCustomer.fire();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to delete the student").show();
        }
    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) {
        txtId.setText("Generated ID");
        txtName.clear();
        txtAddress.clear();
        txtName.requestFocus();
        tblCustomer.getSelectionModel().clearSelection();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (!isDataValid()) return;

        Connection connection = DBConnection.getInstance().getConnection();
        String generatedId = generateNewId();
        try {
            PreparedStatement stm = connection
                    .prepareStatement("INSERT INTO Customer (id, name, address) VALUES (?, ?, ?)");

            stm.setString(1, generatedId);
            stm.setString(2, txtName.getText());
            stm.setString(3, txtAddress.getText());
            stm.executeUpdate();

            Customer customer = new Customer(generatedId, txtName.getText(), txtAddress.getText());
            tblCustomer.getItems().add(customer);
            btnNewCustomer.fire();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save the student").show();
        }
    }


    private String generateNewId() {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Customer ORDER BY id DESC LIMIT 1");
            String generatedId;
            if (!rst.next()) {
                generatedId = String.format("C%03d", 1);
            } else {
                String id = rst.getString("id");
                String latestId = id.substring(1);

                generatedId = String.format("C%03d", (Integer.parseInt(latestId) + 1));
            }
            return generatedId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isDataValid() {
        boolean dataValid = true;
        String name = txtName.getText().strip();
        String address = txtAddress.getText().strip();

        if (!address.matches("[A-Za-z0-9 ]+")) {
            txtAddress.requestFocus();
            txtAddress.selectAll();
            dataValid = false;
        }

        if (!name.matches("[A-Za-z ]+")) {
            txtName.requestFocus();
            txtName.selectAll();
            dataValid = false;
        }

        return dataValid;
    }

}
