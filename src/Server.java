import java.net.*;
import java.io.*;
public class Server {
    public String p1;
    public String p2;
    //public Board p1_Board;
   // public Board p2_Board;

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
        s2In = new DataInputStream( s2.getInputStream());
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

    public String getMove(boolean player) throws IOException{
        if(player){
            return s1In.readUTF();
        }
        else {
            return s2In.readUTF();
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

        Server sv = new Server(4327);
        System.out.println(sv.getMove(true));
        System.out.println(sv.getMove(false));
        sv.close();
    }
}