package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.Tm.EmployeeTm;
import lk.ijse.model.Employee;
import lk.ijse.repository.EmployeeRepo;

import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {

    public TextField txtSearchId;
    @FXML
    private TableColumn<?, ?> colDepartment;

    @FXML
    private TableColumn<?, ?> colE_Name;

    @FXML
    private TableColumn<?, ?> colEmployeeId;
    @FXML
    private TableColumn<?, ?> colRole;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<Employee> tblEmployee;

    @FXML
    private TextField txtDepartment;

    @FXML
    private TextField txtE_Name;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtRole;

    public void initialize() {
        setCellValueFactory();
        loadAllEmployee();
    }

    private void loadAllEmployee() throws RuntimeException{
        ObservableList<Employee> obList = FXCollections.observableArrayList();

        try {
            List<Employee> empList = EmployeeRepo.getAll();
            for (Employee employee : empList) {
                EmployeeTm employeeTm = new EmployeeTm(
                        employee.getEmployeeId(),
                        employee.getE_Name(),
                        employee.getDepartment(),
                        employee.getRole()
                );

                obList.add(employeeTm);
            }

            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private void setCellValueFactory() {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("EmployeeId"));
        colE_Name.setCellValueFactory(new PropertyValueFactory<>("E_Name"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("Department"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("Role"));
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    private void clearFields() {
        txtEmployeeId.setText("");
        txtE_Name.setText("");
        txtDepartment.setText("");
        txtRole.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String EmployeeId= txtEmployeeId.getText();

        try {
            boolean isDeleted = EmployeeRepo.delete(EmployeeId);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee deleted!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String employeeId = txtEmployeeId.getText();
        String eName = txtE_Name.getText();
        String department   = txtDepartment.getText();
        String role = txtRole.getText();

        Employee employee = new Employee(employeeId,eName,department,role);

        try {
            boolean isSaved = EmployeeRepo.save(employee);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "employee saved!").show();
                clearFields();
                initialize();

            }

        }catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String EmployeeId = txtEmployeeId.getText();
        String Name = txtE_Name.getText();
        String Department = txtDepartment.getText();
        String Role = txtRole.getText();

        Employee employee = new Employee(EmployeeId, Name, Department, Role);

        try {
            boolean isUpdated = EmployeeRepo.update(employee);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee updated!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String EmployeeId = txtSearchId.getText();

        Employee employee = EmployeeRepo.searchById(EmployeeId);
        if (employee != null) {
            txtEmployeeId.setText(employee.getEmployeeId());
            txtE_Name.setText(employee.getE_Name());
            txtDepartment.setText(employee.getDepartment());
            txtRole.setText(employee.getRole());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "employee not found!").show();
        }

    }

}
