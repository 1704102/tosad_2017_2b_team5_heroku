package com.vogella.jersey.first.Model;

public class Business_Rule {
    String brName;
    private Operator operator;
    private int value1, value2;
    private Table table1, table2;
    private Column column1, column2;
    private String type;

    public String getRulename() {
        return Rulename;
    }

    public void setRulename(String rulename) {
        Rulename = rulename;
    }

    private String Rulename;
    //This is the constructor for range rules
    public Business_Rule(int value1, int value2,Table table1,Column column1){
        this.value1=value1;
        this.value2=value2;
        this.table1=table1;
        this.column1=column1;
        type="rangeRule";
        validateRangeRule();
    }
    //This is the constructor for tuple rules
    public Business_Rule(Table table1, Column column1, Column column2, Operator operator){
        this.table1 = table1;
        this.column1 = column1;
        this.column2 = column2;
        this.operator = operator;
        type= "tupleRule";
    }
    //This is the constructor for attribute rules
    public Business_Rule(Table table1, Column column1,Operator operator){
        this.table1=table1;
        this.column1=column1;
        this.operator=operator;
        type="attributerule";
    }
    //A validationCheck in place for range rule to make sure value1 has a higher value then value2.
// This prevents problems when creating the final business rules because they will not work when the values are in the opposite order
    private void validateRangeRule(){
        if (value1>value2){
            int rotatevalue;
            rotatevalue= value1;
            value1=value2;
            value2=rotatevalue;
        }
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public int getValue1() {
        return value1;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public int getValue2() {
        return value2;
    }

    public void setValue2(int value2) {
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
}