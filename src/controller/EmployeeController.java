package controller;

import db.DBConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import util.EmployeeTM;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Optional;

public class EmployeeController {


    public AnchorPane root;
    public TextField txtid;
    public TextField txtname;
    public Button btnSave;
    public Button btnRemove;
    public TextField txtsalary;
    public TableView<EmployeeTM> tblEmployee;
    public TextField txtEtf;
    private String id;

    public void initialize() {

        tblEmployee.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblEmployee.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblEmployee.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("salary"));
        tblEmployee.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("etf"));

        txtname.requestFocus();
        setDefault();
        loadTable();
        generateID();
        btnSave.setText("Save");


        tblEmployee.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EmployeeTM>() {
            @Override
            public void changed(ObservableValue<? extends EmployeeTM> observable, EmployeeTM oldValue, EmployeeTM newValue) {
                if (tblEmployee.getSelectionModel().getSelectedItem() == null) {
                    return;
                }

                Connection connection = DBConnection.getInstance().getConnection();
                EmployeeTM selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
                btnSave.setText("Update");

                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("select * from employee where id = ?");
                    preparedStatement.setObject(1, selectedItem.getId());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    txtid.setText(resultSet.getString(1));
                    txtname.setText(resultSet.getString(2));

                    preparedStatement = connection.prepareStatement("select * from salary where id = ?");
                    preparedStatement.setObject(1, selectedItem.getId());
                    resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    txtsalary.setText(resultSet.getString(2));

                    preparedStatement = connection.prepareStatement("select * from etf where id = ?");
                    preparedStatement.setObject(1, selectedItem.getId());
                    resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    txtEtf.setText(resultSet.getString(2));

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

    }


    public void generateID() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Employee ORDER BY id DESC LIMIT 1");
            String newEmployeeId = "E001";

            if (rst.next()) {
                String lastEmployeeId = rst.getString(1);
                newEmployeeId = String.format("E%03d", (Integer.parseInt(lastEmployeeId.substring(1)) + 1));
            }
            txtid.setText(newEmployeeId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (!txtname.getText().matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid employee name", ButtonType.OK).show();
            txtname.requestFocus();
            return;
        } else if (!txtsalary.getText().matches("[0-9]+[.]?[0-9]*")) {
            new Alert(Alert.AlertType.ERROR, "Invalid employee salary", ButtonType.OK).show();
            txtsalary.requestFocus();
            return;
        } else {
            String id = txtid.getText();
            String name = txtname.getText();
            BigDecimal salary = new BigDecimal(txtsalary.getText());
            BigDecimal etf = new BigDecimal(txtEtf.getText());
            Connection connection = DBConnection.getInstance().getConnection();


            if (btnSave.getText().equals("Save")) {

                try {
                    connection.setAutoCommit(false);

                    PreparedStatement preparedStatement = connection.prepareStatement("Insert into employee values(?,?)");
                    preparedStatement.setObject(1, id);
                    preparedStatement.setObject(2, name);
                    int affectedrows = preparedStatement.executeUpdate();

                    if (affectedrows == 0) {
                        connection.rollback();
                        return;
                    }

                    preparedStatement = connection.prepareStatement("Insert into salary values(?,?)");
                    preparedStatement.setObject(1, id);
                    preparedStatement.setObject(2, salary);
                    affectedrows = preparedStatement.executeUpdate();

                    if (affectedrows == 0) {
                        connection.rollback();
                        return;
                    }


                    preparedStatement = connection.prepareStatement("Insert into etf values(?,?)");
                    preparedStatement.setObject(1, id);
                    preparedStatement.setObject(2, etf);
                    affectedrows = preparedStatement.executeUpdate();

                    if (affectedrows == 0) {
                        connection.rollback();
                        return;
                    }

                    connection.commit();
                    loadTable();
                    btnSave.setText("Save");
                    setDefault();

                } catch (Throwable ex) {
                    ex.printStackTrace();
                    try {
                        connection.rollback();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } finally {
                    try {
                        connection.setAutoCommit(true);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

            } else {
                try {

                    connection.setAutoCommit(false);

                    PreparedStatement pstm = connection.prepareStatement("update employee set name = ? where id = ?");
                    pstm.setObject(1, name);
                    pstm.setObject(2, id);
                    int affectedrow = pstm.executeUpdate();

                    if (affectedrow == 0) {
                        connection.rollback();
                        return;
                    }

                    pstm = connection.prepareStatement("update salary set salary = ? where id = ?");
                    pstm.setObject(1, salary);
                    pstm.setObject(2, id);
                    affectedrow = pstm.executeUpdate();

                    if (affectedrow == 0) {
                        connection.rollback();
                        return;
                    }

                    pstm = connection.prepareStatement("update etf set etf = ? where id = ?");
                    pstm.setObject(1, etf);
                    pstm.setObject(2, id);
                    affectedrow = pstm.executeUpdate();

                    if (affectedrow == 0) {
                        connection.rollback();
                        return;
                    }

                    connection.commit();
                    loadTable();
                    btnSave.setText("Save");
                    setDefault();
                } catch (Throwable ex) {
                    ex.printStackTrace();
                    try {
                        connection.rollback();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } finally {
                    try {
                        connection.setAutoCommit(true);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

            }
        }
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to Remove this Member!!!", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().equals(ButtonType.YES)) {
            Connection connection = DBConnection.getInstance().getConnection();
            String eid = txtid.getText();
            try {
                connection.setAutoCommit(false);
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("delete from etf where id = ?");
                    preparedStatement.setObject(1, eid);
                    int affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows == 0) {
                        connection.rollback();
                        return;
                    }

                    preparedStatement = connection.prepareStatement("delete from salary where id = ?");
                    preparedStatement.setObject(1, eid);
                    affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows == 0) {
                        connection.rollback();
                        return;
                    }

                    preparedStatement = connection.prepareStatement("delete from employee where id = ?");
                    preparedStatement.setObject(1, eid);
                    affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows == 0) {
                        connection.rollback();
                        return;
                    }

                    connection.commit();
                    loadTable();
                    btnSave.setText("Save");
                    setDefault();


                } catch (Throwable ex) {
                    ex.printStackTrace();
                    try {
                        connection.rollback();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } finally {
                    try {
                        connection.setAutoCommit(true);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public void setDefault() {
        generateID();
        txtname.setText("");
        txtsalary.setText("");
        txtEtf.setText("");
    }

    public void txtsalaryOnAction(ActionEvent actionEvent) {
        double salary = Double.parseDouble(txtsalary.getText().trim());
        txtEtf.setText((salary * 0.1) + "");
    }


    private void loadTable() {
        ObservableList<EmployeeTM> userTable = tblEmployee.getItems();
        userTable.clear();

        Connection connection = DBConnection.getInstance().getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT employee.id,employee.name,salary.salary,etf.etf FROM employee INNER JOIN salary ON employee.id = salary.id INNER JOIN etf on employee.id = etf.id");
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                BigDecimal salary = resultSet.getBigDecimal(3);
                BigDecimal etf = resultSet.getBigDecimal(4);
                Button button = new Button("Delete");
                button.setStyle("-fx-background-color:#d12449");
                button.setMaxWidth(180);
                userTable.add(new EmployeeTM(id, name, salary, etf));
            }
            tblEmployee.refresh();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
