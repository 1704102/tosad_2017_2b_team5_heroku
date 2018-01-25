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

    public Business_Rule makeBusinessRangeRule(int value1,int value2,  String table1, String Column1,String databaseName ){
       boolean b =false;
        for(Database d : databases){
            if(d.getName().equals(databaseName)){
                Table table=d.getTable(table1);
                Column column =table.getColumn(Column1);
                Business_Rule br = new Business_Rule(value1, value2,table, column);
                d.addBusinessRules(br);
                return(br);
            }
        }
        return null;

    }
    public Business_Rule makeBusinessAtrributeRule(int value1,String table1, String column1,String databaseName, String operatorS){
        for(Database d : databases){
            Column column= null;
            Table table = null;
            if(d.getName().equals(databaseName)){
                table=d.getTable(table1);
                if(table==null){
                    return null;
                }
                column=table.getColumn(column1);
                if(column==null){
                    return null;
                }
            }
                Operator operator= new Operator(operatorS);
                if(operator.getSucces()==false){
                    return null;
                }
            Business_Rule br;

            br = new Business_Rule(table, column, operator);
            d.addBusinessRules(br);
            return br;

        }

        return null;
    }
    public Business_Rule makeTupleCompareRule(String table1s, String column1s, String column2s, String operators, String databaseName){
        for(Database d : databases){
            Column column1= null;
            Column column2= null;
            Table table = null;
            Operator operator = null;
            if(d.getName().equals(databaseName)) {
                table=d.getTable(table1s);
                if(table==null){
                    return null;
                }
                column1=table.getColumn(column1s);
                if(column1==null){
                    return null;
                }
                column2=table.getColumn(column2s);
                if(column2==null){
                    return null;
                }
                operator= new Operator(operators);
                if(operator.getSucces()==false){
                    return null;
                }
                Business_Rule br;
                br= new Business_Rule(table, column1, column2, operator);
                d.addBusinessRules(br);
                return br;
            }
        }
        return null;

    }

    public ArrayList<String> saveRule(String databaseName1,Business_Rule br){
        ArrayList<String> saveRules= new ArrayList();
        String BRtype =br.getType();
        // values first then operator, then tables then columns then database names
        if (BRtype.equals("rangeRule")){
            String value1 = ""+br.getValue1();
            String value2 = ""+br.getValue2();
            String table1 = ""+br.getTable1().getName();
            String column1 = ""+br.getColumn1().getName();
            String databaseName = databaseName1;
            String type = ""+br.getType();
            saveRules.add(value1);
            saveRules.add(value2);
            saveRules.add(table1);
            saveRules.add(column1);
            saveRules.add(databaseName);
            saveRules.add(type);
        }
        if (BRtype.equals("attributerule")) {
            String value1 = ""+ br.getValue1();
            String table1 = ""+ br.getTable1().getName();
            String column1 = ""+ br.getColumn1().getName();
            String operator = ""+ br.getOperator().getOperator();
            String databaseName = databaseName1;
            String type = ""+br.getType();
            saveRules.add(value1);
            saveRules.add(table1);
            saveRules.add(column1);
            saveRules.add(operator);
            saveRules.add(databaseName);
            saveRules.add(type);

        }
        if (BRtype.equals("tupleRule")){
            String value1 = ""+br.getValue1();
            String table = ""+ br.getTable1().getName();
            String column1 = ""+ br.getColumn1().getName();
            String column2 = ""+ br.getColumn2().getName();
            String databaseName = databaseName1;
            String type = ""+br.getType();
            saveRules.add(value1);
            saveRules.add(table);
            saveRules.add(column1);
            saveRules.add(column2);
            saveRules.add(databaseName);
            saveRules.add(type);
        }
        return saveRules;

    }
    public ArrayList<String> Commitsave(String databaseName, Business_Rule br) {
        ArrayList<String>newRule=saveRule(databaseName,br);
        String naam = br.getRulename();
        newRule.add(naam);
        return newRule;
    }
    public void repoSave(String repo){
        //save to reposotory.
    }

    public void loadExistingRules(ArrayList<String> businessrulesData){
        for(String splitted : businessrulesData){
            String[] s = splitted.split(",");
            int id = Integer.parseInt(s[0]);
            String name = s[1];
            String status = s[2];
            String type = s[3];
            String operator = s[4];
            String databaseID = s[5];
            int value1 = Integer.parseInt(s[6]);
            int value2 = Integer.parseInt(s[7]);
            String column1 = s[8];
            String column2 = s[9];
            String table1 = s[10];
            String table2 = s[11];
            String databaseName= s[11];

            if (type == "rangeRule"){
                Business_Rule br = makeBusinessRangeRule(value1,value2,table1,column1,databaseName);
                br.setBrName(name);
                br.setStatus(status);
                br.setId(id);
            }
            if (type == "attributerule"){
                Business_Rule br = makeBusinessAtrributeRule(value1,table1,column1,databaseName,operator);
            }
            if(type == "tupleRule"){
                Business_Rule br = makeTupleCompareRule(table1,column1,column2,operator,databaseName);
            }
        }

    }
    public void targetSave(){}
}
