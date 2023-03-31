package com.example.social_network_3.repository;

import com.example.social_network_3.domain.Friendship;
import com.example.social_network_3.domain.validators.Validator;
import com.example.social_network_3.utils.ChangeEvent;
import com.example.social_network_3.utils.ChangeEventType;
import com.example.social_network_3.utils.Event;
import com.example.social_network_3.utils.observer.Observable;
import com.example.social_network_3.utils.observer.Observer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendshipRepository implements Repository<Long, Friendship>, Observable<Event>
{
    private Connection connection;
    private Validator<Friendship> validator;
    private List<Observer<Event>> observers;

    public FriendshipRepository(Connection connection, Validator<Friendship> validator) {
        this.connection = connection;
        this.validator = validator;
        this.observers = new ArrayList<>();
    }

    @Override
    public Friendship findOne(Long id) {
        Friendship friendship = null;
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM friendship WHERE id_user1=?");
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                friendship = new Friendship();
                friendship.setId(rs.getLong("id"));
                friendship.setUser1(rs.getLong("id_user1"));
                friendship.setUser2(rs.getLong("id_user2"));
                friendship.setDate(rs.getDate("date"));
            }
        }
            catch (SQLException e) {
                e.printStackTrace();
        }
        return friendship;
    }

    public Friendship findOneById(Long id) {
        Friendship friendship = null;
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM friendship WHERE id_user1=?");
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                friendship = new Friendship();
                friendship.setId(rs.getLong("id"));
                friendship.setUser1(rs.getLong("id_user1"));
                friendship.setUser2(rs.getLong("id_user2"));
                friendship.setDate(rs.getDate("date"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return friendship;
    }

    @Override
    public List<Friendship> findAll() {
        List<Friendship> friendships = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM friendship");
            while (rs.next()) {
                Friendship friendship = new Friendship();
                friendship.setId(rs.getLong("id"));
                friendship.setUser1(rs.getLong("username1"));
                friendship.setUser2(rs.getLong("username2"));
                friendship.setDate(rs.getDate("date"));
                friendships.add(friendship);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendships;
    }

    @Override
    public Friendship save(Friendship friendship) {
        try {
            PreparedStatement st = connection.prepareStatement("INSERT INTO friendship (id_user1, id_user2, date) VALUES (?, ?, ?)");
            st.setLong(1, friendship.getUser1());
            st.setLong(2, friendship.getUser2());
            st.setDate(3, friendship.getDate());
            st.executeUpdate();
            notifyObservers(new ChangeEvent(ChangeEventType.ADD,friendship));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendship;
    }
    @Override
    public Friendship delete(Long id) {
        Friendship friendship = findOne(id);
        if (friendship != null) {
            try {
                PreparedStatement st = connection.prepareStatement("DELETE FROM friendship WHERE id=?");
                st.setLong(1, id);
                st.executeUpdate();
                notifyObservers(new ChangeEvent(ChangeEventType.DELETE,friendship));

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return friendship;
    }
    @Override
    public Friendship update(Friendship friendship) {
        try {
            PreparedStatement st = connection.prepareStatement("UPDATE friendship SET id_user1=?, id_user2=?, date=? WHERE id_user2=?");
            st.setLong(1, friendship.getUser1());
            st.setLong(2, friendship.getUser2());
            st.setDate(3, friendship.getDate());
            st.setLong(4, friendship.getId());
            st.executeUpdate();
            notifyObservers(new ChangeEvent(ChangeEventType.UPDATE,friendship));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendship;
    }

    public List<Friendship> findAllForUser(Long id_user1) {
        List<Friendship> friendships = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement("SELECT * FROM friendship WHERE id_user1=? or id_user2=?");
            st.setLong(1, id_user1);
            st.setLong(2, id_user1);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Friendship friendship = new Friendship();
                friendship.setId(rs.getLong("id"));
                friendship.setUser1(rs.getLong("id_user1"));
                friendship.setUser2(rs.getLong("id_user2"));
                friendship.setDate(rs.getDate("date"));
                friendships.add(friendship);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendships;
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