package de.simpleserv.webfilesframework.datasystem.format;

public class MSampleWebfile extends MWebfile {

    private String m_sFirstname;
    private String m_sLastname;
    private String m_sStreet;
    private String m_sHousenumber;
    private String m_sPostcode;
    private String m_sCity;

    public String getStreet() {
        return m_sStreet;
    }

    public String getFirstName() {
        return m_sFirstname;
    }

    public String getLastname() {
        return m_sLastname;
    }

    public String getCity() {
        return m_sCity;
    }

    public String getHousenumber() {
        return m_sHousenumber;
    }

    public String getPostcode() {
        return m_sPostcode;
    }

}
