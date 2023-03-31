package com.example.social_network_3.service;

import com.example.social_network_3.domain.FriendRequest;
import com.example.social_network_3.domain.Friendship;
import com.example.social_network_3.domain.User;
import com.example.social_network_3.repository.FriendRequestRepository;
import com.example.social_network_3.repository.FriendshipRepository;
import com.example.social_network_3.repository.UserRepository;
import com.example.social_network_3.utils.ChangeEvent;
import com.example.social_network_3.utils.Event;
import com.example.social_network_3.utils.observer.Observable;
import com.example.social_network_3.utils.observer.Observer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SocialNetworkService implements Observable<Event> {

    private UserRepository userRepository;
    private FriendshipRepository friendshipRepository;
    private FriendRequestRepository friendRequestRepository;
    private Connection connection;
    private List<Observer<Event>> observers;

    public SocialNetworkService(UserRepository userRepository, FriendshipRepository friendshipRepository, FriendRequestRepository friendRequestRepostitory) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
        this.friendRequestRepository = friendRequestRepostitory;
        observers = new ArrayList<>();
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "armadilo12");
        } catch (SQLException e) {
            e.printStackTrace();
        }}

    
    public User addUser(User user) {
        return userRepository.save(user);
    }

    
    public User deleteUser(Long id) {
        return userRepository.delete(id);
    }

    
    public User updateUser(User user) {
        return userRepository.update(user);
    }

    public List<User> findAllExceptYou(Long id){return userRepository.findAllExceptYou(id);}
    public User findOneUser(Long id) {
        return userRepository.findOne(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    
    public Friendship addFriendship(Friendship friendship) {
        return friendshipRepository.save(friendship);
    }

    
    public Friendship deleteFriendship(Long id) {
        return friendshipRepository.delete(id);
    }

    public Friendship updateFriendship(Friendship friendship) {
        return friendshipRepository.update(friendship);
    }

    
    public Friendship findOneFriendship(Long id) {
        return friendshipRepository.findOne(id);
    }

    
    public List<Friendship> findAllFriendships() {
        return friendshipRepository.findAll();
    }

    public void addFriendRequest(FriendRequest friendRequest) {
        friendRequestRepository.save(friendRequest);
    }

    public void deleteFriendRequest(Long id) {
        friendRequestRepository.delete(id);
    }
    public Long getNextUserId() {
        return userRepository.getNextId();
    }


    public FriendRequest findOneFriendRequest(Long id) {
        return friendRequestRepository.findOneById(id);
    }

    public List<FriendRequest> findAllFriendRequests() {
        return (List<FriendRequest>) friendRequestRepository.findAll();
    }
    public List<User> findAllByUsername(String username) { return userRepository.findAllByUsername(username);}
    public User findOneByUsername(String username) { return userRepository.findOneByUsername(username);}

    public List<User> searchUsers(String searchValue, Long id) {
        return userRepository.findAllExceptYou(id)
                .stream()
                .filter(user -> user.getUsername().startsWith(searchValue))
                .collect(Collectors.toList());
    }
    public List<Friendship> getFriendships(Long userId) {
        return friendshipRepository.findAllForUser(userId);
    }

    public List<FriendRequest> getRequests(Long userId) {
        return friendRequestRepository.findAllForUser(userId);
    }

    public List<User> searchRequests(String searchTerm) {
        List<FriendRequest> requests = friendRequestRepository.findAll();
        List<User> requestUsers = new ArrayList<>();
        for (FriendRequest request : requests) {
            User sender = userRepository.findOne(request.getSenderId());
            User receiver = userRepository.findOne(request.getReceiverId());
            if (sender.getUsername().contains(searchTerm) || receiver.getUsername().contains(searchTerm)) {
                requestUsers.add(sender);
                requestUsers.add(receiver);
            }
        }
        return requestUsers;
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