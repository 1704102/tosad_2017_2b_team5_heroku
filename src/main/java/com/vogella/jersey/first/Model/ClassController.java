package com.vogella.jersey.first.Model;

import com.vogella.jersey.first.Resources.ResourceInterface;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("ALL")
public class ClassController {
    private ArrayList<Database> databases= new ArrayList();
    public ClassController(){}
    private ResourceInterface resourceInterface= new ResourceInterface();

    public void loadDatabase(String name, HashMap<String,ArrayList<String>> database, ArrayList<String> rules){
        Database d = new Database(name);
        for ( String key : database.keySet() ) {
            d.addtable(key);
            for(int i = 0; i < database.get(key).size(); i++){
                d.getTable(key).addColumn(database.get(key).get(i));
            }
        }
        databases.add(d);
        loadExistingRules(rules);
        System.out.println("aa");
    }

    public Business_Rule makeBusinessRangeRule(String value1,String value2,  String table1, String Column1,String databaseName ){
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
    public Business_Rule makeBusinessAtrributeRule(String value1,String table1, String column1,String databaseName, String operatorS){
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

            br = new Business_Rule(value1,table, column, operator);
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
            String value1 = s[6];
            String value2 = s[7];
            String column1 = s[8];
            String column2 = s[9];
            String table1 = s[10];
            String table2 = s[11];
            String databaseName= s[12];

            if (type.equals("rangeRule")){
                Business_Rule br = makeBusinessRangeRule(value1,value2,table1,column1,databaseName);
                br.setBrName(name);
                br.setStatus(status);
                br.setId(id);
            }
            if (type.equals("attributerule")){
                Business_Rule br = makeBusinessAtrributeRule(value1,table1,column1,databaseName,operator);
                br.setId(id);
            }
            if(type.equals("tupleRule")){
                Business_Rule br = makeTupleCompareRule(table1,column1,column2,operator,databaseName);
                br.setId(id);
            }
        }

    }

    public void crateRule(String rule){
        String[] s = rule.split(",");
        String type = s[0];
        String operator = s[1];
        String value1 = s[2];
        String value2 = s[3];
        String column1 = s[4];
        String column2 = s[5];
        String table1 = s[6];
        String table2 = s[7];
        String url= s[8];

        if (type.equals("rangeRule")){
            Business_Rule br = makeBusinessRangeRule(value1,value2,table1,column1,url);
            br.setStatus("new");
        }
        if (type.equals("attributerule")){
            Business_Rule br = makeBusinessAtrributeRule(value1,table1,column1,url,operator);
            br.setStatus("new");
        }
        if(type.equals("tupleRule")){
            Business_Rule br = makeTupleCompareRule(table1,column1,column2,operator,url);
            br.setStatus("new");
        }
    }
    public void targetSave(){}

    public ArrayList<String> getTables(String url){
        ArrayList<String> tables = new ArrayList<String>();
        for(Database dat : databases){
            if (dat.getName().equals(url)){
                for(Table t : dat.getTables()){
                    tables.add(t.getName());
                }
            }
        }
        return tables;
    }

    public Database getDatabase(String name){
        Database database = null;
        for (Database data : databases){
            if (data.getName().equals(name)){database = data;}
        }
        return database;
    }

    public ArrayList<String> getRules(String name){
        ArrayList<String> rules = new ArrayList<String>();
        Database database = getDatabase(name);
        for (Business_Rule rule : database.getRules()){
            rules.add(rule.toString() + "," + database.getName());
        }
        return rules;
    }

    public ArrayList<String> getColumns(String url, String table) {
        ArrayList<String> out = new ArrayList<String>();
        Database database = getDatabase(url);
        ArrayList<Column> columns = database.getTable(table).getColumns();
        for (Column column : columns){
            out.add(column.getName());
        }
        return out;
    }

    public void commitRule(int brID, String databasename){
        ArrayList<String> values= new ArrayList();
        Database d = getDatabase(databasename);
        Business_Rule br = d.getBusiness_Rule(brID);
        String type = br.getType();
        values.add(type);

        if (type.equals("rangeRule")){
            values.add(br.getValue1());
            values.add(br.getValue2());
            values.add(br.getTable1().getName());
            values.add(br.getColumn1().getName());
            values.add(br.getBrName());

        }
        if (type.equals("tupleRule")){
            values.add(br.getOperator().getOperator());
            values.add(br.getTable1().getName());
            values.add(br.getColumn1().getName());
            values.add(br.getColumn2().getName());
            values.add(br.getBrName());

        }
        if (type.equals("attributerule")){
            values.add(br.getValue1());
            values.add(br.getOperator().getOperator());
            values.add(br.getTable1().getName());
            values.add(br.getColumn1().getName());
            values.add(br.getBrName());
        }
        resourceInterface.SaveRule(values);
    }
}
