package com.vogella.jersey.first.DOA;
import java.util.ArrayList;
import java.util.HashMap;

public class DOAFacade {
    private TargetConnector tc;
    public DOAFacade(String url, String port, String service, String username, String password) {
        tc = new TargetConnector(url, port, service, username, password);
    }
    public void connect(){
        tc.connect();
    }
    public HashMap<String, ArrayList<String>> getDatabaseData(){
        return tc.GetDatabaseData();
    }
    public void disconnect(){
        tc.disconnect();
    }
    public void ChangeState(String table,String status,String name){
        tc.changeState(table,status,name);
    }
    public void makeRule(ArrayList<String> arr){
        tc.makeRule(arr);
    }
}
