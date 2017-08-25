package model;

import java.util.ArrayList;
import java.util.Map;


public class Player {

	private int ID;
	private String name;
	private ArrayList<Piece> hand = new ArrayList<Piece>();

	public Player(int id, Map<Character, Piece> map, String name) {
		this.ID = id;
		this.name = name;
		initializeHand(map);
	}

	private void initializeHand(Map<Character, Piece> map) {
		// TODO Auto-generated method stub
		for(Map.Entry<Character, Piece> c :map.entrySet()) {
			hand.add(c.getValue());
		}
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Piece> getHand(){
		return this.hand;
	}

	public boolean checkHand(Piece p) {
		if(!this.hand.contains(p)) {
			return false;
		}
		return true;
	}

	public void removePieceFromHand(Piece p) {
		hand.remove(p);
	}
}
