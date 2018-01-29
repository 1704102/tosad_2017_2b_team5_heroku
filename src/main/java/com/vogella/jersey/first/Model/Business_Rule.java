package com.vogella.jersey.first.Model;

import com.vogella.jersey.first.Model.Column;
import com.vogella.jersey.first.Model.Operator;
import com.vogella.jersey.first.Model.Table;

public class Business_Rule {
    private int id;
    private String name;
    private Operator operator;
    private String value1, value2;
    private Table table1, table2;
    private Column column1, column2;
    private String type;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrName() {
        return name;

    }

    public void setBrName(String brName) {
        this.name = brName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRulename() {
        return Rulename;
    }

    public void setRulename(String rulename) {
        Rulename = rulename;
    }

    private String Rulename;
    //This is the constructor for range rules
    public Business_Rule(String value1, String value2,Table table1,Column column1, int id, String name, String status){
        this.value1=value1;
        this.value2=value2;
        this.table1=table1;
        this.column1=column1;
        this.id = id;
        this.name = name;
        this.status = status;
        type="rangeRule";
        validateRangeRule();
    }
    //This is the constructor for tuple rules
    public Business_Rule(Table table1, Column column1, Column column2, Operator operator, int id, String name, String status){
        this.table1 = table1;
        this.column1 = column1;
        this.column2 = column2;
        this.operator = operator;
        this.id = id;
        this.name = name;
        this.status = status;
        type= "tupleRule";
    }
    //This is the constructor for attribute rules
    public Business_Rule(String value, Table table1, Column column1,Operator operator, int id, String name, String status){
        this.table1=table1;
        this.column1=column1;
        this.operator=operator;
        this.value1 = value;
        this.id = id;
        this.name = name;
        this.status = status;
        type="attributerule";
    }
    //A validationCheck in place for range rule to make sure value1 has a higher value then value2.
// This prevents problems when creating the final business rules because they will not work when the values are in the opposite order
    private void validateRangeRule(){
//        if (value1>value2){
//            int rotatevalue;
//            rotatevalue= value1;
//            value1=value2;
//            value2=rotatevalue;
//        }
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public Table getTable1() {
        return table1;
    }

    public void setTable1(Table table1) {
        this.table1 = table1;
    }

    public Table getTable2() {
        return table2;
    }

    public void setTable2(Table table2) {
        this.table2 = table2;
    }

    public Column getColumn1() {
        return column1;
    }

    public void setColumn1(Column column1) {
        this.column1 = column1;
    }

    public Column getColumn2() {
        return column2;
    }

    public void setColumn2(Column column2) {
        this.column2 = column2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString(){

        String column1 = "null";
        String column2 = "null";
        String table1 = "null";
        String table2 = "null";
        if (this.column1 != null)column1 = this.column1.getName();
        if (this.column2 != null)column2 = this.column2.getName();
        if (this.table1 != null)table1 = this.table1.getName();
        if (this.table2 != null)table2 = this.table2.getName();
        return id + "," + name + "," + status + "," + type + "," + operator.getOperator() + "," + value1 + "," + value2 + "," + column1 + "," + column2 + "," + table1 + "," + table2;
    }
}