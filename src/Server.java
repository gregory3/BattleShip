import java.net.*; 
import java.io.*;
public class Server {
	public String p1;
	public String p2;
	public Board p1_Board;
	public Board p2_Board;
	
	ServerSocket s;
	Socket s1;
	Socket s2;
	DataInputStream s1In;
	DataOutputStream s1Out;
	DataInputStream s2In;
	DataOutputStream s2Out;
	
	public Server(int port) throws Exception{
		this.connectToUsers(port);
	}
	
	public boolean connectToUsers(int port) throws Exception{
		s = new ServerSocket(port);
		s1 = s.accept();
		s1In = new DataInputStream( s1.getInputStream());
		s1Out = new DataOutputStream( s1.getOutputStream());
		s2 = s.accept(); //different port??
		s1In = new DataInputStream( s1.getInputStream());
		s2Out = new DataOutputStream( s2.getOutputStream());
		return true;
	}
	
	public void close() throws Exception{
		s.close();
		s1.close();
		s2.close();
		s1In.close();
		s2In.close();
		s1Out.close();
		s2Out.close();
	}
	
	
	public void playGame(){
		//Stub
	}
	
	public void getMove(boolean player) throws IOException{
		if(player){
			String move = s1In.readLine();
			System.out.println(move);
			String HMTA = "Try Again";
			
			if(isHit(player, move)){
				HMTA = "hit";
			}else{
				HMTA = "miss";
			}
			sendMove(false,move);
			s1Out.writeBytes(HMTA);
		}
		else {
			String move = s2In.readLine();
			String HMTA = "Try Again";
			
			if(isHit(player, move)){
				HMTA = "hit";
			}else{
				HMTA = "miss";
			}
			sendMove(true,move);
			s2Out.writeBytes(HMTA);
		}
	}
	
	public Boolean isHit(boolean player, String Move){
		//stub
		return true;
	}
	
	public void sendMove(boolean player, String move) throws IOException{
		if(player){
			s1Out.writeBytes(move);
		}else{
			s2Out.writeBytes(move);
		}
	}
	
	
	
	public static void main(String[] args) throws Exception {
		
		Server s = new Server(6678);
		int	i = s.s1In.readInt();
			System.out.println(i);
		//s.getMove(true);
		s.close();
	}
}
