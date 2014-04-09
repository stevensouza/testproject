package com.stevesouza;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Hello world!
 *
 */
public class App 
{

    private static String urlStr = "https://www.virustotal.com/intelligence/hunting/notifications-feed/?key=9e799068d917ea16744988d059cb156d60e2a09d879954d1a8865e865965b7d4";



    public static void main( String[] args ) throws IOException {
        urlWithOpenStream();
        urlWithConnection();
        javaProps();
    }


//    String urlStr =  (String) source.getConfiguration().get("url");
//    JsonNode jsonNode = getUrlRequestAsJsonNode(urlStr);
//    ObjectMapper mapper = new ObjectMapper();
//    mapper.writerWithDefaultPrettyPrinter().writeValue(new File("/Users/ssouza/delme/delme.json"), jsonNode);
//    LOG.info(jsonNode.toString());

    private static void urlWithOpenStream() throws IOException {
        URL url = new URL(urlStr);
        BufferedReader input = new BufferedReader(new InputStreamReader(new URL(urlStr).openStream()));
        String inputLine;
        while ((inputLine = input.readLine()) != null)
            System.out.println(inputLine);

        input.close();
    }

    private static void urlToJson() throws IOException {
        URL url = new URL(urlStr);
        BufferedReader input = new BufferedReader(new InputStreamReader(new URL(urlStr).openStream()));

        String inputLine;
        while ((inputLine = input.readLine()) != null)
            System.out.println(inputLine);

        input.close();
    }

    private static void urlWithConnection() throws IOException {
        URL url = new URL("http://example.com");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        System.out.println(connection.getContent());

        int code = connection.getResponseCode();
        System.err.println(code);
    }

    private static void javaProps() {
        System.out.println(System.getProperties());
    }
}
