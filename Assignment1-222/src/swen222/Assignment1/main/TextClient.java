package swen222.Assignment1.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import swen222.Assignment1.Board;
import swen222.Assignment1.Face;
import swen222.Assignment1.Piece;
import swen222.Assignment1.Player;
import swen222.Assignment1.SwordAndShield;
import swen222.Assignment1.SwordAndShield.InvalidMove;
import swen222.Assignment1.Piece.Weapon;

public class TextClient {

	public static void main(String args[]) throws InvalidMove {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();

		Player p = new Player(1, board.getPieceMap(), "Green");
		Player p2 = new Player(2, board.getPieceMap(), "Yellow");

		System.out.println("Sword and Shield Version 1.0!");
		System.out.println("By Cameron Laing");
		System.out.println();
		displayBoard(board);

		System.out.println("Start the game!!");
		System.out.println("Green player goes first");

		//Will be while(isGameWon is false)
		while(!board.isWon()) {
			System.out.println();
			//Display player options.
			int j = 0;
			while(j % 2 == 0) {
				playerOptions(board, p);
				board.isWon();
				j++;
			}
			playerOptions(board, p2);
			board.isWon();
			j++;
		}
	}

	private static void playerOptions(Board board, Player p) throws InvalidMove {
		// TODO Auto-generated method stub
		System.out.println("Options for the " + p.getName() + " player :");
		System.out.println("Create, Move, Rotate, Pass");
		while(1 == 1) {
			String inp = readInput("[create/move/rotate/pass]");
			if(inp.equals("pass")) {
				return;
			}
			else if(inp.equals("create")) {
				createPiece(board, p);
				return;
			}
			else if(inp.equals("move")) {
				movePiece(board, p);
				return;
			}
			else if(inp.equals("rotate")) {
				rotatePiece(board, p);
				return;
			}
		}
	}

	private static void rotatePiece(Board board, Player p) {
		// TODO Auto-generated method stub
		System.out.println("What piece do you want to rotate?");
		Character c = readChar("What Piece");
		System.out.println("How much do you want to rotate by??");
		System.out.println("1 time clockwise? two times clockwise? 3 times clockwise?");
		int amount = readInt("[1/2/3/4]");
		board.rotatePiece(c, p, amount);
		displayBoard(board);
	}


	private static void movePiece(Board board, Player p) throws InvalidMove {
		// TODO Auto-generated method stub
		System.out.println("====Type the character of the piece you want to move====");
		Character c = readChar("What Piece??");
		if(!(board.get(c, p) instanceof Piece)) {
			System.out.println("Not a Piece, Pick a different Cell");
			playerOptions(board, p);
		}
		System.out.println("Do you want to move it Up, Down, Left or Right?");
		String s = readInput("[up/down/left/right]");
		board.findPiece(c, s, p);
		displayBoard(board);
	}

	private static void createPiece(Board board, Player p) throws InvalidMove {
		// TODO Auto-generated method stub
		System.out.println("What Piece do you want to create?");
		Character s = readChar("[a-z] (Cant be Y or G)");
		if(s.equals('g') || s.equals('y')) {
			throw new SwordAndShield.InvalidMove("Cant create that piece");
		}
		Integer i = readInt("What orientation do you want the piece in?");
		board.createPiece(s, p, i);
		displayBoard(board);
	}


	private static String readInput(String msg) {
		// TODO Auto-generated method stub
		System.out.println(msg);
		while(1 == 1) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try{
				return br.readLine();
			}
			catch(IOException e) {
				System.out.println("I/O error.  Please try again!");
			}
		}
	}

	private static Integer readInt(String string) {
		// TODO Auto-generated method stub
		System.out.println(string);
		while(1 == 1) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				String s = br.readLine();
				Integer parse = Integer.parseInt(s);
				return parse;
			}catch(IOException e) {
				System.out.println("I/O error.  Please try again!!");
			}
		}
	}

	private static Character readChar(String msg) {
		// TODO Auto-generated method stub
		System.out.println(msg);
		while(1 == 1) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				return (char) br.read();
			}
			catch(IOException e) {
				System.out.println("I/O error.  Pleas try again!");
			}
		}
	}

	private static void displayBoard(Board b) {
		// TODO Auto-generated method stub

		drawLines(9);
		for(int row = 0; row < 10; row++) {
			System.out.println();
			System.out.print("|");
			drawTop(b, row);
			System.out.print("|");
			drawMiddle(b, row);
			System.out.print("|");
			drawBottom(b, row);
		}
		System.out.println();
		System.out.println();
		System.out.println();
	}

	private static void drawMiddle(Board b, int row) {
		// TODO Auto-generated method stub
		for(int i = 0; i < 10; i++) {
			if(b.get(row, i) instanceof Face) {
				if(row == 1 && i == 1) {
					System.out.print(" G |");
				}else {
					System.out.print(" Y |");
				}
			}
			else if(b.get(row, i) instanceof Piece) {
				if(b.getPiece(row, i).getLeft() == Weapon.Nothing && b.getPiece(row, i).getRight() == Weapon.Nothing) {
					System.out.print(" " + b.getPiece(row, i).getID() + " |");
				}
				else if(b.getPiece(row, i).getLeft() == Weapon.Nothing && b.getPiece(row, i).getRight() == Weapon.Shield) {
					System.out.print(" " + b.getPiece(row, i).getID() + "#|");
				}
				else if(b.getPiece(row, i).getLeft() == Weapon.Nothing && b.getPiece(row, i).getRight() == Weapon.Sword) {
					System.out.print(" " + b.getPiece(row, i).getID() + "+|");
				}
				else if(b.getPiece(row, i).getLeft() == Weapon.Shield && b.getPiece(row, i).getRight() == Weapon.Nothing) {
					System.out.print("#" + b.getPiece(row, i).getID() + " |");
				}
				else if(b.getPiece(row, i).getLeft() == Weapon.Shield && b.getPiece(row, i).getRight() == Weapon.Shield) {
					System.out.print("#" + b.getPiece(row, i).getID() + "#|");
				}
				else if(b.getPiece(row, i).getLeft() == Weapon.Shield && b.getPiece(row, i).getRight() == Weapon.Sword) {
					System.out.print("#" + b.getPiece(row, i).getID() + "+|");
				}
				else if(b.getPiece(row, i).getLeft() == Weapon.Sword && b.getPiece(row, i).getRight() == Weapon.Nothing) {
					System.out.print("+" + b.getPiece(row, i).getID() + " |");
				}
				else if(b.getPiece(row, i).getLeft() == Weapon.Sword && b.getPiece(row, i).getRight() == Weapon.Shield) {
					System.out.print("+" + b.getPiece(row, i).getID() + "#|");
				}
				else if(b.getPiece(row, i).getLeft() == Weapon.Sword && b.getPiece(row, i).getRight() == Weapon.Sword) {
					System.out.print("+" + b.getPiece(row, i).getID() + "+|");
				}
			}
			else {
				System.out.print("   |");
			}
		}
		System.out.println();
	}

	private static void drawBottom(Board b, int row) {
		// TODO Auto-generated method stub
		for(int i = 0; i < 10; i++) {
			if(b.get(row, i) instanceof Face) {
				System.out.print("___|");
			}
			else if(b.get(row, i) instanceof Piece) {
				if(b.getPiece(row, i).getBottom() == Weapon.Shield) {
					System.out.print("_#_|");
				}
				else if(b.getPiece(row, i).getBottom() == Weapon.Sword) {
					System.out.print("_+_|");
				}
				else {
					System.out.print("___|");
				}
			}
			else {
				System.out.print("___|");
			}
		}
	}

	private static void drawTop(Board b, int row) {	// TODO Auto-generated method stub

		for(int i = 0; i < 10; i++) {
			if(b.get(row, i) instanceof Face) {
				System.out.print("   |");
			}
			else if(b.get(row, i) instanceof Piece) {
				if(b.getPiece(row, i).getTop() == Weapon.Shield) {
					System.out.print(" # |");
				}
				else if(b.getPiece(row, i).getTop() == Weapon.Sword) {
					System.out.print(" + |");
				}
				else {
					System.out.print("   |");
				}
			}
			else {
				System.out.print("   |");
			}
		}
		System.out.println();
	}

	private static void drawLines(int size) {
		// TODO Auto-generated method stub
		System.out.print(" ");
		for(int i = 0; i < size; i++) {
			System.out.print("___ ");
		}
		System.out.print("___ ");
	}

}
