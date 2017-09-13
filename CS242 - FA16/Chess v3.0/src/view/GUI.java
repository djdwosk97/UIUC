package view;
import board.Board;
import pieces.Piece;
import Player.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import controller.Chess;

import coord.Coord;

/**
 * Print pieces/board
 * 
 * 
 * Citation: http://stackoverflow.com/questions/21077322/create-a-chess-board-with-jpanel
 * Citation: http://www.wpclipart.com/recreation/games/chess/chess_set_1/
 */
public class GUI { //extends Frame implements ActionListener {
		private final static JPanel gui = new JPanel(new BorderLayout(1,1));
	    private static JButton[][] chessBoardSquares = new JButton[8][8];
	    private static JPanel chessBoard;
	    private static final String COLS = "ABCDEFGH";
		public static Piece[][] pieces = null;
		public static Player[] players = null; 
		public static Coord[] possibleMoves = null;
	    public static JLabel activePlayer = new JLabel("Black is playing");
	    public static JLabel gameTime = new JLabel("Game Time: 0 Minutes 0 Seconds");
	    public static JLabel totalMoves = new JLabel("Moves: 0");
	    public static JLabel score = new JLabel("Score: 0");
	    private static int startX;
		private static int startY;
		private static int destX;
		private static int destY;

	    GUI() {
	        initializeGui();
	    }

	    /**
	     * Builds the GUI 
	     */
	    public final static void initializeGui() {
	    	String blackPlayer = null;
	    	String whitePlayer = null; 
	    	Chess.setPlayers(Board.addPlayers(blackPlayer, whitePlayer));
	    	initializeMenu(); 
	        chessBoard = new JPanel(new GridLayout(0, 10));
	        gui.add(chessBoard);
	        initializeBoard();
	        startX = -1; 
	        startY = -1; 
	        destX = -1; 
	        destY = -1; 
	    }

	    /**
	     * Initializes the top status bar. 
	     * Active player is initially Black. 
	     * Game Time and Moves are initialized to 0. 
	     */
	    public final static void initializeMenu(){
	    	 JToolBar menu =  new JToolBar();
	    	 gui.add(menu, BorderLayout.PAGE_START);
	    	 menu.setLayout(new FlowLayout(FlowLayout.CENTER));
	    	 menu.add(new JButton("New Game")); // TODO - add Listener
	    	 menu.addSeparator();
	    	 menu.addSeparator();
	    	 menu.add(activePlayer);
	    	 menu.addSeparator();
	    	 menu.addSeparator();
	    	 menu.add(totalMoves);
	    	 menu.add(score);
	    	 menu.addSeparator();
	    	 menu.addSeparator();
	    	 JButton undo = new JButton("Undo");
	    	 menu.add(undo);
	    	 undo.addActionListener(undoLastMove()); 
	    }
	    
	    /**
	     * Initializes board and the players to the starting settings and positions. 
	     */
	    public final static void initializeBoard(){
	    	 // create the chess board squares
	    	pieces = Board.buildBoard(8,8);
            initializePlayers(pieces, "", ""); //:TODO replace two strings with user inputed player name
	        for (int x = 0; x < chessBoardSquares.length; x++) {
	            for (int y = 0; y < chessBoardSquares[x].length; y++) {
	                JButton b = new JButton();
	                b.setMargin(new Insets(0, 0, 0, 0));
	                ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB)); //make all squares 64x64
	                b.setIcon(icon);
	                b.setOpaque(true);
	                b.setBorderPainted(false);
	                b.setFont(new Font("Arial", Font.BOLD, 40));
	                b.setHorizontalTextPosition(SwingConstants.CENTER);
	                if ((y % 2 == 1 && x % 2 == 1) || (y % 2 == 0 && x % 2 == 0)) { //every other square
	                    b.setBackground(Color.WHITE); //white
	                } else {
	                    b.setBackground(Color.DARK_GRAY); //black
	                }
	                b.addActionListener(selectedPiece(x,y));
	                chessBoardSquares[y][x] = b;
	                if(pieces[y][x] != null){
	                	ImageIcon pieceIcon = getImageIcon(pieces[y][x]);
	                	if(pieceIcon == null){ //no image exists, set a symbolic letter instead
	                		if(pieces[y][x].getColor() == Color.WHITE)
		                		chessBoardSquares[y][x].setForeground(Color.WHITE);
		                	else 
		                		chessBoardSquares[y][x].setForeground(Color.BLACK);
	                		chessBoardSquares[y][x].setText(pieces[y][x].getLetter());
	                	} else 
	                		chessBoardSquares[y][x].setIcon(pieceIcon);
	                }
	            }
	        }

	        //Add columns letters -- TOP
	        chessBoard.add(new JLabel(""));
	        for (int x = 0; x < 8; x++) {
	            chessBoard.add(new JLabel(COLS.substring(x, x + 1), SwingConstants.CENTER));
	        }
	        //Add squares and row numbers
	        chessBoard.add(new JLabel("", SwingConstants.CENTER));
	        for (int x = 0; x < 8; x++) {
	            for (int y = 0; y < 8; y++) {
	                switch (y) {
	                    case 0:
	                        chessBoard.add(new JLabel("" + (x + 1), SwingConstants.CENTER));
	                    default:
	                        chessBoard.add(chessBoardSquares[y][x]);
	                }
	            }
	            chessBoard.add(new JLabel("" + (x + 1), SwingConstants.CENTER));
	        }
	        //Add columns letters -- BOTTOM
	        chessBoard.add(new JLabel(""));
	        for (int x = 0; x < 8; x++) {
	            chessBoard.add(new JLabel(COLS.substring(x, x + 1), SwingConstants.CENTER));
	        }
	    }
	    
	    /**
	     * Fetch the image location of the specific piece
	     * and return its image. If the image can not be found
	     * return null. 
	     * @param pieces
	     * @return ImageIcon of the piece if it exists, else null
	     */
	    public static ImageIcon getImageIcon(Piece pieces){
	     	BufferedImage img = null;
			try {
				img = ImageIO.read(new File(pieces.getImage()));
			} catch (IOException e) {
				e.printStackTrace();
				return null; 
			}
        	Image dimg = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        	ImageIcon pieceIcon = new ImageIcon(dimg);
        	return pieceIcon; 
	    }
	   
	    /**
	     * Initialize the players and find their respective kings. 
	     * @param pieces
	     * @param blackPlayer
	     * @param whitePlayer
	     * @return
	     */
	    public final static Player[] initializePlayers(Piece[][] pieces, String blackPlayer, String whitePlayer){
	    	Player[] players = Board.addPlayers(blackPlayer, whitePlayer);
	    	players[0].setKing(Board.findKing(pieces, players[0]));
	    	players[1].setKing(Board.findKing(pieces, players[1]));
	    	Chess.setPlayers(players);
	    	Chess.setActivePlayer(0);
	    	return players; 
	    }
	    
	    /**
	     * Activates when a player selects a piece. It will highlight all possible moves for that piece
	     * and then when the player selects a destination it will move to that piece 
	     * (or change to a different piece).
	     * @param x
	     * @param y
	     * @return
	     */
	    private static ActionListener selectedPiece(final int x, final int y){
	    	return new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent actionEvent) { //selecting a piece to move
	            	if(pieces[y][x] != null && startX == -1 && startY == -1 && (pieces[y][x].getColor() != Chess.getActivePlayer().getColor()))
	            		return;
	                if (pieces[y][x] != null && (startX == -1 && startY == -1)) {
	                    startX = x;
	                    startY = y;
	                    possibleMoves = Board.possibleMoves(pieces, pieces[y][x], Chess.getActivePlayer());
	                    if(possibleMoves != null)
	                    	highlightPossibleMoves(pieces, possibleMoves);
	                } 
	                if((startX != -1 && startY != -1) && ((pieces[y][x] != null && pieces[y][x].getColor() == Chess.getActivePlayer().getColor()))) {
	                	startX = x; 
	                	startY = y; 
	                	restoreBoard(pieces); 
	                	possibleMoves = Board.possibleMoves(pieces, pieces[y][x], Chess.getActivePlayer());
	                    if(possibleMoves != null)
	                    	highlightPossibleMoves(pieces, possibleMoves);
	                } else if((startX != -1 && startY != -1)){ //selecting a destination for that piece
	                	destX = x;
	                    destY = y;
	                    boolean status = Chess.sendMove(destY, destX, startY, startX, pieces, pieces[startY][startX]);
	                    if(status){ //move is made
	                    	restoreBoard(pieces);
	                		updateBoard(pieces);
	                		startX = -1;
	                		startY = -1;
	                		destX = -1;
	                		destY = -1;
	                		Chess.nextPlayer();
	                		Piece tempKing = Chess.getActivePlayer().getKing();
	                		if(Board.inCheck(pieces, Chess.getActivePlayer())){
	                			chessBoardSquares[tempKing.getX()][tempKing.getY()].setBackground(Color.ORANGE);
	                		} else if(Board.checkmate(pieces, Chess.getActivePlayer())){
	                			chessBoardSquares[tempKing.getX()][tempKing.getY()].setBackground(Color.RED);
	                		} else if(Board.stalemate(pieces, Chess.getActivePlayer())){
	                			chessBoardSquares[tempKing.getX()][tempKing.getY()].setBackground(Color.BLUE);
	                		}
                			updateBoard(pieces);
	                   } else {
	                	   //do nothing and wait for another destination
	                   }
	                }
	            }
	        };
	    }
	    
	    //TODO 
	    private static ActionListener undoLastMove(){
//	    	return new ActionListener() {
//	            @Override
//	            public void actionPerformed(ActionEvent actionEvent) { //selecting a piece to move
//	                if (startX == -1 && startY == -1) {
//	                    startX = x;
//	                    startY = y;
//	                    possibleMoves = Board.possibleMoves(pieces, pieces[y][x], Chess.getActivePlayer());
//	                    if(possibleMoves != null)
//	                    	highlightPossibleMoves(pieces, possibleMoves);
//	                } else { //selecting a destination for that piece
//	                	destX = x;
//	                    destY = y;
//	                    if(possibleMoves == null || (pieces[destY][destX] != null && pieces[destY][destX].getColor() == Chess.getActivePlayer().getColor())){//get new active piece
//	                    	//:TODO Fix this
//	                    	restoreBoard(pieces);
//	                    	//updateBoard(pieces);
//	                    	return; 
//	                	}
//	                    boolean status = Chess.sendMove(destY, destX, startY, startX, pieces, pieces[startY][startX]);
//	                    System.out.println(status);
//	                    if(status){ //move is made
//	                    	restoreBoard(pieces);
//	                		updateBoard(pieces);
//	                		startX = -1;
//	                		startY = -1;
//	                		destX = -1;
//	                		destY = -1;
//	                		Chess.nextPlayer();
//	                   } else {
//	                	   //do nothing and wait for another destination
//	                   }
//
//
//	                }
//	            }
//	        };
	    	return null;
	    }
	    
	    
	    /**
	     * Updates the pieces on the board after a move is made. 
	     * @param pieces
	     */
	    public static void updateBoard(Piece[][] pieces){
	    	for (int x = 0; x < chessBoardSquares.length; x++) {
	            for (int y = 0; y < chessBoardSquares[x].length; y++) {
	            	chessBoardSquares[y][x].setIcon(null); //clean the board so it can be redrawn
	                if(pieces[y][x] != null){
	                	ImageIcon pieceIcon = getImageIcon(pieces[y][x]);
	                	if(pieceIcon == null){ //no image exists, set a symbolic letter instead
	                		if(pieces[y][x].getColor() == Color.WHITE)
		                		chessBoardSquares[y][x].setForeground(Color.WHITE);
		                	else 
		                		chessBoardSquares[y][x].setForeground(Color.BLACK);
	                		chessBoardSquares[y][x].setText(pieces[y][x].getLetter());
	                	} else 
	                		chessBoardSquares[y][x].setIcon(pieceIcon);
	                }
	            }
	        }
	    }
	    
	    /**
	     * Restore highlighted squares to default state
	     * @param pieces
	     */
	    public static void restoreBoard(Piece[][] pieces){
	    	for (int x = 0; x < chessBoardSquares.length; x++) {
	    		for (int y = 0; y < chessBoardSquares[x].length; y++) {
	    			if ((y % 2 == 1 && x % 2 == 1) || (y % 2 == 0 && x % 2 == 0)) { //every other square
	    				chessBoardSquares[y][x].setBackground(Color.WHITE); //white
	    			} else {
	    				chessBoardSquares[y][x].setBackground(Color.DARK_GRAY); //black
	    			}
	    		}
	    	}
	    }
	    
	    /**
	     * Highlights all possible moves for the selected piece. 
	     * @param possibleMoves
	     */
	    public static void highlightPossibleMoves(Piece[][] pieces, Coord[] possibleMoves){
	    	if(possibleMoves == null) { 
	    		return; 
	    	}
	    	for(int i=0; possibleMoves[i] != null && i<possibleMoves.length; i++){
	    		if(possibleMoves[i] != null)
	    			chessBoardSquares[possibleMoves[i].getX()][possibleMoves[i].getY()].setBackground(Color.YELLOW);
	    	}
	    	updateBoard(pieces);
	    }
	    
	    /**
	     * Update the game and move timer.
	     * @param players
	     */
	    public static void updateMenu(Player[] players){
	    	initializeMenu(); 
	    }
	    
	    public final JComponent getChessBoard() {
	        return chessBoard;
	    }

	    public final JComponent getGui() {
	        return gui;
	    }

	    public static void main(String[] args) {
	    	GUI chessboard = new GUI();
	    	JFrame chessFrame = new JFrame("Chess");
	    	chessFrame.add(chessboard.getGui());
	    	chessFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    	chessFrame.setLocationByPlatform(true);
	    	chessFrame.pack();
	    	chessFrame.setMinimumSize(chessFrame.getSize());
	    	chessFrame.setVisible(true);
	    }

	    /**
	     * Get user inputted names
	     * @return
	     */
//		public static String[] getPlayerNames() { //:TODO
//			JButton inputButton = new JButton("Send");
//			JTextArea inputPlayer1 = new JTextArea("Input Player 1 Name Here");
//			JTextArea inputPlayer2 = new JTextArea("Input Player 2 Name Here");
//			JTextArea uneditTextArea = new JTextArea();
//			inputPlayer1.setBackground(Color.BLUE);
//			inputPlayer2.setBackground(Color.BLUE);
//			inputPlayer1.setForeground(Color.WHITE);
//			inputPlayer2.setForeground(Color.WHITE);
//			uneditTextArea.setEditable(false);
//			// TODO Auto-generated method stub
//			return null;
//		}
}
