import java.util.ArrayList;

//This class is used to place all the player's ships. Filled with ships and Hits or Misses from the opponent.
public class PlayerGrid {
	private ShipCell[][] playerGrid;
	private ArrayList<Ship> shipsOnBoard;
	
	//Constructor
	public PlayerGrid(){
		playerGrid = new ShipCell[10][10];
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++)
				playerGrid[i][j]= new ShipCell(null,-1);
		}
		shipsOnBoard = new ArrayList<Ship>();
	}
	
	public enum Orientation{
		HORIZONTAL,VERTICAL;
	}
	
	
	/** Place a ship on the playerGrid
	 * @author pascal
	 * @param aShip Ship Object to place
	 * @param shipOrientation The orientation of the ship. 
	 * @param x row to place first index of ship
	 * @param y col to place first index of ship.
	 * @return true if ship was placed, false otherwise
	 */
	public boolean placeShip(Ship aShip, Board.Orientation shipOrientation, int x, int y){
		//Check if ship type is already on player's board. If not,add it to arraylist of ships
		for(int i=0;i<shipsOnBoard.size()-1;i++){
			if(shipsOnBoard.get(i).getShipType().equals(aShip.getShipType()))
				return false;
		}
		shipsOnBoard.add(aShip);
		
		//Check desired orientation. If horizontal, place ship from right to left on board, if vertical, place ship from top to bottom on board.
		/*  Example: a newly placed ship
		 *  Vertical:
	     *  1~~~
		 *  O~~~
		 *  O~~~
		 *  O~~~
		 *  Horizontal:
	     *  1OOO
		 *  ~~~~
		 *  ~~~~
		 *  ~~~~
		 * With 1 being the starting index and 0 being part of the ship. Both 1 and 0 are part of the ship.
		 */
		ShipCell[][] placeHolderGrid=playerGrid;
		
		switch(shipOrientation){
			case HORIZONTAL:
				for(int i=0;i<aShip.shipLength();i++){
					if(playerGrid[x][y+i].getSquareIcon()=='O'||playerGrid[x][y+i].getSquareIcon()=='X'){
						playerGrid = placeHolderGrid;
						return false;
					}
					playerGrid[x][y+i]=new ShipCell(aShip,i);
				}
				
				
				break;
			case VERTICAL:
				for(int i=0;i<aShip.shipLength();i++){
					if(playerGrid[x+i][y].getSquareIcon()=='O'||playerGrid[x+i][y].getSquareIcon()=='X'){
						playerGrid = placeHolderGrid;
						return false;
					}
					playerGrid[x+i][y]=new ShipCell(aShip,i);
				}
				break;
		}
		
		
		return true;
	}
	
	/** Code that is executed when opponent attacks player's grid. If opponent hits ship, then mark that the ship was hit else mark that it was a miss.
	 * @author pascal
	 * @since 10/29/2017
	 * @param x row in playerGrid matrix
	 * @param y col in playerGrid matrix
	 * @return true if opponent hits ship, false otherwise
	 */
	public boolean opponentAttacked(int x,int y){
		ShipCell attackedCell = playerGrid[x][y];
		if(attackedCell.containsShip()){
			//Mark that ship was hit on grid
			playerGrid[x][y].attacked();
			//Mark ship was hit in array. Array is for testing end scenarios
			int shipsOnBoardIndex = shipsOnBoard.indexOf(attackedCell.getShip());
			shipsOnBoard.get(shipsOnBoardIndex).hitShip(attackedCell.getShipIndex());
			return true;
		}else{
			//If it hits nothing, mark that square was attacked.
			playerGrid[x][y].attacked();
			return false;
		}
			
	}
	
	/**
	 * @author pascal
	 * @since 10/29/2017
	 * @return true if all ships are completely destroyed, else false
	 */
	public boolean areAllShipsDead(){
		for(int i=0;i<shipsOnBoard.size()-1;i++){
			if(shipsOnBoard.get(i).isAlive())
				return false;
		}
		return true;
	}
	
	/** Prints out the playerGrid
	 * @author pascal
	 * @since 10/29/2017
	 */
	public void printPlayerGrid(){
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				System.out.print(playerGrid[i][j].getSquareIcon());
				if(j==9) System.out.println();
			}
		}
	}
	
	public char getSquareSymbol(int x,int y){
		return playerGrid[x][y].getSquareIcon();
	}
}
