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
import lk.ijse.model.Tm.SupplierTm;
import lk.ijse.model.Customer;
import lk.ijse.model.Supplier;
import lk.ijse.repository.CustomerRepo;
import lk.ijse.repository.SupplierRepo;

import java.sql.SQLException;
import java.util.List;

public class SupplierFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableView<Supplier> tblSupplier;

    @FXML
    private TextField txtSupAddress;

    @FXML
    private TextField txtSupId;

    @FXML
    private TextField txtSupName;

    @FXML
    private TextField txtSupTel;

    public void initialize() {
        setCellValueFactory();
        loadAllSupplier();
    }

    private void loadAllSupplier() {
        ObservableList<Supplier> obList = FXCollections.observableArrayList();

        try {
            List<Supplier> supplierList = SupplierRepo.getAll();
            for (Supplier supplier : supplierList) {
                SupplierTm supplierTm = new SupplierTm(
                        supplier.getSupplierId(),
                        supplier.getName(),
                        supplier.getAddress(),
                        supplier.getContact()
                );

                obList.add(supplierTm);
            }

            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("Contact"));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    private void clearFields() {
        txtSupId.setText("");
        txtSupName.setText("");
        txtSupAddress.setText("");
        txtSupTel.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String SupplierId = txtSupId.getText();

        try {
            boolean isDeleted = CustomerRepo.delete(SupplierId);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String SupplierId = txtSupId.getText();
        String Name = txtSupName.getText();
        String Address = txtSupAddress.getText();
        String Contact = txtSupTel.getText();

        Supplier supplier = new Supplier(SupplierId, Name, Address, Contact);

        try {
            boolean isSaved = SupplierRepo.save(supplier);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String SupplierId = txtSupId.getText();
        String Name = txtSupName.getText();
        String Address = txtSupAddress.getText();
        String Contact = txtSupTel.getText();

        Customer customer = new Customer(SupplierId, Name, Address, Contact);

        try {
            boolean isUpdated = CustomerRepo.update(customer);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException {
        String SupplierId = txtSupId.getText();

        Supplier supplier = SupplierRepo.searchById(SupplierId);
        if (supplier != null) {
            txtSupId.setText(supplier.getSupplierId());
            txtSupName.setText(supplier.getName());
            txtSupAddress.setText(supplier.getAddress());
            txtSupTel.setText(supplier.getContact());
        } else {
            new Alert(Alert.AlertType.INFORMATION, "supplier not found!").show();
        }

    }




    }


