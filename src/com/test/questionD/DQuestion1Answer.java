package com.test.questionD;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DQuestion1Answer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the absolute location of the file");
		String fileLocation = scanner.nextLine();
		File inputFile = new File(fileLocation);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.err.println("Can not build document");
			System.exit(0);

		}
		Document doc = null;
		try {
			doc = dBuilder.parse(inputFile);
		} catch (SAXException | IOException e1) {
			// TODO Auto-generated catch block
			System.err.println("Can not parse document");

			System.exit(0);
		}

		doc.getDocumentElement().normalize();

		NodeList articleList = doc.getElementsByTagName("Article");
		if (articleList.getLength() > 0) {
			MedlineCitationSet citations = new MedlineCitationSet(articleList);
			citations.displayResults();
		} else {
			System.err.println("The XML File does not contain an article");

			System.exit(0);
		}
		scanner.close();

	}

}
