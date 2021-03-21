package com.test.answers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BQuestion2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//This is a simpler way of anwering question2
		System.out.println(foo(10));

	}

	public static List<Integer> foo(int length) {
		List<Integer> list = new ArrayList<>();
		Random random = new Random();
		while (list.size() < length) {
			//
			// int r = random.nextInt(10) + 1; would run forever when the length provided is
			// greater than 10
			int r = random.nextInt(length) + 1;
			if (!list.contains(r)) {
				list.add(r);
			} else {
				continue;
			}

		}
		return list;
	}
}
