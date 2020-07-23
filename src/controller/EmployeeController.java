package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import util.EmployeeTM;

public class EmployeeController {


    public AnchorPane root;
    public TextField txtid;
    public TextField txtname;
    public Button btnSave;
    public Button btnRemove;
    public TextField txtsalary;
    public TableView <EmployeeTM> tblEmployee;
    public TextField txtEtf;
    public Button btnAdd;

    public void initialize(){

        tblEmployee.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblEmployee.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblEmployee.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("salary"));
        tblEmployee.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("etf"));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {
    }

    public void txtsalaryOnAction(ActionEvent actionEvent) {
        int salary = Integer.parseInt(txtsalary.getText().trim());
        txtEtf.setText((salary*0.1)+"");
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
    }
}
