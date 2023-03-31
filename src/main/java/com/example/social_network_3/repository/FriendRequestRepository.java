package com.example.social_network_3.repository;

import com.example.social_network_3.domain.FriendRequest;
import com.example.social_network_3.domain.validators.Validator;
import com.example.social_network_3.utils.ChangeEvent;
import com.example.social_network_3.utils.ChangeEventType;
import com.example.social_network_3.utils.Event;
import com.example.social_network_3.utils.observer.Observable;
import com.example.social_network_3.utils.observer.Observer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendRequestRepository implements Repository<Long, FriendRequest>, Observable<Event> {
    private Connection connection;
    private Validator<FriendRequest> validator;
    private List<Observer<Event>> observers;

    public FriendRequestRepository(Connection connection, Validator<FriendRequest> validator ) {
        this.connection = connection;
        this.validator = validator;
        this.observers = new ArrayList<>();
    }

    @Override
    public FriendRequest save(FriendRequest entity) {
        FriendRequest friendRequest = entity;
        validator.validate(friendRequest);
        try {
            String sql = "INSERT INTO friend_request (id_sender, id_receiver, date) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, friendRequest.getSenderId());
            statement.setLong(2, friendRequest.getReceiverId());
            statement.setDate(3, (Date) friendRequest.getDate());
            statement.executeUpdate();
            notifyObservers(new ChangeEvent(ChangeEventType.ADD,friendRequest));
        } catch (SQLException e) {
            e.getStackTrace();
        }

        return friendRequest;
    }



    @Override
    public FriendRequest delete(Long id) {
        FriendRequest friendRequest = findOne(id);
        try {
            String sql = "DELETE FROM friend_request WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
            notifyObservers(new ChangeEvent(ChangeEventType.DELETE,friendRequest));
        } catch (SQLException e) {
            e.getStackTrace();;
        }
        return friendRequest;
    }

//    public FriendRequest deleteById(Long id) {
//        FriendRequest FriendRequest = findOneById(id);
//        if (FriendRequest != null) {
//            try {
//                PreparedStatement st = connection.prepareStatement("DELETE FROM friend_request WHERE id_sender=?");
//                st.setLong(1, id);
//                st.executeUpdate();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return FriendRequest;
//    }
    public FriendRequest findOneById(Long id) {
        FriendRequest FriendRequest = null;
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM friend_request WHERE id_receiver=?");
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                FriendRequest = new FriendRequest();
                FriendRequest.setId(rs.getLong("id"));
                FriendRequest.setSenderId(rs.getLong("id_sender"));
                FriendRequest.setReceiverId(rs.getLong("id_receiver"));
                FriendRequest.setDate(rs.getDate("date"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return FriendRequest;
    }

    @Override
    public FriendRequest update(FriendRequest entity) {
        FriendRequest friendRequest = entity;
        validator.validate(friendRequest);
        try {
            String sql = "UPDATE friend_request SET id_sender = ?, id_receiver = ?, date = ? WHERE id_receiver = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, friendRequest.getSenderId());
            statement.setLong(2, friendRequest.getReceiverId());
            statement.setDate(3, (Date) friendRequest.getDate());
            statement.setLong(4, friendRequest.getId());
            statement.executeUpdate();
            notifyObservers(new ChangeEvent(ChangeEventType.UPDATE,friendRequest));
            return entity;
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return entity;}

        @Override
        public FriendRequest findOne(Long id) {
            try {
                String sql = "SELECT * FROM friend_request WHERE id_receiver = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setLong(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    FriendRequest friendRequest = new FriendRequest();
                    friendRequest.setId(resultSet.getLong("id"));
                    friendRequest.setSenderId(resultSet.getLong("id_sender"));
                    friendRequest.setReceiverId(resultSet.getLong("id_receiver"));
                    friendRequest.setDate(resultSet.getDate("date"));
                    return (FriendRequest) friendRequest;
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.getStackTrace();
            }
            return null;
        }

        @Override
        public List<FriendRequest> findAll() {
            try {
                String sql = "SELECT * FROM friend_request";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                List<FriendRequest> FriendRequest = new ArrayList<>();
                while (resultSet.next()) {
                    FriendRequest friendRequest = new FriendRequest();
                    friendRequest.setId(resultSet.getLong("id"));
                    friendRequest.setSenderId(resultSet.getLong("id_sender"));
                    friendRequest.setReceiverId(resultSet.getLong("id_receiver"));
                    friendRequest.setDate(resultSet.getDate("date"));
                    FriendRequest.add((FriendRequest) friendRequest);
                }
                return FriendRequest;
            } catch (SQLException e) {
                e.getStackTrace();
            }
            return null;
        }

    public List<FriendRequest> findAllForUser(Long userId) {
        List<FriendRequest> friendRequests = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM friend_request WHERE id_receiver=?");
            st.setLong(1, userId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                FriendRequest friendRequest = new FriendRequest();
                friendRequest.setId(rs.getLong("id"));
                friendRequest.setSenderId(rs.getLong("id_sender"));
                friendRequest.setReceiverId(rs.getLong("id_receiver"));
                friendRequest.setDate(rs.getDate("date"));
                friendRequests.add(friendRequest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendRequests;
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
