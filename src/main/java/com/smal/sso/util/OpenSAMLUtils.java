package com.smal.sso.util;

import com.smal.sso.test.XMLParserException;
import net.shibboleth.utilities.java.support.security.RandomIdentifierGenerationStrategy;
import com.smal.sso.test.BasicParserPool;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.SignableSAMLObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;

/**
 * Created by Privat on 4/6/14.
 */
public class OpenSAMLUtils {
    private static Logger logger = LoggerFactory.getLogger(OpenSAMLUtils.class);
    private static RandomIdentifierGenerationStrategy secureRandomIdGenerator;

    static {
        secureRandomIdGenerator = new RandomIdentifierGenerationStrategy();

    }

    public static <T> T buildSAMLObject(final Class<T> clazz) {
        T object = null;
        try {
            XMLObjectBuilderFactory builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
            QName defaultElementName = (QName)clazz.getDeclaredField("DEFAULT_ELEMENT_NAME").get(null);
            object = (T)builderFactory.getBuilder(defaultElementName).buildObject(defaultElementName);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Could not create SAML object");
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Could not create SAML object");
        }
        return object;
    }

    public static String generateSecureRandomId() {
        return secureRandomIdGenerator.generateIdentifier();
    }



    public static String buildXMLObjectToString(XMLObject xmlObject) {

        Element authDOM;
       String xmlString=null;
        Marshaller marshaller = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(xmlObject);
        if (xmlObject instanceof SignableSAMLObject && ((SignableSAMLObject)xmlObject).isSigned() && xmlObject.getDOM() != null) {
            authDOM = xmlObject.getDOM();
        } else {
            try {
                authDOM = marshaller.marshall(xmlObject);
            } catch (MarshallingException e) {
                throw new RuntimeException(e);
            }
            try {
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                StreamResult result = new StreamResult(new StringWriter());
                DOMSource source = new DOMSource(authDOM);
                transformer.transform(source, result);
                 xmlString = result.getWriter().toString();
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            }

        }
           return xmlString;
    }

    public static XMLObject buildStringToXMLObject(String xmlObjectString) {

        XMLObject xmlObject=null;
        try {
            BasicParserPool parser = new BasicParserPool();
            parser.setNamespaceAware(true);

            Document doc = (Document) parser.parse(new ByteArrayInputStream(xmlObjectString.getBytes()));

            Element   samlElement = (Element) doc.getDocumentElement();
            Unmarshaller    unmarshaller = XMLObjectProviderRegistrySupport.getUnmarshallerFactory().getUnmarshaller(samlElement);
            xmlObject=   unmarshaller.unmarshall(samlElement);
        } catch (UnmarshallingException e) {
            throw new RuntimeException(e);
        } catch (XMLParserException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }













    public static void logSAMLObject(final XMLObject object) {
        Element element = null;

        if (object instanceof SignableSAMLObject && ((SignableSAMLObject)object).isSigned() && object.getDOM() != null) {
            element = object.getDOM();
        } else {
            try {
                Marshaller out = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(object);
                out.marshall(object);
                element = object.getDOM();
                Unmarshaller in =XMLObjectProviderRegistrySupport.getUnmarshallerFactory().getUnmarshaller(element);

            } catch (MarshallingException e) {
                logger.error(e.getMessage(), e);
            }
        }

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(element);
            transformer.transform(source, result);
            String xmlString = result.getWriter().toString();
            logger.info(xmlString);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
