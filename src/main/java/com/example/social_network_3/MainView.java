package com.example.social_network_3;

import com.example.social_network_3.domain.User;
import com.example.social_network_3.domain.validators.FriendRequestValidator;
import com.example.social_network_3.domain.validators.FriendshipValidator;
import com.example.social_network_3.domain.validators.UserValidator;
import com.example.social_network_3.repository.FriendRequestRepository;
import com.example.social_network_3.repository.FriendshipRepository;
import com.example.social_network_3.repository.UserRepository;
import com.example.social_network_3.service.SocialNetworkService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MainView {
    public AnchorPane rootPane;
    public TextField usernameField;
    public TextField passwordField;
    public PasswordField passwordField1;
    public TextField usernameField1;
    @FXML
    public Button ForgotPasswordButton;
    @FXML
    public Button changePasswordButton;
    public Label errorField;
    public Label errorField1;
    @FXML
    private Button signUpButton;

    private SocialNetworkService service;
    private Connection connection;

    public void initialize() {

        changePasswordButton.setVisible(false);
        usernameField1.setVisible(false);
        passwordField1.setVisible(false);
        errorField1.setVisible(false);

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "armadilo12");
            UserRepository userRepository = new UserRepository(connection, new UserValidator());
            FriendshipRepository friendshipRepository = new FriendshipRepository(connection, new FriendshipValidator());
            FriendRequestRepository friendRequestRepository = new FriendRequestRepository(connection, new FriendRequestValidator());
            service = new SocialNetworkService(userRepository, friendshipRepository, friendRequestRepository);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void setService(SocialNetworkService service) {
        this.service = service;
    }
    public void handleLogin(ActionEvent actionEvent) throws IOException {
//        initialize();
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = service.findOneByUsername(username);
        if (user != null && user.getPassword().equals(password)) {

            // crearea scenei "ynm-view"
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ynm-view.fxml"));
            Parent root = loader.load();
            YnmView ynmView = loader.getController();

            Scene scene = new Scene(root);

            // obtinerea Stage-ului din care s-a apelat handleLogin
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            ynmView.setLoggedUser(user);
            ynmView.setService(service);
            stage.setScene(scene);
            stage.show();
        }
        else {
            // instantiez obiectul de tip Stage pentru popup
            Stage errorPopup = new Stage();
            // setez titlul popup-ului
            errorPopup.setTitle("Eroare");
            // creez un Label care sa contina mesajul de eroare
            Label errorLabel = new Label("Datele introduse sunt incorecte!");
            // creez un layout vertical in care adaug Label-ul creat mai sus
            VBox errorVBox = new VBox(errorLabel);
            errorVBox.setAlignment(Pos.CENTER);
            // creez o scena care sa contina layout-ul creat mai sus
            Scene errorScene = new Scene(errorVBox, 300, 200);
            // setez scena pentru popup
            errorPopup.setScene(errorScene);
            // afisez popup-ul
            errorPopup.show();
        }

    }
    @FXML
    public void handleSignUp(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("register-view.fxml"));
        Parent root = loader.load();
        RegisterView registerView = loader.getController();
        registerView.setService(service);
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleChangePassword(ActionEvent actionEvent) throws InterruptedException {
        if (usernameField1.getText().isEmpty() || passwordField1.getText().isEmpty()) {
            errorField.setText("All fields are required!");
        }else{
            List<User> users = service.findAllByUsername(usernameField1.getText());
            if (users.isEmpty()) {
                errorField.setText("Username-ul nu exista!");
            }
            else
            {User user = service.findOneByUsername(usernameField1.getText());
        user.setPassword(passwordField1.getText());
        errorField1.setVisible(true);
        errorField1.setText("V-ati schimbat parola cu succes!");
        doInvisible();
            }}
    }

    public void handleForgotPassword(ActionEvent actionEvent) {
        changePasswordButton.setVisible(true);
        usernameField1.setVisible(true);
        passwordField1.setVisible(true);
        errorField.setVisible(true);
    }

    public void doInvisible() throws InterruptedException {
        Thread.sleep(500);
        changePasswordButton.setVisible(false);
        usernameField1.setVisible(false);
        passwordField1.setVisible(false);
        errorField.setVisible(false);
    }
}
