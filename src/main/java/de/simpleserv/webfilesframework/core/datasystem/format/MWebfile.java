package de.simpleserv.webfilesframework.core.datasystem.format;

import de.simpleserv.webfilesframework.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

public class MWebfile {

    protected Integer time;
    protected String[] dataset;
    protected Integer m_iId;

    /**
     * @var int sets the time of the main context of the given webfile.
     * Example:<br />
     * An event would have the point when it takes place. An news entry
     * would have the creation time as context time.
     */
    public int m_iTime;

    /**
     * @var string
     */
    public static String $m__sClassName;

    public String marshall() {
        return marshall(true);
    }

    /**
     * Converts the current webfile into its xml representation.
     *
     * @param usePreamble sets the option of using a preamble in xml - usually used for setting the version of xml an the encoding.
     * @return string returns the webfile as a marshalled String.
     */
    public String marshall(boolean usePreamble) {
        String out = "";
        LinkedList<Field> attributes = this.getAttributes();
        if (usePreamble) {
            out += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        }
        out += "<object classname=\"" + this.getClass().getCanonicalName() + "\">\n";// TODO normalize class name
        for (Field attribute : attributes) {

            String attributeName = attribute.getName();

            if (isSimpleDatatype(attributeName)) {
                attribute.setAccessible(true);
                String attributeFieldName = getSimplifiedAttributeName(attributeName);
                try {
                    out += "\t<" + attributeFieldName + "><![CDATA[" + attribute.get(this) + "]]></" + attributeFieldName + ">\n";
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        out += "</object>";

        return out;
    }

    /**
     * Converts the given xml into a webfile object.
     *
     * @param data xml which represents a webfile.
     */
    public MWebfile unmarshall(String data) throws MWebfilesFrameworkException {
        return genericUnmarshall(data, this);
    }

    /**
     * Converts the given xml-String into a new webfile object.
     *
     * @param xmlAsString
     * @return MWebfile
     */
    public static MWebfile staticUnmarshall(String xmlAsString) throws MWebfilesFrameworkException {
        return genericUnmarshall(xmlAsString);
    }


    private static MWebfile genericUnmarshall(String xmlAsString) throws MWebfilesFrameworkException {
        return genericUnmarshall(xmlAsString, null);

    }

    private static MWebfile genericUnmarshall(String xmlAsString, MWebfile targetObject) throws MWebfilesFrameworkException {

        try {
            Document document = XmlHelper.readDocumentFromString(xmlAsString);

            Node root = document.getFirstChild();
            String nodeName = root.getNodeName();

            if (nodeName.equals("reference")) {
                /*$url = $root->url;
                $xmlAsString = file_get_contents($url);
                $root = readDocumentFromString($xmlAsString);

                if ($root == null) {
                    throw new MWebfilesFrameworkException("Error on reading reference xml: " + xmlAsString);
                }*/
            }

            System.out.println();
            if ( targetObject == null ) {

                String classname = root.getAttributes().getNamedItem("classname").getNodeValue();
                classname = "de." + classname;
                classname = classname.replace("\\",".");

                Class<?> clazz = Class.forName(classname);
                Constructor<?> ctor = clazz.getConstructor();
                //Object object = ctor.newInstance(new Object[] { ctorArgument });

                // INSTANCIATE NEW
                targetObject = (MWebfile) ctor.newInstance(new Object[] { });

            }

            NodeList objectAttributes = root.getChildNodes();
            LinkedList<Field> attributes = targetObject.getAttributes();

            int actual = 0;
            int length = objectAttributes.getLength();

            while (actual < length) {

                Node node = objectAttributes.item(actual);
                actual++;
                if ( node.getNodeType() == Node.ELEMENT_NODE ) {
                    for (Field attribute : attributes) {

                        attribute.setAccessible(true);
                        String attributeName = attribute.getName();
                        if (node.getNodeName().equals(getSimplifiedAttributeName(attributeName))) {
                            try {
                                if (node.getTextContent() != null) {
                                    setAttributeValue(targetObject, attribute, node.getTextContent());
                                } else {
                                    System.out.println(node.getNodeName() + " is emtpy.");
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            return targetObject;

        } catch (ParserConfigurationException e) {// TODO improve exception handling
            throw new MWebfilesFrameworkException("Error on reading initial xml: " + xmlAsString, e);
        } catch (SAXException e) {
            throw new MWebfilesFrameworkException("Error on reading initial xml: " + xmlAsString, e);
        } catch (IOException e) {
            throw new MWebfilesFrameworkException("Error on reading initial xml: " + xmlAsString, e);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void setAttributeValue(MWebfile targetObject, Field attribute, String nodeValue) throws IllegalAccessException {

        if ( attribute.getType().equals(Boolean.class) ) {
            attribute.set(targetObject, Boolean.parseBoolean(nodeValue));
        } else if ( attribute.getType().equals(Integer.class) ) {
            attribute.set(targetObject,Integer.parseInt(nodeValue));
        } else if ( attribute.getType().equals(Double.class) ) {
            attribute.set(targetObject,Double.parseDouble(nodeValue));
        } else {
            attribute.set(targetObject,nodeValue);
        }

    }

    /**
     * In case of using the current webfile object for making a request
     * on a datastore (getByTemplate()) this method helps to
     * set the defaults for making the template request.
     */
    public void presetForTemplateSearch() {

        List<Field> list = getAttributes();

        // TODO alle felder die in der templatesuche ignoriert werden eine liste machen
        for (Field attribute : list) {

            String attributeName = attribute.getName();

            if (isSimpleDatatype(attributeName)) {
                attribute.setAccessible(true);
                try {
                    setAttributeValue(this, attribute, "?");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean matchesTemplate(MWebfile template) {

        /*if ( $template::$m__sClassName == static::$m__sClassName ) {

            $attributes = $template->getAttributes(true);

            /** @var \ReflectionProperty $attribute */
            /*foreach ($attributes as $attribute) {

                $attribute->setAccessible(true);
                $templateValue = $attribute->getValue($template);

                if (
                        $templateValue != "?"
                                && !($templateValue instanceof MIDatastoreFunction)
                        ) {

                    $webfileValue = $attribute->getValue($this);
                    if ($templateValue != $webfileValue) {
                        return false;
                    }
                }
            }
            return true;
        } else */
        {
            return false;
        }

    }

    /**
     * returns true if attribute is a simple datatype (for example
     * string, integer or boolean).
     *
     * @param datatypeName
     * @return boolean
     */
    public static boolean isSimpleDatatype(String datatypeName) {
        if (!isObject(datatypeName) && datatypeName.substring(2, 3).equals("_")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * returns true if attribute is object in the other case the returnvalue is false
     *
     * @param attributeName
     * @return boolean
     */
    public static boolean isObject(String attributeName) {
        if (attributeName.substring(2, 3).equals("o")) {
            return true;
        } else {
            return false;
        }

    }

    public LinkedList<Field> getAttributes() {
        return getAttributes(true);
    }

    /**
     * Returns the attributes of the actual class which are relevant for the
     * webfile definition.
     *
     * @param onlyAttributesOfSimpleDatatypes
     * @return array array with attributes
     */
    public LinkedList<Field> getAttributes(boolean onlyAttributesOfSimpleDatatypes) {
        Field[] fields = this.getClass().getDeclaredFields();

        LinkedList<Field> fieldList = new LinkedList<Field>();
        for (Field field : fields) {

            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            String attributeName = field.getName();

            if (attributeName.substring(1, 2).equals("_") &&
                    !attributeName.substring(2, 3).equals("_") &&
                    (onlyAttributesOfSimpleDatatypes && !attributeName.substring(2, 3).equals("o"))
                    ) {
                fieldList.add(field);
            }

        }

        return fieldList;
    }

    /**
     * Returns a xml defined class information. It contains the
     * classname and the given attributes.
     *
     * @return string xml with information about the class
     */
    public static String getClassInformation() {

        String returnValue = "<classinformation>\n";
        returnValue += "\t<author>simpleserv.de</author>\n";
        returnValue += "\t<classname>" + $m__sClassName + "</classname>\n";
        returnValue += "\t<attributes>\n";

        /*List<Field> attributes = getAttributes();

        for (Field attribute : attributes) {

            String attributeName = attribute.getName();

            if (isSimpleDatatype(attributeName)) {
                String attributeFieldName = getSimplifiedAttributeName(attributeName);
                String attributeFieldType = getDatatypeFromAttributeName(attributeName);
                returnValue += "\t\t<attribute name=\"" + attributeFieldName + "\" type=\"" + attributeFieldType + "\" />\n";
            }
        }

        returnValue += "\t</attributes>\n";
        returnValue += "</classinformation>";
        return returnValue;*/
        return null;

    }

    /**
     * Enter description here ...
     *
     * @param attributeName
     * @return null|string
     */
    public static String getDatatypeFromAttributeName(String attributeName) {
        // TODO generalize attribute prefix (sample "m_-s-", (start 2, length 1) )
        String datatypeToken = attributeName.substring(2, 3);

        if ("s".equals(datatypeToken)) {
            return "shorttext";
        } else if ("l".equals(datatypeToken)) {
            return "longtext";
        } else if ("h".equals(datatypeToken)) {
            return "htmllongtext";
        } else if ("d".equals(datatypeToken)) {
            return "date";
        } else if ("t".equals(datatypeToken)) {
            return "time";
        } else if ("i".equals(datatypeToken)) {
            return "integer";
        } else if ("f".equals(datatypeToken)) {
            return "float";
        } else if ("o".equals(datatypeToken)) {
            return "object";
        } else {
            return null;
        }
    }

    /**
     * Transforms the actual webfile into an dataset. A dataset is represented by a key value array.
     * The key is the attributes name. The value is the attributes value.
     * @return array
     */
    /*public function getDataset()
    {
        $dataset = array();

        $attributes = $this->getAttributes();

        foreach ($attributes as $attribute) {

        $attributeName = $attribute->getName();
        $attribute->setAccessible(true);
        $attributeValue = $attribute->getValue($this);

        if (MWebfile::isSimpleDatatype($attributeName)) {
            $attributeFieldName = static::getSimplifiedAttributeName($attributeName);
            $dataset[$attributeFieldName] = $attributeValue;
        }
    }
        return $dataset;
    }*/


    /**
     * Returns database field name to a given attribute
     *
     * @param p_sFieldName
     * @return string
     */
    public static String getSimplifiedAttributeName(String p_sFieldName) {
        // TODO generalize attribute prefix (sample "m_-s-", (start 2, length 1) )
        String sDatabaseFieldName = p_sFieldName.substring(3);
        sDatabaseFieldName = sDatabaseFieldName.toLowerCase();
        return sDatabaseFieldName;
    }

    public Integer getId() {
        return this.m_iId;
    }

    public void setId(int itemId) {
        this.m_iId = itemId;
    }

    /**
     *
     */
    public int getTime() {
        return this.m_iTime;
    }

    /**
     * @param $time unix timestamp of the context time.
     */
    public void setTime(int $time) {
        this.m_iTime = $time;
    }

    public String getGeograficPosition() {
        return null;
    }

}
