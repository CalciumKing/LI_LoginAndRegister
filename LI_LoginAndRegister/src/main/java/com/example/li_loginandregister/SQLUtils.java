package com.example.li_loginandregister;

import javafx.scene.control.Alert;

import java.sql.*;

public class SQLUtils {
    private static Connection ConnectDB() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_and_register", "root", "password");
            if (connection != null)
                System.out.println("Connected Successfully");
            return connection;
        } catch (Exception ignored) {
            MainController.ErrorAlert(Alert.AlertType.ERROR, "SQL Error", "Error Retrieving SQL Information", "Information could not be retrieved");
        }
        return null;
    }

    public static String Login(String username, String password, String email) {
        Connection connect = ConnectDB();
        if(connect == null)
            return "Error";
        String sql = "select * from users_table where username = ? and password = ? and email = ?";
        try (PreparedStatement prepared = connect.prepareStatement(sql)) {
            prepared.setString(1, username);
            prepared.setString(2, password);
            prepared.setString(3, email);
            ResultSet rs = prepared.executeQuery();

            if (rs.next()) {
                return rs.getString(1); // returns username
            } else {
                MainController.ErrorAlert(Alert.AlertType.INFORMATION, "Database Error", "Error Retrieving SQL Information, rs is null", "That user does not exist.");
            }
        } catch (NullPointerException ignored) {
            MainController.ErrorAlert(Alert.AlertType.ERROR, "SQL Error", "Error Retrieving SQL Information, null pointer exception", "Variable Connect is Null");
        } catch (Exception ignored) {
            MainController.ErrorAlert(Alert.AlertType.ERROR, "Error", "Error Running SQL", "There was an error running the SQL information, or that user doesn't exist.");
        }
        return "Error";
    }

    public static void Register(String username, String password, String email) {
        Connection connect = ConnectDB();
        if(connect == null)
            return;
        String sql = "insert into users_table (username, password, email) values (?, ?, ?)";
        try (PreparedStatement prepared = connect.prepareStatement(sql)) {
            prepared.setString(1, username);
            prepared.setString(2, password);
            prepared.setString(3, email);
            prepared.executeQuery();
        } catch (SQLException ignored) {
            MainController.ErrorAlert(Alert.AlertType.ERROR, "SQL Error", "Error Retrieving SQL Information, from register", "There was an error retrieving the SQL information, or that user doesn't exist.");
        } catch (Exception ignored) {
            MainController.ErrorAlert(Alert.AlertType.ERROR, "Error", "Error Running SQL", "There was an error running the SQL information, or that user doesn't exist.");
        }
    }
}