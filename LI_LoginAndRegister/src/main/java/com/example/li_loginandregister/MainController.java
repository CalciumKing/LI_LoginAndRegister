package com.example.li_loginandregister;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            try {
                if (signup.getStyleClass().contains("active")) {
                    SQLUtils.Register(username.getText(), password.getText(), email.getText());
                } else if (login.getStyleClass().contains("active")) {
                    String name = SQLUtils.Login(username.getText(), password.getText(), email.getText());
                }
            } catch (Exception ignored) {
                ErrorAlert(Alert.AlertType.ERROR, "SQL Error", "Error Retrieving SQL Information from MainController", "There was an error retrieving the SQL information, or that user doesn't exist.");
            }
        }
    }

    private void ClearForm() {
        username.clear();
        email.clear();
        password.clear();
        confirmPassword.clear();
    }

    private boolean ValidForm() {
        // region Regex Characters
        // . any single character
        // * 0 or more occurrences of the preceding element
        // + 1 or more occurrence of the preceding element
        // [] match any character inside brackets
        // ^ start of a string
        // $ end of a string
        // \ escape character
        // ?=* positive look ahead assertion
        // ?! negative look ahead assertion
        // .{8, } at least 8 characters
        // \\d shortcut for 0-9
        //endregion

        // Pattern.compile("[0-9][A-Z][a-z]").matcher(password.getText()).find()
        // || password.getText().matches(emailRegex)
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9._]+\\.[a-zA-Z]{2,6}$";
//        String passwordRegex = "^[a-zA-Z0-9+_.]+$";
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[/~`!@#$%^&*()_+{};:',<.>? =]).{8,}$";

        // || password.getText().length() < 8
        if (Pattern.compile(passwordRegex).matcher(password.getText()).matches()) {
            ErrorAlert(Alert.AlertType.INFORMATION, "Form Validation", "Invalid Password", "Please Enter A Valid Password That Contains At Least 8 Characters, 1 Uppercase, 1 Lowercase, 1 Number, and 1 Special Character");
            return false;
        }

//        if (username.getText().isEmpty() || email.getText().isEmpty() || password.getText().isEmpty() || (signup.getStyleClass().contains("active") && confirmPassword.getText().isEmpty())) {
//            ErrorAlert(Alert.AlertType.INFORMATION, "Form Validation", "Invalid Fields", "All Fields Must Be Filled In");
//            return false;
//        } else if (!Pattern.compile(emailRegex).matcher(email.getText()).matches()) {
//            ErrorAlert(Alert.AlertType.INFORMATION, "Form Validation", "Invalid Email", "Please Enter A Valid Email That Contains An '@' And A '.com'");
//            return false;
//        } else else if (signup.getStyleClass().contains("active") && !password.getText().equals(confirmPassword.getText())) {
//            ErrorAlert(Alert.AlertType.INFORMATION, "Form Validation", "Passwords Must Match", "Password And Confirm Password Must Match");
//            return false;
//        }
        return true;
    }

    // endregion
    // region Utils
    public static void ErrorAlert(Alert.AlertType type, String title, String headerText, String contentText) {
        Alert alert = new Alert(type);
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