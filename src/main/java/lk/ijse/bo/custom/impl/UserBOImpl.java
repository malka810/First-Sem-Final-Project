package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.UserDTO;

import java.sql.SQLException;

public class UserBOImpl implements UserBO {
    @Override
    public boolean saveUser(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        return false;
    }
}
