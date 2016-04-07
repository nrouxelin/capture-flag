package personnages;
import java.awt.Color;

import jeux.Fenetre;

/**
 * 
 * @author Meuleman
 *Class dont les Joueurs et les ennemis héritent
 */
public abstract class Personnage{
	protected int tailleCase;
	protected int x,y;//position dans la matrice carte
	protected int posX,posY; //position sur la fenêtre
	Fenetre fen;
	protected Color couleur; //Couleur du personnage
	protected int delay;
	protected int pause; 
	
	/**
	 * Constructeur par défaut de Personnage
	 */
	public Personnage(){
		this.tailleCase=10;
		this.x=0;
		this.y=0;
		this.pause=10;
		this.couleur=Color.white;
		this.posX=x*tailleCase+tailleCase/4;
		this.posY=y*tailleCase+tailleCase/4;
	}
	
	/**
	 * 
	 * @param fen Fenetre de lancement du jeu
	 * @param couleur Couleur du joueur
	 * @param x	Coordonnée x sur le labyrinthe
	 * @param y 
	 * @param tailleCase taille d'une case du labyrinthe en pixels
	 * @param delay temps entre deux mouvements
	 */
	public Personnage(Fenetre fen, Color couleur, int x, int y, int tailleCase, int delay){
		this.tailleCase=tailleCase;
		this.couleur=couleur;
		this.fen=fen;
		this.x=x;
		this.y=y;
		this.posX=x*tailleCase+tailleCase/4;
		this.posY=y*tailleCase+tailleCase/4;
		this.delay=delay;
		this.pause=delay/tailleCase;
	}
	
	/**
	 * Lance le thread de déplacement
	 * @param dX Déplacement de case en x
	 * @param dY En y
	 */
	protected synchronized void deplacer(int dX, int dY){
		if(fen.moveIsOk(x,y,dX,dY)){
			Thread t = new Thread(new ThreadDeplacer(dX, dY));
			t.start();
		}
	}
	
	//Thread de déplacement
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
				try {
					Thread.sleep(pause);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
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
	 * @return Couleur du personnage
	 */
	public Color getCouleur(){
		return couleur;
	}

}

