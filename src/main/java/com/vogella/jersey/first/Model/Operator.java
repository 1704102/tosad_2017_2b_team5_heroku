package com.vogella.jersey.first.Model;

public class Operator {
    private String operator;
    private Boolean succes;
    // this constructor has a safety feature added to make sure that commonly used ways of entering operators get converted to correct for errors;
    public Operator(String operator) {
        if (operator.equals("less then")){
            operator="<";
        }
        if (operator.equals("more then")){
            operator=">";
        }
        if (operator.equals("equals")){
            operator="=";
        }
        if (operator.equals("less then or equal")){
            operator="=<";
        }
        if (operator.equals("more then or equal")){
            operator="<";
        }
        if(operator.equals(">") || operator.equals("=") || operator.equals("<") || operator.equals(">=")|| operator.equals("<=")|| operator.equals("!=")) {

            this.operator = operator;
            succes=true;
        }
        else{
            succes=false;
        }
    }

    public String getOperator() {
        return operator;
    }

    public Boolean getSucces() {
        return succes;
    }

    public void setOperator(String operator) {
        if(operator.equals(">")||operator.equals("=")||operator.equals("<")|| operator.equals(">=")|| operator.equals("<=")|| operator.equals("!=") ){
            this.operator = operator;
        }
    }
}
