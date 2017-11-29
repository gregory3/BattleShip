import java.net.*;
import java.util.Scanner;
import java.io.*;
public class User {
   // private Board board;
    private String serverIP = "10.200.177.217";
    Socket socket;
    Scanner inFromUser;
    DataInputStream inFromServer;
    DataOutputStream outToServer;

    public User( int port) throws Exception{
       // this.board = board;
        this.connectToServer(serverIP, port);
    }

    /**
     * connects to server
     */
    private void connectToServer(String IP, int port) throws Exception{
        socket = new Socket(IP, port);
        inFromUser = new Scanner( System.in);
        outToServer = new DataOutputStream( socket.getOutputStream());
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

        outToServer.writeBytes(move);
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
        outToServer.close();
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
        int p = 4327;
        User me = new User(p);
        User you = new User(p);
        me.makeMove();
        you.makeMove();
        me.close();
    }


}
