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
                saveRule(br);
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
                    return "Column not found";
                }
            }
                Operator operator= new Operator(operatorS);
                if(operator.getSucces()==false){
                    return "incorrect operator entered or recieved try sending our one of the follwing \"=,>,<,!=,>=,<=, or < \" ";
                }
            Business_Rule br;
            br = new Business_Rule(table, column, operator);
            saveRule(br);
            return "succes";

        }

        return "falliure to load database because no databasename that exist was given with the code";
    }
    public String makeTupleRangeRule(String table1s, String column1s, String column2s, String operators,String databaseName){
        for(Database d : databases){
            Column column1= null;
            Column column2= null;
            Table table = null;
            Operator operator = null;
            if(d.getName().equals(databaseName)) {
                table=d.getTable(table1s);
                if(table==null){
                    return "table not found";
                }
                column1=table.getColumn(column1s);
                if(column1==null){
                    return "table not found";
                }
                column2=table.getColumn(column2s);
                if(column2==null){
                    return "Column not found";
                }
                operator= new Operator(operators);
                if(operator.getSucces()==false){
                    return "incorrect operator entered or recieved try sending our one of the follwing \"=,>,<,!=,>=,<=, or < \" ";
                }
                Business_Rule br;
                br= new Business_Rule(table, column1, column2, operator);
                saveRule(br);
                return "succes";
            }
        }
        return "falliure to load database because no databasename that exist was given with the code";

    }

    public void saveRule(Business_Rule br){
        ArrayList<String> saveRules= new ArrayList();
        String type =br.getType();
        if (type.equals("rangeRule")){
            String s = ""+br.getValue1();
            String s2 = ""+br.getValue2();
            String s3 = ""+br.getTable1().getName();
            String s4 = ""+br.getColumn1().getName();
            saveRules.add(s);
            saveRules.add(s2);
            saveRules.add(s3);
            saveRules.add(s4);
        }
        if (type.equals("attributerule")) {
            String s = ""+ br.getValue1();
            String s2 = ""+ br.getTable1().getName();
            String s3 = ""+ br.getColumn1().getName();
            String s4 = ""+ br.getOperator().getOperator();
            saveRules.add(s);
            saveRules.add(s2);
            saveRules.add(s3);
            saveRules.add(s4);
        }
        if (type.equals("tupleRule")){
            String s = ""+br.getValue1();
            String s2 = ""+ br.getTable1().getName();
            String s3 = ""+ br.getColumn1().getName();
            String s4 = ""+ br.getTable2().getName();
            saveRules.add(s);
            saveRules.add(s2);
            saveRules.add(s3);
            saveRules.add(s4);
        }

    }

}
