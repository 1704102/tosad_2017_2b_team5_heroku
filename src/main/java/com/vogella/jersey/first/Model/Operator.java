package com.vogella.jersey.first.Model;

public class Operator {
    private String operator;

    public Operator(String operator) {

        if(operator== ">" || operator== "=" || operator =="<" || operator == ">="|| operator=="<=") {
            this.operator = operator;
        }
    }

    public String getOperator() {

        return operator;
    }

    public void setOperator(String operator) {
        if(operator== ">" || operator== "=" || operator =="<" || operator == ">="|| operator=="<=") {
            this.operator = operator;
        }
    }
}
