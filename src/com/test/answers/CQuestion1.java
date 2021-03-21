package com.test.answers;

import java.util.ArrayList;
import java.util.HashMap;

public class CQuestion1 {
	
	public ArrayList<String> findGroupA(HashMap<String, ArrayList<String>> network, HashMap<String, String> employer) {
		//creating an arraylist to contain members of Group A
		ArrayList<String> groupA = new ArrayList<String>();
		
		//each member of network as person
		for (String person : network.keySet()) {
			//initialise a place holder to flag if work at same company
			boolean sameCompany = false;
			//getting company of the person assuming the key of employers is the name of the person
			String company = employer.get(person);
			
			//looping the friends list
			for (String friend : network.get(person)) {
				
				//cheking if the friend of the person works in the same company
				//this would run runs as only one would render joining group one impossible
				if (employer.get(friend).equals(company)) {
					sameCompany = true;
					break;
				}
			}
				// if same compnay is false then add to group A
				if (sameCompany == false) {
					groupA.add(person);
				}
			}

		return groupA;

	}


}
