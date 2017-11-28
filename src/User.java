import java.net.*;
import java.util.Scanner;

import java.io.*;
public class User {
	private Player userPlayer;
	private String serverIP;
	Socket socket;
	BufferedReader inFromUser;
	BufferedReader inFromServer;
	DataOutputStream s1Out;
	
	
	
	public User(Board board){
		userPlayer = new Player("Player");
	}
	
	/**
	 * connects to server
	 */
	private void connectToServer(String IP, int port) throws Exception{
		socket = new Socket(IP, port);
		inFromUser = new BufferedReader(new InputStreamReader( System.in));
		s1Out = new DataOutputStream( socket.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		//ONCE CONNECTION IS MADE, THE FOLLOWING CODE WILL RUN
		//MAKE SURE A CONNECTION IS MADE BEFORE CODE EXECUTES
		userPlayGame();
	}
	
	/** User sends data and recieves data to play game
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void userPlayGame() throws IOException, ClassNotFoundException{
		//Get objects from server
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		//Send objects to server
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		
		//ONCE CONNECTION IS MADE, THE FOLLOWING CODE WILL RUN
		
		//Send ship data to server
		System.out.println(inFromServer.readLine());

		//Send player data to server
		oos.writeObject(shipPlacementMode());
		
		//Play game
		while(true){
			String serverString = inFromServer.readLine();
			switch(serverString){
			case "move": //If server says to move, make move and then get the modified player object from the server
				makeMove();
				userPlayer = (Player) ois.readObject();
			case "attacked":
				userPlayer = (Player) ois.readObject();
			case "game over":
				break;
			}
			
		}
	}
	

	/**
	 * send a string to the server of the user inputed location
	 * checks valid move
	 */
	public void makeMove() throws IOException{
		int x,y;
		boolean isHit;
		Scanner in = new Scanner(System.in);
		//Print both boards
		userPlayer.getAttackGrid().printAttackGrid();
		userPlayer.getPlayerGrid().printPlayerGrid();
		
		//Ask for a move
		while(true){
			//Asks for user coordinates
			System.out.println("Where would you like to fire your missle?");
			System.out.println("x: ");
			x = in.nextInt();
			System.out.println("y: ");
			y = in.nextInt();
			//IMPORTANT SHOULD THE USER SEND LINE 80 OR 81 TO THE SERVER??
			//Sends user move to server
			String move = inFromUser.readLine();
			move = x+""+y;
			s1Out.writeBytes(move + "/n");
			//Test if valid move
			break;
		}
	}
	
	/** Modifies userPlayerObject with ships
	 * 
	 * @return
	 */
	public Player shipPlacementMode(){
		Scanner in = new Scanner(System.in);

		int x,y;
		Board.Orientation orien; //Temp variable, holds orientation of a ship
		String orienString; //user input of orientation
		
		boolean cannotplace=true; //true if invalid placement, false if valid
		
		System.out.println("Place your ships");
		//Tell user to place every ship
		for(Ship.ShipType shipType: Ship.ShipType.values()){
			cannotplace=true;
			//Tell user to choose new ship spot until valid placement
			while(cannotplace){
				//Ask for player 1 for orientation and coordinates
				System.out.println("Place "+shipType);
				System.out.println("Enter x pos: ");
				x = in.nextInt();
				System.out.println("Enter y pos: ");
				y = in.nextInt();
				System.out.println("Enter orientation(h/v): ");
				orienString = in.next();
				if(orienString.toLowerCase().equals("h"))
					orien = Board.Orientation.HORIZONTAL;
				else
					orien = Board.Orientation.VERTICAL;
				Ship tempShip = new Ship(shipType);
				//Place ship if possible
				cannotplace = !userPlayer.placeShip(tempShip, orien, x, y);
			}
			System.out.println("Placed "+shipType);
			userPlayer.getPlayerGrid().printPlayerGrid();
		}
		return userPlayer;
	}
	
	/**
	 * Set board
	 * while loop
	 * turn and wait for other player 
	 */
	public void playGame(){
		//STUB
	}
	
	
	
	
}
