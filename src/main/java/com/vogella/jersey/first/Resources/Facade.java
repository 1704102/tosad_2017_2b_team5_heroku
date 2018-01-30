package com.vogella.jersey.first.Resources;

import com.vogella.jersey.first.Model.Business_Rule;
import com.vogella.jersey.first.Model.ClassController;
import com.vogella.jersey.first.Model.Database;

import java.util.ArrayList;

/**
 * Created by marti on 30-1-2018.
 */
public class Facade {

    ClassController cC;

    public Facade(){
        cC = new ClassController();
    }

    public String loadDatabase(String name, String port, String service, String username, String password, int id){
        return cC.loadDatabase(name,port,service,username,password,id);
    }

    public String getTablesString(String url){
        StringBuilder sB = new StringBuilder();
        ArrayList<String> tables = cC.getTables(url);
        for(String table : tables){
            sB.append(table + "\n");
        }
        return sB.toString();
    }

    public String getColumnString(String url, String table){
        StringBuilder sB = new StringBuilder();
        ArrayList<String> columns = cC.getColumns(url, table);
        for(String column : columns){
            sB.append(column + "\n");
        }
        return sB.toString();
    }

    public String getDatabasesString(int id){
        StringBuilder s = new StringBuilder();
        ArrayList<String> databases = cC.getRepConnector().getDatabases(id);
        for(String database: databases){

            String[] split = database.split(",");
            String url = split[0];
            String port = split[1];
            String service = split[2];
            String username = split[3];
            String password = split[4];

            s.append(url+ "," + port + "," + service + "\n");
            cC.loadDatabase(url, port,service,username,password,id);
        }
        return s.toString();
    }

    public String getRulesString(String url){
        StringBuilder s = new StringBuilder();
        ArrayList<String> rules = cC.getRules(url);
        for (String rule : rules){
            s.append(rule + "\n");
        }
        return s.toString();
    }

    public String saveRule(String url, String type,String operator, String value1, String value2, String column1, String column2, String table1, String table2){
        cC.getRepConnector().saveRule(url,type,operator,value1,value2,column1,column2,table1,table2);

        String[] data = cC.getRepConnector().getLastRule().split(",");
        cC.crateRule(Integer.parseInt(data[0]), data[1], "new", url,type,operator,value1,value2,column1,column2,table1,table2);

        return "succes";
    }

    public String alterRule(String url, String name, String status){
        cC.editBusinessRule(url, name, status);

        if (status.equals("enabled") || status.equals("disabled")){
            String table =cC.getDatabase(url).getBusiness_Rule(name).getTable1().getName();
            cC.getDatabase(url).getTargetConnector().changeState(table, status, name);
        }
        cC.getRepConnector().alterRule(url,name,status);
        return "succes";
    }

    public String commitRules(String url){
        cC.commitRules(url);
        return "succes";
    }
}
