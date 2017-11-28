	
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;


public class Test{

	public static void main(String[] arg){
		try {
			WarpClient.initialize("b85de1353fb574754aff2d121d55a0d4ebd3aab19add8717dc986c9b1afbb7ed","5ab30a257a9741222d37558f3a04f17a38c5956ec4c5933839ecb6c133f086bc");
			WarpClient myGame = WarpClient.getInstance();   
			myGame.addConnectionRequestListener(new MyConnectionListener());   
			WarpClient.getInstance().connectWithUserName("Jonathan");  
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}