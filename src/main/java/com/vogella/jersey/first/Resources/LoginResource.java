package com.vogella.jersey.first.Resources;

import com.vogella.jersey.first.repDatabase.repoFacade;

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
        repoFacade con = new repoFacade();
        int id;
        if (con.login(username, password) != "0"){return con.login(username,password);}else {return "false";}
    }
}
