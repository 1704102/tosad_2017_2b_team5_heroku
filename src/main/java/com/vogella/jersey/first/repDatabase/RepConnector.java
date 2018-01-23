package com.vogella.jersey.first.repDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by marti on 23-1-2018.
 */
public class RepConnector {

    private String url = "ondora02.hu.nl";
    private String port = "8521";
    private String username = "tosad_2017_2b_team5";
    private String password = "tosad_2017_2b_team5";
    private String service = "cursus02.hu.nl";
    private Connection conn;

    public RepConnector(){
    }


    public void connect() {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//"+ url +":"+ port + "/" + service,username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean login(String username, String password){
        try {
            connect();
            String query = String.format("select * from inlog where username = '%s' and password = '%s'", username, password);
            ResultSet s = select(query);
            int count = 0;
            while (s.next()){
                count++;
            }
            if (count == 1) return true;
        }catch (Exception e){}
        return false;
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


