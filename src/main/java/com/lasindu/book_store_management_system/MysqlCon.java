package com.lasindu.book_store_management_system;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;



class MysqlCon{
    Connection connection = null;
    Statement statement= null;

    public void mysqlConnect(){
        try{
            Dotenv dotenv = Dotenv.configure().directory("src/main/java/com/lasindu/book_store_management_system/.env").load();
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    dotenv.get("MYSQL_URL"),dotenv.get("MYSQL_USER"),dotenv.get("MYSQL_PASSWORD"));
            statement = connection.createStatement();

        }catch(Exception e){
            System.out.println(e);
        }
    }

}
