package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class EmployeeController {


    public AnchorPane root;
    public TextField txtid;
    public TextField txtname;
    public Button btnSave;
    public Button btnRemove;
    public TextField txtsalary;
    public TableView tblEmployee;
    public TextField txtEtf;


    public void btnSaveOnAction(ActionEvent actionEvent) {
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {
    }

    public void txtsalaryOnAction(ActionEvent actionEvent) {
        int salary = Integer.parseInt(txtsalary.getText().trim());
        txtEtf.setText((salary*0.1)+"");
    }
}
