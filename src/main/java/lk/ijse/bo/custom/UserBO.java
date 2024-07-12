package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.UserDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface UserBO extends SuperBO {

   // boolean addUser(UserDTO userDTO) throws SQLException,ClassNotFoundException, IOException;

    String generateNewUserID() throws SQLException,ClassNotFoundException, IOException;

    List<UserDTO> getAllUsers() throws SQLException,ClassNotFoundException,IOException;

    boolean saveUser(UserDTO userDTO) throws SQLException,ClassNotFoundException,IOException;
}
