package swordAndShield_GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import model.Board;
import model.EmptyCell;
import model.Face;
import resources.ImgResources;

public class View extends JFrame implements Observer{

	private static final long serialVersionUID = 1L;

	private final JSplitPane split;
	private final JSplitPane split1;
	private final JSplitPane split2;
	private final JPanel greenCreation; //Holds the rotations for green
	private final JPanel greenHand;	//Holds the hand for the player
	private final JPanel yellowCreation;	//Holds the rotations for yellow
	private final JPanel yellowHand;	//Holds the hand for the player
	private final JPanel graveyard;	//Holds the pieces in the graveyard
	public JPanel gameBoard;		//Holds the board
	private final JPanel menu;	//Holds the menu at the beginning of the game
	private final JPanel greenPanel = new JPanel(new CardLayout());
	private final JPanel yellowPanel = new JPanel(new CardLayout());
	private final JPanel finalPanel = new JPanel(new CardLayout());

	//These allow me to switch between scenes for each panel
	CardLayout cl = (CardLayout)finalPanel.getLayout();
	CardLayout c = (CardLayout) greenPanel.getLayout();
	CardLayout c1 = (CardLayout) yellowPanel.getLayout();

	final static String MENU = "Card With Menu";
	final static String ROTATIONSY = "Card with yellow Rotations";
	final static String ROTATIONS = "Card with green Rotations";
	final static String GAME = "Card with game";
	final static String HANDY = "Card with yellow hand";
	final static String HAND = "Card with green hand";

	private JFrame border = new JFrame("Sword And Shield Game");
	private JToolBar tools = new JToolBar();	//Toolbar that contains the buttons for the game
	JToolBar menuBar = new JToolBar();	//Toolbar that contains the buttons for the starting menu
	private JTextArea message1 = new JTextArea();	//Contains the message for the Info area
	public JButton[][] boardSquares = new JButton[10][10];	//Buttons for the game
	public JButton[] greenPlayerHand = new JButton[24];	//Buttons for the green Hand
	public JButton[] yellowPlayerHand = new JButton[24];	//Buttons for the yellow hand
	public JButton[] yellowRots = new JButton[4];
	public JButton[] greenRots = new JButton[4];
	private JLabel message = new JLabel("SwordAndShield is ready to play");
	private ImgResources[] gamePieces = new ImgResources[50];
	private ImgResources[] yellow = new ImgResources[24];
	private ImgResources[] green = new ImgResources[24];
	private Image[] greenR = new Image[4];
	private Image[] yellowR = new Image[4];

	private int turn;
	private boolean surrendered = false;

	Board b;
	public View(Board board) {
		turn = 0;
		createImages();
		b = board;

		split = new JSplitPane();
		split1 = new JSplitPane();
		split2 = new JSplitPane();


		yellowHand = new JPanel();
		greenHand = new JPanel();
		yellowCreation = new JPanel();
		greenCreation = new JPanel();

		graveyard = new JPanel();
		menu = new JPanel();
		finalPanel.add(menu, MENU);

		greenPanel.add(greenCreation, ROTATIONS);
		yellowPanel.add(yellowCreation, ROTATIONS);


		menuBar.setFloatable(false);
		//Setting up the functionality of the buttons
		Action newGameAction = new AbstractAction("New") {
			private static final long serialVersionUID = 741959941556541723L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					setupGame();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
		Action newAction = new AbstractAction("Info") {
			private static final long serialVersionUID = -5176601934247825459L;
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				message1.setText("Author: Cameron Laing    "
						+ " \nThis game requires you to click on a piece in the"
						+ "'create' menusfor either player and then move those"
						+ " pieces around the boardto the opponents face to win!! Enjoy!!!");
			}
		};
		Action quitGame = new AbstractAction("Quit") {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

		};
		message1.setEditable(false);
		message1.setLineWrap(true);
		message1.setPreferredSize(new Dimension(400,100));
		menuBar.add(newGameAction);
		menuBar.addSeparator();
		menuBar.add(newAction);
		menuBar.addSeparator();
		menuBar.add(quitGame);
		menuBar.addSeparator();
		menuBar.add(message);
		menu.add(menuBar);
		menu.add(message1);

		border.setPreferredSize(this.getPreferredSize());
		border.getContentPane().setLayout(new GridLayout());
		border.getContentPane().add(split);
		border.getContentPane().add(split1);

		split.setOrientation(JSplitPane.VERTICAL_SPLIT);
		split.setDividerLocation(500);
		split.setTopComponent(greenPanel);
		split.setBottomComponent(yellowPanel);

		split1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		split1.setDividerLocation(300);
		split1.setBottomComponent(graveyard);

		split2.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		split2.setDividerLocation(400);
		split2.setTopComponent(split);
		split2.setBottomComponent(split1);


		board.addObserver(this);
		this.addMouseListener(new Controller(b, this));
		this.addKeyListener(new Controller(b, this));
		this.requestFocus();
		this.setFocusable(true);
		border.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		border.setVisible(true);


		border.add(finalPanel);
		border.pack();

	}

	private void createImages() {
		// TODO Auto-generated method stub
		gamePieces = ImgResources.class.getEnumConstants();
		for(int i = 0; i < green.length; i++) {
			green[i] = gamePieces[i+2];
		}

		for(int i = 0; i < yellow.length; i++) {
			yellow[i] = gamePieces[i+26];
		}
	}


	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		repaint();
	}

	private void setupGame() throws IOException {
		// TODO Auto-generated method stub
		surrendered = false;
		message.setText("Make a Move, Green Player goes first!");
		finalPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		//Setting up the tool bar for the game
		setupToolBar();
		//Setting up the gameBoard
		setupGameBoard();
		//Setting up the players hands
		setupHands();

		finalPanel.add(split2, GAME);
		greenPanel.add(greenHand, HAND);
		yellowPanel.add(yellowHand, HANDY);
		cl.show(finalPanel, GAME);
		c.show(greenPanel, HAND);
		c1.show(yellowPanel, HANDY);

	}

	private void setupToolBar() {
		// TODO Auto-generated method stub
		tools.setFloatable(false);
		Action undo = new AbstractAction("Undo") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				undo();
			}
		};
		tools.add(undo);
		tools.addSeparator();
		Action pass = new AbstractAction("Pass") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				pass();
			}
		};
		tools.add(pass);
		tools.addSeparator();
		Action surrender = new AbstractAction("Surrender") {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				surrendered = true;
				isGameWon();
			}
		};
		tools.add(surrender);
		tools.addSeparator();
		tools.add(message);

		border.add(tools, BorderLayout.NORTH);

	}

	private void setupGameBoard() {
		// TODO Auto-generated method stub
		gameBoard = new JPanel(new GridLayout(0,10));

		Insets buttonMargin = new Insets(0, 0, 0, 0);
		for(int i = 0; i < boardSquares.length; i++) {
			for(int j = 0; j < boardSquares.length; j++) {
				JButton button = new JButton("");	//Making each one a button.
				button.addMouseListener(new Controller(b, this));
				button.addKeyListener(new Controller(b, this));
				button.setMargin(buttonMargin);
				ImageIcon icon = new ImageIcon(new BufferedImage(67, 67, BufferedImage.TYPE_INT_ARGB));
				button.setIcon(icon);
				if((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
					button.setBackground(Color.WHITE);
				}
				else {
					button.setBackground(Color.GRAY);
				}
				boardSquares[i][j] = button;
				gameBoard.add(boardSquares[i][j]);
			}
		}
		split1.setTopComponent(gameBoard);

		boardSquares[1][1].setIcon(new ImageIcon(gamePieces[0].img));
		boardSquares[2][2].setBackground(Color.GREEN);
		boardSquares[8][8].setIcon(new ImageIcon(gamePieces[1].img));
		boardSquares[7][7].setBackground(Color.yellow);
	}

	private void setupHands() {
		// TODO Auto-generated method stub
		Insets buttonMargin = new Insets(0, 0, 0, 0);
		//Green Hand
		for(int i = 0; i < greenPlayerHand.length; i++) {
			JButton button = new JButton("");
			button.addMouseListener(new Controller(b, this));
			button.setMargin(buttonMargin);
			ImageIcon icon = new ImageIcon(green[i].img);
			button.setIcon(icon);
			greenPlayerHand[i] = button;
			greenHand.add(greenPlayerHand[i]);
		}
		//Yellow hand
		for(int i = 0; i < yellowPlayerHand.length; i++) {
			JButton button = new JButton("");
			button.addMouseListener(new Controller(b, this));
			button.setMargin(buttonMargin);
			ImageIcon icon = new ImageIcon(yellow[i].img);
			button.setIcon(icon);
			yellowPlayerHand[i] = button;
			yellowHand.add(yellowPlayerHand[i]);
		}
		greenHand.setBackground(Color.GREEN);
		yellowHand.setBackground(Color.YELLOW);
		graveyard.setBackground(Color.BLACK);
	}

	protected void isGameWon() {
		// TODO Auto-generated method stub
		//If the Faces are destroyed
		if(!(b.board[1][1] instanceof Face) || !(b.board[8][8] instanceof Face)) {
			String[] options = new String[] {"Continue"};
			String won;
			if(!(b.board[1][1] instanceof Face)) {
				won = "Yellow Wins!";
			}
			else {
				won = "Green Wins!";
			}

			int r = JOptionPane.showOptionDialog(this, won, "Game Over", JOptionPane.DEFAULT_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if(r==0) {
				cl.show(finalPanel, MENU);
			}
			createNewGame();
		}
		if(surrendered) {
			String[] options = new String[] {"Continue"};
			String won;
			if(turn % 2 == 0) {
				won = "Yellow Wins!";
			}
			else {
				won = "Green Wins!";
			}

			int r = JOptionPane.showOptionDialog(this, won, "Game Over", JOptionPane.DEFAULT_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if(r==0) {
				cl.show(finalPanel, MENU);
			}
			createNewGame();
		}
	}

	private void createNewGame() {
		// TODO Auto-generated method stub
		tools.removeAll();
		greenCreation.removeAll();
		yellowCreation.removeAll();
		greenHand.removeAll();
		yellowHand.removeAll();
		menu.revalidate();
		menu.repaint();
		message1.setText("");
		turn = 0;
	}

	protected void pass() {
		// TODO Auto-generated method stub
		turn++;
	}

	public void showRots(String s, int piece) {
		// TODO Auto-generated method stub
		if(s.equals("green") && turn % 2 == 0 && b.board[2][2] instanceof EmptyCell) {
			greenCreation.setBackground(Color.GREEN);
			greenPanel.add(greenCreation, ROTATIONS);
			c.show(greenPanel, ROTATIONS);
			for(int i = 0; i < 4; i++) {
				JButton button = new JButton("");
				button.addMouseListener(new Controller(b, this));
				button.setMargin(getInsets());
				button.setIcon(new ImageIcon(green[piece].rotate(green[piece].img, i)));
				greenRots[i] = button;
				greenCreation.add(greenRots[i]);
			}
			for(int i = 0; i < 4; i++) {
				greenR[i] = green[piece].rotate(green[piece].img, i);
			}
		}
		else if(s.equals("yellow") &&  turn % 2 != 0 && b.board[7][7] instanceof EmptyCell) {
			yellowCreation.setBackground(Color.YELLOW);
			yellowPanel.add(yellowCreation, ROTATIONSY);
			c1.show(yellowPanel, ROTATIONSY);
			for(int i = 0; i < 4; i++) {
				JButton button = new JButton("");
				button.addMouseListener(new Controller(b, this));
				button.setMargin(getInsets());
				button.setIcon(new ImageIcon(yellow[piece].rotate(yellow[piece].img, i)));
				yellowRots[i] = button;
				yellowCreation.add(yellowRots[i]);
			}
			for(int i = 0; i < 4; i++) {
				yellowR[i] = yellow[piece].rotate(yellow[piece].img, i);
			}
		}
		return;
	}

	public void createPiece(String s, int rot, int piece) {
		// TODO Auto-generated method stub
		if(s.equals("green") &&  turn % 2 == 0) {
			//If there is no piece on board
			if(b.board[2][2] instanceof EmptyCell) {
				turn++;
				boardSquares[2][2].setIcon(new ImageIcon(greenR[rot]));
				greenHand.remove(greenPlayerHand[piece]);
				greenHand.revalidate();
				greenHand.repaint();
				//Clearing the rotations array
				for(int j = 0; j < 4; j++) {
					greenCreation.remove(greenRots[j]);
				}
			}
			c.show(greenPanel, HAND);
		}
		else if(s.equals("yellow") &&  turn % 2 != 0) {
			System.out.print("yellows turn");
			if(b.board[7][7] instanceof EmptyCell) {
				turn++;
				boardSquares[7][7].setIcon(new ImageIcon(yellowR[rot]));
				yellowHand.remove(yellowPlayerHand[piece]);
				yellowHand.revalidate();
				yellowHand.repaint();
				//Clearing the rotations array
				for(int j = 0; j < 4; j++) {
					yellowCreation.remove(yellowRots[j]);
				}
			}
			c1.show(yellowPanel, HANDY);
		}
	}

	private void undo() {
		// TODO Auto-generated method stub

	}

	public void move(int x, int y, String string) {
		// TODO Auto-generated method stub
		if(string.equals("left")) {
			turn++;
			boardSquares[x][y-1].setIcon(boardSquares[x][y].getIcon());
			if((x % 2 == 0 && y % 2 == 0) || (x % 2 == 1 && y % 2 == 1)) {
				boardSquares[x][y].setIcon(new ImageIcon());
				boardSquares[x][y].setBackground(Color.WHITE);
			}
			else {
				boardSquares[x][y].setIcon(new ImageIcon());
				boardSquares[x][y].setBackground(Color.GRAY);
			}
			//Reset the creation buttons to their colours
			boardSquares[2][2].setBackground(Color.GREEN);
			boardSquares[7][7].setBackground(Color.YELLOW);
			b.movePiece(x, y, string);
			isGameWon();
		}
		if(string.equals("right")) {
			turn++;
			boardSquares[x][y+1].setIcon(boardSquares[x][y].getIcon());
			if((x % 2 == 0 && y % 2 == 0) || (x % 2 == 1 && y % 2 == 1)) {
				boardSquares[x][y].setIcon(new ImageIcon());
				boardSquares[x][y].setBackground(Color.WHITE);
			}
			else {
				boardSquares[x][y].setIcon(new ImageIcon());
				boardSquares[x][y].setBackground(Color.GRAY);
			}
			boardSquares[2][2].setBackground(Color.GREEN);
			boardSquares[7][7].setBackground(Color.YELLOW);
			b.movePiece(x, y, string);
			isGameWon();
		}
		if(string.equals("up")) {
			turn++;
			boardSquares[x-1][y].setIcon(boardSquares[x][y].getIcon());
			if((x % 2 == 0 && y % 2 == 0) || (x % 2 == 1 && y % 2 == 1)) {
				boardSquares[x][y].setIcon(new ImageIcon());
				boardSquares[x][y].setBackground(Color.WHITE);
			}
			else {
				boardSquares[x][y].setIcon(new ImageIcon());
				boardSquares[x][y].setBackground(Color.GRAY);
			}
			boardSquares[2][2].setBackground(Color.GREEN);
			boardSquares[7][7].setBackground(Color.YELLOW);
			b.movePiece(x, y, string);
			isGameWon();
		}
		if(string.equals("down")) {
			turn++;
			boardSquares[x+1][y].setIcon(boardSquares[x][y].getIcon());
			if((x % 2 == 0 && y % 2 == 0) || (x % 2 == 1 && y % 2 == 1)) {
				boardSquares[x][y].setIcon(new ImageIcon());
				boardSquares[x][y].setBackground(Color.WHITE);
			}
			else {
				boardSquares[x][y].setIcon(new ImageIcon());
				boardSquares[x][y].setBackground(Color.GRAY);
			}
			boardSquares[2][2].setBackground(Color.GREEN);
			boardSquares[7][7].setBackground(Color.YELLOW);
			b.movePiece(x, y, string);
			isGameWon();
		}
	}

	public JButton[] getGreen() {
		return this.greenPlayerHand;
	}

	public JButton[] getYellow() {
		return this.yellowPlayerHand;
	}

	public Dimension getPreferredSize() {
		return new Dimension(700,700);
	}

}
