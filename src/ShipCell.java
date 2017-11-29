import java.io.Serializable;

//Holds data about a playerGrid square
public class ShipCell implements Serializable{
	private Ship ship; 
	private int shipIndex;
	private boolean wasAttacked;
	private char squareIcon;
	public ShipCell(Ship ship,int shipIndex){
		this.ship = ship;
		wasAttacked = false;
		squareIcon = '~';
	}
	
	public char getSquareIcon(){
		//If no ship place water
		if(ship==null&&wasAttacked){
			squareIcon='M';
			return squareIcon;
		}
		else if(ship==null)
			return squareIcon;
		
		//If ship, then place O if not hit and X if hit.
		if(!wasAttacked){
			squareIcon='O';
			return squareIcon;
		}
		else{
			squareIcon='X';
			return squareIcon;
		}
	}
	
	public boolean containsShip(){
		return !(ship==null);
	}
	
	public void attacked(){
		wasAttacked = true;
	}
	
	public Ship getShip(){
		return ship;
	}
	
	public int getShipIndex(){
		return shipIndex;
	}
}
