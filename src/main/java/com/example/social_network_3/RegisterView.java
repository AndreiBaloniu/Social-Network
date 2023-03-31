package com.example.social_network_3;

import com.example.social_network_3.domain.User;
import com.example.social_network_3.service.SocialNetworkService;
import com.example.social_network_3.utils.Event;
import com.example.social_network_3.utils.observer.Observer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class RegisterView implements Observer<Event>{
    private SocialNetworkService service;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Text errorField;



    public void setService(SocialNetworkService service) {
        this.service = service;
        service.addObserver(this);
    }

    @FXML
    public void handleRegisterUser(ActionEvent actionEvent) throws IOException, InterruptedException {
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            errorField.setText("All fields are required!");
            return;
        }else{
            List<User> users = service.findAllByUsername(usernameField.getText());
            if (!users.isEmpty()) {
                errorField.setText("Username-ul exista deja in baza de date!");
        }}
        User user = new User();
        user.setId(service.getNextUserId());
        user.setFirst_name(firstNameField.getText());
        user.setLast_name(lastNameField.getText());
        user.setUsername(usernameField.getText());
        user.setPassword(passwordField.getText());
        service.addUser(user);
        if (user.equals(service.findOneByUsername(usernameField.getText()))) {
            {errorField.setText("V-ati inregistrat cu succes!");
                Thread.sleep( 1000);
            handleGoBack(actionEvent);}
        } else {
            errorField.setText("Erooare la adaugarea user-ului");
        }
    }
    @FXML
    public void handleGoBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(root);

        // obtinerea Stage-ului din care s-a apelat handleLogin
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void update(Event event) {

    }
}
