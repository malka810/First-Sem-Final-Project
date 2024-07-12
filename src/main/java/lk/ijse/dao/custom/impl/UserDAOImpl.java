package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.Product;
import lk.ijse.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    @Override
    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<User> allUsers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM User");
        while (rst.next()) {
            allUsers.add(new User(rst.getString("user_id"),rst.getString("username"), rst.getString("password")));
        }
        return allUsers;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean add(User entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO User (user_id,username, password) VALUES (?,?,?)", entity.getUser_id(), entity.getUsername(), entity.getPassword());
    }

    @Override
    public boolean update(User entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT user_id FROM User ORDER BY user_id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("user_id");
            int newUserId = Integer.parseInt(id.replace("U00-", "")) + 1;
            return String.format("U00-%03d", newUserId);
        } else {
            return "U00-001";
        }
    }

    @Override
    public User search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public User checkPassword(String username, String password) throws SQLException, ClassNotFoundException {
        ResultSet rst= SQLUtil.execute("SELECT user_id,username, password FROM users WHERE userid = ?", username);
        rst.next();
        return new User(rst.getString("user_id"), rst.getString("username"),rst.getString("password"));
    }
}
