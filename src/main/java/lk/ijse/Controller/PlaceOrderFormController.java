package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.PurchaseOrderBO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.OrderDetailsDTO;
import lk.ijse.dto.OrdersDTO;
import lk.ijse.dto.ProductDTO;
import lk.ijse.view.tdm.OrderCartTm;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlaceOrderFormController {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbProductId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colProductId;

    @FXML
    private TableColumn<?, ?> colProductName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colWeight;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<OrderCartTm> tblOrderCart;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtProductDescription;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtWeight;

    private String order_id;

    PurchaseOrderBO purchaseOrderBO  = (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PO);

    public void initialize() throws SQLException, ClassNotFoundException {

        tblOrderCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("product_id"));
        tblOrderCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("product_name"));
        tblOrderCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("weight"));
        tblOrderCart.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tblOrderCart.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        tblOrderCart.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("Total"));
        TableColumn<OrderCartTm, Button> lastCol = (TableColumn<OrderCartTm, Button>) tblOrderCart.getColumns().get(5);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");

            btnDelete.setOnAction(event -> {
                tblOrderCart.getItems().remove(param.getValue());
                tblOrderCart.getSelectionModel().clearSelection();
                calculateTotal();
                enableOrDisablePlaceOrderButton();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        order_id = generateNewOrderId();
        lblOrderId.setText("Order ID: " + order_id);
        lblOrderDate.setText(LocalDate.now().toString());
        btnPlaceOrder.setDisable(true);
        txtCustomerName.setFocusTraversable(false);
        txtCustomerName.setEditable(false);
        txtProductName.setFocusTraversable(false);
        txtProductName.setEditable(false);
        txtProductDescription.setFocusTraversable(false);
        txtProductDescription.setEditable(false);
        txtUnitPrice.setFocusTraversable(false);
        txtUnitPrice.setEditable(false);
        txtWeight.setFocusTraversable(false);
        txtWeight.setEditable(false);
        txtQtyOnHand.setFocusTraversable(false);
        txtQtyOnHand.setEditable(false);
        txtQty.setOnAction(event -> btnAdd.fire());
        txtQty.setEditable(false);
        btnAdd.setDisable(true);

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisablePlaceOrderButton();
            if (newValue != null) {
                try {
                    try {
                        if (!existCustomer(newValue + "")) {
                            //"There is no such customer associated with the id " + id
                            new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + newValue + "").show();
                        }
                        //Search Customer

                        CustomerDTO customerDTO = purchaseOrderBO.searchCustomer(newValue + "");
                        txtCustomerName.setText(customerDTO.getName());

                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, "Failed to find the customer " + newValue + "" + e).show();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                txtCustomerName.clear();
            }
        });


        cmbProductId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newProductId) -> {
            txtQty.setEditable(newProductId != null);
            btnAdd.setDisable(newProductId == null);

            if (newProductId != null) {

                /*Find Item*/
                try {
                    if (!existProduct(newProductId + "")) {
//                        throw new NotFoundException("There is no such item associated with the id " + code);
                    }

                    //Search Item
                    ProductDTO product = purchaseOrderBO.searchProduct(newProductId + "");

                    txtProductDescription.setText(product.getDescription());
                    txtUnitPrice.setText(product.getUnit_price().setScale(2).toString());

//                    txtQtyOnHand.setText(tblOrderDetails.getItems().stream().filter(detail-> detail.getCode().equals(item.getCode())).<Integer>map(detail-> item.getQtyOnHand() - detail.getQty()).findFirst().orElse(item.getQtyOnHand()) + "");
                    Optional<OrderCartTm> optOrderDetail = tblOrderCart.getItems().stream().filter(detail -> detail.getProduct_id().equals(newProductId)).findFirst();
                    txtQtyOnHand.setText((optOrderDetail.isPresent() ? product.getQty_on_hand() - optOrderDetail.get().getQuantity() : product.getQty_on_hand()) + "");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                txtProductDescription.clear();
                txtQty.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
            }
        });

        tblOrderCart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {

            if (selectedOrderDetail != null) {
                cmbProductId.setDisable(true);
                cmbProductId.setValue(selectedOrderDetail.getProduct_id());
                btnAdd.setText("Update");
                txtQtyOnHand.setText(Integer.parseInt(txtQtyOnHand.getText()) + selectedOrderDetail.getQuantity() + "");
                txtQty.setText(selectedOrderDetail.getQuantity() + "");
            } else {
                btnAdd.setText("Add");
                cmbProductId.setDisable(false);
                cmbProductId.getSelectionModel().clearSelection();
                txtQty.clear();
            }

        });

        loadAllCustomerIds();
        loadAllProductId();

    }

    private void loadAllProductId() {
        try {
            /*Get all items*/
            ArrayList<ProductDTO> allItems = purchaseOrderBO.getAllProducts();
            for (ProductDTO p : allItems) {
                cmbProductId.getItems().add(p.getProduct_id());
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void loadAllCustomerIds() {
        try {
            ArrayList<CustomerDTO> allCustomers = purchaseOrderBO.getAllCustomers();
            for (CustomerDTO c : allCustomers) {
                cmbCustomerId.getItems().add(c.getCustomer_id());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String generateNewOrderId() {
        try {
            return purchaseOrderBO.generateOrderID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "OID-001";

    }

    private boolean existProduct(String id) throws SQLException,ClassNotFoundException {
        return purchaseOrderBO.existProduct(id);
    }

    private boolean existCustomer(String id) throws SQLException,ClassNotFoundException{
        return purchaseOrderBO.existCustomer(id);
    }

    private void enableOrDisablePlaceOrderButton() {
        btnPlaceOrder.setDisable(!(cmbCustomerId.getSelectionModel().getSelectedItem() != null && !tblOrderCart.getItems().isEmpty()));
    }

    private void calculateTotal() {
        BigDecimal total = new BigDecimal(0);

        for (OrderCartTm detail : tblOrderCart.getItems()) {
            total = total.add(detail.getTotal());
        }
        lblNetTotal.setText("Total: " + total);
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (!txtQty.getText().matches("\\d+") || Integer.parseInt(txtQty.getText()) <= 0 ||
                Integer.parseInt(txtQty.getText()) > Integer.parseInt(txtQtyOnHand.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
            txtQty.requestFocus();
            txtQty.selectAll();
            return;
        }

        String product_id = (String) cmbProductId.getSelectionModel().getSelectedItem();
        String product_name = txtProductName.getText();
        BigDecimal weight = new BigDecimal(txtWeight.getText()).setScale(2);
        int qty = Integer.parseInt(txtQty.getText());
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
        BigDecimal total = unitPrice.multiply(new BigDecimal(qty)).setScale(2);

        boolean exists = tblOrderCart.getItems().stream().anyMatch(detail -> detail.getProduct_id().equals(product_id));

        if (exists) {
            OrderCartTm orderDetailTM = tblOrderCart.getItems().stream().filter(detail -> detail.getProduct_id().equals(product_id)).findFirst().get();

            if (btnAdd.getText().equalsIgnoreCase("Update")) {
                orderDetailTM.setQuantity(qty);
                orderDetailTM.setTotal(total);
                tblOrderCart.getSelectionModel().clearSelection();
            } else {
                orderDetailTM.setQuantity(orderDetailTM.getQuantity() + qty);
                total = new BigDecimal(orderDetailTM.getQuantity()).multiply(unitPrice).setScale(2);
                orderDetailTM.setTotal(total);
            }
            tblOrderCart.refresh();
        } else {
            tblOrderCart.getItems().add(new OrderCartTm(product_id, product_name, weight,qty, unitPrice, total));
        }
        cmbProductId.getSelectionModel().clearSelection();
        cmbProductId.requestFocus();
        calculateTotal();
        enableOrDisablePlaceOrderButton();


    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        try {
            boolean b = saveOrder(order_id, LocalDate.now(), cmbCustomerId.getValue(),
                    tblOrderCart.getItems().stream().map(tm -> new OrderDetailsDTO(order_id, tm.getProduct_id(), tm.getQuantity(),tm.getWeight(), tm.getUnit_price())).collect(Collectors.toList()));

            if (b) {
                new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }



        order_id = generateNewOrderId();
        lblOrderId.setText("Order Id: " + order_id);
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbProductId.getSelectionModel().clearSelection();
        tblOrderCart.getItems().clear();
        txtQty.clear();
        calculateTotal();

    }

    private boolean saveOrder(String orderId, LocalDate order_date, String customerId, List<OrderDetailsDTO> orderDetails) throws SQLException, ClassNotFoundException{
        OrdersDTO orderDTO = new OrdersDTO(orderId, order_date, customerId, orderDetails);
        return purchaseOrderBO.purchaseOrder(orderDTO);
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) {

    }

    @FXML
    void cmbProductOnAction(ActionEvent event) {

    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {

    }

}
