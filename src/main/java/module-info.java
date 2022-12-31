module com.lasindu.book_store_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;


    opens com.lasindu.book_store_management_system to javafx.fxml;
    exports com.lasindu.book_store_management_system;
}