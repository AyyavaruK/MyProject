package com.telligent.model.db;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.jdom.Document;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.telligent.util.exceptions.ParseException;


/** 
 * Singleton class which is a facade to the
 * System Configuration in Props file.
 *
 * @author spothu
 */
public class Config {
  private static final String DIR_TAG = "directory";

  private org.w3c.dom.Document m_document;
  private String m_configFile;
  private static Config _self;

  /**
   * This method returns the RootElement of the underlaying XML document
   * @return Root element of the xml document
   */
  public Element getRootElement() {
    return m_document.getDocumentElement();
  }
/*
 * Method to set the property and value to the property file
 */
  public synchronized void setProperty(String propName, String value) {
    Element elem = DOMUtils.getElementByPath(m_document.getDocumentElement(), propName);
    DOMUtils.setElementText(elem, value);
  }
  /*
   * Method to add the property and value to the property file
   */
  public synchronized void addProperty(String propName, String value) {
    Element elem = DOMUtils.getElementByPath(m_document.getDocumentElement(), propName, true);
    DOMUtils.setElementText(elem, value);
  }
  /*
   * Method to remove property based on proName
   */
  public synchronized void removeProperty(String propName) {
    DOMUtils.removeElementByPath(m_document.getDocumentElement(), propName);
  }
  /*
   * Method to get the property value baces on property name
   * return string 
   */
  public synchronized String getProperty(String propName) {
    Element elem = DOMUtils.getElementByPath(m_document.getDocumentElement(), propName);
    if (elem == null) {
      return null;
    }
    String str = DOMUtils.getElementText(elem);
    return str;
  }

  /*
   * Method to get the childrens of property
   */
  public synchronized String[] getProperties(String propName) {
    DOMBuilder db = new DOMBuilder();

    Document doc = db.build(m_document);
    org.jdom.Element root = null;

    StringTokenizer st = new StringTokenizer(propName, "/");

    int tokenCount = st.countTokens();
    for (int i = 0; i < (tokenCount - 1); i++) {
      if (root == null) {
        root = doc.getRootElement();
      }
      root = root.getChild(st.nextToken().toString());
    }

    if (root == null)
      root = doc.getRootElement();

    List retElements = root.getChildren(st.nextToken().toString());

    int size = retElements.size();

    if (size == 0)
      return null;

    String[] retVal = new String[size];

    for (int i = 0; i < size; i++) {
      retVal[i] = ((org.jdom.Element) retElements.get(i)).getText();
    }

    return retVal;

  }

  /*
   * Method to get the childrens of the property
   * return Properties contains propname and value
   */
  public synchronized Properties getNameValuePairs(String propName) {
    //Get the Properties elements
    Element elem = DOMUtils.getElementByPath(m_document.getDocumentElement(), propName);
    if (elem == null) {
      return null;
    }
    //Get the list of property elements
    Element[] propElements = DOMUtils.getChildElements(elem, "property");
    if ((propElements == null) || (propElements.length == 0))
      return null;
    Properties props = new Properties();
    for (int i = 0; i < propElements.length; i++) {
      Element child = propElements[i];
      //Get the attribute name
      String name = child.getAttribute("name");
      //if the attribute name is null then value doesnot matter
      if (name != null) {
        String value = DOMUtils.getElementText(child);
        props.setProperty(name, value);
      }
    }
    return props;

  }

  /*
   * Method to get the property value based on propertyName. 
   * if property name doesnot exsists it return default value.
   * @param propname and defaultvalue  
   */
  public synchronized String getProperty(String propName, String defaultValue) {
    String retVal = getProperty(propName);

    if (retVal == null || retVal.equals(""))
      return defaultValue;

    return retVal;
  }

  /**
   * Create a <code>Config</code> object from the given file.
   */
  private Config(String configFile) throws ParseException, IOException {
    loadConfig(configFile);
  }

  private Config() {
  }

  /** Method to get an existing instance of the
   * Config object.
   * @return Same Object.
   */
  public static Config getInstance() throws Exception
  {
      if( _self == null )
      {
          _self = new Config();
      }

      return _self;
  }

  /** Method to get an instance of the Config
   * object given a filename.
   * @return Same Object.
   * @param fileName Configuration file name with path.
   */
  public static Config getInstance( String fileName ) throws Exception
  {
      Config instance = getInstance();
      instance.loadConfig( fileName );
      _self = instance;
      return _self;
  }
  
  /*
   * Method to get a fresh instance of the Config Object
   * without mucking with the existing instance.
   * This method is basically a utility method, since the available 
   * instance gets changed everytime getInstance( fileName ) is called
   * @return Same Object
   * @param FileName - configuration fileName with Path.
   */
  public static Config newInstance( String fileName ) throws Exception
  {
      Config instance = new Config();
      instance.loadConfig( fileName );
      return instance;
  }

  /*
   * Method to set the propertyfile path
   */
  public synchronized void setPath(String propName, String path) {
    setProperty(propName + "/" + DIR_TAG, path);
  }

  /*
   * Method to get integer value of the property
   */
  public int getInt(String propName, int defaultValue) {
    try {
      return getInt(propName);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /*
   * Method to get integer value of the property
   */
  public int getInt(String propName) {
    int i = 0;
    try {
      String s = getProperty(propName);
      if (s != null) {
        i = Integer.parseInt(s);
      } else
        throw new RuntimeException("Invalid property :" + propName);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
    return i;
  }
  /*
   * Method to get all properties
   * @return Hashtable containing property name and value
   */
  public Hashtable getProperties() {
    Hashtable properties = new Hashtable();
    getConfigProperties(properties, new Hashtable(), 0, m_document.getDocumentElement());
    return properties;
  }

  private void getConfigProperties(Hashtable properties, Hashtable names, int level, Node node) {
    NodeList childNodes = node.getChildNodes();
    boolean isLeafNode = true;
    Node cur;
    for (int i = 0; i < childNodes.getLength(); i++) {
      cur = childNodes.item(i);
      if (cur.getNodeType() == Node.ELEMENT_NODE) {
        isLeafNode = false;
        names.put(level + "", cur.getNodeName());
        level++;
        getConfigProperties(properties, names, level, cur);
        level--;
      }
    }
    if (isLeafNode) {
      StringBuffer property = new StringBuffer();
      int k;
      for (k = 0; k < level - 1; k++)
        property.append((String) names.get(k + "") + "/");
      property.append((String) names.get(k + ""));

      String propertyName = property.toString();
      String propertyValue = getProperty(propertyName);
      if (propertyValue != null) {
        properties.put(propertyName, propertyValue);
      }
    }
  }
  
  public Properties getNestedProperties( String top )
  {
    Properties properties = new Properties();
    getConfigProperties(properties, new Hashtable(), 0, m_document.getDocumentElement());
    Enumeration keys = properties.keys();
    while( keys.hasMoreElements() )
    {
        String key = (String) keys.nextElement();
        if(! key.startsWith(top) )
        {
            properties.remove(key);
        }
    }
    return properties;
  }

  public void saveToFile() throws IOException {
    File f = new File(m_configFile);
    try {
      String str = DOMUtils.toString(m_document.getDocumentElement());
      FileWriter writer = new FileWriter(f);
      writer.write(str);
      writer.close();
    } catch (ParseException e) {
      throw new IOException("Cannot write config.xml file: " + e.getMessage());
    }
  }
  
  public String getFileName() {
    return m_configFile;
  }
  /*
   * Method to get property file as xml document
   */
  public String getConfigXMLString() throws ParseException {
    String str = DOMUtils.toString(m_document.getDocumentElement());
    return str;
  }

  public String showAllParameters() {
    String retVal = null;
    try {
      DOMBuilder db = new DOMBuilder();
      org.jdom.Document doc = db.build(m_document);
      XMLOutputter xo = new XMLOutputter("  ", true);
      return xo.outputString(doc);
    } catch (Exception e) {
      retVal = e.getMessage();
    }
    return retVal;
  }
  /**
   * Load properties from the given config file.
   * Existing properties will be cleared.
   *
   * @throws ParseException if there is a problem parsing the XML file
   * @throws IOException if there is a problem reading the file from disk
   */
  public void loadConfig(String configFile) throws ParseException, IOException {
    m_configFile = configFile;
    File f = new File(configFile);    
    m_document = DOMUtils.parse(f);
  }
  /**
   * Load properties from the given xml file.
   */
  public void loadConfigXMLString(String xmlString) throws ParseException, IOException {
    m_document = DOMUtils.parseXMLString(xmlString);
  }

  /**
   * Load properties from the given reader.
   * Existing properties will be cleared.
   *
   * @throws ParseException if there is a problem parsing the XML file
   * @throws IOException if there is a problem reading the file from disk
   * @author rdhar
   */
  public void loadConfig(final Reader in) throws IOException {
    try {
		m_document = DOMUtils.parse(in);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  /**
   * Load properties from the given InputStream.
   * Existing properties will be cleared.
   *
   * @throws ParseException if there is a problem parsing the XML file
   * @throws IOException if there is a problem reading the file from disk
   * @author rdhar
   */
  public void loadConfig(InputStream stream) throws ParseException, IOException {
    m_document = DOMUtils.parse(stream);
  }


  public boolean getBoolean(String inPropName) {
    String value = getProperty(inPropName);
    if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes")) {
      return true;
    } else if (value.equalsIgnoreCase("false") || value.equalsIgnoreCase("no")) {
      return false;
    } else {
      throw new RuntimeException("Property not valid : " + inPropName);
    }
  }

}