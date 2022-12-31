package com.lasindu.book_store_management_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class FromDB {
    static MysqlCon mysqlHandler = new MysqlCon();
    static ObservableList<Book> list = FXCollections.observableArrayList();
    static ObservableList<Book> newBookList = FXCollections.observableArrayList();
    static ObservableList<Book> cartList = FXCollections.observableArrayList();


    public static void getAllBooks() {
        list.clear();
        try {
            mysqlHandler.mysqlConnect();
            ResultSet result = mysqlHandler.statement.executeQuery("SELECT * FROM books");
            while(result.next()) {
                if (result.getInt(5) <= 0) {
                    deleteBook(result.getString(1));
                }
                list.add(new Book(result.getString(1), result.getString(2),
                        result.getString(3), result.getNString(4), result.getInt(5), result.getDouble(6)));
            }
            mysqlHandler.statement.close();
            mysqlHandler.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteBook(String isbn) throws SQLException {
        mysqlHandler.mysqlConnect();
        mysqlHandler.statement.executeUpdate("DELETE FROM books WHERE isbn = \"" + isbn + "\";");
    }

    public void getSelectedBooks(String isbn, String name, String author, String category) {
        list.clear();
        try {
            mysqlHandler.mysqlConnect();

            String query = "SELECT * FROM books WHERE isbn Like \"" + isbn + "%\" AND name LIKE \"" + name +
                    "%\" AND author LIKE \"" + author + "%\" AND category LIKE \"" + category + "%\";";
            ResultSet result = mysqlHandler.statement.executeQuery(query);
            while(result.next()) {
                list.add(new Book(result.getString(1), result.getString(2),
                        result.getString(3),result.getString(4), result.getInt(5), result.getDouble(6)));
            }
            mysqlHandler.statement.close();
            mysqlHandler.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addNewToBooks() {
        getAllBooks();
        try {
            for (Book b : newBookList) {
                mysqlHandler.mysqlConnect();
                if (list.contains(findBookFromList(b.getIsbn()))) {

                    int result = mysqlHandler.statement.executeUpdate("UPDATE Books SET copies = " + (findBookFromList(b.getIsbn()).getCopies() + b.getCopies()) + " WHERE isbn = \"" + b.getIsbn() + "\";");
                    System.out.println(result);
                } else {
                    mysqlHandler.statement.executeUpdate("INSERT IGNORE INTO Books VALUES ( \"" + b.getIsbn() + "\", \"" +
                            b.getName() + "\",\"" + b.getAuthor() + "\", \"" + b.getCategory() + "\", \""+ b.getCopies() + "\", \" " + b.getPrice() + "\");");
                }

                mysqlHandler.statement.close();
                mysqlHandler.connection.close();
            }

            newBookList.clear();
            getAllBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Book findBookFromList(String isbn) {
        for (Book b : list) {
            if (Objects.equals(b.getIsbn(), isbn)) {
                return b;
            }
        }
        return null;
    }


    public void updateQuantity(String isbn, int copies, int quantity) {
        try{
            mysqlHandler.mysqlConnect();
            mysqlHandler.statement.executeUpdate("UPDATE books SET copies = " + (copies - quantity) + " WHERE isbn = \"" + isbn + "\";");
            mysqlHandler.statement.close();
            mysqlHandler.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
