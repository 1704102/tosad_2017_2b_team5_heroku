package com.vogella.jersey.first.Model;

import com.vogella.jersey.first.DOA.TargetConnector;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private ArrayList<Table> tables= new ArrayList();
    private ArrayList<Business_Rule> b_Rules= new ArrayList();
    private String name;
    private TargetConnector targetConnector;

    public Database(String name, String port, String service, String username, String password) {
        this.name = name;
        targetConnector = new TargetConnector(name, port, service, username, password);
    }
    public String getName(){
        return name;
    }

    public void addtable(String tablename){
       tables.add(new Table(tablename));
    }

    public void addBusinessRules(Business_Rule br){
        b_Rules.add(br);

    }
    public Table getTable(String s){
        for(Table t : tables){
            if(t.getName().equals(s)){
                return t;
            }
        }
        return null;
    }

    public ArrayList<Table> getTables(){
        return tables;
    }

    public ArrayList<Business_Rule> getRules(){
        return b_Rules;
    }

    public Business_Rule getBusiness_Rule(int id){
        for(Business_Rule b : b_Rules){
            if (id== b.getId()) {
                return b;
            }
        }
        return null;
    }

    public Business_Rule getBusiness_Rule(String name){
        for(Business_Rule b : b_Rules){
            if (name.equals(b.getBrName())) {
                return b;
            }
        }
        return null;
    }

    public TargetConnector getTargetConnector(){
        return targetConnector;
    }
}
