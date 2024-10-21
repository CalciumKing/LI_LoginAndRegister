module com.example.li_loginandregister {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.li_loginandregister to javafx.fxml;
    exports com.example.li_loginandregister;
}