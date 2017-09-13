package controller;
import Player.Player;
import board.Board;
import pieces.Piece;
import view.GUI;

public class Chess {
	private static Piece[][] pieces; 
	private static Player[] players;
	private static int activePlayer; 
	private GUI gui; 
	
	/**
	 * Getters and Setters
	 */
	public Piece[][] getPieces() {
		return pieces;
	}
	public void setPieces(Piece[][] pieces) {
		Chess.pieces = pieces;
	}
	public Player[] getPlayers() {
		return players;
	}
	public static void setPlayers(Player[] newPlayers) {
		players = newPlayers;
	}
	public GUI getGui() {
		return gui;
	}
	public void setGui(GUI gui) {
		this.gui = gui;
	}
	public static void setActivePlayer(int activePlayer){
		Chess.activePlayer = activePlayer; 
	}
	
//	public static void controller(){
//		String[] newPlayers = GUI.getPlayerNames(); 
//		GUI.initializeGui();
//		String blackPlayer=null; //=get user input
//		String whitePlayer=null; //=get user input
//		players = GUI.initializePlayers(GUI.pieces, blackPlayer, whitePlayer);
//		boolean shouldRun = true; 
//		
//		while(shouldRun){
//			Piece activePiece = null; 
//			while(activePiece == null){
//				//get user input
//			}
//			if(Board.activePlayersPiece(activePiece, players[activePlayer])){ //piece belongs to active player
//				while(true){ //keep trying for valid move
//					GUI.highlightPossibleMoves(pieces, Board.possibleMoves(pieces, activePiece, players[activePlayer]));
//					Piece targetDest = null; // = getDestination(); //player input
//					if(sendMove(targetDest.getX(), targetDest.getY(), activePiece.getX(), activePiece.getY(), pieces, activePiece)){
//						break; 
//					}
//				}
//				players[activePlayer].setInCheck(Board.inCheck(pieces, players[(activePlayer+1)%2]));
//				if(Board.checkmate(pieces, players[(activePlayer+1)%2])){ //if opponent is in checkmate
//					shouldRun = false;
//					int activePlayerScore = players[activePlayer].getScore();
//					players[activePlayer].setScore(activePlayerScore++);
//					System.out.println("Checkmate! " + players[activePlayer].getName() + "Wins after " + players[activePlayer].getMoveNumber() + "moves!");
//				}else if(Board.stalemate(pieces, players[(activePlayer+1)%2])){
//					shouldRun = false; 
//					System.out.println("Stalemate after " + players[activePlayer].getMoveNumber() + "moves!");
//				}else 
//					activePlayer = (activePlayer + 1) % 2;
//			}
//		}
//		System.out.println("Play Again?");
//	}
	

	/**
	 * Send the user's inputed move to the Board. 
	 * If the move is valid, the Board will make the move. 
	 * Update the GUI and move counter. 
	 * @param destX
	 * @param destY
	 * @param x
	 * @param y
	 * @param pieces
	 * @param activePiece
	 * @return
	 */
	public static boolean sendMove(int destX, int destY, int x, int y, Piece[][] pieces, Piece activePiece){
		if(Board.movePiece(destX, destY, x, y, pieces, activePiece)){
			GUI.updateBoard(pieces);
			int moveNumber = players[activePlayer].getMoveNumber();
			players[activePlayer].setMoveNumber(moveNumber++); 
			GUI.updateMenu(players);
			return true;
		}
		return false;
	}
	
	/**
	 * Active player forfeits and opponent's score is incremented by one.
	 * The board is that automatically rebuilt. 
	 */
	public static void forfeit(){
		int playerScore = players[(activePlayer+1)%2].getScore();
		players[(activePlayer+1)%2].setScore(playerScore++);
		GUI.updateMenu(players);
		restart(); 
	}
	
	/**
	 * Rebuild the board and set the active player to black.
	 * initializeBoard() will make sure to clean the board before rebuilding it.
	 */
	public static void restart(){
		GUI.initializeBoard(); 
		activePlayer = 0;
	}
	
	public static Player getActivePlayer(){
		return players[activePlayer];
	}
	public static void nextPlayer() {
		Chess.activePlayer = (Chess.activePlayer + 1)%2;
	}
}
