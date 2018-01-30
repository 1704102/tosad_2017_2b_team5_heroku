package com.vogella.jersey.first.Resources;

import com.vogella.jersey.first.Model.ClassController;
import com.vogella.jersey.first.DOA.TargetConnector;
import com.vogella.jersey.first.Model.Database;
import com.vogella.jersey.first.repDatabase.RepConnector;

import javax.json.*;
import javax.print.DocFlavor;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by marti on 13-6-2017.
 */

@Path("database")
public class DatabaseResource{
    public static ClassController cC;
    @GET
    @Path("/init")
    @Produces(MediaType.APPLICATION_JSON)
    public String Login(@QueryParam("url")  String url,@QueryParam("port")  String port,@QueryParam("service")  String service,@QueryParam("username")  String username,@QueryParam("password")  String password,@QueryParam("id")  int id) {
        try {
            if(cC == null) {
            cC = new ClassController();
            }
            RepConnector rC = new RepConnector();
            rC.addDatabase(url,port,service,username,password,id);
            TargetConnector c = new TargetConnector(url, port, service, username, password);


          // TargetConnector c = new TargetConnector("ondora02.hu.nl", "8521", "cursus02.hu.nl", "tosad_2017_2b_team5_target", "tosad_2017_2b_team5_target");

            cC.loadDatabase(url, port, service, username, password, c.GetDatabase(), rC.getRules(url));

            return "succes";
        }catch (Exception e){
            e.printStackTrace();
            return "wrong";
        }

    }

    @GET
    @Path("/tables")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTables(@QueryParam("url")  String url){
        StringBuilder sB = new StringBuilder();
        ArrayList<String> tables = cC.getTables(url);
        for(String table : tables){
            sB.append(table + "\n");
        }
        return sB.toString();
    }

    @GET
    @Path("/columns")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTables(@QueryParam("url")  String url,@QueryParam("table")  String table){
        StringBuilder sB = new StringBuilder();
        ArrayList<String> columns = cC.getColumns(url, table);
        for(String column : columns){
            sB.append(column + "\n");
        }
        return sB.toString();
    }

    @GET
    @Path("/databases")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDatabases(@QueryParam("url")  int id){
        StringBuilder s = new StringBuilder();
        RepConnector con = new RepConnector();
        if(cC == null) {
            cC = new ClassController();
        }
        try {
            con.connect();
            ResultSet set = con.select(String.format("select * from klant_database_target, database_target where inlogid = %d and database_target_id = id", id));
            while (set.next()){
                s.append(set.getString("url") + ",");
                s.append(set.getString("port") + ",");
                s.append(set.getString("service"));
                TargetConnector c = new TargetConnector(set.getString("url"),set.getString("port"),set.getString("service"),set.getString("username"),set.getString("password"));
                cC.loadDatabase(set.getString("url"),set.getString("port"),set.getString("service"),set.getString("username"),set.getString("password"), c.GetDatabase(), con.getRules(set.getString("url")));
            }
            con.disconnect();
        }catch (Exception e){e.printStackTrace();}
        System.out.println(s.toString());
        return s.toString();
    }

    @GET
    @Path("/rules")
    @Produces(MediaType.APPLICATION_JSON)
    public String getRules(@QueryParam("url")  String url){
        StringBuilder s = new StringBuilder();
        RepConnector con = new RepConnector();
        try {
            con.connect();
            ArrayList<String> rules = cC.getRules(url);
            for (String rule : rules){
                s.append(rule + "\n");
            }
            con.disconnect();
        }catch (Exception e){e.printStackTrace();}
        return s.toString();
    }

    @GET
    @Path("/rule/save")
    @Produces(MediaType.APPLICATION_JSON)
    public  String saveRule(@QueryParam("url")  String url,@QueryParam("type")  String type,@QueryParam("operator")
            String operator,@QueryParam("value1")  String value1,@QueryParam("value2")  String value2,@QueryParam("column1")
                                    String column1,@QueryParam("column2")  String column2,@QueryParam("table1")  String table1,@QueryParam("table2")  String table2){

        RepConnector con = new RepConnector();
        try {
            con.connect();
            String rule = type + "," + operator + "," + value1 + "," + value2 + "," + column1 + "," + column2 + "," + table1 + "," + table2 + "," + url;
            con.insert(String.format("insert into Businessrule (status, type, operator, database_target_id, value1, value2, column1, column2,table1, table2) values ('new' ,'%s','%s',5,'%s','%s','%s','%s','%s','%s')",type, operator, value1, value2, column1, column2,table1, table2));
            ResultSet s = con.select(" select * from businessrule where id = (select max(id) from BUSINESSRULE)");
            while (s.next()){
                cC.crateRule(rule, s.getInt("id"), s.getString("name"), s.getString("status"));
            }
        }catch (Exception e){e.printStackTrace();return "false";}
        return "succes";
    }


    @GET
    @Path("/rule/alter")
    @Produces(MediaType.APPLICATION_JSON)
    public String saveRule(@QueryParam("url")  String url,@QueryParam("name")  String name,@QueryParam("status")  String status) {
        cC.editBusinessRule(url, name, status);
        RepConnector connector = new RepConnector();
        TargetConnector conn = new TargetConnector("ondora02.hu.nl", "8521", "cursus02.hu.nl", "tosad_2017_2b_team5_target", "tosad_2017_2b_team5_target");
        if (status.equals("enabled") || status.equals("disabled")){
            //String name = cC.getDatabase(url).get
            String sql = String.format("alter table table_name ENABLE constraint %s",status, name);
        }
        try{
            connector.connect();
            connector.insert(String.format("update BUSINESSRULE set status = '%s' where name = '%s' and (select id from database_target where url = '%s') = database_target_id", status, name, url));
            connector.disconnect();
        }catch (Exception e){e.printStackTrace();}
        System.out.println("aa");
        return "";
    }

    @GET
    @Path("/rule/generate")
    @Produces(MediaType.APPLICATION_JSON)
    public String generate(@QueryParam("url")  String url){
       cC.commitRules(url);
       return "true";
    }

}