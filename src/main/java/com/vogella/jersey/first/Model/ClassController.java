package com.vogella.jersey.first.Model;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("ALL")
public class ClassController {
    private ArrayList<Database> databases= new ArrayList();
    public ClassController(){}

    public void loadDatabase(String name, HashMap<String,ArrayList<String>> database){
        Database d = new Database(name);
        for ( String key : database.keySet() ) {
            d.addtable(key);
            for(int i = 0; i < database.get(key).size(); i++){
                d.getTable(key).addColumn(database.get(key).get(i));
            }
        }
        databases.add(d);
    }

    public boolean makeBusinessRangeRule(int value1,int value2,  String table1, String Column1,String databaseName ){
       boolean b =false;
        for(Database d : databases){
            if(d.getName().equals(databaseName)){
                Table table=d.getTable(table1);
                Column column =table.getColumn(Column1);
                Business_Rule br = new Business_Rule(value1, value2,table, column);
                d.addBusinessRules(br);
                b=true;
            }
        }
        return b;
    }
    public String makeBusinessAtrributeRule(int value1,String table1, String column1,String databaseName, String operatorS){
        for(Database d : databases){
            Column column= null;
            Table table = null;
            if(d.getName().equals(databaseName)){
                table=d.getTable(table1);
                if(table==null){
                    return "table not found";
                }
                column=table.getColumn(column1);
                if(column==null){
                    return "table not found";
                }
            }
                Operator operator= new Operator(operatorS);
                if(operator.getSucces()==false){
                    return "incorrect operator entered or recieved try sending our one of the follwing \"=,>,<,!=,>=,<=, or < \" ";
                }
            Business_Rule br;
            br = new Business_Rule(table, column, operator);

        }

        return "falliure to load database because no databasename that exist was given with the code";
    }

}
