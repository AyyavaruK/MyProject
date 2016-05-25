/*
 * DBConfig.java
 *
 * Created on May 13, 2004, 5:52 PM
 */

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
 * @author spothu
 */

public class DBConfig 
{
    
   private static final String DIR_TAG = "directory";

  private org.w3c.dom.Document m_document;
  private String m_configFile;
  private static DBConfig _self;

  /**
   * This method returns the RootElement of the underlaying XML document
   * @return Root elemnt of the xml document
   */
  public Element getRootElement() {
    return m_document.getDocumentElement();
  }
  /**
   * This method sets property
   */

  public synchronized void setProperty(String propName, String value) {
    Element elem = DOMUtils.getElementByPath(m_document.getDocumentElement(), propName);
    DOMUtils.setElementText(elem, value);
  }
  /**
   * This method adds property
   */
  public synchronized void addProperty(String propName, String value) {
    Element elem = DOMUtils.getElementByPath(m_document.getDocumentElement(), propName, true);
    DOMUtils.setElementText(elem, value);
  }
  /**
   * This method removes property
   */
  public synchronized void removeProperty(String propName) {
    DOMUtils.removeElementByPath(m_document.getDocumentElement(), propName);

  }
  /**
   * This method gets property
   */
  public synchronized String getProperty(String propName) {
    Element elem = DOMUtils.getElementByPath(m_document.getDocumentElement(), propName);
    if (elem == null) {
      return null;
    }
    String str = DOMUtils.getElementText(elem);
    return str;
  }
  
  /**
   * This method gets properties depending on the param propName
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
  /**
   * This method gets name pair values
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
  
  /**
   * This method gets property depending on the param propName and defaultValue
   */

  public synchronized String getProperty(String propName, String defaultValue) {
    String retVal = getProperty(propName);

    if (retVal == null || retVal.equals(""))
      return defaultValue;

    return retVal;
  }

  /**
   * Create a <code>DBConfig</code> object from the given file.
   * @throws ParseException if there is a problem DBConfig
   * @throws IOException if there is a problem reading the file from disk
   */
  private DBConfig(String configFile) throws ParseException, IOException {
    loadDBConfig(configFile);
  }

  private DBConfig() {
  }

  /** This method to get an existing instance of the
   * DBConfig object.
   * @return Same Object.
   * @throws Exception while getting instance
   */
  public static DBConfig getInstance() throws Exception
  {
      if( _self == null )
      {
          _self = new DBConfig();
      }

      return _self;
  }

  /** This method to get an instance of the DBConfig
   * object given a filename.
   * @return Same Object.
   * @param fileName Configuration file name with path.
   */
  public static DBConfig getInstance( String fileName ) throws Exception
  {
      DBConfig instance = getInstance();
      instance.loadDBConfig( fileName );
      _self = instance;
      return _self;
  }

   /**
   * This method sets path
   */
  public synchronized void setPath(String propName, String path) {
    setProperty(propName + "/" + DIR_TAG, path);
  }

  public int getInt(String propName, int defaultValue) {
    try {
      return getInt(propName);
    } catch (Exception e) {
      return defaultValue;
    }
  }

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
  /**
   * This method gets properties
   */
  public Hashtable getProperties() {
    Hashtable properties = new Hashtable();
    getDBConfigProperties(properties, new Hashtable(), 0, m_document.getDocumentElement());
    return properties;
  }
  /**
   * This method gets DBConfig properties
   */
  private void getDBConfigProperties(Hashtable properties, Hashtable names, int level, Node node) {
    NodeList childNodes = node.getChildNodes();
    boolean isLeafNode = true;
    Node cur;
    for (int i = 0; i < childNodes.getLength(); i++) {
      cur = childNodes.item(i);
      if (cur.getNodeType() == Node.ELEMENT_NODE) {
        isLeafNode = false;
        names.put(level + "", cur.getNodeName());
        level++;
        getDBConfigProperties(properties, names, level, cur);
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
  /**
   * This method gets Nested properties
   */
  public Properties getNestedProperties( String top )
  {
    Properties properties = new Properties();
    getDBConfigProperties(properties, new Hashtable(), 0, m_document.getDocumentElement());
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
  /**
   * This method saves to file
   */
  public void saveToFile() throws IOException {
    File f = new File(m_configFile);
    try {
      String str = DOMUtils.toString(m_document.getDocumentElement());
      FileWriter writer = new FileWriter(f);
      writer.write(str);
      writer.close();
    } catch (Exception e) {
      throw new IOException("Cannot write DBConfig.xml file: " + e.getMessage());
    }
  }
  /**
   * This method gets file name
   */
  public String getFileName() {
    return m_configFile;
  }
  /**
   * This method gets DBConfig XMLString
   */
  public String getDBConfigXMLString() throws ParseException {
    String str = DOMUtils.toString(m_document.getDocumentElement());
    return str;
  }
  /**
   * This method shows all parameters
   */
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
   * Load properties from the given DBConfig file.
   * Existing properties will be cleared.
   *
   * @throws ParseException if there is a problem parsing the XML file
   * @throws IOException if there is a problem reading the file from disk
   */
  public void loadDBConfig(String configFile) throws ParseException, IOException {
    m_configFile = configFile;
    File f = new File(configFile);
    m_document = DOMUtils.parse(f);
  }
  
  /**
   * Load properties from the given DBConfig XMLString.
   * @throws ParseException if there is a problem parsing the XML file
   * @throws IOException if there is a problem reading the file from disk
   */
  public void loadDBConfigXMLString(String xmlString) throws ParseException, IOException {
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
  public void loadDBConfig(final Reader in) throws ParseException, IOException {
    m_document = DOMUtils.parse(in);
  }

  /**
   * Load properties from the given InputStream.
   * Existing properties will be cleared.
   *
   * @throws ParseException if there is a problem parsing the XML file
   * @throws IOException if there is a problem reading the file from disk
   * @author rdhar
   */
  public void loadDBConfig(InputStream stream) throws ParseException, IOException {
    m_document = DOMUtils.parse(stream);
  }
  /**
	  * This method checks for config file
	  * @return <code>true</code> if file exist, or
        <code>false</code> if file does not exit.
	*/
  private static boolean checkConfFile(String fileName) {
    File f = new File(fileName);
    return f.exists();
  }
  /**
	  * This method checks for property name
	  * @return <code>true</code> if property name exist, or
        <code>false</code> if property name does not exit.
	*/
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