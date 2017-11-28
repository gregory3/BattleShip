
import java.net.*; 
import java.io.*;
public class Server {
	public String p1;
	public String p2;
	public Board server_Board;
	public Player player1;
	public Player player2;
	
	ServerSocket s;
	Socket s1;
	Socket s2;
	BufferedReader s1In;
	DataOutputStream s1Out;
	BufferedReader s2In;
	DataOutputStream s2Out;
	
	
	public boolean connectToUsers(String p1, String p2) throws Exception{
		s = new ServerSocket(6789);
		s1 = s.accept();
		BufferedReader s1In = new BufferedReader(new InputStreamReader( s1.getInputStream()));
		DataOutputStream s1Out = new DataOutputStream( s1.getOutputStream());
		s2 = s.accept(); //different port??
		BufferedReader s2In = new BufferedReader(new InputStreamReader( s2.getInputStream()));
		DataOutputStream s2Out = new DataOutputStream( s2.getOutputStream());
		return true;
	}
	
	
	/** Sends and recieves data from clients to play game
	 * @author bakkerp
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void playGame() throws IOException, ClassNotFoundException{
		//Send data to clients
		DataOutputStream s2Out = new DataOutputStream( s2.getOutputStream());
		DataOutputStream s1Out = new DataOutputStream( s1.getOutputStream());
		//Send objects to clients
		ObjectOutputStream s1OutObject = new ObjectOutputStream(s1.getOutputStream());
		ObjectOutputStream s2OutObject = new ObjectOutputStream(s2.getOutputStream());
				
		//Get data from clients
		DataInputStream s1In = new DataInputStream(s1.getInputStream());
		DataInputStream s2In = new DataInputStream(s2.getInputStream());
		//Receive objects from clients
		ObjectInputStream s1InObject = new ObjectInputStream(s1.getInputStream());
		ObjectInputStream s2InObject = new ObjectInputStream(s2.getInputStream());
		

		//Initialize Game
		player1 = new Player("Player 1");
		player2 = new Player("Player 2");
		//Ship Placement Mode
		//Server sends message to both players to place ship
		s1Out.writeUTF("Server: Place Ships");
		s2Out.writeUTF("Server: Place Ships");
		//Server gets player objects from both users
		player1 = (Player) s1InObject.readObject();
		player2 = (Player) s2InObject.readObject();
		
		
		//Play Game
		boolean turn=true; //true if player1 turn, false if player2 turn
		
		//game will run in a loop. need to add condition for end of game
		while(true){
			//If player 1 turn
			if(turn){
				//Tell player 1 to make move
				s1Out.writeUTF("move");
				String player1Move = s1In.readUTF();
				//Get attack coordinates
				int x = Integer.parseInt(player1Move.substring(0,0));
				int y = Integer.parseInt(player1Move.substring(1,1));
				
				//Modify player2 object after being attacked and send to user2
				boolean isHit = player2.incomingAttack(x, y);
				s2OutObject.writeObject(player2);
				//Modify player1 object after attacking and send to player 1
				player1.attack(x, y, isHit);
				s1OutObject.writeObject(player1);
			}
			//Player 2 turn
			else{
				while(true){
					//Tell player 2 to make move
					s2Out.writeUTF("move");
					String player2Move = s2In.readUTF();
					//Get attack coordinates
					int x = Integer.parseInt(player2Move.substring(0,0));
					int y = Integer.parseInt(player2Move.substring(1,1));
					
					//Modify player 1 object after being attacked and send to user 1
					boolean isHit = player1.incomingAttack(x, y);
					s2OutObject.writeObject(player1);
					//Modify player 2 object after attacking and send to player 2
					player2.attack(x, y, isHit);
					s1OutObject.writeObject(player2);
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
	
	
	
	public static void main(String[] args){
		
	}
}
