package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.entity.Customer;
import lk.ijse.view.tdm.CustomerTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManageCustomerFormController {

    public TextField txtSearchId;
    @FXML
    private TableColumn<?, ?> colAddress;
    @FXML
    private TableColumn<?, ?> colCustomerId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colContact;
    @FXML
    private AnchorPane root;
    @FXML
    private TableView<CustomerTm> tblCustomer;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtCustomerId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtContact;

    @FXML
    private JFXButton btnAddNewCustomer;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    CustomerBO customerBO  = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Customer);

    public void initialize() {
//        setCellValueFactory();
//        loadAllCustomers();
        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        initUI();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtCustomerId.setText(newValue.getCustomer_id());
                txtName.setText(newValue.getName());
                txtContact.setText(newValue.getContact());
                txtAddress.setText(newValue.getAddress());
                txtCustomerId.setDisable(false);
                txtName.setDisable(false);
                txtContact.setDisable(false);
                txtAddress.setDisable(false);
            }
        });

        txtAddress.setOnAction(event -> btnSave.fire());
        loadAllCustomers();
    }

    private void initUI() {
        txtCustomerId.clear();
        txtName.clear();
        txtContact.clear();
        txtAddress.clear();
        txtCustomerId.setDisable(true);
        txtName.setDisable(true);
        txtAddress.setDisable(true);
        txtCustomerId.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void loadAllCustomers() {
        tblCustomer.getItems().clear();
        try {
            /*Get all customers*/
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers();

            for (CustomerDTO c : allCustomers) {
                tblCustomer.getItems().add(new CustomerTm(c.getCustomer_id(), c.getName(),c.getContact(), c.getAddress()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

//    private void setCellValueFactory() {
//        colCustomerId.setCellVaDbConnectionlueFactory(new PropertyValueFactory<>("CustomerId"));
//        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
//        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
//    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Main_Form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();


    }

//    @FXML
//    void btnClearOnAction(ActionEvent event) {
//        clearFields();
//
//    }
//
//    private void clearFields() {
//        txtCustomerId.setText("");
//        txtName.setText("");
//        txtAddress.setText("");
//        txtContact.setText("");
//    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        /*Delete Customer*/
        String id = tblCustomer.getSelectionModel().getSelectedItem().getCustomer_id();
        try {
            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }

            //Delete Customer
            customerBO.deleteCustomer(id);

            tblCustomer.getItems().remove(tblCustomer.getSelectionModel().getSelectedItem());
            tblCustomer.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private boolean existCustomer(String id) throws SQLException,ClassNotFoundException{
        return customerBO.existCustomer(id);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtCustomerId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();

        if (!name.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtName.requestFocus();
            return;
        } else if (!address.matches(".{3,}")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
            txtAddress.requestFocus();
            return;
        }
        try {
            if (existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, id + " already exists").show();
            }

            //Add Customer
            customerBO.addCustomer(new CustomerDTO(id,name,address,contact));

            tblCustomer.getItems().add(new CustomerTm(id, name, address,contact));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        CustomerTm selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
        selectedCustomer.setName(name);
        selectedCustomer.setAddress(address);
        tblCustomer.refresh();

        btnAddNewCustomer.fire();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtCustomerId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();

        try {
            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }

            //Update Customer
            customerBO.updateCustomer(new CustomerDTO(id,name,address,contact));

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + id + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        CustomerTm selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
        selectedCustomer.setName(name);
        selectedCustomer.setAddress(address);
        tblCustomer.refresh();

    }

//    @FXML
//    void txtSearchOnAction(ActionEvent event) throws SQLException {
//        String CustomerId = txtSearchId.getText();
//
//        Customer customer = CustomerRepo.searchById(CustomerId);
//        if (customer != null) {
//            txtCustomerId.setText(customer.getCustomerId());
//            txtName.setText(customer.getName());
//            txtAddress.setText(customer.getAddress());
//            txtContact.setText(customer.getContact());
//        } else {
//            new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
//        }
//
//    }
    @FXML
    void btnAddNewCustomerOnACtion(ActionEvent event) {
        txtCustomerId.setDisable(false);
        txtName.setDisable(false);
        txtAddress.setDisable(false);
        txtContact.setDisable(false);
        txtCustomerId.clear();
        txtCustomerId.setText(generateNewId());
        txtName.clear();
        txtAddress.clear();
        txtName.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblCustomer.getSelectionModel().clearSelection();

    }

    private String generateNewId() {
        try {
            //Generate New ID
            return customerBO.generateNewCustomerID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblCustomer.getItems().isEmpty()) {
            return "C00-001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        }
    }

    private String getLastCustomerId() {
        List<CustomerTm> tempCustomersList = new ArrayList<>(tblCustomer.getItems());
        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1).getCustomer_id();
    }
}
