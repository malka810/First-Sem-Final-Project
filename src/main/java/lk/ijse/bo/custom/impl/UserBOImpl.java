package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.UserBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.User);
//    @Override
//    public boolean addUser(UserDTO dto) throws SQLException, ClassNotFoundException, IOException {
//        return userDAO.add(new User(dto.getUser_id(),dto.getUsername(),dto.getPassword()));
//    }

    @Override
    public String generateNewUserID() throws SQLException, ClassNotFoundException, IOException {
        return userDAO.generateNewID();
    }

    @Override
    public List<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException, IOException {
        List<UserDTO> allUsers= new ArrayList<>();
        List<User> all = userDAO.getAll();
        for (User user : all) {
            allUsers.add(new UserDTO(user.getUser_id(), user.getUsername(), user.getPassword()));
        }
        return allUsers;
    }

    @Override
    public boolean saveUser(UserDTO dto) throws SQLException, ClassNotFoundException, IOException {
        return userDAO.add(new User(dto.getUser_id(),dto.getUsername(),dto.getPassword()));
    }
}
