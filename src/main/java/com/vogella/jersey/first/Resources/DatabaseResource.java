package com.vogella.jersey.first.Resources;

import com.vogella.jersey.first.Model.ClassController;
import com.vogella.jersey.first.DOA.TargetConnector;
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
            //TargetConnector c = new TargetConnector(url, port, service, username, password);


           TargetConnector c = new TargetConnector("ondora02.hu.nl", "8521", "cursus02.hu.nl", "tosad_2017_2b_team5_target", "tosad_2017_2b_team5_target");

            cC.loadDatabase(url, c.GetDatabase(), rC.getRules(url));

            return "succes";
        }catch (Exception e){
            e.printStackTrace();
            return "wrong input data";
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
        try {
            con.connect();
            ResultSet set = con.select(String.format("select * from klant_database_target, database_target where inlogid = %d and database_target_id = id", id));
            while (set.next()){
                s.append(set.getString("url") + ",");
                s.append(set.getString("port") + ",");
                s.append(set.getString("service"));

            }
            con.disconnect();
        }catch (Exception e){}
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
        }catch (Exception e){}
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
            cC.crateRule(rule);
            con.insert(String.format("insert into Businessrule (status, type, operator, database_target_id, value1, value2, column1, column2,table1, table2) values ('new' ,'%s','%s',5,'%s','%s','%s','%s','%s','%s')",type, operator, value1, value2, column1, column2,table1, table2));
            con.disconnect();
        }catch (Exception e){return "false";}
        return "succes";
    }

    public static void makeRule(ArrayList<String> arr){
        String type = arr.get(0);
        if (type.equals("rangeRule")){

            String value1 = arr.get(1);
            String value2 = arr.get(2);
            String table1 = arr.get(3);
            String column1 = arr.get(4);
            String bName = arr.get(5);

            String sql="alter table "+table1+ " add contraint "+ bName +" check( " + table1+"."+column1+" between "+value1+" and " +value2+" )" ;
        }
        if (type.equals("tupleRule")){
            String operator = arr.get(1);
            String table1 = arr.get(2);
            String column1 = arr.get(3);
            String column2 = arr.get(4);
            String bName = arr.get(5);
            String sql="alter table "+table1+ " add contraint "+ bName +" check( " + table1+"."+column1+" operator "+column2+")" ;
        }
        if (type.equals("attributerule") ){
            String value1 = arr.get(1);
            String operator = arr.get(2);
            String table1 = arr.get(3);
            String column1 = arr.get(4);
            String bName = arr.get(5);
            String sql="alter table "+table1+ " add contraint "+ bName +" check( " + table1+"."+column1+" operator "+value1+")" ;
        }

        // sql code hier
        RepConnector con = new RepConnector();
        try {
            String updateRule = "update businessrule set status = \'complete\' where name = "+ arr.get(5);
            con.connect();
            con.insert(updateRule);
            con.disconnect();
        }catch (Exception e){}

    }
}