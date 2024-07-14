package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.ProductDTO;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Product;
import lk.ijse.view.tdm.CustomerTm;
import lk.ijse.view.tdm.ProductTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DashboardFormController {

    public Label lblCustomerCount;
    public Label lblEmployeeCount;
    public Label lblProductCount;
    public TableView tblCustomer;
    public TableColumn colCustomerId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableView tblProduct;
    public TableColumn colProductId;
    public TableColumn colProductName;
    public TableColumn colCategory;
    public TableColumn colQtyOnHand;
    public TableColumn colWeight;
    public TableColumn colUnitPrice;
    public TableColumn colDescription;
    @FXML
    private AnchorPane dashboardPane;
    private int customerCount;
    private int employeeCount;
    private int productCount;


    }



