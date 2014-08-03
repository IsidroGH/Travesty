package com.isinnova.travesty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Models a generic Markov chain using a probability tree instead of a transition probability matrix
 */
class MarkovChain {
	private Random random = new Random(System.currentTimeMillis());
	private List<String> states = new ArrayList<String>();
	private Map<String, List<Link>> outLinks = new HashMap<String, List<Link>>();
	
	/**
	 * Defines a new state
	 */
	public void addState(String state) {
		states.add(state);
		outLinks.put(state, new ArrayList<Link>());
	}
	
	/**
	 * Links a state to an out state with a specified probability
	 */
	public void addOutState(String state, String outState, float probability) {
		Link link = new Link(outState, probability);
		outLinks.get(state).add(link);
	}
	
	/**
	 * Returns a random state
	 */
	public String getRandomState() {
		return states.get(random.nextInt(states.size()));
	}
	
	/**
	 * Returns an random out state based on its probabilities
	 */
	public String getRandomOutState(String state) {
		String outState = null;
		
		float probability = random.nextFloat(); 
				
		List<Link> links = outLinks.get(state);
		
		float base=0; 
		for (Link link:links) {
			if (probability<=base+link.probability) {
				outState = link.state;
				break;
			}
			base+=link.probability;
		}
		
		return outState;
	}
}

