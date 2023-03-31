package com.example.social_network_3.repository;

import com.example.social_network_3.domain.User;
import com.example.social_network_3.domain.validators.Validator;
import com.example.social_network_3.utils.ChangeEvent;
import com.example.social_network_3.utils.ChangeEventType;
import com.example.social_network_3.utils.Event;
import com.example.social_network_3.utils.observer.Observable;
import com.example.social_network_3.utils.observer.Observer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<Long, User>, Observable<Event> {

    private Connection connection;
    private Validator<User> validator;
    private List<Observer<Event>> observers;

    public UserRepository(Connection connection, Validator<User> validator) {
        this.connection = connection;
        this.validator = validator;
        this.observers = new ArrayList<>();
    }

    @Override
    public User findOne(Long id) {
        User user = null;
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM users WHERE id=?");
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setFirst_name(rs.getString("first_name"));
                user.setLast_name(rs.getString("last_name"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setFirst_name(rs.getString("first_name"));
                user.setLast_name(rs.getString("last_name"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    public List<User> findAllExceptYou(Long id) {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM users where not id=?");
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setFirst_name(rs.getString("first_name"));
                user.setLast_name(rs.getString("last_name"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User save(User entity) {
        User user = entity;
        validator.validate(user);
        try {
            String sql = "INSERT INTO users (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setLong(1, user.getId());
            statement.setString(1, user.getFirst_name());
            statement.setString(2, user.getLast_name());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getPassword());
            statement.executeUpdate();
            notifyObservers(new ChangeEvent(ChangeEventType.ADD, user));
            return entity;
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return user;
    }

    @Override
    public User delete(Long id) {
        User user = findOne(id);
        if (user != null) {
            try {
                String sql = "DELETE FROM users WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setLong(1, id);
                statement.executeUpdate();
                notifyObservers(new ChangeEvent(ChangeEventType.DELETE, user));
            } catch (SQLException e) {
                e.getStackTrace();
            }
        }
        return user;
    }

    public Long getNextId() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT max(id) FROM users");
            if (resultSet.next()) {
                return resultSet.getLong(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1L;
    }
    @Override
    public User update(User entity) {
        User user = entity;
        validator.validate(user);
        try {
            String sql = "UPDATE users SET first_name = ?, last_name = ?, username = ?, password = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getFirst_name());
            statement.setString(2, user.getLast_name());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getPassword());
            statement.setLong(5, user.getId());
            statement.executeUpdate();
            notifyObservers(new ChangeEvent(ChangeEventType.UPDATE, user));
            return entity;
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return user;
    }

    public User findOneByUsername(String username) {
        User user = null;
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM users WHERE username=?");
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setFirst_name(rs.getString("first_name"));
                user.setLast_name(rs.getString("last_name"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    public List<User> findAllByUsername(String username) {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM users WHERE username=?");
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setFirst_name(rs.getString("first_name"));
                user.setLast_name(rs.getString("last_name"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void addObserver(Observer<Event> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<Event> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(Event t) {
        observers.forEach(o -> o.update(t));
    }
}
