package com.vogella.jersey.first.Model;

public class Business_Rule {
    private Operator operator;
    private int value1, value2;
    private Table table1, table2;
    private Column column1, column2;
    private String type;

    public Business_Rule(int value1, int value2,Table table1,Column column1){
        this.value1=value1;
        this.value2=value2;
        this.table1=table1;
        this.column1=column1;
        type="rangeRule";
    }
    public Business_Rule(Table table1, Table table2, Column column1, Column column2, String operator){
        this.table1 = table1;
        this.table2 = table2;
        this.column1 = column1;
        this.column2 = column2;
        Operator o = new Operator(operator);
        this.operator = o;



    }
}
