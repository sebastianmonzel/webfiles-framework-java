package de.simpleserv.webfilesframework.datastore.remote;


import de.simpleserv.webfilesframework.datastore.MAbstractDatastore;
import de.simpleserv.webfilesframework.datastore.webfilestream.MWebfileStream;
import de.simpleserv.webfilesframework.datasystem.format.MWebfile;
import de.simpleserv.webfilesframework.io.request.MPostHttpRequest;

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
9
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

    private boolean doRemoteCall()
    {
        doRemoteCall(null);
    }

    private String doRemoteCall(String data)
    {
        MPostHttpRequest request = new MPostHttpRequest(this.m_sDatastoreUrl, data);
        String response = request.makeRequest();

        return response;
    }

    public MWebfileStream getWebfilesAsStream()
    {
        return getWebfilesAsStream(null);
    }

    public MWebfileStream getWebfilesAsStream(String data)
    {
        String callResult = this.doRemoteCall(data);
        return new MWebfileStream(callResult);
    }

    public MWebfile[] getWebfilesAsArray() {
        return getWebfilesAsStream().getWebfiles();
    }

    public MWebfile[] searchByTemplate(MWebfile template)
    {
        String[] data = {};
        data[PAYLOAD_FIELD_NAME_METHOD] = METHOD_NAME_SEARCH_BY_TEMPLATE;
        data[PAYLOAD_FIELD_NAME_TEMPLATE] = template->marshall();

        return $this->getWebfilesAsStream($data)->getWebfiles();
    }

    public MWebfile[] getLatestWebfiles()
    {
        return getLatestWebfiles(5);
    }

    public MWebfile[] getLatestWebfiles(int count)
    {
        // TODO
    }

    public function getNextWebfileForTimestamp(time)
    {
        // TODO: Implement getNextWebfileForTimestamp() method.
        return null;
    }


    public void storeWebfile(MWebfile webfile)
    {

        String[] data = {};
        data[PAYLOAD_FIELD_NAME_METHOD] = METHOD_NAME_STORE_WEBFILE;
        data[PAYLOAD_FIELD_NAME_WEBFILE] = webfile->marshall();

        $this->doRemoteCall(data);
    }

    public void deleteByTemplate(MWebfile template)
    {
        String[] data = {};
        data[PAYLOAD_FIELD_NAME_METHOD] = METHOD_NAME_DELETE_BY_TEMPLATE;
        data[PAYLOAD_FIELD_NAME_TEMPLATE] = template.marshall();

        $this->doRemoteCall(data);
    }


}
