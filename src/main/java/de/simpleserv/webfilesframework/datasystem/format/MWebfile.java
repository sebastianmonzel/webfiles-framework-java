package de.simpleserv.webfilesframework.datasystem.format;

public class MWebfile {

    private int time;
    private String[] dataset;

    public boolean matchesTemplate(MWebfile template) {
        return true;
    }


    public int getTime() {
        return time;
    }

    public String[] getDataset() {
        return dataset;
    }
}
