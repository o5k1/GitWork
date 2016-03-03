package com.leaf.swe;


import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Rappresenta il contenitore di tutte le "Liste dei compiti" di Teamwork, relative al progetto corrente.
 */
public class TasklistSet {
    //TeamworkService service;
    //XMLSource xml;
    Map<String,Tasklist> tasklists;

    public TasklistSet() { tasklists = new HashMap<>();}

    public TasklistSet(Map<String, Tasklist> tasklists) { this.tasklists = tasklists;}

    public Map<String, Tasklist> getTasklists() { return tasklists;}

    /*public TasklistSet(TeamworkService service) throws IOException {
        this.service = service;
        HttpURLConnection connection = service.createConnectionToTasklists();
        InputSource inputSource = service.createInputSource(connection);
        xml = new XMLSource(inputSource);
        tasklists = new HashMap<>();
    }*/

    /*public void initializeWithCurrentTaskSet(){
        NodeList tasklist_names = service.getNodes("//tasklist/name/text()",xml.getRoot());
        NodeList tasklist_id = service.getNodes("//tasklist/id/text()",xml.getRoot());
        NodeList tasklist_desc = service.getNodes("//tasklist/description",xml.getRoot());
        NodeList tasklist_complete = service.getNodes("//tasklist/complete/text()",xml.getRoot());

        //Mostra tutte le tasklist del progetto corrente + relativo ID
        int i = 0;
        for (; i < tasklist_id.getLength(); i++) {

                String id = tasklist_id.item(i).getTextContent();

                String nome = tasklist_names.item(i).getTextContent();

                String description = tasklist_desc.item(i).getTextContent();

                String ok = tasklist_complete.item(i).getTextContent();
                boolean complete = false;
                if(ok.equals("true")) {
                    complete = true;
                }

                //System.out.println(id + " " );
                Tasklist tasklist = new Tasklist(id, nome, description, complete);
                tasklists.put(id,tasklist);
        }
    }*/
}
