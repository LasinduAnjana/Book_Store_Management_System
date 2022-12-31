package com.lasindu.book_store_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class BookEditController implements Initializable {

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
    private TextField price;

    private Book temp;

    @FXML
    protected void onCancelButtonClicked(ActionEvent event) {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onEditButtonClicked(ActionEvent event) {

        FromDB.newBookList.remove(FromDB.newBookList.indexOf(temp));

        Book book = new Book(isbn.getText(), name.getText(), author.getText(), category.getValue(), Integer.parseInt(copies.getText()), Double.parseDouble(price.getText()));
        FromDB.newBookList.add(book);

        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

     protected void setValues(Book book) {
        this.temp = book;
        try {
            this.isbn.setText(book.getIsbn());
            this.name.setText(book.getName());
            this.author.setText(book.getAuthor());
            this.category.setValue(book.getCategory());
            this.copies.setText(String.valueOf(book.getCopies()));
            this.price.setText(String.valueOf(book.getPrice()));

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        category.getItems().addAll(categories);

    }
}
