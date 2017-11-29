import java.io.Serializable;

//This grid holds allows the player to mark where they have shot at. Filled with Hits or Misses from the player
public class AttackGrid implements Serializable{
	private char[][] attackGrid;
	
	public AttackGrid(){
		attackGrid = new char[10][10];
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				attackGrid[i][j]='~';
			}
		}
	}
	
	public void markAttack(int x,int y,boolean hit){
		if(hit)
			attackGrid[x][y]='H';
		else
			attackGrid[x][y]='M';
	}
	
	public void printAttackGrid(){
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				System.out.print(attackGrid[i][j]);
				if(j==9) System.out.println();
			}
		}
	}
	
	
}
