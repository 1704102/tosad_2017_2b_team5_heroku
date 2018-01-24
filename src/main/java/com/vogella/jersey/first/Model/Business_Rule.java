package com.vogella.jersey.first.Model;

public class Business_Rule {
    String brName;
    private Operator operator;
    private int value1, value2;
    private Table table1, table2;
    private Column column1, column2;
    private String type;
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
}