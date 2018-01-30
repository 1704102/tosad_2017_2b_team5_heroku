package com.vogella.jersey.first.repDatabase;


import java.sql.*;
import java.util.ArrayList;

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
        }catch (Exception e){e.printStackTrace();}
        return id;
    }

    public void addDatabase(String url, String port, String service, String username, String password, int id) {
        try {
            connect();
            ResultSet s = select(String.format("select * from database_target where url = '%s'", url));

            if (getResLength(s) < 1) {
                insert(String.format("insert into database_target(url,port,service,username,password) values ('%s', '%s', '%s', '%s', '%s')", url, port, service, username, password));
                System.out.println("added database");
            }

            ResultSet s1 = select(String.format("select * from database_target a, KLANT_DATABASE_TARGET b where a.url = '%s' and a.id = b.database_target_id and b.INLOGID = %d", url, id));
            ResultSet s2 = select(String.format("select * from database_target where url = '%s'", url));
            while (s2.next()){
                if (getResLength(s1) < 1){
                    int idD = s2.getInt("id");
                    insert(String.format("insert into klant_database_target(database_target_id, inlogid) values (%d, %d)",idD,id));
                }
            }


            disconnect();
        } catch (Exception e) {
            e.printStackTrace();
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
        }catch (Exception e){e.printStackTrace();}
        return s;
    }

    public void insert(String query){
        try {
            Statement t = conn.createStatement();
                    t.execute(query);
           // conn.commit();
        }catch (Exception e){e.printStackTrace();}
    }

    public int getResLength(ResultSet s){
        int count = 0;
        try {
            while (s.next()) {
                count++;
            }
        }catch (Exception e){e.printStackTrace();}
        return count;
    }
    public ArrayList<String> getRules(String url){
        ArrayList<String> rules = new ArrayList<String>();
        try {
            connect();
            ResultSet s = select(String.format("select * from BUSINESSRULE a, DATABASE_TARGET b where b.url = '%s' and a.database_target_id = b.id", url));
            while (s.next()){
                String[] data = {"id", "name", "status", "type", "operator", "database_target_id", "value1", "value2", "column1", "column2", "table1", "table2"};
                StringBuilder str = new StringBuilder();
                for (String dataT : data){
                    str.append(s.getString(dataT) + ",");
                }
                str.append(url);
                rules.add(str.toString());
                System.out.println(str.toString());
            }
            disconnect();
        }catch (Exception e){e.printStackTrace();}
        return rules;
    }

    public ArrayList<String> getDatabases(int id){
        ArrayList<String> databases = new ArrayList<String>();
        try {
            connect();
            ResultSet set = select(String.format("select * from klant_database_target, database_target where inlogid = %d and database_target_id = id", id));
            while (set.next()) {
                StringBuilder s = new StringBuilder();
                s.append(set.getString("url") + ",");
                s.append(set.getString("port") + ",");
                s.append(set.getString("service") + ",");
                s.append(set.getString("username") + ",");
                s.append(set.getString("password"));

                databases.add(s.toString());
            }
            disconnect();
        }catch (Exception e){e.printStackTrace();}
        return databases;
    }

    public void saveRule(String url, String type,String operator, String value1, String value2, String column1, String column2, String table1, String table2){
        connect();
        insert(String.format("insert into Businessrule (status, type, operator, database_target_id, value1, value2, column1, column2,table1, table2) values ('new' ,'%s','%s',5,'%s','%s','%s','%s','%s','%s')",type, operator, value1, value2, column1, column2,table1, table2));
        disconnect();
    }

    public void alterRule(String url, String name, String status){
        connect();
        insert(String.format("update BUSINESSRULE set status = '%s' where name = '%s' and (select id from database_target where url = '%s') = database_target_id", status, name, url));
        disconnect();
    }

    public String getLastRule(){
        StringBuilder sB = new StringBuilder();
        try {
            connect();
            ResultSet s = select(" select * from businessrule where id = (select max(id) from BUSINESSRULE)");
            while (s.next()) {
                sB.append(s.getInt("id") + ",");
                sB.append(s.getInt("name"));
            }
            disconnect();
        }catch (Exception e){}
        return s.toString();
    }
}


