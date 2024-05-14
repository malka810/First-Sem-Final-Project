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
import lk.ijse.model.Tm.ProductTm;
import lk.ijse.model.OrderProductDetails;
import lk.ijse.model.Product;
import lk.ijse.repository.ProductRepo;

import java.sql.SQLException;
import java.util.List;

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
    private TableView<Product> tblProduct;

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

    public void initialize() {
        setCellValueFactory();
        loadAllProduct();
    }

    private void loadAllProduct() throws RuntimeException{
        ObservableList<Product> obList = FXCollections.observableArrayList();

        try {
            List<Product> productList = ProductRepo.getAll();
            for (Product product : productList) {
                ProductTm productTm = new ProductTm(
                        product.getProductId(),
                        product.getProductName(),
                        product.getDescription(),
                        product.getCategory(),
                        product.getQuantityOnHand(),
                        product.getWeight(),
                        product.getUnitPrice()
                );

                obList.add(productTm);
            }

            tblProduct.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("ProductId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("QuantityOnHand"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("Weight"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        ClearFields();

    }

    private void ClearFields() {
        txtProductId.setText("");
        txtProductName.setText("");
        txtDescription.setText("");
        txtCategory.setText("");
        txtWeight.setText("");
        txtQtyOnHand.setText("");
        txtUnitPrice.setText("");

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String ProductId = txtProductId.getText();

        try {
            boolean isDeleted = ProductRepo.delete(ProductId);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "product deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event){
        String ProductId= txtProductId.getText();
        String ProductName = txtProductName.getText();
        String Description = txtDescription.getText();
        String Category = txtCategory.getText();
        Integer QtyOnHand = Integer.valueOf(txtQtyOnHand.getText());
        Double Weight = Double.valueOf(txtWeight.getText());
        Double UnitPrice = Double.valueOf(txtUnitPrice.getText());


        Product product = new Product(ProductId, ProductName, Description, Category, QtyOnHand,Weight,UnitPrice);

        try {
            boolean isSaved = ProductRepo.save(product);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "product saved!").show();
                ClearFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String ProductId = txtProductId.getText();
        String ProductName = txtProductName.getText();
        String Description = txtDescription.getText();
        String Category = txtCategory.getText();
        Integer QtyOnHand = Integer.valueOf(txtQtyOnHand.getText());
        Double Weight = Double.valueOf(txtWeight.getText());
        Double UnitPrice = Double.valueOf(txtUnitPrice.getText());


        Product product = new Product(ProductId, ProductName, Description, Category,QtyOnHand,Weight,UnitPrice);

        try {
            boolean isUpdated = ProductRepo.update((List<OrderProductDetails>) product);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "product updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException {
        String ProductId = txtProductId.getText();

        Product product = ProductRepo.searchById(ProductId);
        if (product != null) {
            txtProductId.setText(product.getProductId());
            txtProductName.setText(product.getProductName());
            txtDescription.setText(product.getDescription());
            txtCategory.setText(product.getCategory());
            txtQtyOnHand.setText(String.valueOf(product.getQuantityOnHand()));
            txtWeight.setText(String.valueOf(product.getWeight()));
            txtUnitPrice.setText(String.valueOf(product.getUnitPrice()));
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Product not found!").show();
        }

    }

}
