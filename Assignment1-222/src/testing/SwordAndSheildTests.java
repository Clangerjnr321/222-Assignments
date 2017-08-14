package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import swen222.Assignment1.Board;
import swen222.Assignment1.Face;
import swen222.Assignment1.Piece;
import swen222.Assignment1.SwordAndShield;
import swen222.Assignment1.SwordAndShield.InvalidMove;
import swen222.Assignment1.Player;

public class SwordAndSheildTests {


	@Test
	public void testForBoard() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		//Ensure that the faces have been placed
		assertNotNull(board.getFace(1, 1));
		assertNotNull(board.getFace(8, 8));
	}

	@Test
	public void testForCreate1() throws InvalidMove {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p = new Player(1, board.getPieceMap(), "Green");
		board.createPiece('c', p, 1);
		//Ensure that the piece has been placed and that the Players hand decreased
		assert(board.get('c', p) instanceof Piece);
		assertEquals(p.getHand().size(), 23);
	}

	@Test
	public void testForCreate2() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");
		Player p2 = new Player(2, board.getPieceMap(), "Yellow");
		try {
			board.createPiece('c', p1, 1);
			board.createPiece('c', p2, 1);
			//Testing the the piece cant be added
			board.createPiece('e', p1, 1);
		} catch (SwordAndShield.InvalidMove e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test
	public void testForCreate3() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");
		Player p2 = new Player(2, board.getPieceMap(), "Yellow");
		try {
			board.createPiece('a', p1, 1);
			board.movePiece(2, 2, "up", p1);
			board.createPiece('f', p1, 1);
			//Testing that the piece is destroyed and added to the graveyard
			assertNull(board.getPiece(2, 1));
			assertEquals(board.getGraveyard().size(), 1);
		}catch(SwordAndShield.InvalidMove e) {
			e.getMessage();
		}
	}

	@Test
	public void testForCreate4() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");
		Player p2 = new Player(2, board.getPieceMap(), "Yellow");
		try {
			board.createPiece('c', p1, 1);
			board.createPiece('c', p2, 1);
			//Testing that the piece cant be added
			board.createPiece('e', p2, 1);
		} catch (SwordAndShield.InvalidMove e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test
	public void testForCreate5() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");
		Player p2 = new Player(2, board.getPieceMap(), "Yellow");
		try {
			board.createPiece('c', p1, 1);
			board.createPiece('c', p2, 1);
			//Testing that the same piece cant be added by the same player
			board.movePiece(2, 2, "down", p1);
			board.createPiece('c', p1, 1);
			assertEquals(p1.getHand().size(), 23);
		} catch (SwordAndShield.InvalidMove e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test
	public void testForCreate6() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");
		Player p2 = new Player(2, board.getPieceMap(), "Yellow");
		try {
			board.createPiece('c', p1, 1);
			board.createPiece('c', p2, 1);
			//Testing that the same piece cant be added by the same player
			board.movePiece(7, 7, "down", p1);
			board.createPiece('c', p2, 1);
			assertEquals(p2.getHand().size(), 23);
		} catch (SwordAndShield.InvalidMove e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Test
	public void testForMove() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");

		try {
			board.createPiece('d', p1, 1);
			//Testing that the move works properly for one piece
			board.movePiece(2, 2, "left", p1);
			board.movePiece(2, 1, "right", p1);
		}catch(SwordAndShield.InvalidMove e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testForReact() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p2 = new Player(1, board.getPieceMap(), "Green");

		try {
			board.createPiece('t', p2, 3);
			board.movePiece(2, 2, "up", p2);
			//Testing that a player can destroy his own face and lose the game
			assertTrue(board.isWon());
		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForReact1() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");

		try {
			board.createPiece('a', p1, 2);
			board.movePiece(2, 2, "up", p1);
			board.createPiece('f', p1, 1);
			//Testing that a piece created with a sword on top will kill the piece above
			//if it has nothing on the bottom
			assertEquals(board.getGraveyard().size(), 1);

		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForReact2() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");

		try {
			board.createPiece('b', p1, 2);
			board.movePiece(2, 2, "up", p1);
			board.createPiece('f', p1, 1);
			//Testing that a piece created with a sword on the top will destroy both if the
			//piece above has a sword on the bottom
			assertEquals(board.getGraveyard().size(), 2);
		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Test
	public void testForReact3() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");

		try {
			board.createPiece('b', p1, 2);
			board.movePiece(2, 2, "up", p1);
			board.createPiece('a', p1, 1);
			//Testing if a piece created with nothing will be destroyed if the piece above
			//has a sword on the bottom
			assertEquals(board.getGraveyard().size(), 1);
		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForReact4() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");

		try {
			board.createPiece('l', p1, 2);
			board.movePiece(2, 2, "up", p1);
			board.createPiece('f', p1, 1);
			//Testing if a piece created with nothing will be destroyed if the piece above
			//has a sword on the bottom
			assertNotNull(board.getPiece(3, 2));
		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForReact5() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(2, board.getPieceMap(), "Yellow");

		try {
			board.createPiece('f', p1, 2);
			board.movePiece(7, 7, "up", p1);
			board.createPiece('l', p1, 1);
			//Testing if a piece created with a Shield will move the piece above if it
			//has a sword on the bottom
			assertNotNull(board.getPiece(5, 7));
			//Testing if a piece is pushed outside the board it will be destroyed
			board.movePiece(5, 7, "up", p1);
			board.movePiece(7, 7, "up", p1);
			board.movePiece(5, 7, "up", p1);
			board.movePiece(4, 7, "up", p1);
			board.movePiece(3, 7, "up", p1);
			board.movePiece(2, 7, "up", p1);
			board.movePiece(1, 7, "up", p1);
			board.movePiece(0, 7, "up", p1);
			assertEquals(board.getGraveyard().size(), 1);
		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForReact6() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");

		try {
			board.createPiece('f', p1, 3);
			board.movePiece(2, 2, "left", p1);
			board.createPiece('a', p1, 1);
			//Testing if a piece created with Nothing will be destroyed by a
			//Piece with a Sword on the right
			assertEquals(board.getGraveyard().size(), 1);
		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForReact7() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");

		try {
			board.createPiece('f', p1, 3);
			board.movePiece(2, 2, "left", p1);
			board.createPiece('m', p1, 1);
			//Testing if a piece created with a Sword will destroy both pieces if the piece
			//has a sword on the right
			assertEquals(board.getGraveyard().size(), 2);
		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForReact8() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(2, board.getPieceMap(), "Yellow");

		try {
			board.createPiece('f', p1, 3);
			board.movePiece(7, 7, "left", p1);
			board.createPiece('l', p1, 2);
			//Testing if a piece created with a Shield will move a piece
			//to the left if it has a sword on its right
			assertNotNull(board.getPiece(7, 5));
		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForReact9() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");

		try {
			board.createPiece('l', p1, 3);
			board.movePiece(2, 2, "left", p1);
			board.createPiece('f', p1, 3);
			//Testing that a piece created with a Sword to the left will
			//be moved to the right by a fully shield piece
			assertNotNull(board.getPiece(2, 3));
		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForReact10() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");

		try {
			board.createPiece('f', p1, 1);
			board.movePiece(2, 2,"right", p1);
			board.createPiece('a', p1, 1);
			//Testing that a piece created with nothing on it will be destroyed
			//by a piece with a sword on its left
			assertEquals(board.getGraveyard().size(), 1);
		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForReact11() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");

		try {
			board.createPiece('a', p1, 1);
			board.movePiece(2, 2,"right", p1);
			board.createPiece('f', p1, 1);
			//Testing that a piece created with a Sword on it will destroyed
			//a piece with nothing on it
			assertEquals(board.getGraveyard().size(), 1);
		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForReact12() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");

		try {
			board.createPiece('f', p1, 1);
			board.movePiece(2, 2,"right", p1);
			board.createPiece('m', p1, 1);
			//Testing that a piece created with a sword on it will destroy
			//both pieces if they both have swords
			assertEquals(board.getGraveyard().size(), 2);
		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForReact13() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");

		try {
			board.createPiece('f', p1, 1);
			board.movePiece(2, 2,"right", p1);
			board.createPiece('l', p1, 1);
			//Testing that a piece created with a shield on it will move
			//a piece with a sword on its left to the right
			assertNotNull(board.getPiece(2, 4));
		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForReact14() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");

		try {
			board.createPiece('l', p1, 1);
			board.movePiece(2, 2,"right", p1);
			board.createPiece('f', p1, 1);
			//Testing that a piece created with sword on it will be moved to the left
			//by a piece with a shield on its right
			assertNotNull(board.getPiece(2, 1));
		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForReact15() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");

		try {
			board.createPiece('f', p1, 2);
			board.movePiece(2, 2,"down", p1);
			board.createPiece('a', p1, 1);
			//Testing that a piece created with nothing on it will be destroyed
			//by a piece with a sword on its top
			assertEquals(board.getGraveyard().size(), 1);
		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForReact16() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");

		try {
			board.createPiece('a', p1, 2);
			board.movePiece(2, 2,"down", p1);
			board.createPiece('m', p1, 2);
			//Testing that a piece created with sword on it will destroy
			//a piece below with a nothing on its top
			assertEquals(board.getGraveyard().size(), 1);
		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForReact17() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");

		try {
			board.createPiece('f', p1, 2);
			board.movePiece(2, 2,"down", p1);
			board.createPiece('m', p1, 2);
			//Testing that a piece created with sword on it will destroy
			//both pieces if it has a sword on its top
			assertEquals(board.getGraveyard().size(), 2);
		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForReact18() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");

		try {
			board.createPiece('l', p1, 2);
			board.movePiece(2, 2,"down", p1);
			board.createPiece('f', p1, 2);
			//Testing that a piece created with sword on it will be moved
			//by a piece below with a Shield on its top
			assertNotNull(board.getPiece(1, 2));
		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForReact19() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");

		try {
			board.createPiece('f', p1, 2);
			board.movePiece(2, 2,"down", p1);
			board.createPiece('l', p1, 2);
			//Testing that a piece created with Shield on it will move
			//a piece below with a Sword on its top
			assertNotNull(board.getPiece(4, 2));
		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForWin() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(2, board.getPieceMap(), "Yellow");

		try {
			board.createPiece('m', p1, 1);
			board.findPiece('m', "down", p1);
			assertTrue(board.isWon());
		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForWin2() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(2, board.getPieceMap(), "Yellow");

		try {
			board.createPiece('l', p1, 1);
			board.findPiece('l', "down", p1);
			assertFalse(board.isWon());
		} catch (SwordAndShield.InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForMove1() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");

		try {
			board.createPiece('l', p1, 1);
			board.movePiece(2, 2, "up", p1);
			assertNull(board.getPiece(0, 2));
		} catch (InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForMove2() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");

		try {
			board.createPiece('a', p1, 3);
			board.movePiece(2, 2, "down", p1);
			board.movePiece(1, 2, "left", p1);
			board.movePiece(1, 1, "left", p1);
			board.movePiece(1, 0, "left", p1);
			//Testing if a piece pushed out of the board is destroyed
			assertEquals(board.getGraveyard().size(), 1);
		} catch (InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForMove3() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");
		Player p2 = new Player(2, board.getPieceMap(), "Yellow");

		try {
			board.createPiece('a', p1, 3);
			board.createPiece('a', p2, 2);
			board.movePiece(7, 7, "left", p2);
			board.movePiece(7, 6, "down", p2);
			board.movePiece(8, 6, "down", p2);
			board.movePiece(9, 6, "down", p2);
			//Testing if a piece pushed out of the board is destroyed
			assertEquals(board.getGraveyard().size(), 1);
		} catch (InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testForMove4() {
		SwordAndShield game = new SwordAndShield();
		Board board = game.getBoard();
		Player p1 = new Player(1, board.getPieceMap(), "Green");
		Player p2 = new Player(2, board.getPieceMap(), "Yellow");

		try {
			board.createPiece('a', p1, 3);
			board.createPiece('a', p2, 2);
			board.movePiece(7, 7, "up", p2);
			board.movePiece(6, 7, "right", p2);
			board.movePiece(6, 8, "right", p2);
			board.movePiece(6, 9, "right", p2);
			//Testing if a piece pushed out of the board is destroyed
			assertEquals(board.getGraveyard().size(), 1);
		} catch (InvalidMove e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
