package lk.ijse.dep10.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep10.app.db.DBConnection;
import lk.ijse.dep10.app.model.Customer;
import lk.ijse.dep10.app.model.Teacher;

import java.sql.*;

public class TeacherViewController {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnNewTeacher;

    @FXML
    private TableView<Teacher> tblTeachers;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    public void initialize(){
        btnSave.setDisable(true);
        btnDelete.setDisable(true);

        tblTeachers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblTeachers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblTeachers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        tblTeachers.getSelectionModel().selectedItemProperty().addListener((ov,previous,current)->{
            if(tblTeachers.getSelectionModel().isEmpty())return;
            btnDelete.setDisable(current==null);
            txtId.setText(current.getId()+"");
            txtName.setText(current.getName());
            txtAddress.setText(current.getAddress());
        });
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if(tblTeachers.getSelectionModel().getSelectedItems()==null)return;

        tblTeachers.getItems().remove(tblTeachers.getSelectionModel().getSelectedItem());


    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        Connection connection = DBConnection.getInstance().getConnection();
        String generatedId = generateNewId();
        try {
            PreparedStatement stm = connection
                    .prepareStatement("INSERT INTO Teacher (id, name, address) VALUES (?, ?, ?)");

            stm.setString(1, generatedId);
            stm.setString(2, txtName.getText());
            stm.setString(3, txtAddress.getText());
            stm.executeUpdate();

            Teacher teacher = new Teacher(Integer.valueOf(generatedId), txtName.getText(), txtAddress.getText());
            tblTeachers.getItems().add(teacher);
            btnNewTeacher.fire();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save the student").show();
        }
        Teacher teacher=new Teacher(Integer.valueOf(txtId.getText()),txtName.getText(),txtAddress.getText());

    }
    private String generateNewId() {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Teacher ORDER BY id DESC LIMIT 1");
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

    @FXML
    void btnNewTeacherOnAction(ActionEvent event) {
        txtId.setText("Generate ID");
        txtName.clear();
        txtAddress.clear();
        tblTeachers.getSelectionModel().getSelectedItems().clear();

        txtName.requestFocus();





    }

}
