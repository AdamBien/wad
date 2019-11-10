package com.airhacks.wad.control;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface ThinWarNameProvider {

    public static String getCurrentThinWarName(Path pom) throws IOException {
        String thinWARName = getNameByDefaultConvention();

        String finalName = getNameByFinalName(pom);

        if (finalName != null && finalName.trim().length() > 0) {
            thinWARName = finalName + ".war";
        }

        return thinWARName;
    }

    static String getNameByFinalName(final Path pom) throws IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        String result;

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            File pomXml = pom.toFile();
            Document document = builder.parse(new InputSource(new FileReader(pomXml)));
            XPath xPath = XPathFactory.newInstance().newXPath();

            result = (String) xPath.evaluate("/project/build/finalName", document, XPathConstants.STRING);
        } catch (ParserConfigurationException | SAXException | XPathExpressionException e) {
            return null;
        }

        return result;
    }

    static String getNameByDefaultConvention() {
        Path currentPath = Paths.get("").toAbsolutePath();
        Path currentDirectory = currentPath.getFileName();
        return currentDirectory + ".war";
    }


}
