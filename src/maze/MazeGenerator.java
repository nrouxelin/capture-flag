/**
 * 
 */
package maze;
import java.util.*;

/**
 * @author nat
 *
 */
public class MazeGenerator {
	
	//Attributs
	private int lignes;
	private int colonnes;
	private byte[][] maze;
	
	
	class Coordonnees{
		private int x;
		private int y;
		
		public Coordonnees(int x, int y){
			this.x = x;
			this.y = y;
		}
		/**
		 * Redéfinit les coordonees stockées en mémoire
		 * @param x
		 * @param y
		 */
		public void set(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		public int getX(){
			return this.x;
		}
		
		public int getY(){
			return this.y;
		}
	}
	
	/**
	 * COnstructeur de MazeGenerator
	 * @param lignes
	 * @param colonnes
	 */
	public MazeGenerator(int lignes, int colonnes){
		this.lignes = lignes;
		this.colonnes = colonnes;
		this.maze = new byte[lignes][colonnes];
		
		
		generateMaze(0,0);
		
	}
	
	private void generateMaze(int dx, int dy){
		boolean[] voisins = {false,false,false,false};
		ArrayList<Integer> choix = new ArrayList<Integer>();
		choix.add(0);
		choix.add(1);
		choix.add(2);
		choix.add(3);
		int i = 0;
		Stack<Coordonnees> pile = new Stack<Coordonnees>();
		Coordonnees cellule = new Coordonnees(dx, dy);
		pile.push(cellule);
		while(!pile.empty()){
			cellule = pile.peek();
			int lig = cellule.getY();
			int col = cellule.getX();
			//System.out.println(lig+" "+col);
			//On visite la case courante
			if((this.maze[lig][col] & 16) == 0){
				this.maze[lig][col] += 16;
			}

			//Recherche les voisins non visités
			voisins[0] = wasVisited(col,lig-1);
			voisins[1] = wasVisited(col,lig+1);
			voisins[2] = wasVisited(col-1,lig);
			voisins[3] = wasVisited(col+1,lig);

			if(voisins[0] && voisins[1] && voisins[2] && voisins[3]){
				pile.pop();
			}else{
				Collections.shuffle(choix);
				i = 0;
				while(voisins[choix.get(i)]){
					i++;
				}
				switch(choix.get(i)){
				case 0:
					this.maze[lig][col] += 2;
					this.maze[lig-1][col] += 1;
					cellule.set(col,lig-1);
					break;
					
				case 1:
					this.maze[lig][col] += 1;
					this.maze[lig+1][col] += 2;
					cellule.set(col,lig+1);
					break;
					
				case 2:
					this.maze[lig][col] += 4;
					this.maze[lig][col-1] += 8;
					cellule.set(col-1,lig);
					break;
					
				case 3:
					this.maze[lig][col] += 8;
					this.maze[lig][col+1] += 4;
					cellule.set(col+1, lig);
					break;
				}
				pile.push(cellule);
				//System.out.println("Cellule choisie :"+cellule.getX()+" "+cellule.getY());
			}

		}
	}
	

	
	private boolean belongsToMaze(int x, int y){
		return (x >= 0) && (x <= this.colonnes-1) && (y >= 0) && (y <= this.lignes-1);
	}
	
	private boolean wasVisited(int x, int y){
		return (belongsToMaze(x,y) && (this.maze[y][x] & 16)!=0) || !belongsToMaze(x,y);
	}
	
	public void display(){
		for(int lig=0; lig<this.lignes; lig++){
			for(int col=0;col<this.colonnes; col++){
				System.out.print((this.maze[lig][col] & 2) == 0 ? "+---": "+   ");
			}
			System.out.println("+");
			for(int col=0;col<this.colonnes; col++){
				System.out.print((this.maze[lig][col] & 4) == 0 ? "|   ": "    ");
			}
			System.out.println("|");
			
		}
		for(int col=0;col<this.colonnes; col++){
			System.out.print("+---");
		}
		System.out.println("+");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MazeGenerator lab = new MazeGenerator(8,8);
		lab.display();
	}

}
;