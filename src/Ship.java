
public class Ship {
	private int shipLength;	 //Length of ship
	private boolean [] ship; //If spot is hit, then true, if spot is not hit, then false
	boolean isAlive;
	ShipType shipType;
	
	/** Enumerator for ship type
	 *  @since 10/29/2017
	 *  @author pascal
	 */
	public enum ShipType{
		CARRIER, BATTLESHIP,CRUISER,SUBMARINE,DESTROYER;
	}
	
	public Ship(){}
	
	/** Constructor
	 * @since 10/29/2017
	 * @author pascal
	 */
	public Ship(ShipType shipType){
		switch(shipType){
		//Determines ship length
		case CARRIER: 
			shipLength = 5;
			ship = new boolean[shipLength];
			break;
		case BATTLESHIP:
			shipLength = 4;
			ship = new boolean[shipLength];
			break;
		case CRUISER:
			shipLength = 3;
			ship = new boolean[shipLength];
			break;
		case SUBMARINE:
			shipLength = 3;
			ship = new boolean[shipLength];
			break;
		case DESTROYER:
			shipLength=2;
			ship = new boolean[shipLength];
			break;
		default:
			throw new NullPointerException("Ship must have a type");
		}
		
		//Sets all spots of the ship to not hit
		for(int i=0;i<shipLength;i++)
			ship[i]=false;
		
		isAlive=true;
		this.shipType=shipType;
	}
	
	/** Marks that a ship has been hit on a certain spot
	 *  @since 10/29/2017
	 *  @author pascal
	 *  @param shipIndex the spot where the ship has been hit
	 */
	public void hitShip(int shipIndex){
		try{
			ship[shipIndex]=true; //Mark that ship
			
			//If ship is hit in all its spots, destroy ship
			for(int i=0;i<shipLength;i++){
				if(!ship[i])
					return;
			}
			isAlive = false;
			//Method to destroy ship
			
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.err.println("Invalid ship index: "+e);
		}
	}
	
	public boolean[] getShipArray(){
		return ship;
	}
	
	public int shipLength(){
		return shipLength;
	}
	
	public ShipType getShipType(){
		return shipType;
	}
	
	public boolean isAlive(){
		return isAlive;
	}
}
