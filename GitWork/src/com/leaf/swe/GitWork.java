package com.leaf.swe;

import com.google.gson.Gson;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryIssue;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class GitWork {

	
	public static void main(String[] args) {

		String APIKey = "fire888trainer";
		String teamworkURL = "http://swegroup.teamwork.com";
        String project = "150632";

        //Username GitHub personale
        String GH_user = "";
        //Password GitHub personale
        String GH_psw = "";
        //Username del gestore del repo target (i.e. mzanella)
        String GH_target_repo_username = "";
        //Nome del repo target (i.e. Leaf)
        String GH_target_repo_name = "";
			
		
		try {
            TeamworkService service = new TeamworkService(APIKey, teamworkURL, project);

            XMLSource xml = new XMLSource(service);
            xml.taskListsSource();

            TasklistSet taskListSet = new TasklistSet(xml.getTasklistsMap());

            System.out.println("*** LISTE DEI COMPITI ***\n");
            Map<String,Tasklist> tasklists = taskListSet.getTasklists();
            for (Map.Entry<String,Tasklist> entry : tasklists.entrySet()) {
                System.out.println("    > " + entry.getValue().getNome() + " <"
                        + "ID: " + entry.getKey() + ">");
            }

            //Scegli di quale lista visualizzare i task
            Scanner sc = new Scanner(System.in);
            String scelta = "";
            while (scelta.equals("")){
                System.out.print("\nInserisci ID di una lista:");
                scelta = sc.nextLine();
                if(!tasklists.containsKey(scelta))
                    scelta = "";
            }

            xml.tasksSource(scelta);
            Map<String,Task> tasks = xml.getTaskMap();

            System.out.println("*** TASK ***\n");
            for (Map.Entry<String,Task> entry : tasks.entrySet()) {
                System.out.println("    > " + entry.getValue().getTitle() + " <"
                        + "ID: " + entry.getKey() + ">");
            }

            // TODO: 02/03/2016  Aggiungere i figli di tutti i task

            //I figli di quale task trasformare in issues?
            sc = new Scanner(System.in);
            scelta = "";
            while (scelta.equals("")){
                System.out.print("\nInserisci ID del task i cui sub-task vuoi trasformare in issues:");
                scelta = sc.nextLine();
                if(!tasks.containsKey(scelta))
                    scelta = "";
            }


            Map<String,Subtask> subtasks = xml.getSubtaskMap(Integer.parseInt(scelta));

            if (subtasks.isEmpty())
                System.out.println("QUESTO TASK NON CONTIENE SUBTASK!!!");
            else{
                GitHubClient client = new GitHubClient();
                client.setCredentials(GH_user, GH_psw);
                IssueService is = new IssueService(client);
                RepositoryIssue issue;

                for (Map.Entry<String, Subtask> entry : subtasks.entrySet()) {
                    issue = new RepositoryIssue();
                    issue.setTitle(entry.getValue().getNome());
                    issue.setBody(entry.getValue().getDescrizione());
                    is.createIssue(GH_target_repo_username,GH_target_repo_name,issue);
                    System.out.println("<" + entry.getValue().getNome() + "> ---> ISSUE CREATA!");
                }
            }
		} catch(Exception e) {
			System.out.println( "Error Received:" + e.getMessage() + " " + e.getClass() );
		}
	}

    public static String streamToString(InputStream in) throws IOException {
		StringBuilder out = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		for(String line = br.readLine(); line != null; line = br.readLine()) 
			out.append(line);
		//br.close();
		return out.toString();
	}
}
