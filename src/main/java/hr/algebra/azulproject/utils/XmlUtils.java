package hr.algebra.azulproject.utils;

import hr.algebra.azulproject.model.GameMove;
import hr.algebra.azulproject.model.Tiles;
import hr.algebra.azulproject.xml.GameMoveSorter;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class XmlUtils {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
    public static final String FILENAME = "xml/gameMoves.xml";

    public static void saveGameMove(GameMove gameMove) {

        Set<GameMove> gameMoveList = new HashSet<>();

        if (Files.exists(Path.of(FILENAME))) {
            gameMoveList.addAll(XmlUtils.readAllGamemoves());
        }

        gameMoveList.add(gameMove);
        try {
            Document document = createDocument("gameMoves");
            for (GameMove gm : gameMoveList) {
                Element gameMoveElement = document.createElement("gameMove");
                document.getDocumentElement().appendChild(gameMoveElement);

                gameMoveElement.appendChild(createElement(document, "tile", gm.getTile()));
                gameMoveElement.appendChild(createElement(document, "position", gm.getPosition()));
                gameMoveElement.appendChild(createElement(document, "dateTime", gm.getLocalDateTime().format(dateTimeFormatter)));
                gameMoveElement.appendChild(createElement(document, "factoryTile", gm.getFactoryTile()));
            }

            saveDocument(document, FILENAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Document createDocument(String element) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation domImplementation = builder.getDOMImplementation();
        return domImplementation.createDocument(null, element, null);
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

        transformer.transform(new DOMSource(document), new StreamResult(new File(FILENAME)));
    }

    public static Set<GameMove> readAllGamemoves() {


        SortedSet<GameMove> gameMoves = new TreeSet<>(new GameMoveSorter());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(FILENAME));

            Element documentElement = document.getDocumentElement();

            NodeList gameMovesChildList =  documentElement.getChildNodes();

            for(int i = 0; i < gameMovesChildList.getLength(); i++){

                Node gameMoveNode = gameMovesChildList.item(i);
                if(gameMoveNode.getNodeType() == Node.ELEMENT_NODE){

                    String tile = "";
                    String position = "";
                    LocalDateTime localDate = LocalDateTime.now();
                    String factoryTile = "";

                    Element gameMoveElement = (Element) gameMoveNode;
                    System.out.println("Read: " + gameMoveElement.getTagName());

                    NodeList gameMoveChildList = gameMoveElement.getChildNodes();
                    for(int j = 0; j < gameMoveChildList.getLength(); j++){
                        Node gameMoveChildNode = gameMoveChildList.item(j);
                        if(gameMoveChildNode.getNodeType() == Node.ELEMENT_NODE){
                            Element gameMoveChildElement = (Element) gameMoveChildNode;

                            switch (gameMoveChildElement.getTagName()){
                                case "tile" -> tile = gameMoveChildElement.getTextContent();
                                case "position" -> position = gameMoveChildElement.getTextContent();
                                case "factoryTile" -> factoryTile = gameMoveChildElement.getTextContent();
                                case "dateTime" -> localDate = LocalDateTime.parse(gameMoveChildElement.getTextContent(),
                                        dateTimeFormatter);
                            }
                        }
                    }
                    GameMove newGameMove = new GameMove(tile, position, localDate, factoryTile);
                    gameMoves.add(newGameMove);
                }
            }
        }
        catch(ParserConfigurationException | SAXException | IOException e){
            e.printStackTrace();
        }

        return gameMoves;
    }
}
