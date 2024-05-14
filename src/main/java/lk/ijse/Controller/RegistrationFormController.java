package lk.ijse.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationFormController {

    @FXML
    private Button btnRegister;

    @FXML
    private TextField txtPassWord;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtuserID;

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        String userid = txtuserID.getText();
        String username = txtUserName.getText();
        String pw = txtPassWord.getText();

        try {
            boolean isSaved = saveUser(userid, username, pw);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "user saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private boolean saveUser(String userid, String username, String pw) throws SQLException {
        String sql = "INSERT INTO User VALUES(?, ?, ?)";
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userid);
        pstm.setObject(2, username);
        pstm.setObject(3, pw);

        return pstm.executeUpdate() > 0;
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
