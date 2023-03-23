package lk.ijse.dep10.app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep10.app.model.Teacher;

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

    }

    @FXML
    void btnNewTeacherOnAction(ActionEvent event) {


    }

}
