package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.User;

import java.io.IOException;
import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    User checkPassword(String username, String password) throws IOException, SQLException, ClassNotFoundException;
}
