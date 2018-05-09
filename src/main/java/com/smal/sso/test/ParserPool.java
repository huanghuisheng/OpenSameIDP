package com.smal.sso.test;


import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.validation.Schema;
import java.io.InputStream;
import java.io.Reader;

public interface ParserPool {
    DocumentBuilder getBuilder() throws XMLParserException;

    void returnBuilder(DocumentBuilder var1);

    Document newDocument() throws XMLParserException;

    Document parse(InputStream var1) throws XMLParserException;

    Document parse(Reader var1) throws XMLParserException;

    Schema getSchema();

    void setSchema(Schema var1);
}
