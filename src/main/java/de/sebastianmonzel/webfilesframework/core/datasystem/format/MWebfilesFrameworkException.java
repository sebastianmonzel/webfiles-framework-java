package de.sebastianmonzel.webfilesframework.core.datasystem.format;

public class MWebfilesFrameworkException extends Exception {
    public MWebfilesFrameworkException(String message) {
        super(message);
    }

    public MWebfilesFrameworkException(String message, Exception cause) {
        super(message,cause);
    }
}
