package com.vogella.jersey.first;

import com.vogella.jersey.first.Model.ClassController;
import com.vogella.jersey.first.Model.Database;
import com.vogella.jersey.first.repDatabase.RepConnector;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by marti on 13-6-2017.
 */

@Path("database")
public class LoginResource {

    @POST
    @Path("/{param1}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String Login(@PathParam("param1") String url) {
        ClassController cC = new ClassController();

        StringBuilder tables = new StringBuilder();
        RepConnector c = new RepConnector(url, "8521","cursus02.hu.nl", "tosad_2017_2b_team5_target", "tosad_2017_2b_team5_target");
        //RepConnector c = new RepConnector("ondora02.hu.nl", "8521","cursus02.hu.nl", "tosad_2017_2b_team5_target", "tosad_2017_2b_team5_target");
        Database dat = new Database("target");

        cC.loadDatabase("ondora", c.GetDatabase());



        return "succes";
    }
}