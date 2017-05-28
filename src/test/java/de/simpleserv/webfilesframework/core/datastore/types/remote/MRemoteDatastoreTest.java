package de.simpleserv.webfilesframework.core.datastore.types.remote;


import de.simpleserv.webfilesframework.core.datastore.types.database.MSampleWebfile;
import de.simpleserv.webfilesframework.core.datastore.webfilestream.MWebfileStream;
import de.simpleserv.webfilesframework.core.datasystem.format.MWebfile;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MRemoteDatastoreTest {

    @Test
    public void getWebfiles() throws IOException {

        MRemoteDatastore remoteDatastore = new MRemoteDatastore("http://www.sebastianmonzel.de/datastore/");
        MWebfileStream webfilesAsStream = remoteDatastore.getWebfilesAsStream();

        List<MWebfile> webfiles = webfilesAsStream.getWebfiles();

        assertThat(webfiles.size(), is(3));
        assertThat(webfiles.get(0) instanceof MSampleWebfile, is(true));
        assertThat(webfiles.get(1) instanceof MSampleWebfile, is(true));

        MSampleWebfile sampleWebfile1 = (MSampleWebfile) webfiles.get(0);
        assertThat(sampleWebfile1.getFirstname(), is("Sebastian"));
        assertThat(sampleWebfile1.getLastname(), is("Monzel"));
    }

    @Test
    public void storeWebfile() throws IOException {

        MRemoteDatastore remoteDatastore = new MRemoteDatastore("http://www.sebastianmonzel.de/datastore/");


        MSampleWebfile sampleWebfile = new MSampleWebfile();
        sampleWebfile.setId(5);


        remoteDatastore.storeWebfile(sampleWebfile);

        MWebfileStream webfilesAsStream = remoteDatastore.getWebfilesAsStream();
        List<MWebfile> webfiles = webfilesAsStream.getWebfiles();

        assertThat(webfiles.size(), is(4));
        assertThat(webfiles.get(2) instanceof MSampleWebfile, is(true));

        MSampleWebfile sampleWebfile1 = (MSampleWebfile) webfiles.get(2);
        assertThat(sampleWebfile1.getId(), is(1));

        sampleWebfile.presetForTemplateSearch();
        sampleWebfile.setId(5);
        remoteDatastore.deleteByTemplate(sampleWebfile);

        webfilesAsStream = remoteDatastore.getWebfilesAsStream();
        webfiles = webfilesAsStream.getWebfiles();
        assertThat(webfiles.size(), is(3));
    }

    @Test
    public void searchByTemplate() throws IOException {

        MRemoteDatastore remoteDatastore = new MRemoteDatastore("http://www.sebastianmonzel.de/datastore/");

        MSampleWebfile template = new MSampleWebfile();
        template.presetForTemplateSearch();
        template.setId(1);
        List<MWebfile> webfiles = remoteDatastore.searchByTemplate(template);
        assertThat(webfiles.size(),is(1));
        MSampleWebfile foundWebfile = (MSampleWebfile) webfiles.get(0);
        assertThat(foundWebfile.getId(),is(1));

    }
}