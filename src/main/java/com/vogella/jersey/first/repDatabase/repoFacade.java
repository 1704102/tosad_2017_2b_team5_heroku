package com.vogella.jersey.first.repDatabase;

import java.util.ArrayList;

public class repoFacade {
    private RepConnector rC;
    public repoFacade() {
        rC = new RepConnector();
    }


    public String login(String username, String password){

        return rC.login(username,password);
    }

    public void addDatabase(String url, String port, String service, String username, String password, int id) {
       rC.addDatabase(url,port,service,username,password,id);
    }

    public void linkDatabase(String url, int id){
       rC.linkDatabase(url,id);
    }

    public ArrayList<String> getRules(String url){
        return rC.getRules(url);
    }

    public ArrayList<String> getDatabases(int id){
        return rC.getDatabases(id);
    }

    public void saveRule(String url, String type,String operator, String value1, String value2, String column1, String column2, String table1, String table2){
        rC.saveRule(url,type,operator,value1,value2,column1,column2,table1,table2);
    }

    public void alterRule(String url, String name, String status){
        rC.alterRule(url,name,status);
    }

    public String getLastRule(){
        return rC.getLastRule();
    }
    public void updateRuletoactive(String bName){
        rC.updateRuletoactive(bName);

    }
}
