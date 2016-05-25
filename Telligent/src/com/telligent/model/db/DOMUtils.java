package com.telligent.model.db;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.telligent.util.exceptions.ParseException;

public class DOMUtils {
    public static final String NS_URI_XMLNS = "http://www.w3.org/2000/xmlns/";

    private static DocumentBuilderFactory m_defaultFactory =
        getDefaultFactory();


    public static DocumentBuilderFactory getDefaultFactory() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        return factory;
    }

    public static DocumentBuilder getDocumentBuilder()
        throws ParserConfigurationException {
        return m_defaultFactory.newDocumentBuilder();
    }

    private static DocumentBuilder getDocumentBuilder2(){
        try {
            return getDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Parse the given file and return a DOM document
     *
     * @param f file that contains XML content
     * @return new DOM document
     * @throws IOException if there is an exception while reading the file
     * @throws ParseException if there are any parse errors.
     * @throws java.lang.IllegalArgumentException If the file is null.
     */
    public static Document parse(File f) throws ParseException, IOException {
        DocumentBuilder builder = getDocumentBuilder2();

        try {
            return builder.parse(f);
        } catch (SAXException e) {
            throw new ParseException(e);
        }
    }


    /**
     * Parse the content of the given URI as an XML document and return a new
     * DOM Document object.
     *
     * @param uri The location of the content to be parsed.
     * @return A new DOM document object
     * @throws IOException if there are any io errors.
     * @throws ParseException if there are any parse errors.
     * @throws java.lang.IllegalArgumentException If the uri is null.
     */
    public static Document parseURI(String uri)
        throws ParseException, IOException {
        DocumentBuilder builder = getDocumentBuilder2();

        try {
            return builder.parse(uri);
        } catch (SAXException e) {
            throw new ParseException(e);
        }
    }


    /**
     * Parse the content of the given InputStream as an XML document and
     * return a new DOM Document object.
     *
     * @param is InputStream containting the content to be parsed.
     * @return A new DOM document object.
     * @throws IOException if there are any io errors.
     * @throws ParseException if there are any parse errors.
     * @throws java.lang.IllegalArgumentException If the InputStream is null.
     */
    public static Document parse(InputStream is)
        throws ParseException, IOException {
        DocumentBuilder builder = getDocumentBuilder2();

        try {
            return builder.parse(is);
        } catch (SAXException e) {
            throw new ParseException(e);
        }
    }


    /**
     * Parse the content of the given InputStream as an XML document and
     * return a new DOM Document object.
     *
     * @param is InputStream containting the content to be parsed.
     * @param systemId Provide a base for resolving relative URIs.
     * @return A new DOM document object.
     * @throws IOException if there are any io errors.
     * @throws ParseException if there are any parse errors.
     * @throws java.lang.IllegalArgumentException If the InputStream is null.
     */
    public static Document parse(InputStream is,
        String systemId) throws ParseException, IOException {
        DocumentBuilder builder = getDocumentBuilder2();

        try {
            return builder.parse(is, systemId);
        } catch (SAXException e) {
            throw new ParseException(e);
        }
    }


    /**
     * Parse the content of the given Reader as an XML document and
     * return a new DOM Document object.
     *
     * @param reader Reader containting the content to be parsed.
     * @return A new DOM document object.
     * @throws IOException if there are any io errors.
     * @throws ParseException if there are any parse errors.
     * @throws java.lang.IllegalArgumentException If the reader is null.
     */
    public static Document parse(Reader reader)
        throws ParseException, IOException {
        if (reader == null) {
            throw new IllegalArgumentException("Reader cannot be null");
        }
        DocumentBuilder builder = getDocumentBuilder2();
        InputSource source = new InputSource(reader);

        try {
            return builder.parse(source);
        } catch (SAXException e) {
            throw new ParseException(e);
        }
    }


    /**
     * Parse the content of the given String as an XML document and
     * return a new DOM Document object.
     *
     * @param xmlContent String containting the content to be parsed.
     * @return A new DOM document object.
     * @throws IOException if there are any io errors.
     * @throws ParseException if there are any parse errors.
     * @throws java.lang.IllegalArgumentException If the String is null.
     */
    public static Document parseXMLString(String xmlContent)
        throws ParseException, IOException {
        if (xmlContent == null) {
            throw new IllegalArgumentException("xmlContent cannot be null");
        }
        DocumentBuilder builder = getDocumentBuilder2();
        StringReader reader = new StringReader(xmlContent);
        InputSource source = new InputSource(reader);

        try {
            return builder.parse(source);
        } catch (SAXException e) {
            throw new ParseException(e);
        }
    }


    /**
     * Access an immediate child element inside the given Element
     *
     * @param element the starting element, cannot be null.
     * @param elemName the name of the child element to look for, cannot be
     * null.
     * @return the first immediate child element inside element, or
     * <code>null</code> if the child element is not found.
     */
    public static Element getChildElement(Element element, String elemName) {
        NodeList list = element.getChildNodes();
        int len = list.getLength();

        for (int i = 0; i < len; i++) {
            Node n = list.item(i);

            if (n.getNodeType() == Node.ELEMENT_NODE) {
                if (elemName.equals(n.getNodeName())) {
                    return (Element)n;
                }
            }
        }
        return null;
    }


    /**
     * Access an immediate child element inside the given Element
     *
     * @param element the starting element, cannot be null.
     * @param namespaceURI name space of the child element to look for,
     * cannot be null. Use "*" to match all namespaces.
     * @param elemName the name of the child element to look for, cannot be
     * null.
     * @return the first immediate child element inside element, or
     * <code>null</code> if the child element is not found.
     */
    public static Element getChildElement(Element element,
        String namespaceURI, String elemName) {
        NodeList list = element.getChildNodes();
        int len = list.getLength();

        for (int i = 0; i < len; i++) {
            Node n = list.item(i);

            if (n.getNodeType() == Node.ELEMENT_NODE) {
                if (elemName.equals(n.getLocalName()) &&
                    ("*".equals(namespaceURI) ||
                     namespaceURI.equals(n.getNamespaceURI()))) {
                    return (Element)n;
                }
            }
        }
        return null;
    }


    /**
     * Access all immediate child elements inside the given Element
     *
     * @param element the starting element, cannot be null.
     * @param elemName the name of the child element to look for, cannot be
     * null.
     * @return array of all immediate child element inside element, or
     * an array of size zero if no child elements are found.
     */
    public static Element[] getChildElements(Element element,
        String elemName) {
        NodeList list = element.getChildNodes();
        int len = list.getLength();
        ArrayList array = new ArrayList(len);

        for (int i = 0; i < len; i++) {
            Node n = list.item(i);

            if (n.getNodeType() == Node.ELEMENT_NODE) {
                if (elemName.equals(n.getNodeName())) {
                    array.add(n);
                }
            }
        }
        Element[] elems = new Element[array.size()];

        return (Element[])array.toArray(elems);
    }


    /**
     * Access all immediate child elements inside the given Element
     *
     * @param element the starting element, cannot be null.
     * @param namespaceURI name space of the child element to look for,
     * cannot be null. Use "*" to match all namespaces
     * @param elemName the name of the child element to look for,
     * cannot be null.
     * @return array of all immediate child element inside element, or
     * an array of size zero if no child elements are found.
     */
    public static Element[] getChildElements(Element element,
        String namespaceURI, String elemName) {
        NodeList list = element.getChildNodes();
        int len = list.getLength();
        ArrayList array = new ArrayList(len);

        for (int i = 0; i < len; i++) {
            Node n = list.item(i);

            if (n.getNodeType() == Node.ELEMENT_NODE) {
                if (elemName.equals(n.getLocalName()) &&
                    ("*".equals(namespaceURI) ||
                     namespaceURI.equals(n.getNamespaceURI()))) {
                    array.add(n);
                }
            }
        }
        Element[] elems = new Element[array.size()];

        return (Element[])array.toArray(elems);
    }


    /**
     * Find an element using XPath-like expressions. Path must not including
     * the context element, path elements can be separated by / or .,
     * and namespace is NOT supported.
     * @param context Element to start the search from, cannot be null.
     * @param path XPath-like expression, cannot be null.
     * @return the first matched element if there are matches, otherwise
     * return null.
     */
    public static Element getElementByPath(Element context, String path) {
        Element cur = context;
        StringTokenizer tokens = new StringTokenizer(path, "/");

        while (tokens.hasMoreTokens()) {
            String name = tokens.nextToken();

            cur = getChildElement(cur, name);
            if (cur == null) {
                return null;
            }
        }
        return cur;
    }

	/**
     * Find an element using XPath-like expressions. Path must not including
     * the context element, path elements can be separated by / or .,
     * and namespace is NOT supported.
     * @param context Element to start the search from, cannot be null.
     * @param path XPath-like expression, cannot be null.
     * @param create if true, new elements are created if necessary.
     * @return the first matched element if there are matches, otherwise
     * return null.
     */
    public static Element getElementByPath(Element context, String path, boolean create) {
        Element cur = context;
        StringTokenizer tokens = new StringTokenizer(path, "/");

        while (tokens.hasMoreTokens()) {
            String name = tokens.nextToken();
			Element parent = cur;
            cur = getChildElement(cur, name);
            if (cur == null) {
                if (create) { 
                    // create the element
                    Element newElement = context.getOwnerDocument().createElement(name);
                    //context.appendChild(newElement);
                    parent.appendChild(newElement);
                    cur = newElement;
                } else {
                    return null;
                } 
            }
        }
        return cur;
    }
    
	/**
     * Remove an element using XPath-like expressions. Path must not including
     * the context element, path elements can be separated by / or .,
     * and namespace is NOT supported.
     * @param context Element to start the search from, cannot be null.
     * @param path XPath-like expression, cannot be null.
     * @return the removed element if there are matches, otherwise
     * return null.
     */
    public static Element removeElementByPath(Element context, String path) {
        Element cur = context;
        StringTokenizer tokens = new StringTokenizer(path, "/");
    	String name = null;
    	while (tokens.hasMoreTokens()) {
            name = tokens.nextToken();
            cur = getElementByPath(cur, name);
            if (cur == null) {
                return null;
            }
        }
        if(name != null)
        {
        	Element parent = (Element)cur.getParentNode();
        	return removeChildElement(parent, name);
        }
        return null;
    }
  	
    /**
     * Removes the child elements inside the given Element
     *
     * @param element the starting element, cannot be null.
     * @param elemName the name of the child element to look for, cannot be
     * null.
     * @return the removed child element inside element, or
     * <code>null</code> if the child element is not found.
     */    
    public static Element removeChildElement(Element element, String elemName) {
        NodeList list = element.getChildNodes();
        Element cur = element;
        int len = list.getLength();
        for (int i = 0; i < len; i++) {
            Node n = list.item(i);

            if(n != null)
            {
	            if (n.getNodeType() == Node.ELEMENT_NODE) {
	                if (elemName.equals(n.getNodeName())) {
	                	cur = (Element) element.removeChild(n);
	                }
	            }
        	}
        }
		return cur;
    }


    /**
     * Get the text content of an element. If the element contains
     * mixed content (both elements and text), only the first text section
     * is returned.
     *
     * @param element target element to retrieve text on, cannot be null.
     * @return text content of the element.
     */
    public static String getElementText(Element element) {
        element.normalize();
        NodeList list = element.getChildNodes();
        int len = list.getLength();

        for (int i = 0; i < len; i++) {
            Node n = list.item(i);

            if (n.getNodeType() == Node.TEXT_NODE) {
                String s = n.getNodeValue();

                if (s != null) {
                    return s.trim();
                }
            }
        }
        return null;
    }


    /**
     * Set the text content of an element. All exisitng Text Node are
     * removed before adding a new Text Node containing the given text.
     *
     * @param element target element to set text content, cannot be null.
     * @param text content of the element, cannot be null.
     */
    public static void setElementText(Element element, String text) {

        // Remove all text element
        NodeList list = element.getChildNodes();
        int len = list.getLength();

        for (int i = 0; i < len; i++) {
            Node n = list.item(i);

            if (n.getNodeType() == Node.TEXT_NODE) {
                element.removeChild(n);
            }
        }
        Node child = element.getFirstChild();
        Node textnode = element.getOwnerDocument().createTextNode(text);

        // insert text node as first child
        if (child == null) {
            element.appendChild(textnode);
        } else {
            element.insertBefore(textnode, child);
        }
    }


    /**
     * Convert an element to String. xml declaration IS included.
     * No pretty formatting.
     *
     * @param element element to be converted to String, cannot be null.
     * @return String representing the content of the element.
     * @throws ParseException if there are parse errors.
     */
    public static String toString(Element element) throws ParseException {
        try {
            Transformer serializer =
                TransformerFactory.newInstance().newTransformer();

            serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
                "no");
            StringWriter writer = new StringWriter();

            serializer.transform(new DOMSource(element),
                new StreamResult(writer));
            return writer.toString();
        } catch (Exception e) {
            throw new ParseException("Failed to convert element to string",
                e);
        }
    }

    /**
     * Convert an element to String.
     *
     * @param element element to be converted to String, cannot be null.
     * @param xmlDecl true if you want <?xml ?> to be included in output
     *
     * @return String representing the content of the element.
     * @throws ParseException if there are parse errors.
     */
    public static String toString(Element element, boolean xmlDecl)
        throws ParseException {
        try {
            Transformer serializer =
                TransformerFactory.newInstance().newTransformer();
            if (xmlDecl) {
                serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
                                             "no");
            } else {
                serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
                                             "yes");
            }
            StringWriter writer = new StringWriter();

            serializer.transform(new DOMSource(element),
                new StreamResult(writer));
            return writer.toString();
        } catch (Exception e) {
            throw new ParseException("Failed to convert element to string",
                e);
        }
    }


    /**
     * Escape a String using the default character
     * encoding.
     *
     * @param String string to escape, cannot be null.
     * @return escaped String
     */
    public static String escape(String str) {
        StringReader r = new StringReader(str);
        StringWriter w = new StringWriter();

        try {
            while (true) {
                int c = r.read();

                if (c <= 0) {
                    break;
                }
                switch (c) {
                case '<':
                    w.write("&lt;");
                    break;
                case '>':
                    w.write("&gt;");
                    break;
                case '&':
                    w.write("&amp;");
                    break;
                case '\'':
                    w.write("&apos;");
                    break;
                case '\"':
                    w.write("&quot;");
                    break;
                case '\r':
                    w.write("&#xd;");
                    break;
                default:
                    if (c > 127) {
                        w.write("&#");
                        w.write(String.valueOf(c));
                        w.write(";");
                    } else {
                        w.write(c);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return w.toString();
    }

    /**
     * Escape a sequence of bytes to String using the default character
     * encoding.
     *
     * @param bytes byte content, cannot be null.
     * @return escaped String
     * TODO: Character encoding
     */
    public static String escape(byte[] bytes) {
        String str = new String(bytes);
        return escape(str);
    }

    /**
     * Returns the namespace assocated with a prefix
     */
    public static String getNamespace(String prefix, Node e) {
        while (e != null && (e.getNodeType() == Node.ELEMENT_NODE)) {
            Attr attr =
                ((Element)e).getAttributeNodeNS(NS_URI_XMLNS, prefix);
            if (attr != null) return attr.getValue();
            e = e.getParentNode();
        }
        return null;
    }
}
