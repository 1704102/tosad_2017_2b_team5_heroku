package com.vogella.jersey.first.Model;

import java.util.ArrayList;

public class Table {
    private String name;
    private ArrayList<Column> columns = new ArrayList();

    public Table(String name) {
        this.name = name;
    }
    public void addColumn(String name){
        boolean b = false;
        Column c = new Column(name);
        for(Column column : columns) {
            b = column.equalsName(name);

        }
        if(b!=true) {
            columns.add(c);
        }
    }

    public ArrayList<Column> getColumns() {
        return columns;
    }

    public String getName() {
        return name;
    }
    public boolean equalsName(String tName){
        boolean b=false;
        if(name ==  tName){
            b=true;
        }
        return b;
    }
    public Column getColumn(String name){
        for(Column c : columns){
            if(c.getName()==name){
                return c;
            }
        }
        return null;
    }
}
