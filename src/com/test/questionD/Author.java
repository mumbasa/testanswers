package com.test.questionD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Author {

	private String name;
	private List<String> articles;
	private HashMap<String, Integer> coAuthors;

	public Author(String name) {
		coAuthors = new HashMap<String, Integer>();
		articles = new ArrayList<String>();
		this.name = name;
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

	public HashMap<String, Integer> addCoAuthor(String author) {
		if (!coAuthors.keySet().contains(author)) {
			coAuthors.put(author, 1);

		} else {
			int count = coAuthors.get(author);
			coAuthors.put(author, count + 1);
		}
		return coAuthors;
	}

	public int getAuthorNameLength() {
		return name.length();

	}

}
