package de.sebastianmonzel.webfilesframework.core.datastore.webfilestream;

import de.sebastianmonzel.webfilesframework.core.datasystem.format.MWebfile;
import de.sebastianmonzel.webfilesframework.core.datasystem.format.MWebfilesFrameworkException;
import de.sebastianmonzel.webfilesframework.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

public class MWebfileStream {


    private List<MWebfile> webfiles;

    public MWebfileStream(String callResult) {
        webfiles = unmarshall(callResult);
    }

    public List<MWebfile> getWebfiles() {
        return webfiles;
    }

    public LinkedList<MWebfile> unmarshall(String input)
    {

        LinkedList<MWebfile> webfilesResultArray = new LinkedList<MWebfile>();

        try {
            Document document = XmlHelper.readDocumentFromString(input);
            NodeList childNodes = document.getFirstChild().getFirstChild().getChildNodes();

            int length = childNodes.getLength();
            int actual = 0;

            while ( actual < length) {

                Node node = childNodes.item(actual);
                actual++;
                System.out.println(nodeToString(node));

                if ( node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("object") ) {
                    webfilesResultArray.add(MWebfile.staticUnmarshall(nodeToString(node)));
                }

            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MWebfilesFrameworkException e) {
            e.printStackTrace();
        }

        return webfilesResultArray;
    }

    private static String nodeToString(Node node) {
        StringWriter sw = new StringWriter();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (TransformerException te) {
            System.out.println("nodeToString Transformer Exception");
        }
        return sw.toString();
    }
}
