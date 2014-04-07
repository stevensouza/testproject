package com.stevesouza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    }

    private static void urlWithOpenStream() throws IOException {
        URL url = new URL(urlStr);

//        URLConnection yc = oracle.openConnection();
//        BufferedReader in = new BufferedReader(new InputStreamReader(
//                yc.getInputStream()));
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
}
