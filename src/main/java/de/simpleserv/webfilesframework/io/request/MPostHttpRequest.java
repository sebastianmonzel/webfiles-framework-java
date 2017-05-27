package de.simpleserv.webfilesframework.io.request;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

public class MPostHttpRequest extends MAbstractHttpRequest {

    private final String USER_AGENT = "Mozilla/5.0";

    private String m_sUrlParameters;
    private String m_sDatastoreUrl;

    public MPostHttpRequest(String m_sDatastoreUrl, HashMap<String, String> data) {
        this.m_sDatastoreUrl = m_sDatastoreUrl;
        this.m_sUrlParameters = toParameterString(data);
    }

    private String toParameterString(HashMap<String, String> data) {

        String result = "";

        for (String key : data.keySet()) {
            String value = data.get(key);
            result += key + "=" + value + "&";
        }

        return result;

    }

    public String makeRequest() throws IOException {

        URL url = new URL(m_sDatastoreUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        addRequestHeader(con);
        sendRequest(con, m_sUrlParameters);

        String response = getResponse(con);
        System.out.println(response);

        return response;
    }

    private void addRequestHeader(HttpURLConnection con) throws ProtocolException {
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
    }

    private void sendRequest(HttpURLConnection con, String urlParameters) throws IOException {
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());

        System.out.println("\nSending 'POST' request to URL : " + m_sDatastoreUrl);
        System.out.println("Post parameters : " + m_sUrlParameters);

        wr.writeBytes(urlParameters);

        wr.flush();
        wr.close();
        System.out.println("Response Code : " + con.getResponseCode());
    }

    private String getResponse(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public static void main(String[] args) throws IOException {

        HashMap<String, String> data = new HashMap<String, String>();
        data.put("test","test");
        MPostHttpRequest request = new MPostHttpRequest("https://sebastianmonzel.de/datastore/", data);
        request.makeRequest();
    }
}
