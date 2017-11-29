import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Test {
	
	public static void main(String[] args) throws UnknownHostException, IOException{
		Socket socket = new Socket("10.200.177.217", 4326);
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		dos.writeByte(1);
		
	}
}
