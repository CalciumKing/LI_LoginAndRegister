package com.example.li_loginandregister;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class MainController {
    // region Variables
    @FXML
    private Label formText;
    @FXML
    private Button login, signup;
    @FXML
    private TextField username, email, password, confirmPassword;
    @FXML
    private Button formButton;
    // endregion

    // region Form
    @FXML
    private void ChangeForm() {
        ObservableList<String> shortLogin = login.getStyleClass(), shortSignUp = signup.getStyleClass();
        if (shortLogin.contains("active")) { // switching to signup
            formText.setText("Signup Form");
            shortLogin.remove("active");
            shortLogin.add("notActive");
            shortSignUp.remove("notActive");
            shortSignUp.add("active");
            confirmPassword.setVisible(true);
            formButton.setText("Sign Up");
        } else if (shortSignUp.contains("active")) { // switching to login
            formText.setText("Login Form");
            shortSignUp.remove("active");
            shortSignUp.add("notActive");
            shortLogin.remove("notActive");
            shortLogin.add("active");
            confirmPassword.setVisible(false);
            formButton.setText("Login");
        }
        ClearForm();
    }

    @FXML
    private void FormSubmit() {
        if (ValidForm()) {
            System.out.println("valid form");
        }
    }

    private void ClearForm() {
        username.clear();
        email.clear();
        password.clear();
        confirmPassword.clear();
    }

    private boolean ValidForm() {
        if (username.getText().isEmpty() || email.getText().isEmpty() || password.getText().isEmpty() || confirmPassword.getText().isEmpty()) {
            ErrorAlert("Form Validation", "Invalid Email", "All Fields Must Be Filled In");
            return false;
        }
        //email@email.com
        else if (!email.getText().contains("@") || !email.getText().contains(".com")) {
            ErrorAlert("Form Validation", "Invalid Email", "Please Enter A Valid Email That Contains An '@' And A '.com'");
            return false;
        }
        // Pattern.compile("[0-9][A-Z][a-z]").matcher(password.getText()).find()
        else if (password.getText().matches("^[a-zA-Z0-9+_.]+$") || password.getText().length() < 8) {
            ErrorAlert("Form Validation", "Invalid Password", "Please Enter A Valid Password That Contains At Least 8 Characters, 1 Uppercase, 1 Lowercase, 1 Number, and 1 Special Character");
            return false;
        } else if (signup.getStyleClass().contains("active") && !password.getText().equals(confirmPassword.getText())) {
            ErrorAlert("Form Validation", "Passwords Must Match", "Password And Confirm Password Must Match");
            return false;
        } else return true;
    }

    // endregion
    // region Utilities
    private void ErrorAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    // endregion

    // region Window Settings
    @FXML
    private void Minimize(ActionEvent event) {
        ((Stage) ((Button) event.getSource()).getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void Close() {
        System.exit(0);
    }

    // endregion
}