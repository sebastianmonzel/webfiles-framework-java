package de.simpleserv.webfilesframework.datastore;

import de.simpleserv.webfilesframework.datastore.webfilestream.MWebfileStream;
import de.simpleserv.webfilesframework.datasystem.format.MWebfile;

import java.util.Date;
import java.util.List;

public abstract class MAbstractDatastore {

    /**
     * Checks if a connection is possible.
     */
    public abstract boolean tryConnect();

    /**
     * Determines if the datastore is read-only or not.
     * @return boolean information if datastore is readonly or not.
     */
    public abstract boolean isReadOnly();

    public void getNextWebfileForTimestamp(Date time) throws MDatastoreException {
        throw new MDatastoreException("datastore cannot be sorted by timestamp.");
    }

    /**
     * Returns a webfiles stream with all webfiles from
     * the actual datastore.
     *
     * @return MWebfileStream
     */
    public abstract MWebfileStream getWebfilesAsStream();

    /**
     * Returns all webfiles from the actual datastore.
     *
     * @return array list of webfiles
     */
    public abstract MWebfile[] getWebfilesAsArray();

    public MWebfile[] getLatestWebfiles() {
        return getLatestWebfiles(5);
    }

    /**
     * Returns the latests webfiles. Sorting will
     * happen according to the time information of the webfiles.
     *
     * @param count Count of webfiles to be selected.
     * @return array list of webfiles
     */
    public abstract MWebfile[] getLatestWebfiles(int count);

    /**
     * Returns a set of webfiles in the actual datastore which matches
     * with the given template.<br />
     * Searching by template is devided in two steps:<br />
     * <ol>
     * <li>On the first step you define the template you want to search with. Here can help you the method
     * <b>presetDefaultForTemplate</b> on the class <b>MWebfile</b>.</li>
     * <li>On the second step you put the template to the datastore to start the search</li>
     * </ol>
     *
     * @param MWebfile $template template to search for
     * @return array list of webfiles
     */
    public abstract MWebfile[] searchByTemplate(MWebfile $template);

    /**
     * @param array    $webfiles
     * @param MWebfile $template
     * @return array
     */
    protected MWebfile[] filterWebfilesArrayByTemplate(MWebfile[] webfiles, final MWebfile template) {
        MWebfile[] filteredWebfiles = {};
        /** @var MWebfile $webfile */
        for (MWebfile webfile : webfiles) {
            if (webfile.matchesTemplate(template)) {
                filteredWebfiles = addWebfileSafetyToArray(webfile, filteredWebfiles);
            }
        }
        return filteredWebfiles;
    }
    /**@noinspection PhpUnusedParameterInspection*/
    /**
     * Stores a single webfile in the datastore.
     *
     * @param MWebfile $webfile
     * @throws MDatastoreException
     */
    public void storeWebfile(MWebfile $webfile) throws MDatastoreException {

        if (this.isReadOnly()) {
            throw new MDatastoreException("cannot modify data on read-only datastore.");
        } else {
            throw new MDatastoreException("not implemented yet.");
        }
    }

    /**
     * Stores all webfiles from a given webfilestream in the actual datastore
     *
     * @param MWebfileStream $webfileStream
     * @throws MDatastoreException
     */
    public void storeWebfilesFromStream(MWebfileStream $webfileStream) throws MDatastoreException {
        if (this.isReadOnly()) {
            throw new MDatastoreException("cannot modify data on read-only datastore.");
        }
        List<MWebfile> webfiles = $webfileStream.getWebfiles();

        for (MWebfile webfile : webfiles) {
            this.storeWebfile(webfile);
        }
    }

    /**
     * Deletes a set of webfiles in the actual datastore which can be
     * applied to the given template.
     *
     * @param MWebfile $template
     * @throws MDatastoreException
     */
    public void deleteByTemplate(MWebfile $template) throws MDatastoreException {
        if (this.isReadOnly()) {
            throw new MDatastoreException("cannot modify data on read-only datastore.");
        } else {
            throw new MDatastoreException("not implemented yet.");
        }
    }

    /**
     * Resolves a datastore which is localized in the folder
     * <b>"./custom/datastore"</b> according to the given id.<br />
     * Every file situated in the datastore folder will be converted
     * to a webfile an the list of webfiles will be used to compare
     * the id on each datastore in the folder.
     *
     * @param String $datastoreId
     * @return MWebfile returns the found datastore
     * @throws MDatastoreException will be thrown if no datastore with
     *                             the given id is available.
     */
    public MAbstractDatastore resolveCustomDatastoreById(String datastoreId) {
        return null;
    }

    /**
     * @param webfilesArray
     * @return array
     */
    public static String[][] extractDatasetsFromWebfilesArray(MWebfile[] webfilesArray) {
        String[][] webfilesDatasets = {{}};

        /** @var MWebfile $webfile */
        for (MWebfile webfile : webfilesArray) {
            String[] dataset = webfile.getDataset();
            webfilesDatasets[webfilesDatasets.length] = dataset;
        }
        return webfilesDatasets;
    }


    protected MWebfile[] addWebfileSafetyToArray(MWebfile webfile, MWebfile[] objectsArray) {
        String arrayKey = Integer.toString(webfile.getTime());
        int arrayKeyCount = 1;

        // make sure files with the same key (normally timetamp) have an unique array key
        while (objectsArray[arrayKeyCount] != null) {
            arrayKeyCount++;
            arrayKey = arrayKey + "," + arrayKeyCount;
        }
        objectsArray[arrayKey] = webfile;
        return objectsArray;
    }

}
