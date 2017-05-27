package de.simpleserv.webfilesframework.datasystem.format;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MWebfileTest {

    @Test
    public void marshall() throws Exception, MWebfilesFrameworkException {

        MSampleWebfile sampleWebfile = new MSampleWebfile();

        sampleWebfile.unmarshall("<object classname=\"simpleserv\\webfilesframework\\core\\datastore\\types\\database\\MSampleWebfile\">\n" +
                "\t<firstname><![CDATA[Sebastian]]></firstname>\n" +
                "\t<lastname><![CDATA[Monzel]]></lastname>\n" +
                "\t<street><![CDATA[Blumenstrasse]]></street>\n" +
                "\t<housenumber><![CDATA[4]]></housenumber>\n" +
                "\t<postcode><![CDATA[67433]]></postcode>\n" +
                "\t<city><![CDATA[Neustadt an der Weinstrasse]]></city>\n" +
                "\t<id><![CDATA[2]]></id>\n" +
                "\t<time><![CDATA[4711]]></time>\n" +
                "</object>");

        assertThat(sampleWebfile.getFirstName(),is("Sebastian"));
        assertThat(sampleWebfile.getLastname(),is("Monzel"));
        assertThat(sampleWebfile.getStreet(),is("Blumenstrasse"));
        assertThat(sampleWebfile.getHousenumber(),is("4"));
        assertThat(sampleWebfile.getPostcode(),is("67433"));
        assertThat(sampleWebfile.getCity(),is("Neustadt an der Weinstrasse"));

        //assertThat(sampleWebfile.getTime(),is(4711));
        //assertThat(sampleWebfile.getId(),is(2));

    }

}