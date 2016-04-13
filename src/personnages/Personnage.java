package personnages;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import jeux.Fenetre;

/**
 * 
 * @author Meuleman
 *Class dont les Joueurs et les ennemis h�ritent
 */
public abstract class Personnage{
	protected int tailleCase;
	protected int x,y;//position dans la matrice carte
	protected int posX,posY; //position sur la fen�tre
	Fenetre fen;
	protected ArrayList<Image> sprites = new ArrayList<Image>() ; 
	protected boolean enVie=true;
	protected Image sprite;
	protected int delay;
	protected int pause; 
	protected String nomSprite;
	
	/**
	 * Constructeur par d�faut de Personnage
	 */
	public Personnage(){
		this.tailleCase=10;
		this.x=0;
		this.y=0;
		this.pause=10;
		this.sprites.add(new ImageIcon("images/defaut.png").getImage());
		this.sprite=this.sprites.get(0);
		this.posX=x*tailleCase+tailleCase/8;
		this.posY=y*tailleCase+tailleCase/8;
	}
	
	/**
	 * 
	 * @param fen Fenetre de lancement du jeu
	 * @param couleur Couleur du joueur
	 * @param x	Coordonn�e x sur le labyrinthe
	 * @param y 
	 * @param tailleCase taille d'une case du labyrinthe en pixels
	 * @param delay temps entre deux mouvements
	 */
	public Personnage(Fenetre fen, String nomSprite, int x, int y, int tailleCase, int delay){
		this.tailleCase=tailleCase;
		this.nomSprite=nomSprite;
		for(int i=1;i<=3;i++){
			this.sprites.add(new ImageIcon("images/"+this.nomSprite+"/pos"+i+".png").getImage());
		}		
		this.sprite=this.sprites.get(0);
		this.fen=fen;
		this.x=x;
		this.y=y;
		this.posX=x*tailleCase+tailleCase/8;
		this.posY=y*tailleCase+tailleCase/8;
		this.delay=delay;
		this.pause=delay/tailleCase;
	}
	
	/**
	 * Lance le thread de d�placement
	 * @param dX D�placement de case en x
	 * @param dY En y
	 */
	protected synchronized void deplacer(int dX, int dY){
		if(fen.moveIsOk(x,y,dX,dY)&&(enVie)){
			Thread t = new Thread(new ThreadDeplacer(dX, dY));
			t.start();
		}
	}
	
	//Thread de d�placement
	class ThreadDeplacer implements Runnable{
		int dX, dY;
		
		
		public ThreadDeplacer(int dX, int dY){
			this.dX=dX;
			this.dY=dY;
		}
		
		public synchronized void run(){
			x+=dX;
			y+=dY;
			for(int i=0; i<tailleCase; i++){
				posX+=dX;
				posY+=dY;
				sprite=sprites.get(i%3);
				fairePause(pause);
			}
		}
	}
	
	//thread de mort
	class ThreadMourir implements Runnable{
		int ind;
		
		public ThreadMourir(int ind){
			this.ind=ind;
		}
		
		public void run(){
			for(int i=3;i<8;i++){
				sprite=sprites.get(i);
				for(int j=0;j<3;j++){
					sprites.set(j, sprites.get(i));
				}
				fairePause(delay);
			}
			fen.retirerJoueur(ind);			
		}
	}
	
	/**
	 * fonction qui lance la mort d'un personnage
	 * @param ind indice du joueur � tuer
	 */
	public void mourir(int ind){
		enVie=false;
		Thread t = new Thread(new ThreadMourir(ind));
		t.start();
	}
	
	private void fairePause(int temps){
		try {
			Thread.sleep(temps);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * red�finition de la m�thode toString pour l'affichage
	 */
	public String toString(){
		return nomSprite;
	}
	
	/**
	 * 
	 * @return Position posX du personnage en pixel
	 */
	public int getPosX(){
		return posX;
	}
	
	/**
	 * 
	 * @return Position posY du personnage en pixel
	 */
	public int getPosY(){
		return posY;
	}
	
	public boolean getEnVie(){
		return enVie;
	}
	
	/**
	 * 
	 * @return Position x du personnage en case
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * 
	 * @return Position y du personnage en case
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * 
	 * @return sprite du personnage
	 */
	public Image getSprite(){
		return sprite;
	}

}

