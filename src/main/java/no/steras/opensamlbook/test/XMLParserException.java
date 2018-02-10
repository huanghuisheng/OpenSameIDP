package no.steras.opensamlbook.test;



public class XMLParserException extends Exception {
    private static final long serialVersionUID = 7260425832643941776L;

    public XMLParserException() {
    }

    public XMLParserException(String message) {
        super(message);
    }

    public XMLParserException(Exception wrappedException) {
        super(wrappedException);
    }

    public XMLParserException(String message, Exception wrappedException) {
        super(message, wrappedException);
    }
}