package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.dto.SupplierDTO;
import lk.ijse.view.tdm.SupplierTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TextField txtSupAddress;

    @FXML
    private TextField txtSupId;

    @FXML
    private TextField txtSupName;

    @FXML
    private TextField txtSupTel;

    @FXML
    private JFXButton btnAddNew;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    SupplierBO supplierBO  = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Supplier);
    public void initialize() {
        tblSupplier.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        tblSupplier.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblSupplier.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblSupplier.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        initUI();

        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtSupId.setText(newValue.getSupplier_id());
                txtSupName.setText(newValue.getName());
                txtSupTel.setText(newValue.getContact());
                txtSupAddress.setText(newValue.getAddress());
                txtSupId.setDisable(false);
                txtSupName.setDisable(false);
                txtSupTel.setDisable(false);
                txtSupAddress.setDisable(false);
            }
        });

        txtSupAddress.setOnAction(event -> btnSave.fire());
        loadAllSupplier();
    }

    private void loadAllSupplier() {
        tblSupplier.getItems().clear();
        try {
            /*Get all customers*/
            ArrayList<SupplierDTO> allSupplier = supplierBO.getAllSupplier();

            for (SupplierDTO c : allSupplier) {
                tblSupplier.getItems().add(new SupplierTm(c.getSupplier_id(), c.getName(),c.getContact(), c.getAddress()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void initUI() {
        txtSupId.clear();
        txtSupName.clear();
        txtSupTel.clear();
        txtSupAddress.clear();
        txtSupId.setDisable(true);
        txtSupName.setDisable(true);
        txtSupTel.setDisable(true);
        txtSupAddress.setDisable(true);
        txtSupId.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }


//    private void setCellValueFactory() {
//        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
//        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
//        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
//        colTel.setCellValueFactory(new PropertyValueFactory<>("Contact"));
//    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = tblSupplier.getSelectionModel().getSelectedItem().getSupplier_id();
        try {
            if (!existSupplier(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }

            //Delete Customer
            supplierBO.deleteSupplier(id);

            tblSupplier.getItems().remove(tblSupplier.getSelectionModel().getSelectedItem());
            tblSupplier.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private boolean existSupplier(String id) throws SQLException,ClassNotFoundException {
        return supplierBO.existSupplier(id);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtSupId.getText();
        String name = txtSupName.getText();
        String address = txtSupAddress.getText();
        String contact = txtSupTel.getText();

        if (!name.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtSupName.requestFocus();
            return;
        } else if (!address.matches(".{3,}")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
            txtSupAddress.requestFocus();
            return;
        }
        try {
            if (existSupplier(id)) {
                new Alert(Alert.AlertType.ERROR, id + " already exists").show();
            }

            //Add Customer
            supplierBO.addSupplier(new SupplierDTO(id,name,contact,address));

            tblSupplier.getItems().add(new SupplierTm(id, name, contact,address));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        SupplierTm selectedSupplier = tblSupplier.getSelectionModel().getSelectedItem();
        selectedSupplier.setName(name);
        selectedSupplier.setAddress(address);
        tblSupplier.refresh();

        btnAddNew.fire();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtSupId.getText();
        String name = txtSupName.getText();
        String address = txtSupAddress.getText();
        String contact = txtSupTel.getText();

        try {
            if (!existSupplier(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }

            //Update Customer
            supplierBO.updateSupplier(new SupplierDTO(id,name,address,contact));

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + id + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        SupplierTm selectedSupplier = tblSupplier.getSelectionModel().getSelectedItem();
        selectedSupplier.setName(name);
        selectedSupplier.setAddress(address);
        tblSupplier.refresh();
    }
//    @FXML
//    void btnSearchOnAction(ActionEvent event) throws SQLException {
//        String SupplierId = txtSupId.getText();
//
//        Supplier supplier = SupplierRepo.searchById(SupplierId);
//        if (supplier != null) {
//            txtSupId.setText(supplier.getSupplierId());
//            txtSupName.setText(supplier.getName());
//            txtSupAddress.setText(supplier.getAddress());
//            txtSupTel.setText(supplier.getContact());
//        } else {
//            new Alert(Alert.AlertType.INFORMATION, "supplier not found!").show();
//        }
//
//    }

    @FXML
    void btnAddNewOnAction(ActionEvent event) {
        txtSupId.setDisable(false);
        txtSupName.setDisable(false);
        txtSupAddress.setDisable(false);
        txtSupTel.setDisable(false);
        txtSupId.clear();
        txtSupId.setText(generateNewId());
        txtSupName.clear();
        txtSupAddress.clear();
        txtSupTel.clear();
        txtSupName.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblSupplier.getSelectionModel().clearSelection();
    }

    private String generateNewId() {
        try {
            //Generate New ID
            return supplierBO.generateNewSupplierId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblSupplier.getItems().isEmpty()) {
            return "S00-001";
        } else {
            String id = getLastSupplierId();
            int newCustomerId = Integer.parseInt(id.replace("S", "")) + 1;
            return String.format("S00-%03d", newCustomerId);
        }
    }

    private String getLastSupplierId() {
        List<SupplierTm> tempSupplierList = new ArrayList<>(tblSupplier.getItems());
        Collections.sort(tempSupplierList);
        return tempSupplierList.get(tempSupplierList.size() - 1).getSupplier_id();
    }


}


