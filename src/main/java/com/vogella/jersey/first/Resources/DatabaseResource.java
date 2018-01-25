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
            ArrayList<String> rules = con.getRules(url);
            for (String rule : rules){
                s.append(rule + "\n");
            }
            con.disconnect();
        }catch (Exception e){}
        return s.toString();
    }
}