import java.util.Scanner;
public class Board {
	
	Scanner in;
	public static final char WATER = '~';
	public static final char HIT = 'H';
	public static final char MISS = 'M';
	public static final int BOARDSIZE = 10;
	public static final char[] LETTERS = {'A','B','C','D','E','F','G','H'};
	Player player1;
	Player player2;
	
	public enum Orientation{
		HORIZONTAL,VERTICAL;
	}
	
	/** Allows both players to place their ships
	 * 
	 */
	public void initializeGame(){
		in = new Scanner(System.in);
		//Create players
		Player player1Local = new Player("Player 1");
		Player player2Local = new Player("Player 2");
		this.player1=player1Local;
		this.player2=player2Local;
		
		//Ask first player to place ships
		int x,y;
		Orientation orien; //Temp variable, holds orientation of a ship
		String orienString; //user input of orientation
		
		boolean cannotplace=true; //true if invalid placement, false if valid
		
		//Place all ships for player 1
		System.out.println("PLAYER 1 SHIP PLACEMENTS");
		for(Ship.ShipType shipType: Ship.ShipType.values()){
			cannotplace=true;
			while(cannotplace){
				//Ask for player 2 for orientation and coordinates
				System.out.println("Place "+shipType);
				System.out.println("Enter x pos: ");
				x = in.nextInt();
				System.out.println("Enter y pos: ");
				y = in.nextInt();
				System.out.println("Enter orientation(h/v): ");
				orienString = in.next();
				if(orienString.toLowerCase().equals("h"))
					orien = Orientation.HORIZONTAL;
				else
					orien = Orientation.VERTICAL;
				Ship tempShip = new Ship(shipType);
				cannotplace = !player1.placeShip(tempShip, orien, x, y);
			}
			System.out.println("Placed "+shipType);
			player1.getPlayerGrid().printPlayerGrid();
		}
		
		//Place all ships for player 2
		cannotplace=true;
		System.out.println("PLAYER 2 SHIP PLACEMENTS");
		for(Ship.ShipType shipType: Ship.ShipType.values()){
			cannotplace=true;
			while(cannotplace){
				//Ask for player 2 for orientation and coordinates
				System.out.println("Place "+shipType);
				System.out.println("Enter x pos: ");
				x = in.nextInt();
				System.out.println("Enter y pos: ");
				y = in.nextInt();
				System.out.println("Enter orientation(h/v): ");
				orienString = in.next();
				if(orienString.toLowerCase().equals("h"))
					orien = Orientation.HORIZONTAL;
				else
					orien = Orientation.VERTICAL;
				Ship tempShip = new Ship(shipType);
				cannotplace = !player2.placeShip(tempShip, orien, x, y);
			}
			System.out.println("Placed "+shipType);
			player2.getPlayerGrid().printPlayerGrid();
		}
	}
	
	public void playGame(){
		boolean turn=true; //true if player1 turn, false if player2 turn
		int x,y; //coordinates used to record attacks
		//Play game until player has won
		while(true){
			if(turn){
				while(true){
					//Ask player 1 for input
					System.out.println("Player 1 turn");
					System.out.println("Your grid");
					player1.getPlayerGrid().printPlayerGrid();
					System.out.println("Guess grid:");
					player1.getAttackGrid().printAttackGrid();
					System.out.println("Guess x:");
					x = in.nextInt();
					System.out.println("Guess y: ");
					y = in.nextInt();
					//Attack player 2
					//If not valid play, keep asking for user input until valid
					if(!(x<0||x>9||y<0||y>9||player1.getPlayerGrid().getSquareSymbol(x, y)=='M'||player1.getPlayerGrid().getSquareSymbol(x, y)=='H')){
						player1.attack(x, y, player2.incomingAttack(x, y));
						if(player2.getPlayerGrid().areAllShipsDead()){
							System.out.println("Player 1 won.");
							return;
						}
						break;
					}
				}
					
			}
			else{
				while(true){
					//Ask player 2 for input
					System.out.println("Player 2 turn");
					System.out.println("Your grid");
					player2.getPlayerGrid().printPlayerGrid();
					System.out.println("Guess grid:");
					player2.getAttackGrid().printAttackGrid();
					System.out.println("Guess x:");
					x = in.nextInt();
					System.out.println("Guess y: ");
					y = in.nextInt();
					//Attack player 1
					//If not valid play, keep asking for user input until valid
					if(!(x<0||x>9||y<0||y>9||player2.getPlayerGrid().getSquareSymbol(x, y)=='M'||player2.getPlayerGrid().getSquareSymbol(x, y)=='H')){
						player2.attack(x, y, player1.incomingAttack(x, y));
						if(player1.getPlayerGrid().areAllShipsDead()){
							System.out.println("Player 1 won.");
							return;
						}
						break;
					}
				}
			}
			turn = !turn;
		}
	}
	

	public static void main(String[] args){
		Board board= new Board();
		board.initializeGame();
		board.playGame();
	}
	
}
