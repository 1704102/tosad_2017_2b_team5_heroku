package com.vogella.jersey.first.DOA;

import com.vogella.jersey.first.repDatabase.RepConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class TargetConnector {

    private String url;
    private String port;
    private String username;
    private String password;
    private String service;
    private Connection conn;
    private final String QUERY_COLUMS = "SELECT * FROM USER_TAB_COLUMNS";

    public TargetConnector(String url, String port, String service, String username, String password) {
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
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//" + url + ":" + port + "/" + service, username, password);
            System.out.println("connection succesfull");
        } catch (Exception e) {
            System.out.println("connection failed");
            e.printStackTrace();
        }
    }


    public HashMap<String, ArrayList<String>> GetDatabaseData() {
        connect();
        HashMap<String, ArrayList<String>> columns = new HashMap<String, ArrayList<String>>();
        ResultSet s = select(QUERY_COLUMS);
        try {
            while (s.next()) {
                if (columns.get(s.getString("table_name")) == null) {
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

    public ResultSet select(String query) {
        ResultSet s = null;
        try {
            Statement stm = conn.createStatement();
            s = stm.executeQuery(query);
        } catch (Exception e) {
        }
        return s;
    }

    public void insert(String query) {
        try {
            Statement t = conn.createStatement();
            t.execute(query);
            conn.commit();
        } catch (Exception e) {
        }
    }

    public void makeRule(ArrayList<String> arr) {
        String type = arr.get(0);
        String sql = "";
        if (type.equals("rangeRule")) {

            String value1 = arr.get(1);
            String value2 = arr.get(2);
            String table1 = arr.get(3);
            String column1 = arr.get(4);
            String bName = arr.get(5);

            sql = "alter table " + table1 + " add constraint " + bName + " check(" + column1 + " between " + value1 + " and " + value2 + " )ENABLE NOVALIDATE";
        }
        if (type.equals("tupleRule")) {
            String operator = arr.get(1);
            String table1 = arr.get(2);
            String column1 = arr.get(3);
            String column2 = arr.get(4);
            String bName = arr.get(5);
            sql = "alter table " + table1 + " add constraint " + bName + " check("+ column1 + " "+operator +" " + column2 + ")ENABLE NOVALIDATE";
        }
        if (type.equals("attributerule")) {
            String value1 = arr.get(1);
            String operator = arr.get(2);
            String table1 = arr.get(3);
            String column1 = arr.get(4);
            String bName = arr.get(5);
            sql = "alter table " + table1 + " add constraint " + bName + " check(" + column1 + " "+operator +" " + value1 + ") ENABLE NOVALIDATE";
        }

        try {
            connect();
            insert(sql);
            disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RepConnector con = new RepConnector();
        try {
            String updateRule = String.format("update businessrule set status = \'enabled\' where name = '%s' ", arr.get(5));
            con.connect();
            con.insert(updateRule);
            con.disconnect();
        } catch (Exception e) {e.printStackTrace();
        }


    }

    public void changeState(String table, String status, String name){
        connect();
        String s = "";
        if (status.equals("enabled")) s = "NOVALIDATE";
        String sql = String.format("alter table %s %s %s constraint %s",table , status.substring(0, status.length()-1),s, name);
        insert(sql);
        disconnect();
    }
}
