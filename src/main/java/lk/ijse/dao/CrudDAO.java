package lk.ijse.dao;

import lk.ijse.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {
    public ArrayList<T> getAll() throws SQLException,ClassNotFoundException;

    boolean exist(String id) throws SQLException,ClassNotFoundException;

    boolean delete(String id) throws SQLException,ClassNotFoundException;

    public boolean add(T entity) throws SQLException,ClassNotFoundException;

    boolean update(T entity) throws SQLException,ClassNotFoundException;

    String generateNewID() throws SQLException,ClassNotFoundException;

    public T search(String id ) throws SQLException,ClassNotFoundException;
}
