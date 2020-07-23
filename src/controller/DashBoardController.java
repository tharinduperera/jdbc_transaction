package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardController {
    public Button btnCustomer;
    public AnchorPane rootpane;

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Employee.fxml"));
        Scene scene = new Scene(root);
        Stage mainStage = (Stage)(rootpane.getScene().getWindow());
        mainStage.setScene(scene);
        mainStage.centerOnScreen();
    }

}
