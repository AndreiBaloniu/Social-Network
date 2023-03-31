package com.example.social_network_3;

import com.example.social_network_3.domain.validators.FriendRequestValidator;
import com.example.social_network_3.domain.validators.FriendshipValidator;
import com.example.social_network_3.domain.validators.UserValidator;
import com.example.social_network_3.repository.FriendRequestRepository;
import com.example.social_network_3.repository.FriendshipRepository;
import com.example.social_network_3.repository.UserRepository;
import com.example.social_network_3.service.SocialNetworkService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        String url = "jdbc:postgresql://localhost:5432/socialnetwork";
        String username = "postgres";
        String password = "armadilo12";

        Connection connection = DriverManager.getConnection(url, username, password);
        UserRepository userRepository = new UserRepository(connection, new UserValidator());
        FriendshipRepository friendshipRepository = new FriendshipRepository(connection, new FriendshipValidator());
        FriendRequestRepository friendRequestRepository = new FriendRequestRepository(connection, new FriendRequestValidator());
        SocialNetworkService socialNetworkService = new SocialNetworkService(userRepository,friendshipRepository,friendRequestRepository);
        SocialNetworkService service = new SocialNetworkService(userRepository, friendshipRepository, friendRequestRepository);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));


        Scene scene = new Scene(fxmlLoader.load(), 400, 600);
        MainView controller = fxmlLoader.getController();
        controller.setService(service);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}