package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.Tm.CartTm;
import lk.ijse.model.Tm.OrderProductDetails;
import lk.ijse.model.Tm.Orders;
import lk.ijse.model.Tm.PlaceOrderTm;
import lk.ijse.model.Customer;
import lk.ijse.model.Product;
import lk.ijse.repository.CustomerRepo;
import lk.ijse.repository.OrdersRepo;
import lk.ijse.repository.ProductRepo;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlaceOrderFormController {

    @FXML
    private Button btnAddCart;

    @FXML
    private Button btnPlaceOrder;

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
    private Label lblCustomerId;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblProductName;

    @FXML
    private Label lblQuantityOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Label lblWeight;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<CartTm> tblOrderCart;

    @FXML
    private TextField txtQuantity;

    private ObservableList<CartTm> obList = FXCollections.observableArrayList();


    public void initialize(){
        setDate();
        getCurrentOrderId();
        getCustomerIds();
        getProductIds();
        setCellValueFactory();

    }

    private void setCellValueFactory() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("ProductId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        colWeight.setCellValueFactory(new PropertyValueFactory<>("Weight"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private void getProductIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> idList = ProductRepo.getProductId();

            for (String id : idList) {
                obList.add(id);
            }
            cmbProductId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void getCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = CustomerRepo.getIds();

            for(String id : idList) {
                obList.add(id);
            }

            cmbCustomerId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void getCurrentOrderId() {
        try {
            String currentId = OrdersRepo.getCurrentId();

            String nextOrderId = generateNextOrderId(currentId);
            lblOrderId.setText(nextOrderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextOrderId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("O");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "O" + ++idNum;
        }
        return "O1";

    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblOrderDate.setText(String.valueOf(now));
    }

    @FXML
    void btnAddCartOnAction(ActionEvent event) {
        String ProductId = cmbProductId.getValue();
        String description = lblDescription.getText();
        int qty = Integer.parseInt(txtQuantity.getText());
        int qtyOnHand = Integer.parseInt(lblQuantityOnHand.getText());
        double weight = Double.parseDouble((lblWeight.getText()));
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double total = qty * unitPrice;
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblOrderCart.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);

                tblOrderCart.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            if(ProductId.equals(colProductId.getCellData(i))) {

                CartTm tm = obList.get(i);
                qty += tm.getQuantity();
                total = qty * unitPrice;

                tm.setQuantity(qty);
                tm.setTotal(total);

                tblOrderCart.refresh();

                calculateNetTotal();
                return;
            }
        }

        CartTm tm = new CartTm(ProductId, description, qty, qtyOnHand,weight, unitPrice, total, btnRemove);
        obList.add(tm);

        tblOrderCart.setItems(obList);
        calculateNetTotal();
        txtQuantity.setText("");

    }

    private void calculateNetTotal() {
        int netTotal = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String OrderId = lblOrderId.getText();
        String CustomerId = (String) cmbCustomerId.getValue();
        Date OrderDate = Date.valueOf(LocalDate.now());

        var Order = new Orders(OrderId, CustomerId, OrderDate);

        List<OrderProductDetails> odList = new ArrayList<>();

        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            CartTm tm = obList.get(i);

            OrderProductDetails od = new OrderProductDetails(
                    OrderId,
                    tm.getProductId(),
                    tm.getQuantity(),
                    tm.getUnitPrice()
            );

            odList.add(od);
        }

        PlaceOrderTm po = new PlaceOrderTm(Order, odList);

    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) {

        String CustomerId = (String) cmbCustomerId.getValue();
        try {
            Customer customer = CustomerRepo.searchById(CustomerId);

            lblCustomerId.setText(customer.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void cmbProductOnAction(ActionEvent event) {
        String ProductId = (String) cmbProductId.getValue();
        try {
            Product product = ProductRepo.searchById(ProductId);

            lblProductName.setText(product.getProductName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void txtQuantityOnAction(ActionEvent event) {
        btnAddCartOnAction(event);

    }

}
