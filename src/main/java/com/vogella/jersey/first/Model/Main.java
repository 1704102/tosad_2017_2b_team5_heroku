package com.vogella.jersey.first.Model;

public class Main {

    public static void main(String[] args) {
	// write your code here
    Table t = new Table("table");
    t.addColumn("fuck");
    t.addColumn("shit");
    t.addColumn("hoer");
    t.addColumn("hoer");
    System.out.println(t.getColumns());

    }
}
