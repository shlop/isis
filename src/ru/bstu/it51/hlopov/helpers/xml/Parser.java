package ru.bstu.it51.hlopov.helpers.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ru.bstu.it51.hlopov.helpers.xml.Elements;
import ru.bstu.it51.hlopov.helpers.xml.Sax;

public class Parser {
    protected File xml;
    protected Properties prop;

    public Parser(String config) {
        try {
            prop = new Properties();
            FileInputStream file = new FileInputStream("configLab5.properties");
            prop.load(file);
            xml = new File(prop.getProperty("PATH_FILE_XML"));
        } catch (IOException e) {
            System.out.println("Файл не найден");
        }
    }

    public void printAllDocument() {
        Sax SAXParser = new Sax(xml);
    }

    public void addItemToDocument() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xml);
            Element root = document.getDocumentElement();
            document.getDocumentElement().normalize();
            root.appendChild(Elements.getCountry(document));
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult file = new StreamResult(xml);
            transformer.transform(source, file);
            System.out.println("Новая запись успешно создана");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void routingCommands(byte cmd, byte min, byte max) {
        if(cmd <= max && cmd >= min) {
            switch (cmd) {
                case 0:
                    printAllDocument();
                    break;
                case 1:
                    addItemToDocument();
                    break;
            }
        } else {
            System.out.println("Команда не определена");
        }
    }
}
