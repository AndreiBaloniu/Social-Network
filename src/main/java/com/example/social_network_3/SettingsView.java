package com.example.social_network_3;

import com.example.social_network_3.domain.User;
import com.example.social_network_3.service.SocialNetworkService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SettingsView {
    public TextField NewFnField;
    public TextField NewLnField;
    public TextField NewUField;
    public PasswordField NewPField;
    public Button updateButton1;
    public Button updateButton2;
    public Button deleteButton;
    private SocialNetworkService service;
    private User loggedUser;
    @FXML
    private Text errorField;

    public void handleUpdateAccount(ActionEvent actionEvent) {
        if (NewFnField.getText().isEmpty() || NewLnField.getText().isEmpty() || NewUField.getText().isEmpty() || NewPField.getText().isEmpty()) {
            errorField.setText("All fields are required!");
            return;
        }else{
            List<User> users = service.findAllByUsername(NewUField.getText());
            if (!users.isEmpty()) {
                errorField.setText("This username is already in the database");
            }}
        User user = new User();
        user.setId(loggedUser.getId());
        user.setFirst_name(NewFnField.getText());
        user.setLast_name(NewLnField.getText());
        user.setUsername(NewUField.getText());
        user.setPassword(NewPField.getText());
        service.updateUser(user);
        if (user.equals(service.findOneByUsername(NewUField.getText()))) {
            errorField.setText("Update succesful!");
        } else {
            errorField.setText("Error!");
        }
    }

    public void initialize(){
        NewFnField.setVisible(false);
        NewLnField.setVisible(false);
        NewUField.setVisible(false);
        NewPField.setVisible(false);
        updateButton2.setVisible(false);

        updateButton1.setOnAction(actionEvent ->{
            NewFnField.setVisible(true);
            NewLnField.setVisible(true);
            NewUField.setVisible(true);
            NewPField.setVisible(true);
            updateButton2.setVisible(true);
            updateButton1.setVisible(false);

        });
    }
    public void handleGoBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ynm-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        YnmView settingsView = loader.getController();
        // obtinerea Stage-ului din care s-a apelat handleLogin
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        settingsView.setLoggedUser(loggedUser);
        settingsView.setService(service);
        stage.setScene(scene);
        stage.show();
    }

    public void setService(SocialNetworkService service) {
        this.service = service;
    }
    public void setLoggedUser(User user) {
        this.loggedUser = user;
    }

    public void handleDeleteAccount(ActionEvent actionEvent) throws IOException, InterruptedException {
        service.deleteUser(loggedUser.getId());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Congrats");
        alert.setHeaderText("Account deleted!");
        alert.showAndWait();
        Thread.sleep( 1000);
        goToMain(actionEvent);
    }

    private void goToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(root);

        // obtinerea Stage-ului din care s-a apelat handleLogin
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
