package com.leaf.swe;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Contiene l'InputStream ottenuto dalla connessione a Teamwork. Bisogna valutare ogni espressione XPath su XMLSource.root in quanto
 * il metodo evaluate() chiude lo stream e non sarebbe possibile eseguire più valutazioni sullo stesso dopo la prima passata.
 */
public class XMLSource {
    TeamworkService service;
    InputSource inputSource;
    Node root;

    public XMLSource(TeamworkService service) {
        this.service = service;
    }

    public Node getRoot() {
        return root;
    }

    public void taskListsSource() throws IOException {
        HttpURLConnection connection = service.connectToTasklists();
        inputSource = service.createInputSource(connection);
        setRoot();
    }

    public void tasksSource(String id) throws IOException {
        HttpURLConnection connection = service.connectToTasks(id);
        inputSource = service.createInputSource(connection);
        setRoot();
    }

    public Map<String,Tasklist> getTasklistsMap() {
        Map<String,Tasklist> tasklists = new HashMap<>();
        NodeList tasklist_names = service.getNodes("//tasklist/name/text()",root);
        NodeList tasklist_id = service.getNodes("//tasklist/id/text()",root);
        NodeList tasklist_desc = service.getNodes("//tasklist/description/text()",root);
        NodeList tasklist_complete = service.getNodes("//tasklist/complete/text()",root);

        for (int i=0; i < tasklist_id.getLength(); i++) {
            String id = tasklist_id.item(i).getTextContent();

            String nome = tasklist_names.item(i).getTextContent();

            String ok = tasklist_complete.item(i).getTextContent();
            boolean complete = false;
            if(ok.equals("true")) {
                complete = true;
            }

            String description = "";
            try{
                description = tasklist_desc.item(i).getTextContent();
            }
            catch(NullPointerException e){
                e.getMessage();
            }

            Tasklist tasklist = new Tasklist(id, nome, description, complete);
            tasklists.put(id,tasklist);
        }

        return tasklists;
    }

    public Map<String,Task> getTaskMap() {
        Map<String,Task> tasks = new HashMap<>();

        NodeList task_title = service.getNodes("/todo-items/todo-item[has-predecessors>0]/content/text()",root);
        NodeList task_id = service.getNodes("/todo-items/todo-item[has-predecessors>0]/id/text()",root);
        NodeList task_parent_id = service.getNodes("/todo-items/todo-item[has-predecessors>0]/todo-list-id/text()",root);

        for (int i=0; i < task_id.getLength(); i++) {
            String id = task_id.item(i).getTextContent();
            String title = task_title.item(i).getTextContent();
            String parent_id = task_parent_id.item(i).getTextContent();

            Task task= new Task(parent_id, id, title);
            tasks.put(id,task);
        }

        return tasks;
    }

    public Map<String,Subtask> getSubtaskMap(int parentTask_id){
        Map<String,Subtask> subtasks = new HashMap<>();

        NodeList subtask_name = service.getNodes("//predecessor[parent-task-id="+ parentTask_id +"]/name/text()",root);
        NodeList subtask_id = service.getNodes("//predecessor[parent-task-id="+ parentTask_id +"]/id/text()",root);
        NodeList subtask_parent_id = service.getNodes("//predecessor[parent-task-id="+ parentTask_id +"]/parent-task-id/text()",root);

        for (int i=0; i < subtask_id.getLength(); i++) {
            String id = subtask_id.item(i).getTextContent();
            String name = subtask_name.item(i).getTextContent();
            String parent_id = subtask_parent_id.item(i).getTextContent();

            NodeList subtask_description = service.getNodes("//todo-item[id="+ Integer.parseInt(id) +"]/description/text()",root);
            String description = "";
            try{
                description = subtask_description.item(i).getTextContent();
            }
            catch(NullPointerException e){
                e.getMessage();
            }

            Subtask subtask= new Subtask(parent_id, id, name, description);
            subtasks.put(id,subtask);
        }

        return subtasks;
    }

    public void setRoot() {
        XPath xpath = XPathFactory.newInstance().newXPath();
        try {
            this.root = (org.w3c.dom.Node) xpath.evaluate("/", inputSource, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    public InputSource getInputSource() {
        return inputSource;
    }
}
