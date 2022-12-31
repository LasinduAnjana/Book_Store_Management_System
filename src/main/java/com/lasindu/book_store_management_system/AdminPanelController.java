package com.lasindu.book_store_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminPanelController implements Initializable {

    @FXML
    private Button addBookButton;

    @FXML
    private TableColumn<Book, String> authorTbl;

    @FXML
    private TableView<Book> newBooksTable;

    @FXML
    private TableColumn<Book, String> categoryTbl;

    @FXML
    private TableColumn<Book, Integer> quantityTbl;

    @FXML
    private TableColumn<Book, String> isbnTbl;

    @FXML
    private TableColumn<Book, String> nameTbl;

    @FXML
    private TableColumn<Book, Double> priceTbl;

    @FXML
    private TextField author;

    @FXML
    private ChoiceBox<String> category;

    private String[] categories = {"novel", "detective", "Sci-Fi"};


    @FXML
    private TextField copies;

    @FXML
    private TextField isbn;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    private TextField price;

    @FXML
    private TextField username;


    @FXML
    protected void onAddNewBooksButtonClicked(ActionEvent event) {
        FromDB.newBookList.add(new Book(isbn.getText(), name.getText(), author.getText(), category.getValue(),
                Integer.parseInt(copies.getText()), Double.parseDouble(price.getText())));
    }

    @FXML
    protected void onEditButtonClicked(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bookEdit.fxml"));
        Parent root = loader.load();

        BookEditController editController = loader.getController();
        Book book = newBooksTable.getSelectionModel().getSelectedItem();
        editController.setValues(book);

        stage.setTitle("Book Store Management System");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    protected void onRemoveButtonClicked(ActionEvent event) {
        Book book = newBooksTable.getSelectionModel().getSelectedItem();
        FromDB.newBookList.remove(FromDB.newBookList.indexOf(book));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            category.getItems().addAll(categories);

            isbnTbl.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
            nameTbl.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));
            authorTbl.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
            categoryTbl.setCellValueFactory(new PropertyValueFactory<Book, String>("category"));
            quantityTbl.setCellValueFactory(new PropertyValueFactory<Book, Integer>("copies"));
            priceTbl.setCellValueFactory(new PropertyValueFactory<Book, Double>("price"));

        } catch(Exception e) {
            e.printStackTrace();
        }

        newBooksTable.setItems(FromDB.newBookList);
    }

    public void onAddToDatabaseClicked(ActionEvent event) {
        FromDB.addNewToBooks();
    }

}
