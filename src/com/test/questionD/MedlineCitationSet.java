package com.test.questionD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MedlineCitationSet {
	private Map<String, Author> authors;
	private NodeList articleList;

	public MedlineCitationSet(NodeList articleList) {
		// TODO Auto-generated constructor stub
		this.authors = new HashMap<String, Author>();

		this.setArticleList(articleList);
	}

	public Map<String, Author> getAuthors() {

		for (int i = 0; i < articleList.getLength(); i++) {

			// creating holder list to loop to assign each author as co author
			List<Author> nodeAuthors = new ArrayList<Author>();
			Node articles = articleList.item(i);
			Element e = (Element) articles;

			// getting the nodes of the authors
			NodeList authorsNodeList = e.getElementsByTagName("Author");

			// this is to get the title of the article to add to the list of articles belong
			// to the author the number in it would be mapped to the author in the matrix
			String title = e.getElementsByTagName("ArticleTitle").item(0).getTextContent();

			for (int a = 0; a < authorsNodeList.getLength(); a++) {

				// Simple process to get the name minus the initials
				String[] names = (authorsNodeList.item(a).getTextContent().strip().split("[^\\S\\r\\n]{2,}"));
				String name = names[0].strip() + ", " + names[1].strip();

				Author author = new Author(name);
				author.getArticles().add(title);
				nodeAuthors.add(author);

				// checking to find the map keyset contains the name of the author
				if (!authors.containsKey(name)) {
					authors.put(name, author);

				} else {
					// if already exits only add the article to the article list of the author
					authors.get(name).getArticles().add(title);
				}

			}

			for (int a = 0; a < nodeAuthors.size(); a++) {
				Author as = nodeAuthors.get(a);
				// removing the author from the list of authors article to assign it as
				// co-author
				nodeAuthors.stream().filter(ge -> !ge.getName().equalsIgnoreCase(as.getName()))
						.forEach(au -> authors.get(as.getName()).addCoAuthor(au.getName()));

			}

		}

		return authors;

	}

	public void displayResults() {
		getAuthors();
		int sizeOfLongesName = sizeOfLongestAuthorName();
		System.out.print("| " + String.join("", Collections.nCopies(sizeOfLongesName, " ")) + " |");

		for (String a : authors.keySet()) {
			// Making the cell standard by padding based on the length of the name of the
			// authors

			System.out.print(" " + a + " |");
		}
		System.out.println();
		System.out.print("| " + String.join("", Collections.nCopies(sizeOfLongesName, "-")) + " |");

		for (String a : authors.keySet()) {
			// Making the cell standard by padding based on the length of the name of the
			// authors
			System.out.print(String.join("", Collections.nCopies(a.length() + 2, "-")) + "|");
		}

		System.out.println();

		for (String a : authors.keySet()) {
			System.out.print("| " + rightPadding(a, ' ', sizeOfLongesName) + " |");
			for (String peer : authors.keySet()) {

				// when the peer is equal to author then use the size of the list of titles the
				// author has written
				if (peer.contentEquals(a)) {
					System.out.print(" "
							+ rightPadding(String.valueOf(authors.get(peer).getArticles().size()), ' ', peer.length())
							+ " |");

				}
				// when the peer is not the author then use the co-author
				else {
					int data = 0;
					// if the map does not contain the peer this returns null which is not a number
					try {
						data = authors.get(a).getCoAuthors().get(peer);
					} catch (NullPointerException e) {

					}
					// formatting the cell by right padding
					System.out.print(" " + rightPadding("" + data, ' ', peer.length()) + " |");

				}

			}
			System.out.println();
		}

	}

	public void setAuthors(Map<String, Author> authors) {
		this.authors = authors;
	}

	public static String rightPadding(String input, char ch, int length) {
		String result = String.format("%" + (-length) + "s", input).replace(' ', ch);
		return result;
	}

	public int sizeOfLongestAuthorName() {
		int maxLength = 0;
		for (Author a : authors.values()) {
			maxLength = a.getAuthorNameLength() > maxLength ? a.getAuthorNameLength() : maxLength;
		}
		return maxLength;

	}

	public NodeList getArticleList() {
		return articleList;
	}

	public void setArticleList(NodeList articleList) {
		this.articleList = articleList;
	}

}
