package lk.ijse.Controller;

import com.github.plushaze.traynotification.notification.Notifications;
import com.github.plushaze.traynotification.notification.TrayNotification;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFormController {

    @FXML
    private AnchorPane CenterNode;

    @FXML
    private AnchorPane rootNode;
    @FXML
    private Button btnCustomer;

    public void initialize() throws IOException {
        loadDashboardForm();
    }



    private void loadDashboardForm() throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/Dashboard_Form.fxml"));


        CenterNode.getChildren().clear();
        CenterNode.getChildren().add(dashboardPane);
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
//        btnCustomer.setStyle("-fx-background-color: red");

        AnchorPane customerPane = FXMLLoader.load(this.getClass().getResource("/view/CustomerForm.fxml"));

        CenterNode.getChildren().clear();
        CenterNode.getChildren().add(customerPane);

    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
/*btnCustomer.setStyle("-fx-background-color: null");*/

        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/Dashboard_Form.fxml"));

        CenterNode.getChildren().clear();
        CenterNode.getChildren().add(dashboardPane);

    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        AnchorPane EmployeePane = FXMLLoader.load(this.getClass().getResource("/view/EmployeeForm.fxml"));

        CenterNode.getChildren().clear();
        CenterNode.getChildren().add(EmployeePane);

    }

    @FXML
    void btnInventoryOnAction(ActionEvent event) throws IOException {
        AnchorPane inventoryPane = FXMLLoader.load(this.getClass().getResource("/view/InventoryForm.fxml"));

        CenterNode.getChildren().clear();
        CenterNode.getChildren().add(inventoryPane);

    }

    @FXML
    void btnLogoutAction(ActionEvent event) throws IOException {
        Pane loginPane = FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));

        Scene scene = new Scene(loginPane);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();

    }

    @FXML
    void btnOrdersOnAction(ActionEvent event) throws IOException {
        AnchorPane placeOrderPane = FXMLLoader.load(this.getClass().getResource("/view/PlaceOrderForm.fxml"));

        CenterNode.getChildren().clear();
        CenterNode.getChildren().add(placeOrderPane);

    }

    @FXML
    void btnProductOnAction(ActionEvent event) throws IOException {
        AnchorPane productPane = FXMLLoader.load(this.getClass().getResource("/view/ProductForm.fxml"));

        CenterNode.getChildren().clear();
        CenterNode.getChildren().add(productPane);

    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        AnchorPane supplierPane = FXMLLoader.load(this.getClass().getResource("/view/SupplierForm.fxml"));

        CenterNode.getChildren().clear();
        CenterNode.getChildren().add(supplierPane);

    }

}
