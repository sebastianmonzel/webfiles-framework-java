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

        assertThat(webfiles.size(),is(2));
        assertThat(webfiles.get(0) instanceof MSampleWebfile,is(true));
        assertThat(webfiles.get(1) instanceof MSampleWebfile,is(true));

        MSampleWebfile sampleWebfile1 = (MSampleWebfile) webfiles.get(0);
        assertThat(sampleWebfile1.getFirstName(),is("Sebastian"));
        assertThat(sampleWebfile1.getLastname(),is("Monzel"));
    }

}