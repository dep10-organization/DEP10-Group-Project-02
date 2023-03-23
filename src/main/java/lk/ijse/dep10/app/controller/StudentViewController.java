package lk.ijse.dep10.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.dep10.app.model.Student;

public class StudentViewController {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnNewStudent;

    @FXML
    private Button btnSave;

    @FXML
    private TableView<Student> tblStudent;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;
    public void initialize() {
        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblStudent.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStudent.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        btnDelete.setDisable(true);



    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnNewStudentOnAction(ActionEvent event) {
        txtId.setText(generateNewId());
        txtName.clear();
        txtAddress.clear();
        tblStudent.getSelectionModel().clearSelection();
        txtName.requestFocus();
    }

    private String generateNewId() {
        int size = tblStudent.getItems().size();
        if(size == 0) return "S001";
        int lastStudentNumber = Integer.parseInt(tblStudent.getItems().get(size-1).getId().substring(1));
        return String.format("S%03d",lastStudentNumber+1);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void tblStudentOnKeyReleased(KeyEvent event) {

    }

}
