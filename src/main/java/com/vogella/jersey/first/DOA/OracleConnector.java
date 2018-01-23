package com.vogella.jersey.first.DOA;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

/**
 * Created by marti on 20-1-2018.
 */
public class OracleConnector implements DatabaseConnector{

    private String url;
    private String port;
    private String username;
    private String password;
    private String data;

    public OracleConnector(String url, String port, String username, String password){
        this.url = url;
        this.port = port;
        this.username = username;
        this.password = password;


        connect();
    }


    public void connect() {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//"+ url +":"+ port,username, password);
            System.out.println("connection succesfull");

            String query = "SELECT table_name FROM user_tables";
            Statement stm = conn.createStatement();
            ResultSet s = stm.executeQuery(query);
            while(s.next()) {   // Move the cursor to the next row, return false if no more row
                String price = s.getString("table_name");
                System.out.println(price);
            }

        } catch (Exception e) {
            System.out.println("connection failed");
            e.printStackTrace();
        }
    }

    public void disconnect() {

    }
}
