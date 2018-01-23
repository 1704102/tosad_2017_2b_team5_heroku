package com.vogella.jersey.first.repDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class RepConnector {

    private String url;
    private String port;
    private String username;
    private String password;
    private String service;
    private Connection conn;
    private final String QUERY_COLUMS = "SELECT * FROM USER_TAB_COLUMNS";
    private final String QUERY_CONSTRAINT = "SELECT * FROM user_constraints";

    public RepConnector(String url, String port, String service, String username, String password){
        this.url = url;
        this.port = port;
        this.username = username;
        this.password = password;
        this.service = service;
        connect();
    }


    public void connect() {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//"+ url +":"+ port + "/" + service,username, password);
            System.out.println("connection succesfull");
        } catch (Exception e) {
            System.out.println("connection failed");
            e.printStackTrace();
        }
    }


    public HashMap<String,ArrayList<String>> GetDatabase(){
        connect();
        HashMap<String,ArrayList<String>> columns = new HashMap<>();
        ResultSet s = select(QUERY_COLUMS);
        try {
            while (s.next()) {
                if(columns.get(s.getString("table_name")) == null){
                    columns.put(s.getString("table_name"), new ArrayList<String>());
                }
                columns.get(s.getString("table_name")).add(s.getString("column_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnect();
        return columns;
    }

    public void disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet select(String query){
        ResultSet s = null;
        try {
            Statement stm = conn.createStatement();
            s = stm.executeQuery(query);
        }catch (Exception e){}
        return s;
    }
}
