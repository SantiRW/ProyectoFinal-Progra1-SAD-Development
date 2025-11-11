package com.example.loginpf.Repositories;

import com.example.loginpf.Model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserRepository {

    private static final ObservableList<Client> usuarios = FXCollections.observableArrayList();

    static {

        Client cliente1 = new Client();
        cliente1.setUsername("clientePrueba");
        cliente1.setPassword("cliente");
        cliente1.setAccount("0009");
        cliente1.setCash(100000.00);
        usuarios.add(cliente1);
    }

    public static void agregarUsuario(Client usuario) {
        usuarios.add(usuario);
        System.out.println("Usuario agregado: " + usuario.getUsername());
    }

    public static boolean validarCredenciales(String username, String password) {
        for (Client user : usuarios) {
            if (user.getUsername().equals(username) &&
                    user.getPassword().equals(password)) {
                System.out.println("Login exitoso: " + username);
                return true;
            }
        }
        System.out.println("Credenciales incorrectas para: " + username);
        return false;
    }

    public static ObservableList<Client> getUsuarios() {
        return usuarios;
    }

    public static boolean existeUsuario(String username) {
        for (Client user : usuarios) {
            if (user.getUsername().equals(username)) {
                System.out.println("Usuario ya existe: " + username);
                return true;
            }
        }
        return false;
    }

    public static Client buscarPorCuenta(String numeroCuenta) {
        return usuarios.stream()
                .filter(client -> client.getAccount().equals(numeroCuenta))
                .findFirst()
                .orElse(null);
    }

    public static Client obtenerUsuario(String username) {
        for (Client user : usuarios) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static void cargarUsuariosDePrueba() {

        usuarios.clear();

        Client usuario1 = new Client("JuanCa","Juan123", "password123","24", "0002", 5000.0);
        Client usuario2 = new Client("Maria","Maria456", "password456","25", "0003", 3500.50);
        Client usuario3 = new Client("Pdro Pascal","Pedro789", "password789","26", "0004", 10000.0);
        Client usuario4 = new Client("Anita","Ana321", "password321","27", "0005", 7500.75);
        Client usuario5 = new Client("Carlos David","Carlos654", "password654","28", "0006", 2000.0);

        usuarios.add(usuario1);
        usuarios.add(usuario2);
        usuarios.add(usuario3);
        usuarios.add(usuario4);
        usuarios.add(usuario5);

    }

    public static ObservableList<Client> obtenerTodosLosUsuarios() {
        return usuarios;
    }

    public static Client buscarUsuario(String username) {
        return usuarios.stream()
                .filter(client -> client.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    public static int getCantidadUsuarios() {
        return usuarios.size();
    }
}