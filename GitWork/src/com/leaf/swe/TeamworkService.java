package com.leaf.swe;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Questa classe espone servizi per interagire con Teamwork (Teamwork-side).
 */
public class TeamworkService {

    String APIKey;
    String teamworkURL;
    String project;

    /**
     * Costruttore.
     * @param APIKey = Teamwork > Il mio profilo > Modifica il mio profilo > API & Mobile
     * @param teamworkURL = i.e. https://swegroup.teamwork.com
     * @param project = ID del progetto corrente
     */
    public TeamworkService(String APIKey, String teamworkURL, String project) {
        this.APIKey = APIKey;
        this.teamworkURL = teamworkURL;
        this.project = project;
    }

    /**
     * Crea una connessione HTTP (con RequestMethod GET ed autorizzazione Basic) con url.
     */
    public HttpURLConnection connect(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String userpassword = APIKey + ":" + "";
        String encodedAuthorization = Base64Coder.encodeString( userpassword );
        if (connection != null) {
            connection.setRequestProperty("Authorization", "Basic "+ encodedAuthorization);
        }
        try {
            if (connection != null) {
                connection.setRequestMethod("GET");
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Restituisce la connessione relativa alle task list del progetto corrente.
     * @return
     * @throws MalformedURLException
     */
    public HttpURLConnection connectToTasklists() throws MalformedURLException {
        HttpURLConnection connection = null;
        URL url = new URL(teamworkURL + "/projects" + "/" + project + "/tasklists.xml");
        connection = connect(url);
        return connection;
    }

    /**
     * Restituisce la connessione relativa alle taskdel progetto corrente.
     * @return
     * @throws MalformedURLException
     */
    public HttpURLConnection connectToTasks(String id) throws MalformedURLException {
        HttpURLConnection connection = null;
        URL url = new URL(teamworkURL + "/tasklists/" + id + "/tasks.xml");
        connection = connect(url);
        return connection;
    }

    /**
     * Valuta la stringa XPath expression sull'XML contenuto nello stream derivante da connection.
     * @param expression
     * @return
     */
    public NodeList getNodes(String expression, Node root) {
        NodeList nodes = null;
        try {
            XPath xpath = XPathFactory.newInstance().newXPath();
            nodes = (NodeList) xpath.evaluate(expression, root, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return nodes;
    }

    public InputSource createInputSource(HttpURLConnection connection) throws IOException {
        InputStream responseStream = connection.getInputStream();
        InputSource inputSource = new InputSource(responseStream);
        return inputSource;
    }

}
