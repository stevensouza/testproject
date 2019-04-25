package com.stevesouza;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Hello world!
 */
public class App {

    private static final Logger LOG = Logger.getLogger(App.class);
    private static final String urlStr = "https://www.virustotal.com/intelligence/hunting/notifications-feed/?key=9e799068d917ea16744988d059cb156d60e2a09d879954d1a8865e865965b7d4";

    public static void main(String[] args) throws IOException {
        urlWithOpenStream();
        urlWithConnection();
        javaProps();
        jamon();
        urlToJson();
    }


//    String urlStr =  (String) source.getConfiguration().get("url");
//    JsonNode jsonNode = getUrlRequestAsJsonNode(urlStr);
//    ObjectMapper mapper = new ObjectMapper();
//    mapper.writerWithDefaultPrettyPrinter().writeValue(new File("/Users/ssouza/delme/delme.json"), jsonNode);
//    LOG.info(jsonNode.toString());

    private static void urlWithOpenStream() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(new URL(urlStr).openStream()));
        String inputLine;
        while ((inputLine = input.readLine()) != null)
            LOG.info(inputLine);

        input.close();
    }

    private static void urlToJson() throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(new URL(urlStr).openStream()));
        ObjectMapper mapper = new ObjectMapper();

        JsonNode rootNode = mapper.readTree(input);
        String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        LOG.info(prettyJson);

        input.close();
    }

    private static void jamon() {
        Monitor mon = MonitorFactory.add("key", "units", 100);
        LOG.info("mon=" + mon);
    }

    private static void urlWithConnection() throws IOException {
        URL url = new URL("http://example.com");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        System.out.println(connection.getContent());

        int code = connection.getResponseCode();
        LOG.info("http status=" + code);
    }

    private static void javaProps() {
        for (Map.Entry<Object, Object> obj : System.getProperties().entrySet()) {
            LOG.info(obj);
        }
    }
}
