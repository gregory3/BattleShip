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
	
	public void initializeGame(){
		in = new Scanner(System.in);
		Player player1Local = new Player("Player 1");
		Player player2Local = new Player("Player 2");
		this.player1=player1Local;
		this.player2=player2Local;
		
		//Ask first player to place ships
		int x,y;
		Orientation orien;
		String orienString;
		boolean cannotplace=true;
		//Place all ships for player 1
		System.out.println("PLAYER 1 SHIP PLACEMENTS");
		for(Ship.ShipType shipType: Ship.ShipType.values()){
			cannotplace=true;
			while(cannotplace){
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
		boolean turn=true;
		int x,y;
		while(true){
			if(turn){
				System.out.println("Player 1 turn");
				System.out.println("Your grid");
				player1.getPlayerGrid().printPlayerGrid();
				System.out.println("Guess grid:");
				player1.getEnemyGrid().printAttackGrid();
				System.out.println("Guess x:");
				x = in.nextInt();
				System.out.println("Guess y: ");
				y = in.nextInt();
				//Attack player 2
				player1.attack(x, y, player2.incomingAttack(x, y));
				if(player2.getPlayerGrid().areAllShipsDead()){
					System.out.println("Player 1 won.");
					return;
				}
					
			}
			else{
				System.out.println("Player 2 turn");
				System.out.println("Your grid");
				player2.getPlayerGrid().printPlayerGrid();
				System.out.println("Guess grid:");
				player2.getEnemyGrid().printAttackGrid();
				System.out.println("Guess x:");
				x = in.nextInt();
				System.out.println("Guess y: ");
				y = in.nextInt();
				//Attack player 2
				player2.attack(x, y, player1.incomingAttack(x, y));
				if(player1.getPlayerGrid().areAllShipsDead()){
					System.out.println("Player 1 won.");
					return;
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
