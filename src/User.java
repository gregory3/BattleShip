import java.net.*;
import java.util.Scanner;
import java.io.*;
public class User {
	private Board board;
	private String serverIP = "10.200.177.217";
	Socket socket;
	Scanner inFromUser;
	DataInputStream inFromServer;
	DataOutputStream s1Out;
	
	public User(Board board, int port) throws Exception{
		this.board = board;
		this.connectToServer(serverIP, port);
	}
	
	/**
	 * connects to server
	 */
	private void connectToServer(String IP, int port) throws Exception{
		socket = new Socket(IP, port);
		inFromUser = new Scanner( System.in);
		s1Out = new DataOutputStream( socket.getOutputStream());
		inFromServer = new DataInputStream(socket.getInputStream());
	}
	
	/**
	 * send a string to the server of the user inputed location
	 * checks valid move
	 */
	public void makeMove() throws IOException{
		//while(true){
			System.out.println("Where would you like to fire your missle?");
			String move = inFromUser.nextLine();
			
			s1Out.writeBytes(move);
			String HMTA = inFromServer.readLine(); //hit miss try again message
			if(HMTA == "Hit" || HMTA == "Miss" ){
				System.out.println(HMTA);
				//break;
			}
		//}
	}
	public void close() throws Exception{
		socket.close();
		inFromUser.close();
		s1Out.close();
		inFromUser.close();
	}
	/**
	 * Set board
	 * while loop
	 * turn and wait for other player 
	 */
	public void playGame(){
		//STUB
	}
	
	public static void main(String[] args) throws Exception{
		User me = new User(new Board(),6678);
		me.s1Out.writeByte(1);
		//me.makeMove();
		me.close();
	}
	
	
}
