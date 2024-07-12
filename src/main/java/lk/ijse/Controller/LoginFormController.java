package lk.ijse.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.entity.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private PasswordField txtPassWord;

    @FXML
    private TextField txtUserName;

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.User);

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        String username = txtUserName.getText();
        String password = txtPassWord.getText();

        try {
            checkCredential(username, password);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        txtUserName.clear();
        txtPassWord.clear();

    }

    private void checkCredential(String username, String password) throws SQLException,ClassNotFoundException,IOException {
        if(username.isEmpty() && password.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION,"Empty fields Try again!").show();
            return;
        }
        if(username.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION,"User is Empty!").show();
            return;
        }
        if(password.isEmpty()){
            new Alert(Alert.AlertType.INFORMATION,"Password is empty!").show();
        }
        User userDTO=userDAO.checkPassword(username,password);
        if(userDTO==null){
            new Alert(Alert.AlertType.INFORMATION,"Sorry userId can't be find").show();
            return;
        }
        if(!userDTO.getPassword().equals(password)){
            new Alert(Alert.AlertType.ERROR,"Sorry! wrong password").show();
            // System.out.println("Sorry");
            return;
        }
        navigateToTheDashboard();
    }


    private void navigateToTheDashboard() {
        try {
            AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/Main_Form.fxml"));
            Scene scene = new Scene(rootNode);

            Stage stage = (Stage) this.rootNode.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("Dashboard Form");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void linkRegisterOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/RegisterForm.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();

        stage.setTitle("Registration Form");

        stage.show();

    }

}
