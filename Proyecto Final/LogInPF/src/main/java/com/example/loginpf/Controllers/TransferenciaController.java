package com.example.loginpf.Controllers;

import com.example.loginpf.Model.Client;
import com.example.loginpf.Repositories.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TransferenciaController implements Initializable {

    @FXML
    private TableView<Client> tblAccounts;

    @FXML
    private TableColumn<Client, String> colAccount;

    @FXML
    private TableColumn<Client, String> colName;

    @FXML
    private TableColumn<Client, String> colId;

    @FXML
    private TextField txtCuenta;

    @FXML
    private TextField txtMonto;

    @FXML
    private TextArea txtMensaje;

    @FXML
    private Button btnTransferir;

    @FXML
    private Button btnInfo;

    @FXML
    private AnchorPane layout;


    private Client clienteActual;

    private ObservableList<Client> clientList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colAccount.setCellValueFactory(new PropertyValueFactory<>("account"));
        colName.setCellValueFactory(new PropertyValueFactory<>("username"));
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        clientList = UserRepository.obtenerTodosLosUsuarios();

        tblAccounts.setItems(clientList);
    }

    public void setClienteActual(Client cliente) {
        this.clienteActual = cliente;
        System.out.println("✓ Cliente recibido en TransferenciaController: " + cliente.getUsername());
        System.out.println("  ID: " + cliente.getId());
        System.out.println("  Cuenta: " + cliente.getAccount());
        System.out.println("  Saldo: $" + cliente.getCash());
    }

    @FXML
    private void onInfo() {
        UserRepository.cargarUsuariosDePrueba();
        cargarDatos();
    }

    private void cargarDatos() {
        tblAccounts.setItems(UserRepository.obtenerTodosLosUsuarios());
        System.out.println("✓ Datos cargados en la tabla: " + tblAccounts.getItems().size() + " usuarios");
    }

    @FXML
    private void onTransferir(ActionEvent event) {
        if (clienteActual == null) {
            showAlert("Error", "No hay un usuario en sesión", Alert.AlertType.ERROR);
            System.err.println("✗ Error: clienteActual es null");
            return;
        }

        String cuentaDestino = txtCuenta.getText().trim();
        String montoStr = txtMonto.getText().trim();
        String mensaje = txtMensaje.getText().trim();

        if (cuentaDestino.isEmpty() || montoStr.isEmpty()) {
            showAlert("Error", "Debe completar la cuenta y el monto", Alert.AlertType.WARNING);
            return;
        }

        if (cuentaDestino.equals(clienteActual.getAccount())) {
            showAlert("Error", "No puede transferir a su propia cuenta", Alert.AlertType.WARNING);
            return;
        }

        try {
            double monto = Double.parseDouble(montoStr);

            if (monto <= 0) {
                showAlert("Error", "El monto debe ser mayor a 0", Alert.AlertType.WARNING);
                return;
            }

            if (monto > clienteActual.getCash()) {
                showAlert("Error",
                        "Saldo insuficiente.\n" +
                                "Saldo disponible: $" + String.format("%.2f", clienteActual.getCash()),
                        Alert.AlertType.WARNING);
                return;
            }

            Client cuentaDestinoClient = UserRepository.buscarPorCuenta(cuentaDestino);

            if (cuentaDestinoClient == null) {
                showAlert("Error", "La cuenta destino no existe", Alert.AlertType.WARNING);
                return;
            }

            clienteActual.setCash(clienteActual.getCash() - monto);
            cuentaDestinoClient.setCash(cuentaDestinoClient.getCash() + monto);

            System.out.println("✓ Transferencia exitosa:");
            System.out.println("  De: " + clienteActual.getUsername() + " (" + clienteActual.getAccount() + ")");
            System.out.println("  A: " + cuentaDestinoClient.getUsername() + " (" + cuentaDestino + ")");
            System.out.println("  Monto: $" + monto);
            System.out.println("  Nuevo saldo: $" + clienteActual.getCash());

            showAlert("¡Éxito!",
                    "Transferencia realizada con éxito.\n\n" +
                            "De: " + clienteActual.getAccount() + "\n" +
                            "Titular: " + clienteActual.getUsername() + "\n" +
                            "A: " + cuentaDestino + "\n" +
                            "Destinatario: " + cuentaDestinoClient.getUsername() + "\n" +
                            "Monto transferido: $" + String.format("%.2f", monto) + "\n" +
                            "Nuevo saldo: $" + String.format("%.2f", clienteActual.getCash()) +
                            (mensaje.isEmpty() ? "" : "\nMensaje: " + mensaje),
                    Alert.AlertType.INFORMATION);

            txtCuenta.clear();
            txtMonto.clear();
            txtMensaje.clear();

            if (tblAccounts.getItems() != null && !tblAccounts.getItems().isEmpty()) {
                tblAccounts.refresh();
            }

            UserRepository.cargarUsuariosDePrueba();

        } catch (NumberFormatException e) {
            showAlert("Error", "El monto debe ser un número válido", Alert.AlertType.ERROR);
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
    private void hoverOn() {
        if (btnInfo != null) {
            btnInfo.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 12;");
        }
    }

    @FXML
    private void hoverOff() {
        if (btnInfo != null) {
            btnInfo.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 12;");
        }
    }

    @FXML
    private void hoverOn1() {
        if (btnTransferir != null) {
            btnTransferir.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 12;");
        }
    }

    @FXML
    private void hoverOff1() {
        if (btnTransferir != null) {
            btnTransferir.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 12;");
        }
    }
}