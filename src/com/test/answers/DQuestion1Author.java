package com.test.answers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DQuestion1Author {

	private String name;
	private List<String> articles;
	private HashMap<String, Integer> coAuthors;
	private String fileLocation;
	Map<String, DQuestion1Author> authors;

	public DQuestion1Author() {
		coAuthors = new HashMap<String, Integer>();
		articles = new ArrayList<String>();
	}

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		DQuestion1Author auths = new DQuestion1Author();
		System.out.println("Enter the absolute location of the file");
		String fileLocation = scanner.nextLine();
		auths.setFileLocation(fileLocation);
		auths.showResult();
		scanner.close();
		
	}

	public static String rightPadding(String input, char ch, int length) {

		String result = String.format("%" + (-length) + "s", input).replace(' ', ch);
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getArticles() {
		return articles;
	}

	public void setArticles(List<String> articles) {
		this.articles = articles;
	}

	public HashMap<String, Integer> getCoAuthors() {
		return coAuthors;
	}

	public void setCoAuthors(HashMap<String, Integer> coAuthors) {
		this.coAuthors = coAuthors;
	}

	public String addCoAuthor(String author) {
		if (!coAuthors.keySet().contains(author)) {
			coAuthors.put(author, 1);

		} else {
			int count = coAuthors.get(author);
			coAuthors.put(author, count + 1);
		}
		return author;
	}

	public  Map<String, DQuestion1Author> getAuthors() {

		Map<String, DQuestion1Author> authors = new HashMap<String, DQuestion1Author>();

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

		for (int i = 0; i < articleList.getLength(); i++) {

			// creating holder list to loop to assign each author as co author
			List<DQuestion1Author> authorsInArticle = new ArrayList<DQuestion1Author>();
			Node articleAuthors = articleList.item(i);
			Element e = (Element) articleAuthors;

			// getting the nodes of the authors
			NodeList authorsNodeList = e.getElementsByTagName("Author");

			// this is to get the title of the article to add to the list of articles belong
			// to the author the number in it would be mapped to the author in the matrix
			String title = e.getElementsByTagName("ArticleTitle").item(0).getTextContent();

			for (int a = 0; a < authorsNodeList.getLength(); a++) {

				// Simple process to get the name minus the initials
				String[] names = (authorsNodeList.item(a).getTextContent().strip().split("[^\\S\\r\\n]{2,}"));
				String name = names[0].strip() + ", " + names[1].strip();

				DQuestion1Author author = new DQuestion1Author();
				author.setName(name);
				author.getArticles().add(title);
				authorsInArticle.add(author);

				// checking to find the map keyset contains the name of the author
				if (!authors.containsKey(name)) {
					authors.put(name, author);

				} else {
					// if already exits only add the article to the article list of the author
					authors.get(name).getArticles().add(title);
				}

			}

			for (int a = 0; a < authorsInArticle.size(); a++) {
				DQuestion1Author as = authorsInArticle.get(a);
		
				// removing the author from the list of authors article to assign it as
				// co-author
				authorsInArticle.stream().filter(ge -> !ge.getName().equalsIgnoreCase(as.getName()))
						.forEach(au -> authors.get(as.getName()).addCoAuthor(au.getName()));

			}

		}
		return authors;

	}
	
	public  void showResult() {
		//this is to repeat the empty spaces
		System.out.print(String.join("", Collections.nCopies(13, " ")) + " |");
		
		for (String a : getAuthors().keySet()) {
			// Making the cell standard by padding based on the length of the name of the
			// authors

			System.out.print(" " + a + " |");
		}
		System.out.println();
		System.out.print(String.join("", Collections.nCopies(13, "-")) + " |");

		System.out.println();
		System.out.print(String.join("", Collections.nCopies(13, "-")) + " |");

		for (String a : getAuthors().keySet()) {
			// Making the cell standard by padding based on the length of the name of the
			// authors
			System.out.print(String.join("", Collections.nCopies(a.length() + 2, "-")) + "|");
		}

		System.out.println();

		for (String a : getAuthors().keySet()) {
			System.out.print("| " + rightPadding(a, ' ', 11) + " |");
			for (String peer : getAuthors().keySet()) {

				// when the peer is equal to author then use the size of the list of titles the
				// author has written
				if (peer.contentEquals(a)) {
					System.out.print(" "
							+ rightPadding(String.valueOf(getAuthors().get(peer).getArticles().size()), ' ', peer.length())
							+ " |");

				}
				// when the peer is not the author then use the co-author

				else {
					int data = 0;
					// if the map does not contain the peer this returns null which is not a number
					try {
						data = getAuthors().get(a).getCoAuthors().get(peer);
					} catch (NullPointerException e) {

					}
					// formatting the cell by right padding
					System.out.print(" " + rightPadding("" + data, ' ', peer.length()) + " |");

				}

			}
			System.out.println();
		}
		
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
}
