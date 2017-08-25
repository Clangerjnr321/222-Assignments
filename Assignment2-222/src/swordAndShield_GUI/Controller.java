package swordAndShield_GUI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

import model.Board;

public class Controller implements MouseListener, KeyListener {

	View view;
	Board board;
	static int x;
	static int y;
	static int height;
	static int width;
	JButton button = new JButton();

	static boolean selected = false;

	public Controller(Board b, View v) {
		board = b;
		view = v;
	}

	static int piece;
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//If button clicked is on the creation screen
		//Display rotations
		for(int i = 0; i < 24; i++) {
			if(e.getSource() instanceof JButton) {
				if(e.getSource() == view.greenPlayerHand[i]) {
					piece = i;
					view.showRots("green", i);
				}
				if(e.getSource() == view.yellowPlayerHand[i]) {
					piece = i;
					view.showRots("yellow", i);
				}
			}
		}
		//If button clicked is one of the 4 rotations, create the piece.
		for(int i = 0; i < 4; i++) {
			if(e.getSource() instanceof JButton) {
				if(e.getSource() == view.greenRots[i]) {
					view.createPiece("green", i, piece);
					board.createPiece(piece, "green", i);
				}
				if(e.getSource() == view.yellowRots[i]) {
					view.createPiece("yellow", i, piece);
					board.createPiece(piece, "yellow", i);
				}
			}
		}
		for(int i = 0; i < view.boardSquares.length; i++) {
			for(int j = 0; j < view.boardSquares.length; j++) {
				if(e.getSource() == view.boardSquares[i][j] && button == view.boardSquares[i][j] && selected) {
					int w = button.getWidth();
					int h = button.getHeight();
					selected = false;
					if(e.getX() < w/2 && (e.getY() < (h/2)+5 && e.getY() > (h/2)-5)) {
						view.move(x, y, "left");
					}
					if(e.getX() > w/2 && (e.getY() < (h/2)+5 && e.getY() > (h/2)-5)) {
						view.move(x, y, "right");
					}
					if(e.getY() < h/2 && (e.getX() < (w/2) + 5 && e.getX() > (w/2)-5)) {
						view.move(x, y, "up");
					}
					if(e.getY() > h/2 && (e.getX() < (w/2) + 5 && e.getX() > (w/2)-5)) {
						view.move(x, y, "down");
					}

				}
			}
		}
		for(int i = 0; i < view.boardSquares.length; i++) {
			for(int j = 0; j < view.boardSquares[0].length; j++) {
				if(e.getSource() == view.boardSquares[i][j]) {
					selected = true;
					button = view.boardSquares[i][j];
					x = i;
					y = j;
				}
			}
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		if(selected) {
			if(code == KeyEvent.VK_KP_RIGHT || code == KeyEvent.VK_RIGHT) {
				view.move(x, y, "right");
			}
			if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {
				view.move(x, y, "left");
			}
			if(code == KeyEvent.VK_UP || code == KeyEvent.VK_KP_UP) {
				view.move(x, y, "up");
			}
			if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_KP_DOWN) {
				view.move(x, y, "down");
			}
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
