package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.bo.custom.impl.UserBOImpl;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RegistrationFormController {
    @FXML
    public PasswordField txtpassword;
    @FXML
    public AnchorPane rootnode;

    @FXML
    private Button btnRegister;
    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtuserID;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.User);

    @FXML
    void btnRegisterOnAction(ActionEvent event) throws  ClassNotFoundException, IOException {

        String id = txtuserID.getText();
        String username = txtUserName.getText();
        String password = txtpassword.getText();

        try {
            boolean isSaved = userBO.saveUser(new UserDTO(id, username, password));
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "user saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        clearTextFilds();
        generateNextUserId();


    }


    private void generateNextUserId() throws ClassNotFoundException {
        try {
            String nextId = userBO.generateNewUserID();
            txtuserID.setText(nextId);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void clearTextFilds() {
        txtuserID.clear();
        txtUserName.clear();
        txtpassword.clear();
    }


    @FXML
    void linkLoginOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/LoginForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Login Form");

        stage.show();


    }

}
