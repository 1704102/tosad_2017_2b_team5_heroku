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

    @Path("/hello")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String Login() {

        return "succes";
    }
}