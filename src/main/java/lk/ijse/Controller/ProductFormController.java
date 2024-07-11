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
import lk.ijse.bo.custom.ProductBO;
import lk.ijse.dto.ProductDTO;
import lk.ijse.view.tdm.ProductTm;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductFormController {

    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colProductId;

    @FXML
    private TableColumn<?, ?> colProductName;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colWeight;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<ProductTm> tblProduct;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtProductId;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtWeight;
    @FXML
    private JFXButton btnAddNewProduct;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    ProductBO productBO = (ProductBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.Product);

    public void initialize() {
        tblProduct.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("product_id"));
        tblProduct.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("product_name"));
        tblProduct.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblProduct.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("category"));
        tblProduct.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty_on_hand"));
        tblProduct.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("weight"));
        tblProduct.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        initUI();

        tblProduct.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtProductId.setText(newValue.getProduct_id());
                txtProductName.setText(newValue.getProduct_name());
                txtDescription.setText(newValue.getDescription());
                txtCategory.setText(newValue.getCategory());
                txtQtyOnHand.setText(newValue.getQty_on_hand() + "");
                txtWeight.setText(newValue.getWeight().setScale(2).toString());
                txtUnitPrice.setText(newValue.getUnit_price().setScale(2).toString());


                txtProductId.setDisable(false);
                txtProductName.setDisable(false);
                txtDescription.setDisable(false);
                txtCategory.setDisable(false);
                txtQtyOnHand.setDisable(false);
                txtUnitPrice.setDisable(false);
                txtWeight.setDisable(false);
            }
        });

        txtWeight.setOnAction(event -> btnSave.fire());
        loadAllProduct();


    }

    private void initUI() {
        txtProductId.clear();
        txtProductName.clear();
        txtDescription.clear();
        txtCategory.clear();
        txtQtyOnHand.clear();
        txtUnitPrice.clear();
        txtWeight.clear();
        txtProductId.setDisable(true);
        txtProductName.setDisable(true);
        txtDescription.setDisable(true);
        txtCategory.setDisable(true);
        txtQtyOnHand.setDisable(true);
        txtUnitPrice.setDisable(true);
        txtWeight.setDisable(true);
        txtProductId.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void loadAllProduct() throws RuntimeException{
        tblProduct.getItems().clear();
        try {
            /*Get all items*/
            ArrayList<ProductDTO> allProducts = productBO.getAllProducts();
            for (ProductDTO p : allProducts) {
                tblProduct.getItems().add(new ProductTm(p.getProduct_id(),p.getProduct_name(), p.getDescription(),p.getCategory(),p.getQty_on_hand(), p.getUnit_price(), p.getWeight()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = tblProduct.getSelectionModel().getSelectedItem().getProduct_id();
        try {
            if (!existProduct(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such employee associated with the id " + id).show();
            }

            productBO.deleteProduct(id);

            tblProduct.getItems().remove(tblProduct.getSelectionModel().getSelectedItem());
            tblProduct.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the employee " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private boolean existProduct(String id) throws SQLException,ClassNotFoundException {
        return productBO.existProduct(id);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event){
        String id = txtProductId.getText();
        String productName = txtProductName.getText();
        String description = txtDescription.getText();
        String category = txtCategory.getText();

        if (!description.matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid description").show();
            txtDescription.requestFocus();
            return;
        } else if (!txtUnitPrice.getText().matches("^[0-9]+[.]?[0-9]*$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid unit price").show();
            txtUnitPrice.requestFocus();
            return;
        } else if (!txtQtyOnHand.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty on hand").show();
            txtQtyOnHand.requestFocus();
            return;
        }else if (!txtWeight.getText().matches("\\d+(\\.\\d+)?\\s*")) {
            new Alert(Alert.AlertType.ERROR, "Invalid weight").show();
            txtWeight.requestFocus();
            return;
        }
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
        BigDecimal weight = new BigDecimal(txtWeight.getText()).setScale(2);

            try {
                if (existProduct(id)) {
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                }
                //Save Item
                productBO.saveProduct(new ProductDTO(id,productName, description,category, qtyOnHand, unitPrice,weight));

                tblProduct.getItems().add(new ProductTm(id,productName, description,category, qtyOnHand, unitPrice,weight));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();

        }

        btnAddNewProduct.fire();

    }
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtProductId.getText();
        String productName = txtProductName.getText();
        String description = txtDescription.getText();
        String category = txtCategory.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
        BigDecimal weight = new BigDecimal(txtWeight.getText()).setScale(2);

        try {
            if (!existProduct(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such employee associated with the id " + id).show();
            }
            productBO.updateProduct(new ProductDTO(id,productName, description,category, qtyOnHand, unitPrice,weight));

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update the employee " + id + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

//    @FXML
//    void txtSearchOnAction(ActionEvent event) throws SQLException {
//        String ProductId = txtProductId.getText();
//
//        Product product = ProductRepo.searchById(ProductId);
//        if (product != null) {
//            txtProductId.setText(product.getProductId());
//            txtProductName.setText(product.getProductName());
//            txtDescription.setText(product.getDescription());
//            txtCategory.setText(product.getCategory());
//            txtQtyOnHand.setText(String.valueOf(product.getQuantityOnHand()));
//            txtWeight.setText(String.valueOf(product.getWeight()));
//            txtUnitPrice.setText(String.valueOf(product.getUnitPrice()));
//        } else {
//            new Alert(Alert.AlertType.INFORMATION, "Product not found!").show();
//        }
//
//    }
    @FXML
    void btnAddNewProductOnAction(ActionEvent event) {
        txtProductId.setDisable(false);
        txtProductName.setDisable(false);
        txtDescription.setDisable(false);
        txtCategory.setDisable(false);
        txtUnitPrice.setDisable(false);
        txtQtyOnHand.setDisable(false);
        txtWeight.setDisable(false);
        txtProductId.clear();
        txtProductId.setText(generateNewId());
        txtDescription.clear();
        txtCategory.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtWeight.clear();
        txtDescription.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblProduct.getSelectionModel().clearSelection();

    }

    private String generateNewId() {
        try {
            return productBO.generateNewId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "P00-001";
    }
    }




