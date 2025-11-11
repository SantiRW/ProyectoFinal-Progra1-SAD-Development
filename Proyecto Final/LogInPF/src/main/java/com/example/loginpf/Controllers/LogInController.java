package com.example.loginpf.Controllers;

import com.example.loginpf.Model.Admin;
import com.example.loginpf.Model.Client;
import com.example.loginpf.Repositories.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    @FXML
    public PasswordField txtPassword;


    @FXML
    private AnchorPane createPane;

    @FXML
    private AnchorPane pane;

    @FXML
    private AnchorPane anchorContainer;

    @FXML
    private Label lblUser;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblUserName;

    @FXML
    private ComboBox<Object> comboUser;

    @FXML
    private TextField txtUserName;

    @FXML
    private AnchorPane anchorView;

    @FXML
    private Hyperlink hypCreate;

    @FXML
    private Button btnLogIn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Object> users = FXCollections.observableArrayList();
        users.add(new Client());
        users.add(new Admin());
        comboUser.setItems(users);
        comboUser.getSelectionModel().selectFirst();
        UserRepository.cargarUsuariosDePrueba();

    }

    private boolean validateCredentials(String username, String password) {

        return UserRepository.validarCredenciales(username, password);
    }

    @FXML
    private void onLogIn(ActionEvent event) {
        try {

            if (txtUserName == null || txtUserName.getText().trim().isEmpty()) {
                showAlert("Campo Vacío", "Por favor ingrese un nombre de usuario", Alert.AlertType.WARNING);
                return;
            }

            if (txtPassword == null || txtPassword.getText().trim().isEmpty()) {
                showAlert("Campo Vacío", "Por favor ingrese una contraseña", Alert.AlertType.WARNING);
                return;
            }

            if (comboUser == null || comboUser.getValue() == null) {
                showAlert("Selección Requerida", "Por favor seleccione un tipo de usuario", Alert.AlertType.WARNING);
                return;
            }

            String userName = txtUserName.getText().trim();
            String password = txtPassword.getText().trim();

            if (!validateCredentials(userName, password)) {
                showAlert("Credenciales Incorrectas",
                        "Usuario o contraseña incorrectos.\n\n" +
                                "Usuarios de prueba:\n" +
                                "Admin: admin/admin\n" +
                                "Cliente: cliente/cliente\n\n" +
                                "O use una cuenta creada previamente.",
                        Alert.AlertType.ERROR);
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/loginpf/User.fxml"));
            Parent root = loader.load();

            UserController userController = loader.getController();
            String userType = comboUser.getValue().toString();
            userController.setUserInfo(userName, userType);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 600, 600);
            stage.setScene(scene);
            stage.setTitle("SAD Bank - Área de Usuario");

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar la vista: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void hoverOn() {
        if (btnLogIn != null) {
            btnLogIn.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand;");
        }
    }

    @FXML
    private void hoverOff() {
        if (btnLogIn != null) {
            btnLogIn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand;");
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void onCreateAccount(ActionEvent event) throws IOException {
        AnchorPane createAccount = FXMLLoader.load(getClass().getResource("/com/example/loginpf/CreateAccount.fxml"));
        pane.getChildren().setAll(createAccount);
    }
}