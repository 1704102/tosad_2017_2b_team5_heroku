package com.vogella.jersey.first.Resources;

import com.vogella.jersey.first.Model.ClassController;
import com.vogella.jersey.first.DOA.TargetConnector;
import com.vogella.jersey.first.repDatabase.RepConnector;

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
        if(cC == null) cC = new ClassController();

        return cC.loadDatabase(url, port, service, username, password,id);

    }

    @GET
    @Path("/tables")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTables(@QueryParam("url")  String url){
        return cC.getTablesString(url);
    }

    @GET
    @Path("/columns")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTables(@QueryParam("url")  String url,@QueryParam("table")  String table){
        return cC.getColumnString(url,table);
    }

    @GET
    @Path("/databases")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDatabases(@QueryParam("url")  int id){
        if(cC == null) cC = new ClassController();
        return cC.getDatabasesString(id);
    }

    @GET
    @Path("/rules")
    @Produces(MediaType.APPLICATION_JSON)
    public String getRules(@QueryParam("url")  String url){
       return cC.getRulesString(url);
    }

    @GET
    @Path("/rule/save")
    @Produces(MediaType.APPLICATION_JSON)
    public  String alterRUle(@QueryParam("url")  String url, @QueryParam("type")  String type, @QueryParam("operator")
            String operator, @QueryParam("value1")  String value1, @QueryParam("value2")  String value2, @QueryParam("column1")
                                    String column1, @QueryParam("column2")  String column2, @QueryParam("table1")  String table1, @QueryParam("table2")  String table2){

        return cC.saveRule(url,type,operator,value1,value2,column1,column2,table1,table2);
    }


    @GET
    @Path("/rule/alter")
    @Produces(MediaType.APPLICATION_JSON)
    public String alterRule(@QueryParam("url")  String url, @QueryParam("name")  String name, @QueryParam("status")  String status) {
       return cC.alterRule(url,name,status);
    }

    @GET
    @Path("/rule/generate")
    @Produces(MediaType.APPLICATION_JSON)
    public String generate(@QueryParam("url")  String url){
       cC.commitRules(url);
       return "true";
    }

}