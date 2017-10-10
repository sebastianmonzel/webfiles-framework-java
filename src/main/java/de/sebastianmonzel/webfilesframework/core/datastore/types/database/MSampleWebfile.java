package de.sebastianmonzel.webfilesframework.core.datastore.types.database;

import de.sebastianmonzel.webfilesframework.core.datasystem.format.MWebfile;

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

    public void setStreet(String street) {
        templatedAttributes.remove("m_sStreet");
        m_sStreet = street;
    }

    public String getFirstname() {
        return m_sFirstname;
    }

    public void setFirstname(String firstname) {
        templatedAttributes.remove("m_sFirstname");
        m_sFirstname = firstname;
    }

    public String getLastname() {
        return m_sLastname;
    }

    public void setLastname(String lastname) {
        templatedAttributes.remove("m_sLastname");
        m_sLastname = lastname;
    }

    public String getCity() {
        return m_sCity;
    }

    public void setCity(String city) {
        templatedAttributes.remove("m_sCity");
        m_sCity = city;
    }

    public String getHousenumber() {
        return m_sHousenumber;
    }

    public void setHousenumber(String housenumber) {
        templatedAttributes.remove("m_sHousenumber");
        m_sHousenumber = housenumber;
    }

    public String getPostcode() {
        return m_sPostcode;
    }

    public void setPostcode(String postcode) {
        templatedAttributes.remove("m_sPostcode");
        m_sPostcode = postcode;
    }

}
