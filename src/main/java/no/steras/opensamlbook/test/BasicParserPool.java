package no.steras.opensamlbook.test;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class BasicParserPool  implements ParserPool {
    private final Logger log = LoggerFactory.getLogger(BasicParserPool.class);
    private long poolVersion;
    private boolean dirtyBuilderConfiguration;
    private DocumentBuilderFactory builderFactory;
    private Stack<SoftReference<DocumentBuilder>> builderPool = new Stack();
    private int maxPoolSize = 5;
    private Map<String, Object> builderAttributes = new HashMap<String, Object>();
    private boolean coalescing = true;
    private boolean expandEntityReferences = false;
    private Map<String, Boolean> builderFeatures = this.buildDefaultFeatures();
    private boolean ignoreComments = true;
    private boolean ignoreElementContentWhitespace = true;
    private boolean namespaceAware = true;
    private Schema schema = null;
    private boolean dtdValidating = false;
    private boolean xincludeAware = false;
    private EntityResolver entityResolver;
    private ErrorHandler errorHandler;

    public BasicParserPool() {
//        this.errorHandler = new LoggingErrorHandler(this.log);

        try {
            this.dirtyBuilderConfiguration = true;
            this.initializePool();
        } catch (XMLParserException var2) {
            ;
        }

    }

    public DocumentBuilder getBuilder() throws XMLParserException {
        DocumentBuilder builder = null;
        long version = 0L;
        if (this.dirtyBuilderConfiguration) {
            this.initializePool();
        }

        synchronized(this) {
            version = this.getPoolVersion();
            if (!this.builderPool.isEmpty()) {
                builder = (DocumentBuilder)((SoftReference)this.builderPool.pop()).get();
            }

            if (builder == null) {
                builder = this.createBuilder();
            }
        }

        return builder != null ? new BasicParserPool.DocumentBuilderProxy(builder, this, version) : null;
    }

    public void returnBuilder(DocumentBuilder builder) {
        if (builder instanceof BasicParserPool.DocumentBuilderProxy) {
            BasicParserPool.DocumentBuilderProxy proxiedBuilder = (BasicParserPool.DocumentBuilderProxy)builder;
            if (proxiedBuilder.getOwningPool() == this) {
                synchronized(this) {
                    if (!proxiedBuilder.isReturned()) {
                        if (proxiedBuilder.getPoolVersion() == this.poolVersion) {
                            DocumentBuilder unwrappedBuilder = proxiedBuilder.getProxiedBuilder();
                            unwrappedBuilder.reset();
                            SoftReference<DocumentBuilder> builderReference = new SoftReference(unwrappedBuilder);
                            if (this.builderPool.size() < this.maxPoolSize) {
                                proxiedBuilder.setReturned(true);
                                this.builderPool.push(builderReference);
                            }

                        }
                    }
                }
            }
        }
    }

    public Document newDocument() throws XMLParserException {
        DocumentBuilder builder = this.getBuilder();
        Document document = builder.newDocument();
        this.returnBuilder(builder);
        return document;
    }

    public Document parse(InputStream input) throws XMLParserException {
        DocumentBuilder builder = this.getBuilder();

        Document var4;
        try {
            Document document = builder.parse(input);
            var4 = document;
        } catch (SAXException var9) {
            throw new XMLParserException("Invalid XML", var9);
        } catch (IOException var10) {
            throw new XMLParserException("Unable to read XML from input stream", var10);
        } finally {
            this.returnBuilder(builder);
        }

        return var4;
    }

    public Document parse(Reader input) throws XMLParserException {
        DocumentBuilder builder = this.getBuilder();

        Document var4;
        try {
            Document document = builder.parse(new InputSource(input));
            var4 = document;
        } catch (SAXException var9) {
            throw new XMLParserException("Invalid XML", var9);
        } catch (IOException var10) {
            throw new XMLParserException("Unable to read XML from input stream", var10);
        } finally {
            this.returnBuilder(builder);
        }

        return var4;
    }

    public int getMaxPoolSize() {
        return this.maxPoolSize;
    }

    public void setMaxPoolSize(int newSize) {
        this.maxPoolSize = newSize;
    }

    /** @deprecated */
    public boolean getCreateBuildersAtPoolLimit() {
        return true;
    }

    /** @deprecated */
    public void setCreateBuildersAtPoolLimit(boolean createBuilders) {
    }

    public Map<String, Object> getBuilderAttributes() {
        return Collections.unmodifiableMap(this.builderAttributes);
    }

    public synchronized void setBuilderAttributes(Map<String, Object> newAttributes) {
        this.builderAttributes = newAttributes;
        this.dirtyBuilderConfiguration = true;
    }

    public boolean isCoalescing() {
        return this.coalescing;
    }

    public synchronized void setCoalescing(boolean isCoalescing) {
        this.coalescing = isCoalescing;
        this.dirtyBuilderConfiguration = true;
    }

    public boolean isExpandEntityReferences() {
        return this.expandEntityReferences;
    }

    public synchronized void setExpandEntityReferences(boolean expand) {
        this.expandEntityReferences = expand;
        this.dirtyBuilderConfiguration = true;
    }

    public Map<String, Boolean> getBuilderFeatures() {
        return Collections.unmodifiableMap(this.builderFeatures);
    }

    public synchronized void setBuilderFeatures(Map<String, Boolean> newFeatures) {
        this.builderFeatures = newFeatures;
        this.dirtyBuilderConfiguration = true;
    }

    public boolean getIgnoreComments() {
        return this.ignoreComments;
    }

    public synchronized void setIgnoreComments(boolean ignore) {
        this.ignoreComments = ignore;
        this.dirtyBuilderConfiguration = true;
    }

    public boolean isIgnoreElementContentWhitespace() {
        return this.ignoreElementContentWhitespace;
    }

    public synchronized void setIgnoreElementContentWhitespace(boolean ignore) {
        this.ignoreElementContentWhitespace = ignore;
        this.dirtyBuilderConfiguration = true;
    }

    public boolean isNamespaceAware() {
        return this.namespaceAware;
    }

    public synchronized void setNamespaceAware(boolean isNamespaceAware) {
        this.namespaceAware = isNamespaceAware;
        this.dirtyBuilderConfiguration = true;
    }

    public Schema getSchema() {
        return this.schema;
    }

    public synchronized void setSchema(Schema newSchema) {
        this.schema = newSchema;
        if (this.schema != null) {
            this.setNamespaceAware(true);
            this.builderAttributes.remove("http://java.sun.com/xml/jaxp/properties/schemaSource");
            this.builderAttributes.remove("http://java.sun.com/xml/jaxp/properties/schemaLanguage");
        }

        this.dirtyBuilderConfiguration = true;
    }

    public boolean isDTDValidating() {
        return this.dtdValidating;
    }

    public synchronized void setDTDValidating(boolean isValidating) {
        this.dtdValidating = isValidating;
        this.dirtyBuilderConfiguration = true;
    }

    public boolean isXincludeAware() {
        return this.xincludeAware;
    }

    public synchronized void setXincludeAware(boolean isXIncludeAware) {
        this.xincludeAware = isXIncludeAware;
        this.dirtyBuilderConfiguration = true;
    }

    protected long getPoolVersion() {
        return this.poolVersion;
    }

    protected int getPoolSize() {
        return this.builderPool.size();
    }

    protected synchronized void initializePool() throws XMLParserException {
        if (this.dirtyBuilderConfiguration) {
            DocumentBuilderFactory newFactory = DocumentBuilderFactory.newInstance();
            this.setAttributes(newFactory, this.builderAttributes);
            this.setFeatures(newFactory, this.builderFeatures);
            newFactory.setCoalescing(this.coalescing);
            newFactory.setExpandEntityReferences(this.expandEntityReferences);
            newFactory.setIgnoringComments(this.ignoreComments);
            newFactory.setIgnoringElementContentWhitespace(this.ignoreElementContentWhitespace);
            newFactory.setNamespaceAware(this.namespaceAware);
            newFactory.setSchema(this.schema);
            newFactory.setValidating(this.dtdValidating);
            newFactory.setXIncludeAware(this.xincludeAware);
            ++this.poolVersion;
            this.dirtyBuilderConfiguration = false;
            this.builderFactory = newFactory;
            this.builderPool.clear();
        }
    }

    protected void setAttributes(DocumentBuilderFactory factory, Map<String, Object> attributes) {
        if (attributes != null && !attributes.isEmpty()) {
            Iterator i$ = attributes.entrySet().iterator();

            while(i$.hasNext()) {
                Entry attribute = (Entry)i$.next();

                try {
                    this.log.debug("Setting DocumentBuilderFactory attribute '{}'", attribute.getKey());
                    factory.setAttribute((String)attribute.getKey(), attribute.getValue());
                } catch (IllegalArgumentException var6) {
                    this.log.warn("DocumentBuilderFactory attribute '{}' is not supported", attribute.getKey());
                }
            }

        }
    }

    protected void setFeatures(DocumentBuilderFactory factory, Map<String, Boolean> features) {
        if (features != null && !features.isEmpty()) {
            Iterator i$ = features.entrySet().iterator();

            while(i$.hasNext()) {
                Entry feature = (Entry)i$.next();

                try {
                    this.log.debug("Setting DocumentBuilderFactory attribute '{}'", feature.getKey());
                    factory.setFeature((String)feature.getKey(), (Boolean)feature.getValue());
                } catch (ParserConfigurationException var6) {
                    this.log.warn("DocumentBuilderFactory feature '{}' is not supported", feature.getKey());
                }
            }

        }
    }

    protected DocumentBuilder createBuilder() throws XMLParserException {
        try {
            DocumentBuilder builder = this.builderFactory.newDocumentBuilder();
            if (this.entityResolver != null) {
                builder.setEntityResolver(this.entityResolver);
            }

            if (this.errorHandler != null) {
                builder.setErrorHandler(this.errorHandler);
            }

            return builder;
        } catch (ParserConfigurationException var2) {
            this.log.error("Unable to create new document builder", var2);
            throw new XMLParserException("Unable to create new document builder", var2);
        }
    }

    protected Map<String, Boolean> buildDefaultFeatures() {
        HashMap<String, Boolean> features = new HashMap();
        features.put("http://javax.xml.XMLConstants/feature/secure-processing", true);
        features.put("http://apache.org/xml/features/disallow-doctype-decl", true);
        return features;
    }

    protected class DocumentBuilderProxy extends DocumentBuilder {
        private DocumentBuilder builder;
        private ParserPool owningPool;
        private long owningPoolVersion;
        private boolean returned;

        public DocumentBuilderProxy(DocumentBuilder target, BasicParserPool owner, long version) {
            this.owningPoolVersion = version;
            this.owningPool = owner;
            this.builder = target;
            this.returned = false;
        }

        public DOMImplementation getDOMImplementation() {
            this.checkValidState();
            return this.builder.getDOMImplementation();
        }

        public Schema getSchema() {
            this.checkValidState();
            return this.builder.getSchema();
        }

        public boolean isNamespaceAware() {
            this.checkValidState();
            return this.builder.isNamespaceAware();
        }

        public boolean isValidating() {
            this.checkValidState();
            return this.builder.isValidating();
        }

        public boolean isXIncludeAware() {
            this.checkValidState();
            return this.builder.isXIncludeAware();
        }

        public Document newDocument() {
            this.checkValidState();
            return this.builder.newDocument();
        }

        public Document parse(File f) throws SAXException, IOException {
            this.checkValidState();
            return this.builder.parse(f);
        }

        public Document parse(InputSource is) throws SAXException, IOException {
            this.checkValidState();
            return this.builder.parse(is);
        }

        public Document parse(InputStream is) throws SAXException, IOException {
            this.checkValidState();
            return this.builder.parse(is);
        }

        public Document parse(InputStream is, String systemId) throws SAXException, IOException {
            this.checkValidState();
            return this.builder.parse(is, systemId);
        }

        public Document parse(String uri) throws SAXException, IOException {
            this.checkValidState();
            return this.builder.parse(uri);
        }

        public void reset() {
        }

        public void setEntityResolver(EntityResolver er) {
            this.checkValidState();
        }

        public void setErrorHandler(ErrorHandler eh) {
            this.checkValidState();
        }

        protected ParserPool getOwningPool() {
            return this.owningPool;
        }

        protected long getPoolVersion() {
            return this.owningPoolVersion;
        }

        protected DocumentBuilder getProxiedBuilder() {
            return this.builder;
        }

        protected boolean isReturned() {
            return this.returned;
        }

        protected void setReturned(boolean isReturned) {
            this.returned = isReturned;
        }

        protected void checkValidState() throws IllegalStateException {
            if (this.isReturned()) {
                throw new IllegalStateException("DocumentBuilderProxy has already been returned to its owning pool");
            }
        }

        protected void finalize() throws Throwable {
            super.finalize();
            this.owningPool.returnBuilder(this);
        }
    }
}
