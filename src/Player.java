import java.io.Serializable;

//The player controls his boards. He controls a playergrid, which has his ships, and an enemy board, which allows him to mark down where his ships are.
public class Player implements Serializable{
	private PlayerGrid playersPlayerGrid;
	private AttackGrid playersAttackGrid;
	private String name;
	
	Player(String name){
		playersPlayerGrid = new PlayerGrid();
		playersAttackGrid = new AttackGrid();
	}
	
	public void attackOpponent(int x,int y){
		
	}
	
	
	public boolean placeShip(Ship aShip, Board.Orientation shipOrientation, int x, int y){
		try{
			return playersPlayerGrid.placeShip(aShip, shipOrientation, x, y);
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Cannot place ship there.");
			return false;
		}
	}
	
	public PlayerGrid getPlayerGrid(){
		return playersPlayerGrid;
	}
	
	public AttackGrid getAttackGrid(){
		return playersAttackGrid;
	}
	
	/** When the opponent attacks, this method is called. If hit, return true,else return false.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean incomingAttack(int x, int y){
		boolean hit = playersPlayerGrid.opponentAttacked(x, y);
		return hit;
	}
	
	/** When player attacks, mark if hit or not in their grid
	 * 
	 * @param x
	 * @param y
	 * @param hit
	 */
	public void attack(int x,int y,boolean hit){
		playersAttackGrid.markAttack(x, y, hit);
	}
	
}
