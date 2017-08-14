package swen222.Assignment1;

public class SwordAndShield {

	private Board board = new Board();

	public Board getBoard() {
		// TODO Auto-generated method stub
		return board;
	}

	public static class InvalidMove extends Exception {
		public InvalidMove(String msg) {
			super(msg);
		}
	}
}
