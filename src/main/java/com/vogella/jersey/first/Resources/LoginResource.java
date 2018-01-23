package com.vogella.jersey.first.Resources;

import com.vogella.jersey.first.repDatabase.RepConnector;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * Created by marti on 23-1-2018.
 */
@Path("login")
public class LoginResource {
    @GET
    public String login(@QueryParam("username")  String username, @QueryParam("password")  String password){
        RepConnector con = new RepConnector();
        if (con.login(username, password)){return "true";}else {return "false";}
    }
}
