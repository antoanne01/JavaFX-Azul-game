package hr.algebra.azulproject.dom.xml;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class DOMCreator {

    private static final String FILENAME = "xml/employees_manual.xml";

    public static void main(String[] args) {

        try {
            Document document = createDocument("employees");
            Element employee = document.createElement("employee");
            document.getDocumentElement().appendChild(employee);

            employee.setAttributeNode(createAttribute(document, "type", "REGULAR"));
            employee.appendChild(createElement(document, "id", "123"));
            employee.appendChild(createElement(document, "firstname", "Robert"));
            employee.appendChild(createElement(document, "lastname", "meDiro"));
            employee.appendChild(createElement(document, "income", "1000.01"));

            saveDocument(document, FILENAME);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private static Document createDocument(String element) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation domImplementation = builder.getDOMImplementation();
        DocumentType documentType = domImplementation.createDocumentType("DOCTYPE", null, "employees.dtd");
        return domImplementation.createDocument(null, element, documentType);
    }

    private static Attr createAttribute(Document document, String name, String value) {
        Attr attr = document.createAttribute(name);
        attr.setValue(value);
        return attr;
    }

    private static Node createElement(Document document, String tagName, String data) {
        Element element = document.createElement(tagName);
        Text text = document.createTextNode(data);
        element.appendChild(text);
        return element;
    }

    private static void saveDocument(Document document, String fileName) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, document.getDoctype().getSystemId());
        //transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        //transformer.transform(new DOMSource(document), new StreamResult(System.out));
        transformer.transform(new DOMSource(document), new StreamResult(new File(FILENAME)));
    }

}
