package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import lk.ijse.model.Tm.CustomerTm;
import lk.ijse.model.Customer;
import lk.ijse.repository.CustomerRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {

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
    private TableView<Customer> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtContact;

    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
    }

    private void loadAllCustomers() throws RuntimeException {
        ObservableList<Customer> obList = FXCollections.observableArrayList();

        try {
            List<Customer> customerList = CustomerRepo.getAll();
            for (Customer customer : customerList) {
                CustomerTm customerTm = new CustomerTm(
                        customer.getCustomerId(),
                        customer.getName(),
                        customer.getAddress(),
                        customer.getContact()
                );

                obList.add(customerTm);
            }

            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/Main_Form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();


    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    private void clearFields() {
        txtCustomerId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String CustomerId = txtCustomerId.getText();

        try {
            boolean isDeleted = CustomerRepo.delete(CustomerId);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String CustomerId = txtCustomerId.getText();
        String Name = txtName.getText();
        String Address = txtAddress.getText();
        String Contact = txtContact.getText();

        Customer customer = new Customer(CustomerId, Name, Address, Contact);

        try {
            boolean isSaved = CustomerRepo.save(customer);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String CustomerId = txtCustomerId.getText();
        String Name = txtName.getText();
        String Address = txtAddress.getText();
        String Contact = txtContact.getText();

        Customer customer = new Customer(CustomerId, Name, Address, Contact);

        try {
            boolean isUpdated = CustomerRepo.update(customer);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String CustomerId = txtSearchId.getText();

        Customer customer = CustomerRepo.searchById(CustomerId);
        if (customer != null) {
            txtCustomerId.setText(customer.getCustomerId());
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtContact.setText(customer.getContact());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
        }

    }

}
