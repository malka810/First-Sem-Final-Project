package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.dto.EmployeeDTO;
import lk.ijse.view.tdm.EmployeeTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtDepartment;

    @FXML
    private TextField txtE_Name;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtRole;

    @FXML
    private JFXButton btnAddNewEmployee;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    EmployeeBO employeeBO  = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Employee);

    public void initialize() {
//        setCellValueFactory();
//        loadAllEmployee();
        tblEmployee.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        tblEmployee.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("e_name"));
        tblEmployee.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("department"));
        tblEmployee.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("role"));

        initUI();

        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtEmployeeId.setText(newValue.getEmployee_id());
                txtE_Name.setText(newValue.getE_name());
                txtDepartment.setText(newValue.getDepartment());
                txtRole.setText(newValue.getRole());

                txtEmployeeId.setDisable(false);
                txtE_Name.setDisable(false);
                txtDepartment.setDisable(false);
                txtRole.setDisable(false);
            }
        });

        txtRole.setOnAction(event -> btnSave.fire());
        loadAllEmployee();

    }

    private void initUI() {
        txtEmployeeId.clear();
        txtE_Name.clear();
        txtDepartment.clear();
        txtRole.clear();
        txtEmployeeId.setDisable(true);
        txtE_Name.setDisable(true);
        txtDepartment.setDisable(true);
        txtRole.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void loadAllEmployee() throws RuntimeException{
        tblEmployee.getItems().clear();
        try {
            /*Get all customers*/
            ArrayList<EmployeeDTO> allEmployee = employeeBO.getAllEmployee();

            for (EmployeeDTO e : allEmployee) {
                tblEmployee.getItems().add(new EmployeeTm(e.getEmployee_id(), e.getE_name(), e.getDepartment(),e.getRole()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

//    private void setCellValueFactory() {
//        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("EmployeeId"));
//        colE_Name.setCellValueFactory(new PropertyValueFactory<>("E_Name"));
//        colDepartment.setCellValueFactory(new PropertyValueFactory<>("Department"));
//        colRole.setCellValueFactory(new PropertyValueFactory<>("Role"));
//    }
//
//
//    @FXML
//    void btnClearOnAction(ActionEvent event) {
//        clearFields();
//
//    }

//    private void clearFields() {
//        txtEmployeeId.setText("");
//        txtE_Name.setText("");
//        txtDepartment.setText("");
//        txtRole.setText("");
//    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        String id = tblEmployee.getSelectionModel().getSelectedItem().getEmployee_id();
        try {
            if (!existEmployee(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such employee associated with the id " + id).show();
            }

            employeeBO.deleteEmployee(id);

            tblEmployee.getItems().remove(tblEmployee.getSelectionModel().getSelectedItem());
            tblEmployee.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the employee " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private boolean existEmployee(String id) throws SQLException,ClassNotFoundException{
        return employeeBO.existEmployee(id);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();
        String name = txtE_Name.getText();
        String department = txtDepartment.getText();
        String role = txtRole.getText();

        if (!name.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtE_Name.requestFocus();
            return;
        } else if (!department.matches(".{3,}")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
            txtDepartment.requestFocus();
            return;
        }
        try {
            if (existEmployee(id)) {
                new Alert(Alert.AlertType.ERROR, id + " already exists").show();
            }
            employeeBO.addEmployee(new EmployeeDTO(id,name,department,role));

            tblEmployee.getItems().add(new EmployeeTm(id, name, department,role));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the employee " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        EmployeeTm selectedEmployee = tblEmployee.getSelectionModel().getSelectedItem();
        selectedEmployee.setE_name(name);
        selectedEmployee.setDepartment(department);
        tblEmployee.refresh();

        btnAddNewEmployee.fire();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();
        String name = txtE_Name.getText();
        String department = txtDepartment.getText();
        String role = txtRole.getText();

        try {
            if (!existEmployee(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such employee associated with the id " + id).show();
            }
            employeeBO.updateEmployee(new EmployeeDTO(id,name,department,role));

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update the employee " + id + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        EmployeeTm selectedEmployee = tblEmployee.getSelectionModel().getSelectedItem();
        selectedEmployee.setE_name(name);
        selectedEmployee.setDepartment(department);
        tblEmployee.refresh();

    }

//    @FXML
//    void txtSearchOnAction(ActionEvent event) throws SQLException {
//        String EmployeeId = txtSearchId.getText();
//
//        Employee employee = EmployeeRepo.searchById(EmployeeId);
//        if (employee != null) {
//            txtEmployeeId.setText(employee.getEmployeeId());
//            txtE_Name.setText(employee.getE_Name());
//            txtDepartment.setText(employee.getDepartment());
//            txtRole.setText(employee.getRole());
//        } else {
//            new Alert(Alert.AlertType.INFORMATION, "employee not found!").show();
//        }
//
//    }
    @FXML
    void btnAddNewEmployeeOnAction(ActionEvent event) {
        txtEmployeeId.setDisable(false);
        txtE_Name.setDisable(false);
        txtDepartment.setDisable(false);
        txtRole.setDisable(false);
        txtEmployeeId.clear();
        txtE_Name.setText(generateNewId());
        txtDepartment.clear();
        txtRole.clear();
        txtE_Name.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblEmployee.getSelectionModel().clearSelection();

    }

    private String generateNewId() {
        try {
            //Generate New ID
            return employeeBO.generateNewEmployeeID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblEmployee.getItems().isEmpty()) {
            return "E00-001";
        } else {
            String id = getLastEmployeeId();
            int newEmployeeId = Integer.parseInt(id.replace("E", "")) + 1;
            return String.format("E00-%03d", newEmployeeId);
        }
    }

    private String getLastEmployeeId() {
        List<EmployeeTm> tempEmployeeList = new ArrayList<>(tblEmployee.getItems());
        Collections.sort(tempEmployeeList);
        return tempEmployeeList.get(tempEmployeeList.size() - 1).getEmployee_id();
    }


}
