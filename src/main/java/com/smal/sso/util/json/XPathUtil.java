package com.smal.sso.util.json;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;

public class XPathUtil {
	private static final XPathFactory xfactory = XPathFactory.newInstance();

	private static final XPath xpath = xfactory.newXPath();

	/**
	 * 
	 * Des:通过XPath表达式生成XPathExpression<br>
	 * Logic:<br>
	 * 
	 * @param path
	 * @return <br>
	 *         Wuhao crated the method at 2010-11-8
	 */
	public static XPathExpression getXPathExp(String path) {
		try {
			return xpath.compile(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Document getDocumentByData(String data) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(getInputSource(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * Des:通过指定路径获取Document对象<br>
	 * Logic:<br>
	 * 
	 * @param path
	 * @return <br>
	 *         Wuhao crated the method at 2010-11-8
	 */
	public static Document getDocument(String path) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static InputSource getInputSource(String data) {
		StringReader reaader = new StringReader(data);
		return new InputSource(reaader);
	}

}
