package de.simpleserv.webfilesframework.datastore.remote;


import de.simpleserv.webfilesframework.datastore.MAbstractDatastore;
import de.simpleserv.webfilesframework.datastore.webfilestream.MWebfileStream;
import de.simpleserv.webfilesframework.datasystem.format.MWebfile;
import de.simpleserv.webfilesframework.io.request.MPostHttpRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class MRemoteDatastore extends MAbstractDatastore {

    private String m_sDatastoreUrl;

    public static String METHOD_NAME_SEARCH_BY_TEMPLATE = "searchByTemplate";
    public static String METHOD_NAME_STORE_WEBFILE = "storeWebfile";
    public static String METHOD_NAME_DELETE_BY_TEMPLATE = "deleteByTemplate";

    public static String PAYLOAD_FIELD_NAME_WEBFILE = "webfile";
    public static String PAYLOAD_FIELD_NAME_TEMPLATE = "template";
    public static String PAYLOAD_FIELD_NAME_METHOD = "method";


    //public static $m__sClassName = __CLASS__;

    public MRemoteDatastore(String datastoreUrl)
    {
        this.m_sDatastoreUrl = datastoreUrl;
    }

    public boolean tryConnect()
    {
        // TODO
        return true;
    }

    public boolean isReadOnly()
    {
        // TODO
        return false;
    }

    private String doRemoteCall(HashMap<String, String> data) throws IOException {
        MPostHttpRequest request = new MPostHttpRequest(this.m_sDatastoreUrl, data);
        String response = request.makeRequest();

        return response;
    }

    public MWebfileStream getWebfilesAsStream() throws IOException {
        return getWebfilesAsStream(null);
    }

    public MWebfileStream getWebfilesAsStream(HashMap<String,String> data) throws IOException {
        String callResult = this.doRemoteCall(data);
        return new MWebfileStream(callResult);
    }

    public List<MWebfile> getWebfilesAsList() throws IOException {
        return getWebfilesAsStream().getWebfiles();
    }

    public List<MWebfile> searchByTemplate(MWebfile template) throws IOException {
        HashMap<String,String> data = new HashMap<String, String>();
        data.put(PAYLOAD_FIELD_NAME_METHOD,METHOD_NAME_SEARCH_BY_TEMPLATE);
        data.put(PAYLOAD_FIELD_NAME_TEMPLATE,template.marshall());

        return getWebfilesAsStream(data).getWebfiles();
    }

    public List<MWebfile> getLatestWebfiles()
    {
        return getLatestWebfiles(5);
    }

    public List<MWebfile> getLatestWebfiles(int count)
    {
        // TODO
        return null;
    }

    public List<MWebfile> getNextWebfileForTimestamp(int time)
    {
        // TODO: Implement getNextWebfileForTimestamp() method.
        return null;
    }


    public void storeWebfile(MWebfile webfile) throws IOException {
        HashMap<String,String> data = new HashMap<String, String>();
        data.put(PAYLOAD_FIELD_NAME_METHOD,METHOD_NAME_STORE_WEBFILE);
        data.put(PAYLOAD_FIELD_NAME_WEBFILE,webfile.marshall());

        this.doRemoteCall(data);
    }

    public void storeWebfile(String webfile) throws IOException {
        HashMap<String,String> data = new HashMap<String, String>();
        data.put(PAYLOAD_FIELD_NAME_METHOD,METHOD_NAME_STORE_WEBFILE);
        data.put(PAYLOAD_FIELD_NAME_WEBFILE,webfile);

        this.doRemoteCall(data);
    }

    public void deleteByTemplate(MWebfile template) throws IOException {
        HashMap<String,String> data = new HashMap<String, String>();
        data.put(PAYLOAD_FIELD_NAME_METHOD,METHOD_NAME_DELETE_BY_TEMPLATE);
        data.put(PAYLOAD_FIELD_NAME_TEMPLATE,template.marshall());

        this.doRemoteCall(data);
    }

}
