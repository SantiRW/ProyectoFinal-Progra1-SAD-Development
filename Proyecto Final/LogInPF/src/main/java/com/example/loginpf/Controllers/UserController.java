package com.example.loginpf.Controllers;

import com.example.loginpf.Repositories.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    public AnchorPane paneTransferencia;

    @FXML
    private AnchorPane userPane;

    @FXML
    private Label lblMessage;

    @FXML
    private Label lblChoose;

    @FXML
    private ImageView imgDollar;

    @FXML
    private Button btnTransferencia;

    @FXML
    private ImageView imgBanner;

    @FXML
    private Button btnDepositar;

    @FXML
    private Label lblAccountNumber;

    @FXML
    private Label lblCash;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (lblMessage != null) {
            lblMessage.setText("¡Bienvenido a SAD Bank!");
        }
        if(lblAccountNumber != null){
            lblAccountNumber.setText(lblAccountNumber.getText());
        }

        if(lblCash != null){
            lblCash.setText(lblCash.getText());
        }

        UserRepository.cargarUsuariosDePrueba();

    }

    @FXML
    private void onDepositar(ActionEvent event){
        System.out.println("Depositar :D");
    }

    @FXML
    private void onTransferencia(ActionEvent event) {
        try {
            URL fxmlLocation = getClass().getResource("/com/example/loginpf/Transferencia.fxml");

            if (fxmlLocation == null) {
                System.err.println("ERROR: No se encontró Transferencia.fxml");
                System.err.println("Ruta esperada: /com/example/loginpf/Transferencia.fxml");
                return;
            }

            AnchorPane vistaTransferencia = FXMLLoader.load(fxmlLocation);

            if (paneTransferencia == null) {
                System.err.println("ERROR: paneTransferencia es null");
                return;
            }

            paneTransferencia.getChildren().setAll(vistaTransferencia);
            System.out.println("Transferencia cargada exitosamente");

        } catch (IOException e) {
            System.err.println("ERROR al cargar Transferencia.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setUserInfo(String userName, String userType) {
        if (lblMessage != null) {
            lblMessage.setText("¡Bienvenido a SAD Bank " + userName + "!");
        }
    }


    @FXML
    private void hoverOn() {
        if (btnTransferencia != null) {
            btnTransferencia.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand;");
        }
    }

    @FXML
    private void hoverOff() {
        if (btnTransferencia != null) {
            btnTransferencia.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand;");
        }
    }

    @FXML
    private void hoverOn2() {
        if (btnDepositar != null) {
            btnDepositar.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand;");
        }
    }

    @FXML
    private void hoverOff2() {
        if (btnDepositar != null) {
            btnDepositar.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand;");
        }
    }

}