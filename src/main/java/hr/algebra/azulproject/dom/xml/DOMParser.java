package hr.algebra.azulproject.dom.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DOMParser {

    private static final String FILENAME = "xml/employees_manual.xml";

    public static void main(String[] args) {
        try {
            parse(FILENAME);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private static void parse(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // play -> break xml <incom>...
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {
                System.err.println("Warning: " + exception);
            }

            @Override
            public void error(SAXParseException exception) throws SAXException {
                System.err.println("Error: " + exception);
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                System.err.println("Fatal error: " + exception);
            }
        });

        Document document = builder.parse(new File(FILENAME));
        StringBuilder report = new StringBuilder();
        processNode(report, document.getDocumentElement(), "");
        System.out.println(report);
    }

    private static void processNode(StringBuilder report, Node node, String indent) {

        switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:
                report
                        .append(indent)
                        .append(node.getNodeName());
                if (node.getAttributes().getLength() > 0) {
                    for (int i = 0; i < node.getAttributes().getLength(); i++) {
                        report
                                .append(" ")
                                .append(node.getAttributes().item(i));
                    }
                }
                break;
            case Node.TEXT_NODE:
            case Node.CDATA_SECTION_NODE:
                String value = node.getNodeValue().trim();
                if (!value.isEmpty()) {
                    report
                            .append("=")
                            .append(value)
                            .append("\n");
                } else if (!report.toString().endsWith("\n")) {
                    report.append("\n");
                }
                break;
        }
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            processNode(report, childNodes.item(i), indent + "\t");
        }
    }
}