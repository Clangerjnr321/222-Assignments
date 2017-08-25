package model;

import java.util.ArrayList;

public class Piece extends Cell{


	private Character ID;
	private Player p;

	private Weapon left, right, top , bottom;
	public ArrayList<Weapon> weps = new ArrayList<Weapon>();

	public enum Weapon{
		Sword,
		Shield,
		Nothing
	}

	public Piece(Weapon l, Weapon r, Weapon t, Weapon b, Character c) {
		this.left = l;
		this.right = r;
		this.top = t;
		this.bottom = b;

		this.ID = c;
		//Adding the weapons that are on the piece to the arraylist
		//Will always be in l,r,t,b format
		this.weps.add(l);
		this.weps.add(r);
		this.weps.add(t);
		this.weps.add(b);
	}

	public void rotateWeps(int amount) {
		// TODO Auto-generated method stub
		Weapon temp = this.getBottom();
		if(amount == 1) {
			this.bottom = this.getRight();
			this.right = this.getTop();
			this.top = this.getLeft();
			this.left = temp;
		}
		if(amount == 2) {
			Weapon temp1 = this.getRight();
			this.bottom = this.getTop();
			this.right = this.getLeft();
			this.left = temp1;
			this.top = temp;
		}
		if(amount == 3) {
			this.bottom = this.getLeft();
			this.left = this.getTop();
			this.top = this.getRight();
			this.right = temp;
		}
	}


	public Character getID() {
		return this.ID;
	}

	public Player getPlayer() {
		return this.p;
	}

	public void setPlayer(Player p) {
		this.p = p;
	}

	/*
	 * Get Methods are for the react methods
	 */

	public Weapon getLeft() {
		return this.left;
	}

	public Weapon getRight() {
		return this.right;
	}

	public Weapon getTop() {
		return this.top;
	}

	public Weapon getBottom() {
		return this.bottom;
	}

}

