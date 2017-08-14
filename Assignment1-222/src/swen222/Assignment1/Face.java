package swen222.Assignment1;


public class Face extends Piece {

	private String name;

	public Face(Weapon l, Weapon r, Weapon t, Weapon b, Character ID, String Name) {
		super(l,r,t,b,ID);
		this.name = Name;
	}

	public String getName() {
		return this.name;
	}

}
