package com.example.loginpf.Controllers;

import com.example.loginpf.Model.Client;
import com.example.loginpf.Repositories.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateAccountController {

    @FXML
    private TextField txtCreateUsername;

    @FXML
    private AnchorPane createPane;

    @FXML
    private TextField txtCreatePassword;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private Button btnCreateUser;

    private final ObservableList<Client> newUsers = FXCollections.observableArrayList();

    @FXML
    private void onCreateUser(ActionEvent event) {
        try {
            String username = txtCreateUsername.getText().trim();
            String password = txtCreatePassword.getText().trim();
            String confirmPassword = txtConfirmPassword.getText().trim();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showAlert("Error", "Todos los campos son obligatorios", Alert.AlertType.WARNING);
                return;
            }

            if (!password.equals(confirmPassword)) {
                showAlert("Error", "Las contraseñas no coinciden", Alert.AlertType.WARNING);
                return;
            }

            if (password.length() < 6) {
                showAlert("Error", "La contraseña debe tener al menos 6 caracteres", Alert.AlertType.WARNING);
                return;
            }

            if (UserRepository.existeUsuario(username)) {
                showAlert("Error", "El usuario '" + username + "' ya existe", Alert.AlertType.WARNING);
                return;
            }

            Client newClient = new Client(username, password);
            UserRepository.agregarUsuario(newClient);

            showAlert("¡Éxito!",
                    "Cuenta creada correctamente.\n" +
                            "Usuario: " + username + "\n\n" +
                            "Numero de cuenta: " + newClient.getAccount() + "\n\n" +
                            "Saldo inicial $: " + newClient.getCash() + "\n\n" +
                            "Ahora puede iniciar sesión.",
                    Alert.AlertType.INFORMATION);

            txtCreateUsername.clear();
            txtCreatePassword.clear();
            txtConfirmPassword.clear();

            volverALogin(event);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Ocurrió un error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void volverALogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpf/LogIn.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root, 600, 600);

        stage.setScene(scene);
        stage.setTitle("SAD Bank - Login");
    }

    private void volverALoginConPane() throws IOException {
        if (createPane != null) {
            AnchorPane backTo = FXMLLoader.load(getClass().getResource("/com/example/loginpf/LogIn.fxml"));
            createPane.getChildren().setAll(backTo);
        }
    }

    public ObservableList<Client> getNewUsers() {
        return newUsers;
    }
}