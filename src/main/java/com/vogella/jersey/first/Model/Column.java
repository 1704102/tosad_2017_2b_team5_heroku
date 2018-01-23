package com.vogella.jersey.first.Model;

public class Column {
    private String name;

    public Column(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean equalsName(String cName){
        boolean b = false;
        if(cName == name){
            b=true;
        }

        return b;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                '}';
    }
}
