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


    public String login(String username, String password){
        String id = "0";
        try {
            connect();
            String query = String.format("select * from inlog where username = '%s' and password = '%s'", username, password);
            ResultSet s = select(query);

            while (s.next()){
                id = s.getString("id");
            }
            disconnect();
        }catch (Exception e){}
        return id;
    }

    public void addDatabase(String url, String port, String service, String username, String password, int id) {
        try {
            connect();
            ResultSet s = select(String.format("select * from database_target where url = '%s'", url));
            int count = 0;
            while (s.next()) {
                count++;
            }
            if (count != 1) {
                insert(String.format("insert into database_target(url,port,service,username,password) values ('%s', '%s', '%s', '%s', '%s')", url, port, service, username, password));
                ResultSet s1 = select(String.format("select * from database_target where url = '%s'", url));
                while (s1.next()){
                    insert(String.format("insert into klant_database_target(database_target_id, inlogid) values (%d, %d)",s1.getInt("id"),id));
                }
                System.out.println("added database");
            }
            disconnect();
        } catch (Exception e) {
        }
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

    public void insert(String query){
        try {
            Statement t = conn.createStatement();
                    t.execute(query);
            conn.commit();
        }catch (Exception e){}
    }
}


