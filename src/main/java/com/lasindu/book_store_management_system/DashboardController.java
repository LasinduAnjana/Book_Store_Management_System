package com.lasindu.book_store_management_system;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private TableColumn<Book, String> allAuthor;

    @FXML
    private TableView<Book> allBooksTable;

    @FXML
    private TableColumn<Book, String> allCategory;

    @FXML
    private TableColumn<Book, Integer> allCopies;

    @FXML
    private TableColumn<Book, String> allIsbn;

    @FXML
    private TableColumn<Book, String> allName;

    @FXML
    private TableColumn<Book, Double> allPrice;

    @FXML
    private TextField author;

    @FXML
    private TableColumn<Book, String> cartIsbn;

    @FXML
    private TableColumn<Book, String> cartName;

    @FXML
    private TableColumn<Book, String> cartAuthor;

    @FXML
    private TableColumn<Book, Double> cartPrice;

    @FXML
    private TableColumn<Book, Integer> cartQuantity;

    @FXML
    private TableView<Book> cartTable;

    @FXML
    private ChoiceBox<String> category;

    private String[] categories = {"", "novel", "detective", "Sci-Fi"};


    @FXML
    private TextField isbn;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;


    @FXML
    private TextField username;

    @FXML
    private Spinner<Integer> countSpinner;


    FromDB db = new FromDB();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{
            category.getItems().addAll(categories);
            category.setValue("");

            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99);
            valueFactory.setValue(1);
            countSpinner.setValueFactory(valueFactory);

            allIsbn.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
            allName.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));
            allAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
            allCategory.setCellValueFactory(new PropertyValueFactory<Book, String>("category"));
            allCopies.setCellValueFactory(new PropertyValueFactory<Book, Integer>("copies"));
            allPrice.setCellValueFactory(new PropertyValueFactory<Book, Double>("price"));

            cartIsbn.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
            cartName.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));
            cartAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
            cartQuantity.setCellValueFactory(new PropertyValueFactory<Book, Integer>("copies"));
            cartPrice.setCellValueFactory(new PropertyValueFactory<Book, Double>("price"));

            db.getAllBooks();
            allBooksTable.setItems(db.list);
            cartTable.setItems(db.cartList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void onSellButtonClicked(ActionEvent event) {
        int temp = 0;
        for (Book b : FromDB.cartList) {
            temp = FromDB.list.get(FromDB.list.indexOf(FromDB.findBookFromList(b.getIsbn()))).getCopies();
            db.updateQuantity(b.getIsbn(), temp, b.getCopies());
            FromDB.getAllBooks();
        }
        printBill();
        FromDB.cartList.clear();
    }

    private void printBill() {
        Double totalPrice = 0.0;
        System.out.println("************** BootStore Name ************");
        System.out.println("isbn \t\tquantity \tprice");
        for (Book b : FromDB.cartList) {
            System.out.println(b.getIsbn() + "\t" + b.getCopies() + "\t\t" + b.getPrice());
            System.out.println("\t" + b.getName());
            totalPrice += b.getPrice() * b.getCopies();
        }
        System.out.println("----------------------------------------------");
        System.out.println("Total price \t= " + totalPrice);
    }

    @FXML
    protected void onResetButtonClicked(ActionEvent event) {
        db.getAllBooks();
        isbn.setText("");
        name.setText("");
        author.setText("");
    }

    @FXML
    protected void onSearchButtonClicked(ActionEvent event) {
        db.getSelectedBooks(isbn.getText(), name.getText(), author.getText(), category.getValue());
    }

    @FXML
    public void onAdminPanelButtonClick(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminPanel.fxml")));
        stage.setTitle("Book Store Management System");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

    }

    @FXML
    public void onAddToCartButtonClick(ActionEvent event) {
        try {
            Book sb = allBooksTable.getSelectionModel().getSelectedItem();
            if (countSpinner.getValue() <= sb.getCopies()) {
                db.cartList.add(new Book(sb.getIsbn(), sb.getName(), sb.getAuthor(), sb.getCategory(), countSpinner.getValue(), sb.getPrice()));
            } else {
                Dialog<String> dialog = new Dialog<String>();
                dialog.setTitle("OUT OF STOCK");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                dialog.setContentText("There is only " + sb.getCopies() + " books left. please select no below " + sb.getCopies() + " and try again");
                dialog.getDialogPane().getButtonTypes().add(type);
                dialog.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onRemoveFromCartClicked(ActionEvent event) {
        try {
            Book sb = cartTable.getSelectionModel().getSelectedItem();
            FromDB.cartList.remove(FromDB.cartList.indexOf(sb));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onClearCartButtonClicked() {
        db.cartList.clear();
    }
}
