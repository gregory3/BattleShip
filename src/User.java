
import java.net.*;
import java.util.Scanner;



import java.io.*;
public class User {
	private String serverIP="10.200.24.45";
	//Socket
	Socket socket;
	//Readers
	ObjectInputStream ois;
	ObjectOutputStream oos;
	DataOutputStream dos;
	DataInputStream dis;
	Scanner userInput;
	//Game objects
	Player userPlayer;
	
	public User(){
		userPlayer = new Player("Player");
	}
	
	/**
	 * connects to server
	 */
	private void connectToServer(int port) throws Exception{
		System.out.println(InetAddress.getLocalHost());
		socket = new Socket(serverIP, 6789);
		dos = new DataOutputStream(socket.getOutputStream());
		dis = new DataInputStream(socket.getInputStream());
		ois = new ObjectInputStream(socket.getInputStream());
		oos = new ObjectOutputStream(socket.getOutputStream());
		userInput = new Scanner(System.in);
	}
	
	
	
	/**
	 * send a string to the server of the user inputed location
	 * checks valid move
	 */
	public void makeMove() throws IOException{
		int x,y;
		System.out.println("X coordinate: ");
		x = userInput.nextInt();
		System.out.println("Y coordinate: ");
		y = userInput.nextInt();
		
		//Send move to server
		String move = x +""+y;
		dos.writeUTF(move);
		
		//DO ERROR CHECKING
	}
	
	/**
	 * Set board
	 * while loop
	 * turn and wait for other player 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void playGame() throws IOException, ClassNotFoundException{
		//Place Ships
		System.out.println(dis.readUTF());
		System.out.println("Placed ships successfully: "+placeShips());
		//Write Player object to server
		oos.writeObject(userPlayer);
		
		//Receives moves
		while(true){
			String serverMessage = dis.readUTF();
			
			switch(serverMessage){
				case "make move":
					makeMove();
					oos.writeObject(userPlayer);
					break;
				case "attacked":
					userPlayer = (Player)ois.readObject();
					break;
				case "won":
					System.out.println("You won the game!");
					break;
				case "lost":
					System.out.println("You lost the game!");
					break;
			}
			System.out.println("Attack Grid");
			userPlayer.getAttackGrid().printAttackGrid();
			System.out.println("Player Grid");
			userPlayer.getPlayerGrid().printPlayerGrid();
		}
	}
	public boolean placeShips(){
		//Variables
		Scanner in = new Scanner(System.in);
		int x,y;
		Board.Orientation orien; //Temp variable, holds orientation of a ship
		String orienString; //user input of orientation
		boolean cannotplace;
		
		for(Ship.ShipType shipType: Ship.ShipType.values()){
			cannotplace=true;
			while(cannotplace){
				//Ask for player 2 for orientation and coordinates
				System.out.println("Place "+shipType);
				System.out.println("Enter x pos: ");
				x = userInput.nextInt();
				System.out.println("Enter y pos: ");
				y = userInput.nextInt();
				System.out.println("Enter orientation(h/v): ");
				orienString = in.next();
				if(orienString.toLowerCase().equals("h"))
					orien = Board.Orientation.HORIZONTAL;
				else
					orien = Board.Orientation.VERTICAL;
				Ship tempShip = new Ship(shipType);
				cannotplace = !userPlayer.placeShip(tempShip, orien, x, y);
			}
			System.out.println("Placed "+shipType);
			userPlayer.getPlayerGrid().printPlayerGrid();
		}	
		return true;
	}
	
	public static void main(String[] args) throws Exception{
		User myUser = new User();
		myUser.connectToServer(6789);
		myUser.playGame();
	}
}