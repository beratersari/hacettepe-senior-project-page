package com.example.bitirmeprojesi;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbFunctions {

    public Connection connect_to_db(String dbname,String user,String pass){
        Connection conn = null;
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/"+dbname,user,pass);
            if (conn!=null){
                System.out.println("Connection establish!");
            }
            else {
                System.out.println("Connection Failed");
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return conn;
    }
}
