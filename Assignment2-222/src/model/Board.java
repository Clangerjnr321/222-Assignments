package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import model.Piece.Weapon;


public class Board extends Observable{


	private ArrayList<Piece> graveyard = new ArrayList<Piece>();
	private int boardSize = 10;
	public Cell[][] board = new Cell[10][10];
	private Map<Character,Piece> pieces = new HashMap<Character, Piece>();	//Need to add the 24 pieces to the array

	Player p;
	Player p2;

	public Board() {
		//Making every cell on the board empty
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				board[i][j] = new EmptyCell();
			}
		}

		intializePieceMap();

		p = new Player(1, this.getPieceMap(), "Green");
		p2 = new Player(2, this.getPieceMap(), "Yellow");


		//Adding the faces to the board.
		board[1][1] = new Face(Weapon.Nothing,Weapon.Nothing,Weapon.Nothing,Weapon.Nothing, 'G', "Green");
		board[8][8] = new Face(Weapon.Nothing,Weapon.Nothing,Weapon.Nothing,Weapon.Nothing, 'Y', "Yellow");
	}

	public void intializePieceMap() {
		//Left, Right, Top, Bottom
		pieces.put('a', new Piece(Weapon.Nothing, Weapon.Nothing, Weapon.Nothing, Weapon.Nothing, 'a'));
		pieces.put('b', new Piece(Weapon.Sword, Weapon.Shield, Weapon.Sword, Weapon.Sword, 'b'));
		pieces.put('c', new Piece(Weapon.Shield, Weapon.Shield, Weapon.Sword, Weapon.Sword, 'c'));
		pieces.put('d', new Piece(Weapon.Shield, Weapon.Nothing, Weapon.Sword, Weapon.Sword, 'd'));
		pieces.put('e', new Piece(Weapon.Nothing, Weapon.Nothing, Weapon.Sword, Weapon.Sword, 'e'));
		pieces.put('f', new Piece(Weapon.Sword, Weapon.Nothing, Weapon.Sword, Weapon.Sword, 'f'));
		pieces.put('h', new Piece(Weapon.Sword, Weapon.Shield, Weapon.Sword, Weapon.Shield, 'h'));
		pieces.put('i', new Piece(Weapon.Sword, Weapon.Shield, Weapon.Sword, Weapon.Nothing,'i'));
		pieces.put('j', new Piece(Weapon.Sword, Weapon.Nothing, Weapon.Sword, Weapon.Shield,'j'));
		pieces.put('k', new Piece(Weapon.Sword, Weapon.Nothing, Weapon.Sword, Weapon.Nothing, 'k'));
		pieces.put('l', new Piece(Weapon.Shield, Weapon.Shield, Weapon.Shield, Weapon.Shield,'l'));
		pieces.put('m', new Piece(Weapon.Sword, Weapon.Sword, Weapon.Sword, Weapon.Sword,'m'));
		pieces.put('n', new Piece(Weapon.Nothing, Weapon.Nothing, Weapon.Sword, Weapon.Nothing,'n'));
		pieces.put('o', new Piece(Weapon.Shield, Weapon.Nothing, Weapon.Sword, Weapon.Nothing,'o'));
		pieces.put('p', new Piece(Weapon.Nothing, Weapon.Shield, Weapon.Sword, Weapon.Nothing,'p'));
		pieces.put('q', new Piece(Weapon.Nothing, Weapon.Nothing, Weapon.Sword, Weapon.Shield,'q'));
		pieces.put('r', new Piece(Weapon.Shield, Weapon.Nothing, Weapon.Sword, Weapon.Shield,'r'));
		pieces.put('s', new Piece(Weapon.Nothing, Weapon.Shield, Weapon.Sword, Weapon.Shield,'s'));
		pieces.put('t', new Piece(Weapon.Shield, Weapon.Shield, Weapon.Sword, Weapon.Nothing,'t'));
		pieces.put('u', new Piece(Weapon.Shield, Weapon.Shield, Weapon.Sword, Weapon.Shield,'u'));
		pieces.put('v', new Piece(Weapon.Nothing, Weapon.Shield, Weapon.Nothing, Weapon.Nothing,'v'));
		pieces.put('w', new Piece(Weapon.Nothing, Weapon.Shield, Weapon.Nothing, Weapon.Shield,'w'));
		pieces.put('x', new Piece(Weapon.Shield, Weapon.Shield, Weapon.Nothing, Weapon.Nothing,'x'));
		pieces.put('z', new Piece(Weapon.Shield, Weapon.Shield, Weapon.Nothing, Weapon.Shield,'z'));
	}

	public ArrayList<Piece> getGraveyard(){
		return this.graveyard;
	}

	public Cell get(int index1, int index2) {
		return this.board[index1][index2];
	}

	public Cell get(Character s, Player p) {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(this.get(i,j) instanceof Piece) {
					if(this.getPiece(i, j).getID() == s && this.getPiece(i, j).getPlayer().getName() == p.getName()) {
						return (Piece) this.getPiece(i, j);
					}
				}
			}
		}
		return null;
	}

	private int getX(Character c, Player p) {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(this.get(i, j) instanceof Piece) {
					if(this.getPiece(i, j).getID() == c && this.getPiece(i, j).getPlayer().getName() == p.getName()) {
						return i;
					}
				}
			}
		}
		return -1;
	}

	private int getY(Character c, Player p) {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(this.get(i, j) instanceof Piece) {
					if(this.getPiece(i, j).getID() == c && this.getPiece(i, j).getPlayer().getName() == p.getName()) {
						return j;
					}
				}
			}
		}
		return -1;
	}

	public Piece getPiece(int i, int j) {
		if(this.board[i][j] instanceof Piece) {
			return (Piece) this.board[i][j];
		}
		return null;
	}

	public Face getFace(int i, int j) {
		// TODO Auto-generated method stub
		if(this.board[i][j] instanceof Face) {
			return (Face) this.board[i][j];
		}
		return null;
	}

	public Map<Character, Piece> getPieceMap() {
		// TODO Auto-generated method stub
		return pieces;
	}


	public void createPiece(int piece, String s, int rotations) {
		if(s.equals("green")) {
			//Player is green
			//Add at 2,2
			if(this.board[2][2] instanceof Piece) {
				System.out.println("Cant create, there is a piece already there");
				return;
			}
			if(p.getHand().get(piece) == null) {
				System.out.println("Piece is not in hand");
				return;
			}
			board[2][2] = p.getHand().get(piece);
			this.getPiece(2, 2).setPlayer(p);
			this.rotatePiece(piece, p, rotations);
			this.react(2, 2);
			p.removePieceFromHand(this.getPiece(2, 2));
		}
		if(s.equals("yellow")) {
			//Player is Yellow
			//Add at 7,7
			if(this.board[7][7] instanceof Piece) {
				System.out.println("Cant create, there is a piece already there");
				return;			}
			if(p2.checkHand(p2.getHand().get(piece)) == false) {

				System.out.println("Piece is not in hand");
				return;			}
			board[7][7] = p2.getHand().get(piece);
			this.getPiece(7, 7).setPlayer(p2);
			this.rotatePiece(piece, p2, rotations);
			this.react(7, 7);
			p.removePieceFromHand(this.getPiece(7, 7));
		}
	}

	private void checkCell(int x, int y, int change, Character c) {
		// TODO Auto-generated method stub
		//Character is to push the piece in the same direction.
		if(get(x, y) instanceof Piece) {
			if(c.equals('x')) {
				this.checkCell(x+change, y, change, c);
				this.board[x+change][y] = this.board[x][y];
			}
			else if(c.equals('y')) {
				this.checkCell(x, y+change, change, c);
				this.board[x][y+change] = this.board[x][y];
			}
		}
	}

	public void rotatePiece(int pi, Player p, int amount) {
		// TODO Auto-generated method stub


		Piece piece = p.getHand().get(pi);
		piece.rotateWeps(amount);

	}

	public void findPiece(Character c, String s, Player p) {
		// TODO Auto-generated method stub
		int x = getX(c, p);
		int y = getY(c, p);
		this.movePiece(x, y, s);
	}


	public void movePiece(int x, int y, String s) {
		// TODO Auto-generated method stub
		if(s.equals("up")) {
			//If a piece is moving outside of the board
			//Destroy  it
			if(x == 0) {
				this.graveyard.add(this.getPiece(x, y));
				this.board[x][y] = new EmptyCell();
				return;
			}
			this.checkCell(x, y, -1, 'x');
			this.board[x-1][y] = this.board[x][y];
			this.board[x][y] = new EmptyCell();
			this.react(x-1, y);
		}
		if(s.equals("down")) {
			//If a piece is moving outside of the board
			//Destroy  it
			if(x == 9) {
				this.graveyard.add(this.getPiece(x, y));
				this.board[x][y] = new EmptyCell();
				return;
			}
			this.checkCell(x, y, 1, 'x');
			this.board[x+1][y] = this.board[x][y];
			this.board[x][y] = new EmptyCell();
			this.react(x+1, y);
		}
		if(s.equals("left")) {
			//If a piece is moving outside of the board
			//Destroy  it
			if(y == 0) {
				this.graveyard.add(this.getPiece(x, y));
				this.board[x][y] = new EmptyCell();
				return;
			}
			this.checkCell(x, y, -1, 'y');
			this.board[x][y-1] = this.board[x][y];
			this.board[x][y] = new EmptyCell();
			this.react(x, y-1);
		}
		if(s.equals("right")) {
			//If a piece is moving outside of the board
			//Destroy  it
			if(y == 9) {
				this.graveyard.add(this.getPiece(x, y));
				this.board[x][y] = new EmptyCell();
				return;
			}
			this.checkCell(x, y, 1, 'y');
			this.board[x][y+1] = this.board[x][y];
			this.board[x][y] = new EmptyCell();
			this.react(x, y+1);
		}
	}

	public void react(int x, int y) {
		// TODO Auto-generated method stub
		if(x == 0) {
			return;
		}
		if(x == 9) {
			return;
		}
		if(y == 0) {
			return;
		}
		if(y == 9) {
			return;
		}
		if(this.board[x-1][y] instanceof Piece) {	//If there is a piece above
			if(this.getPiece(x-1, y).getBottom() == Weapon.Nothing && this.getPiece(x, y).getTop() == Weapon.Sword) {
				this.graveyard.add(this.getPiece(x-1, y));
				this.board[x-1][y] = new EmptyCell();
			}
			else if(this.getPiece(x-1, y).getBottom() == Weapon.Sword && this.getPiece(x, y).getTop() == Weapon.Sword){
				this.graveyard.add(this.getPiece(x, y));
				this.graveyard.add(this.getPiece(x-1, y));
				this.board[x][y] = new EmptyCell();
				this.board[x-1][y] = new EmptyCell();
			}
			else if(this.getPiece(x-1, y).getBottom() == Weapon.Sword && this.getPiece(x, y).getTop() == Weapon.Nothing) {
				this.graveyard.add(this.getPiece(x, y));
				this.board[x][y] = new EmptyCell();
			}
			else if(this.getPiece(x-1, y).getBottom() == Weapon.Shield && this.getPiece(x, y).getTop() == Weapon.Sword) {
				this.movePiece(x, y, "down");
			}
			else if(this.getPiece(x-1, y).getBottom() == Weapon.Sword && this.getPiece(x, y).getTop() == Weapon.Shield) {
				this.movePiece(x-1, y, "up");
			}
		}
		else if(this.board[x+1][y] instanceof Piece) {	//If there is a piece below
			if(this.getPiece(x+1, y).getTop() == Weapon.Nothing && this.getPiece(x, y).getBottom() == Weapon.Sword) {
				this.graveyard.add(this.getPiece(x+1, y));
				this.board[x+1][y] = new EmptyCell();
			}
			else if(this.getPiece(x+1, y).getTop() == Weapon.Sword && this.getPiece(x, y).getBottom() == Weapon.Sword) {
				this.graveyard.add(this.getPiece(x+1, y));
				this.graveyard.add(this.getPiece(x, y));
				this.board[x+1][y] = new EmptyCell();
				this.board[x][y] = new EmptyCell();
			}
			else if(this.getPiece(x+1, y).getTop() == Weapon.Sword && this.getPiece(x, y).getBottom() == Weapon.Nothing) {
				this.graveyard.add(this.getPiece(x, y));
				this.board[x][y] = new EmptyCell();
			}
			else if(this.getPiece(x+1, y).getTop() == Weapon.Shield && this.getPiece(x, y).getBottom() == Weapon.Sword) {
				this.movePiece(x, y, "up");
			}
			else if(this.getPiece(x+1, y).getTop() == Weapon.Sword && this.getPiece(x, y).getBottom() == Weapon.Shield) {
				this.movePiece(x+1, y, "down");
			}
		}
		else if(this.board[x][y-1] instanceof Piece) {	//If there is a piece to the left
			if(this.getPiece(x, y-1).getRight() == Weapon.Nothing && this.getPiece(x, y).getLeft() == Weapon.Sword) {
				this.graveyard.add(this.getPiece(x, y-1));
				this.board[x][y-1] = new EmptyCell();
			}
			else if(this.getPiece(x, y-1).getRight() == Weapon.Sword && this.getPiece(x, y).getLeft() == Weapon.Sword) {
				this.graveyard.add(this.getPiece(x, y));
				this.graveyard.add(this.getPiece(x, y-1));
				this.board[x][y-1] = new EmptyCell();
				this.board[x][y] = new EmptyCell();
			}
			else if(this.getPiece(x, y-1).getRight() == Weapon.Sword && this.getPiece(x, y).getLeft() == Weapon.Nothing) {
				this.graveyard.add(this.getPiece(x, y));
				this.board[x][y] = new EmptyCell();
			}
			else if(this.getPiece(x, y-1).getRight() == Weapon.Shield && this.getPiece(x, y).getLeft() == Weapon.Sword) {
				this.movePiece(x, y, "right");
			}
			else if(this.getPiece(x, y-1).getRight() == Weapon.Sword && this.getPiece(x, y).getLeft() == Weapon.Shield) {
				this.movePiece(x, y-1, "left");
			}
		}
		else if(this.board[x][y+1] instanceof Piece) {	//If there is a piece to the right
			if(this.getPiece(x, y+1).getLeft() == Weapon.Nothing && this.getPiece(x, y).getRight() == Weapon.Sword) {
				this.graveyard.add(this.getPiece(x, y+1));
				this.board[x][y+1] = new EmptyCell();
			}
			else if(this.getPiece(x, y+1).getLeft() == Weapon.Sword && this.getPiece(x, y).getRight() == Weapon.Sword) {
				this.graveyard.add(this.getPiece(x, y));
				this.graveyard.add(this.getPiece(x, y+1));
				this.board[x][y] = new EmptyCell();
				this.board[x][y+1] = new EmptyCell();
			}
			else if(this.getPiece(x, y+1).getLeft() == Weapon.Sword && this.getPiece(x, y).getRight() == Weapon.Nothing) {
				this.graveyard.add(this.getPiece(x, y));
				this.board[x][y] = new EmptyCell();
			}
			else if(this.getPiece(x, y+1).getLeft() == Weapon.Shield && this.getPiece(x, y).getRight() == Weapon.Sword) {
				this.movePiece(x, y, "left");
			}
			else if(this.getPiece(x, y+1).getLeft() == Weapon.Sword && this.getPiece(x, y).getRight() == Weapon.Shield) {
				this.movePiece(x, y+1, "right");
			}
		}
		else if(this.board[x-1][y] instanceof EmptyCell || this.board[x+1][y] instanceof EmptyCell || this.board[x][y+1] instanceof EmptyCell || this.board[x][y-1] instanceof EmptyCell) {
			return;
		}
	}

	public boolean isWon() {
		// TODO Auto-generated method stub
		if(!(this.getPiece(1, 1) instanceof Face)) {
			System.out.println(this.getFace(8, 8).getName() + " Player wins!!");
			return true;
		}
		if(!(this.getPiece(8, 8) instanceof Face)) {
			System.out.println(this.getFace(1, 1).getName() + " Player wins!!");
			return true;
		}
		return false;
	}

}