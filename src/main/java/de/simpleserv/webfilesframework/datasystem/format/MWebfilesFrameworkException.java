package de.simpleserv.webfilesframework.datasystem.format;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by dem_m on 27.05.2017.
 */
public class MWebfilesFrameworkException extends Exception {
    public MWebfilesFrameworkException(String message) {
        super(message);
    }

    public MWebfilesFrameworkException(String message, Exception cause) {
        super(message,cause);
    }
}
