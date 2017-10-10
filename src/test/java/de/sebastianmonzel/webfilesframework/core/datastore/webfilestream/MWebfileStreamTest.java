package de.sebastianmonzel.webfilesframework.core.datastore.webfilestream;

import de.sebastianmonzel.webfilesframework.core.datasystem.format.MWebfile;
import de.sebastianmonzel.webfilesframework.core.datastore.types.database.MSampleWebfile;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MWebfileStreamTest {

    @Test
    public void unmarshall() {

        MWebfileStream mWebfileStream = new MWebfileStream("<?xml version=\"1.0\" encoding=\"UTF-8\"?><webfilestream><webfiles><object classname=\"simpleserv\\webfilesframework\\core\\datastore\\types\\database\\MSampleWebfile\">\n" +
                "\t<firstname><![CDATA[Sebastian]]></firstname>\n" +
                "\t<lastname><![CDATA[Monzel]]></lastname>\n" +
                "\t<street><![CDATA[Blumenstrasse]]></street>\n" +
                "\t<housenumber><![CDATA[4]]></housenumber>\n" +
                "\t<postcode><![CDATA[67433]]></postcode>\n" +
                "\t<city><![CDATA[Neustadt an der Weinstrasse]]></city>\n" +
                "\t<id><![CDATA[2]]></id>\n" +
                "\t<time><![CDATA[4711]]></time>\n" +
                "</object><object classname=\"simpleserv\\webfilesframework\\core\\datastore\\types\\database\\MSampleWebfile\">\n" +
                "\t<firstname><![CDATA[Sebastian2]]></firstname>\n" +
                "\t<lastname><![CDATA[Monzel2]]></lastname>\n" +
                "\t<street><![CDATA[Blumenstrasse]]></street>\n" +
                "\t<housenumber><![CDATA[4]]></housenumber>\n" +
                "\t<postcode><![CDATA[67433]]></postcode>\n" +
                "\t<city><![CDATA[Neustadt an der Weinstrasse]]></city>\n" +
                "\t<id><![CDATA[1]]></id>\n" +
                "\t<time><![CDATA[4711]]></time>\n" +
                "</object></webfiles></webfilestream>");

        List<MWebfile> webfiles = mWebfileStream.getWebfiles();

        assertThat(webfiles.size(),is(2));
        assertThat(webfiles.get(0) instanceof MSampleWebfile,is(true));
        assertThat(webfiles.get(1) instanceof MSampleWebfile,is(true));

        MSampleWebfile sampleWebfile1 = (MSampleWebfile) webfiles.get(0);
        assertThat(sampleWebfile1.getFirstname(),is("Sebastian"));
        assertThat(sampleWebfile1.getLastname(),is("Monzel"));

        MSampleWebfile sampleWebfile2 = (MSampleWebfile) webfiles.get(1);
        assertThat(sampleWebfile2.getFirstname(),is("Sebastian2"));
        assertThat(sampleWebfile2.getLastname(),is("Monzel2"));

    }

}