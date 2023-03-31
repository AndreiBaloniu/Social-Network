package com.example.social_network_3;

import com.example.social_network_3.domain.FriendRequest;
import com.example.social_network_3.domain.Friendship;
import com.example.social_network_3.domain.User;
import com.example.social_network_3.service.SocialNetworkService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class YnmView{
    public Button deleteFriendButton;
    public Button AcceptRequestButton;
    public Button DeclineRequestButton;
    public Button sendRequestButton;
    @FXML
    private TableView<Friendship> friendsTableView;
    @FXML
    private TableView<User> usersTableView;
    @FXML
    private TableView<FriendRequest> requestsTableView;
    @FXML
    private Button friendsButton;
    @FXML
    private Button usersButton;
    @FXML
    private Button requestsButton;
    @FXML
    private TextField searchUsersTextField;
    @FXML
    private TextField searchRequestsTextField;
    @FXML
    private TextField searchFriendsTextField;

    private User loggedUser;

    private SocialNetworkService service;

    public void setLoggedUser(User user) {
        this.loggedUser = user;
    }

    @FXML
    private void handleShowFriends(ActionEvent actionEvent) {
        List<Friendship> friendships = service.getFriendships(loggedUser.getId());
        ObservableList<Friendship> friendshipsObservable = FXCollections.observableArrayList(friendships);
        // cream coloana "Friends" si setam valorile ce vor fi afisate
        TableColumn<Friendship, String> friendsColumn = new TableColumn<>("Friends");
        friendsColumn.setCellValueFactory(friendship -> {
            // obtinem id-ul prietenului
            Long friendId = friendship.getValue().getUser1().equals(loggedUser.getId()) ? friendship.getValue().getUser2() : friendship.getValue().getUser1();
            // obtinem prietenul cu id-ul obtinut mai sus
            User friend = service.findOneUser(friendId);
            // returnam numele si prenumele prietenului
            return new SimpleStringProperty(friend.getFirst_name() + " " + friend.getLast_name() + "( " + friend.getUsername()+ " )");
        });

            // cream coloana "Dates" si setam valorile ce vor fi afisate
        TableColumn<Friendship, String> friendsDatesColumn = new TableColumn<>("Dates");
        friendsDatesColumn.setCellValueFactory(friendship -> {
            // obtinem data adaugarii prieteniei si o convertim in string
            Date date = friendship.getValue().getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = sdf.format(date);
            return new SimpleStringProperty(dateString);
        });

        friendsTableView.getColumns().setAll(friendsColumn, friendsDatesColumn);
        friendsTableView.setItems(friendshipsObservable);
    }
    @FXML
    private void handleShowFriends() {
        List<Friendship> friendships = service.getFriendships(loggedUser.getId());
        ObservableList<Friendship> friendshipsObservable = FXCollections.observableArrayList(friendships);
        // cream coloana "Friends" si setam valorile ce vor fi afisate
        TableColumn<Friendship, String> friendsColumn = new TableColumn<>("Friends");
        friendsColumn.setCellValueFactory(friendship -> {
            // obtinem id-ul prietenului
            Long friendId = friendship.getValue().getUser1().equals(loggedUser.getId()) ? friendship.getValue().getUser2() : friendship.getValue().getUser1();
            // obtinem prietenul cu id-ul obtinut mai sus
            User friend = service.findOneUser(friendId);
            // returnam numele si prenumele prietenului
            return new SimpleStringProperty(friend.getFirst_name() + " " + friend.getLast_name() + "( " + friend.getUsername()+ " )");
        });

        // cream coloana "Dates" si setam valorile ce vor fi afisate
        TableColumn<Friendship, String> friendsDatesColumn = new TableColumn<>("Dates");
        friendsDatesColumn.setCellValueFactory(friendship -> {
            // obtinem data adaugarii prieteniei si o convertim in string
            Date date = friendship.getValue().getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = sdf.format(date);
            return new SimpleStringProperty(dateString);
        });

        friendsTableView.getColumns().setAll(friendsColumn, friendsDatesColumn);
        friendsTableView.setItems(friendshipsObservable);
    }
    @FXML
    private void handleShowRequests(ActionEvent event) {
        List<FriendRequest> requests = service.getRequests(loggedUser.getId());
        ObservableList<FriendRequest> requestsList = FXCollections.observableArrayList(requests);


        TableColumn<FriendRequest, String> senderColumn = new TableColumn<>("Requests from");
        senderColumn.setCellValueFactory(request -> {
            Long friendId = request.getValue().getReceiverId().equals(loggedUser.getId()) ? request.getValue().getSenderId() : request.getValue().getReceiverId();
            User friend = service.findOneUser(friendId);
            return new SimpleStringProperty(friend.getFirst_name() + " " + friend.getLast_name() + "( " + friend.getUsername()+ " )");
        });


        TableColumn<FriendRequest, String> dateColumn = new TableColumn<>("Dates");
        dateColumn.setCellValueFactory(request -> {

            Date date = request.getValue().getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = sdf.format(date);
            return new SimpleStringProperty(dateString);
        });

        requestsTableView.getColumns().setAll(senderColumn, dateColumn);
        requestsTableView.setItems(requestsList);
    }


    @FXML
    public void initialize() {

        friendsTableView.setVisible(true);
        usersTableView.setVisible(false);
        requestsTableView.setVisible(false);
        searchUsersTextField.setVisible(false);
        searchRequestsTextField.setVisible(false);
        searchFriendsTextField.setVisible(true);
        AcceptRequestButton.setVisible(false);
        DeclineRequestButton.setVisible(false);
        sendRequestButton.setVisible(false);


        friendsButton.setOnAction(actionEvent -> {
            friendsTableView.setVisible(true);
            usersTableView.setVisible(false);
            requestsTableView.setVisible(false);
            handleShowFriends(actionEvent);
            searchUsersTextField.setVisible(false);
            searchRequestsTextField.setVisible(false);
            searchFriendsTextField.setVisible(true);
            AcceptRequestButton.setVisible(false);
            DeclineRequestButton.setVisible(false);
            sendRequestButton.setVisible(false);
        });

        usersButton.setOnAction(actionEvent -> {
            friendsTableView.setVisible(false);
            usersTableView.setVisible(true);
            requestsTableView.setVisible(false);
            handleShowUsers(actionEvent);
            searchUsersTextField.setVisible(true);
            searchRequestsTextField.setVisible(false);
            searchFriendsTextField.setVisible(false);
            AcceptRequestButton.setVisible(false);
            DeclineRequestButton.setVisible(false);
            sendRequestButton.setVisible(true);

        });

        requestsButton.setOnAction(actionEvent -> {
            friendsTableView.setVisible(false);
            usersTableView.setVisible(false);
            requestsTableView.setVisible(true);
            handleShowRequests(actionEvent);
            searchUsersTextField.setVisible(false);
            searchRequestsTextField.setVisible(true);
            searchFriendsTextField.setVisible(false);
            AcceptRequestButton.setVisible(true);
            DeclineRequestButton.setVisible(true);
            sendRequestButton.setVisible(false);
        });
    }
    public void setService(SocialNetworkService service) {
        this.service = service;
        handleShowFriends();
    }

    @FXML
    public void handleShowUsers(ActionEvent event) {
            List<User> users = service.findAllExceptYou(loggedUser.getId());
            ObservableList<User> usersData = FXCollections.observableArrayList(users);

            TableColumn<User, String> firstNameColumn = new TableColumn<>("first_name");
            firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("first_name"));

            TableColumn<User, String> lastNameColumn = new TableColumn<>("last_name");
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));

            TableColumn<User, String> usernameColumn = new TableColumn<>("username");
            usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

            usersTableView.getColumns().setAll(usernameColumn, firstNameColumn, lastNameColumn);
            usersTableView.setItems(usersData);
        }


    @FXML
    public void handleSearchUsers() {
        searchUsersTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchText = newValue;
            List<User> users = service.searchUsers(searchText,loggedUser.getId());
            ObservableList<User> usersObservable = FXCollections.observableArrayList(users);
            usersTableView.setItems(usersObservable);
        });
    }


    @FXML
    public void handleSearchFriends() {
        searchFriendsTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchText = newValue;
            List<Friendship> friendships = service.getFriendships(loggedUser.getId());
            ObservableList<Friendship> friendsObservable = FXCollections.observableArrayList(friendships);
            List<Friendship> filteredFriendships = new ArrayList<>();
            for (Friendship friendship : friendsObservable) {
                Long friendId = friendship.getUser1().equals(loggedUser.getId()) ? friendship.getUser2() : friendship.getUser1();
                User friend = service.findOneUser(friendId);
                if (friend.getUsername().contains(searchText)) {
                    filteredFriendships.add(friendship);
                }
            }
            friendsTableView.setItems(FXCollections.observableArrayList(filteredFriendships));
        });
    }
    @FXML
    private void handleSearchRequests() {
        searchRequestsTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchText = newValue;
            List<FriendRequest> requests = service.getRequests(loggedUser.getId());
            ObservableList<FriendRequest> requestsObservable = FXCollections.observableArrayList(requests);
            List<FriendRequest> filteredRequests = new ArrayList<>();
            for (FriendRequest request : requestsObservable) {
                Long friendId = request.getSenderId().equals(loggedUser.getId()) ? request.getReceiverId() : request.getSenderId();
                User friend = service.findOneUser(friendId);
                if (friend.getUsername().contains(searchText)) {
                    filteredRequests.add(request);
                }
            }
            requestsTableView.setItems(FXCollections.observableArrayList(filteredRequests));
        });
    }

    @FXML
    private void handleDeleteFriend(ActionEvent event) {
        Friendship selectedFriendship = friendsTableView.getSelectionModel().getSelectedItem();
        if (selectedFriendship == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No request selected");
            alert.showAndWait();
            return;
        }
        service.deleteFriendship(selectedFriendship.getId());
        updateFriendsTable();
    }

    private void updateFriendsTable() {

        List<Friendship> friendships = service.getFriendships(loggedUser.getId());
        friendsTableView.setItems(FXCollections.observableArrayList(friendships));
    }

    private void updateRequestsTable(){

        List<FriendRequest> requests = service.getRequests(loggedUser.getId());
        requestsTableView.setItems(FXCollections.observableArrayList(requests));
    }

    @FXML
    private void handleAcceptRequest(ActionEvent event) {
        FriendRequest friendRequest = requestsTableView.getSelectionModel().getSelectedItem();
        if (friendRequest == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No request selected");
            alert.showAndWait();
            return;
        }
        Friendship friendship = new Friendship(friendRequest.getReceiverId(), friendRequest.getSenderId(), friendRequest.getDate());
        service.addFriendship(friendship);
        service.deleteFriendRequest(friendRequest.getId());
        updateRequestsTable();
        updateFriendsTable();
    }

    @FXML
    private void handleDeclineRequest(ActionEvent actionEvent) {
        FriendRequest friendRequest = requestsTableView.getSelectionModel().getSelectedItem();
        if (friendRequest == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No request selected");
            alert.showAndWait();
            return;
        }
        service.deleteFriendRequest(friendRequest.getId());
        updateRequestsTable();
    }

    @FXML
    private void handleSendRequest(ActionEvent actionEvent) {
        User user = usersTableView.getSelectionModel().getSelectedItem();
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No user selected");
            alert.showAndWait();
            return;
        }
        if (isAlreadyFriend(user.getId())) {
// afisam popup-ul
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User is already your friend");
            alert.showAndWait();
            return;
        }
        if (hasRequestFromUser(user.getId())) {
        // afisam popup-ul
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You already have a request from this user");
            alert.showAndWait();
            return;
        }
        if (hasRequestedFromUser(user.getId())) {
        // afisam popup-ul
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You already sent a request to this user");
            alert.showAndWait();
            return;
        }
        // daca totul este in regula, trimitem cererea de prietenie

        Date n = Date.valueOf(java.time.LocalDate.now());
        FriendRequest request = new FriendRequest( loggedUser.getId(),user.getId(), n);
        service.addFriendRequest(request);
        // afiseaza un mesaj de confirmare
        showSuccessPopup("Friend request sent successfully!");
    }
    private boolean isAlreadyFriend(Long userId) {
        List<Friendship> friendships = service.getFriendships(loggedUser.getId());
        for (Friendship friendship : friendships) {
            if (friendship.getUser1().equals(userId) || friendship.getUser2().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasRequestFromUser(Long userId) {
        List<FriendRequest> requests = service.getRequests(loggedUser.getId());
        for (FriendRequest request : requests) {
            if (request.getReceiverId() == userId || request.getSenderId().equals(userId)) {
                return true;
            }
        }
        return false;
    }
    private boolean hasRequestedFromUser(Long userId) {
        List<FriendRequest> requests = service.getRequests(userId);
        for (FriendRequest request : requests) {
            if (request.getReceiverId() == userId || request.getSenderId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    private void showErrorPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleLogOut(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(root);

        // obtinerea Stage-ului din care s-a apelat handleLogin
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void handleSettings(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("settings-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        SettingsView settingsView = loader.getController();
        // obtinerea Stage-ului din care s-a apelat handleLogin
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        settingsView.setLoggedUser(loggedUser);
        settingsView.setService(service);
        stage.setScene(scene);
        stage.show();
}}
