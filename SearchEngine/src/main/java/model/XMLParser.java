package model;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    public List<String> parser(String filename) throws ParserConfigurationException, IOException, SAXException {

        List<String> siteList = new ArrayList<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(new File(filename));
        doc.getDocumentElement().normalize();
        String line = doc.getFirstChild().getFirstChild().getTextContent();

        siteList = List.of(line.split(","));

        return siteList;
    }
}
