

import java.net.*;
import java.io.*;
public class BSServer1 {
	public String p1;
	public String p2;
	public Board server_Board;
	public Player player1;
	public Player player2;
	
	ServerSocket s;
	Socket s1;
	Socket s2;
	
	//Readers and Senders
	DataOutputStream s1Out;
	DataOutputStream s2Out;
	DataInputStream s1In;
	DataInputStream s2In;
	ObjectOutputStream s1ObjOut;
	ObjectOutputStream s2ObjOut;
	ObjectInputStream s1ObjIn;
	ObjectInputStream s2ObjIn;

	
	
	
	
	public boolean connectToUsers() throws Exception{
		System.out.println("Connecting to users");
		//Connect to player 1
		s = new ServerSocket(6789);
		s1 = s.accept();
		s1Out = new DataOutputStream(s1.getOutputStream());
		s1In = new DataInputStream(s1.getInputStream());
		s1ObjOut = new ObjectOutputStream(s1.getOutputStream());
		s1ObjIn = new ObjectInputStream(s1.getInputStream());
		System.out.println("Connected to first player.");
		//Connect to player 2
		s2 = s.accept(); //different port??
		s2Out = new DataOutputStream(s2.getOutputStream());
		s2In = new DataInputStream(s2.getInputStream());
		s2ObjOut = new ObjectOutputStream(s2.getOutputStream());
		s2ObjIn = new ObjectInputStream(s2.getInputStream());
		System.out.println("Connected to second player.");

		return true;
	}
	
	
	/** Sends and recieves data from clients to play game
	 * @author bakkerp
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void playGame() throws IOException, ClassNotFoundException{
		
		System.out.println("Starting Game");
		//Initialize Game
		player1 = new Player("Player 1");
		player2 = new Player("Player 2");
		//Ship Placement Mode
		//Server sends message to both players to place ship
		System.out.println("Ship Placement Mode");
		s1Out.writeUTF("Server: Place Ships");
		s2Out.writeUTF("Server: Place Ships");
		//Server gets player objects from both users
		player1 = (Player) s1ObjIn.readObject();
		player2 = (Player) s2ObjIn.readObject();
		System.out.println("Got ship locations from each player");
		
		
		//Play Game
		boolean turn=true; //true if player1 turn, false if player2 turn
		
		//game will run in a loop. need to add condition for end of game
		while(true){
			//If player 1 turn
			if(turn){
				System.out.println("Player 1 make a move.");
				//Tell player 1 to make move
				s1Out.writeUTF("make move");
				String player1Move = s1In.readUTF();
				System.out.println("Player 1 move: "+player1Move);
				//Get attack coordinates
				int x =  Character.getNumericValue(player1Move.charAt(0));
				int y =  Character.getNumericValue(player1Move.charAt(1));
				System.out.println("Player 1 attacked square "+x+" "+y);
				
				//Modify player2 object after being attacked and send to user2
				boolean isHit = player2.incomingAttack(x, y);
				System.out.println("isHit: "+isHit);
				s2Out.writeUTF("attacked");
				s2ObjOut.writeObject(player2);
				
				//Modify player1 object after attacking and send to player 1
				player1.attack(x, y, isHit);
				s1ObjOut.writeObject(player1);
				if(player2.getPlayerGrid().areAllShipsDead()){
					s2Out.writeUTF("loss");
					s1Out.writeUTF("won");
					return;
				}
			}
			//Player 2 turn
			else{
				System.out.println("Player 2 make a move.");
				//Tell player 2 to make move
				s2Out.writeUTF("make move");
				String player2Move = s2In.readUTF();
				System.out.println("Player 2 move: "+player2Move);

				//Get attack coordinates
					
				int x =  Character.getNumericValue(player2Move.charAt(0));
				int y =  Character.getNumericValue(player2Move.charAt(1));
				System.out.println("Player 2 attacked square "+x+" "+y);

				//Modify player 1 object after being attacked and send to user 1
				boolean isHit = player1.incomingAttack(x, y);
				

				
				System.out.println("isHit: "+isHit);
				s2Out.writeUTF("attacked");
				s2ObjOut.writeObject(player1);
				//Modify player 2 object after attacking and send to player 2
					
				player2.attack(x, y, isHit);
				s1ObjOut.writeObject(player2);
				
				//Test if end of game
				if(player1.getPlayerGrid().areAllShipsDead()){
					s1Out.writeUTF("loss");
					s2Out.writeUTF("won");
					return;
				}
			}
			turn = !turn;
		}
		
		
	}
	
	public void getMove(int player) throws IOException{
		if(player == 1){
			String move = s1In.readLine();
			String HMTA = "Try Again";
			
			if(isHit(player, move)){
				HMTA = "hit";
			}else{
				HMTA = "miss";
			}
			sendMove(2,move);
			s1Out.writeBytes(HMTA);
		}
		else if(player == 2){
			String move = s2In.readLine();
			String HMTA = "Try Again";
			
			if(isHit(player, move)){
				HMTA = "hit";
			}else{
				HMTA = "miss";
			}
			sendMove(1,move);
			s2Out.writeBytes(HMTA);
		}
	}
	
	public Boolean isHit(int player, String Move){
		//stub
		return true;
	}
	
	public void sendMove(int player, String move) throws IOException{
		if(player == 1){
			s1Out.writeBytes(move);
		}else if(player == 2){
			s2Out.writeBytes(move);
		}
	}	
	
	
	
	public static void main(String[] args) throws Exception{
		System.out.println("Server starting");
		BSServer1 server = new BSServer1();
		server.connectToUsers();
		server.playGame();
	}
}

