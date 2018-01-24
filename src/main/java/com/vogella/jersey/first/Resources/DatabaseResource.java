package com.vogella.jersey.first.Resources;

import com.vogella.jersey.first.Model.ClassController;
import com.vogella.jersey.first.DOA.TargetConnector;
import com.vogella.jersey.first.repDatabase.RepConnector;

import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.ResultSet;

/**
 * Created by marti on 13-6-2017.
 */

@Path("database")
public class DatabaseResource{

    @GET
    @Path("/init")
    @Produces(MediaType.APPLICATION_JSON)
    public String Login(@QueryParam("url")  String url,@QueryParam("port")  String port,@QueryParam("service")  String service,@QueryParam("username")  String username,@QueryParam("password")  String password,@QueryParam("id")  int id) {
        try {
            ClassController cC = new ClassController();
            RepConnector rC = new RepConnector();
            rC.addDatabase(url,port,service,username,password,id);
            TargetConnector c = new TargetConnector(url, port, service, username, password);


            //TargetConnector c = new TargetConnector("ondora02.hu.nl", "8521", "cursus02.hu.nl", "tosad_2017_2b_team5_target", "tosad_2017_2b_team5_target");

            cC.loadDatabase(url, c.GetDatabase());

            return "succes";
        }catch (Exception e){
            return "wrong input data";
        }

    }

    @GET
    @Path("/tables")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTables(){


        JsonArray value = Json.createArrayBuilder()
                .add(Json.createObjectBuilder()
                        .add("type", "home")
                        .add("number", "212 555-1234"))
                .add(Json.createObjectBuilder()
                        .add("type", "fax")
                        .add("number", "646 555-4567"))
                .build();
        JsonObject o = Json.createObjectBuilder().add("objects", value).build();
        return o    .toString();
    }

    @GET
    @Path("/databases")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDatabases(@QueryParam("url")  int id){
        StringBuilder s = new StringBuilder();
        RepConnector con = new RepConnector();
        try {
            con.connect();
            ResultSet set = con.select(String.format("select * from klant_database_target, database_target where inlogid = %d and a.database_target_id = id", id));
            while (set.next()){
                s.append(set.getString("url") + ",");
                s.append(set.getString("port") + ",");
                s.append(set.getString("service"));

            }
            con.disconnect();
        }catch (Exception e){}
        return s.toString();
    }
}