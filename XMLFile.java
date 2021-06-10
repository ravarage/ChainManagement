//***********************************************************************
// XMLFile.java Author: Ravyar Sarbast
//
// mostly copied from internet, its to save and read setting in xml format
//************************************************************************

package RavyarSarbastTahir;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XMLFile {

    public static final String xmlFilePath = "config.xml";

    public static void save(String usernames, String passwords, String databases, String hosts, String ports) {

        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            // root element
            Element database = document.createElement("Database");
            document.appendChild(database);
            Element maria = document.createElement("MariaDB");
            database.appendChild(maria);


            // employee element
            Attr account = document.createAttribute("account");
            account.setValue("1");
            maria.setAttributeNode(account);
            Element username = document.createElement("Username");
            username.appendChild(document.createTextNode(usernames));
            maria.appendChild(username);
            Element password = document.createElement("Password");
            password.appendChild(document.createTextNode(passwords));
            maria.appendChild(password);
            Element dbname = document.createElement("DatabaseName");
            dbname.appendChild(document.createTextNode(databases));
            maria.appendChild(dbname);
            Element address = document.createElement("Address");
            address.appendChild(document.createTextNode(hosts));
            maria.appendChild(address);
            Element port = document.createElement("Port");
            port.appendChild(document.createTextNode(ports));
            maria.appendChild(port);

            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));
            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    String[] read() throws ParserConfigurationException, IOException, SAXException {

        //an instance of factory that gives a document builder
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//an instance of builder to parse the specified xml file
        DocumentBuilder db = dbf.newDocumentBuilder();
        String[] data = {"", "", "", "127.0.0.1", "3306"};
        try {
            Document doc = db.parse(xmlFilePath);

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Database");

// nodeList is not iterable, so we are using for loop
            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                Node node = nodeList.item(itr);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    data = new String[]{eElement.getElementsByTagName("Username").item(0).getTextContent(), eElement.getElementsByTagName("Password").item(0).getTextContent(), eElement.getElementsByTagName("DatabaseName").item(0).getTextContent(), eElement.getElementsByTagName("Address").item(0).getTextContent(), eElement.getElementsByTagName("Port").item(0).getTextContent()};
                    return data;

                }
            }
        } catch (Exception e) {

        }

        return data;
    }
}