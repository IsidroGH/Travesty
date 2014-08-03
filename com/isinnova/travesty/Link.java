package com.isinnova.travesty;

/**
 * Models a link between two states
 */
class Link {
	final float probability;
	final String state;
	
	public Link(String state, float probability) {
		this.state=state;
		this.probability=probability;
	}
}
