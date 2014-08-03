package com.isinnova.travesty;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Travesty {
	private MarkovChain markovNet;
	
	/**
	 * Generates the scrambled text
	 */
	private String scramble(String file, int k, int maxOutputLength) throws IOException {
		StringBuilder outText = new StringBuilder();
		
		// Read the file into memory (only for developing!)
		StringBuilder sb = readFile(file);
		
		// Generates the Markov with states and out states probabilities
		markovNet = generateMarkovNet(sb, k);

		// Select a random state to start
		String currentState = markovNet.getRandomState();
		outText.append(currentState.substring(0, 3));
		
		int i=0;

		while (i<maxOutputLength) {
			if (currentState!=null) {
				// Select the last character of the output tuple (state)
				outText.append(currentState.charAt(3));
				i++;
				// Update the current state (going from state s to state t)
				currentState = markovNet.getRandomOutState(currentState);
			} else {
				// Select another random state because this is a state with no outputs (termination state)
				currentState = markovNet.getRandomState();
				outText.append(" ");
			}
		}
		
		return outText.toString();
	}

	/**
	 * Factory method to create the Markov network
	 */
	private MarkovChain generateMarkovNet(StringBuilder sb, int k) {
		MarkovChain markovNet = new MarkovChain();
		
		Map<String, Integer> tuplesMap = new HashMap<String, Integer>();
		char buffer[] = new char[k+1];
		int lng = sb.length();

		// Calculate tuples occurency (frequency)
		for (int i=0;i<lng-(k+1);i++) {
			sb.getChars(i, i+k+1, buffer, 0);
			String tupleText = new String(buffer);

			Integer count = tuplesMap.get(tupleText);
			if (count==null) {
				tuplesMap.put(tupleText, 1);
				markovNet.addState(tupleText);
			} else {
				tuplesMap.put(tupleText, count+1);	
			}
		}

		// Calculate output states and its probabilities based on frequency
		for (String tuple:tuplesMap.keySet()) {
			
			// The last k chars
			String s = tuple.substring(1, 4);

			// Get the tuples that start with s
			List<String> foundTuples = new ArrayList<String>();
			int totalOccurrences=0;
			
			for (Map.Entry<String, Integer> otherTupleOccurrence:tuplesMap.entrySet()) {
				String otherTuple = otherTupleOccurrence.getKey();
				
				if (otherTuple.startsWith(s)) {
					foundTuples.add(otherTuple);
					totalOccurrences += otherTupleOccurrence.getValue();
				}
			}
			
			// Add out states with its probabilities
			for (String foundTuple:foundTuples) {
				float probability = tuplesMap.get(foundTuple)/(float)totalOccurrences;
				markovNet.addOutState(tuple, foundTuple, probability);
			}			
		}

		return markovNet;
	}

	/**
	 * Reads a file into memory.
	 * 
	 * Only for developing, not use in Production
	 */
	private StringBuilder readFile(String file) throws IOException {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		try {
			br = new BufferedReader(new FileReader(file));

			String line;
			while ( (line=br.readLine()) !=null) {
				sb.append(line).append(" ");
			}
		} finally {
			if (br!=null) br.close();
		}
		
		return sb;
	}

	public static void main(String[] args) throws Exception  {
		/*
		String file = args[0];
		int k = Integer.parseInt(args[1]);
		int max = Integer.parseInt(args[1]);
		*/
		
		String file="c:/borra/texto.txt";
		int k=3;
		int max=1000;

		String text = new Travesty().scramble(file, k, max);
		System.out.println(text);
	}

}
