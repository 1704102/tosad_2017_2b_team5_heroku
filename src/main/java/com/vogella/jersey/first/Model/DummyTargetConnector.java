package com.vogella.jersey.first.Model;
import com.vogella.jersey.first.DOA.DOAFacade;

import java.util.ArrayList;
import java.util.HashMap;

public class DummyTargetConnector {

    private DOAFacade doaFacade;
    public DummyTargetConnector(String url, String port, String service, String username, String password) {

        doaFacade = new DOAFacade(url,port,service,username,password);
    }
    public void connect() {
        doaFacade.connect();

    }


    public HashMap<String, ArrayList<String>> GetDatabaseData() {
        return doaFacade.getDatabaseData();
    }

    public void disconnect() {
        doaFacade.disconnect();
    }

    public void changeState(String table,String status,String state){
        doaFacade.ChangeState(table,status,state);
    }
    public void makeRule(ArrayList<String> arr){
        doaFacade.makeRule(arr);
    }

}
