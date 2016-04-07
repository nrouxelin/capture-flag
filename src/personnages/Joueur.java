package personnages;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import jeux.Fenetre;

/**
 * Class héritant de Personnage
 * @author Meuleman
 * Représente un personnage controlable par un joueur
 */
public class Joueur extends Personnage {
	int up, left, right, down;
	
	/**
	 * Constructeur de Joueur
	 * @param fen
	 * @param couleur
	 * @param x
	 * @param y
	 * @param tailleCase
	 * @param delay
	 * @param up Code de la touche qui permettra de déplacer le personnage vers le haut
	 * @param down Vers le bas
	 * @param left À gauche
	 * @param right À droite
	 */
	public Joueur(Fenetre fen, Color couleur, int x, int y, int tailleCase, int delay, int up, int down, int left, int right){
		super(fen, couleur, x,y,tailleCase, delay);
		this.fen.addJoueur();
		this.up=up;
		this.down=down;
		this.right=left;
		this.left=right;
		this.fen.addKeyListener(new ClavierListener());
	}

	/**
	 * 
	 * @author Meuleman
	 * Permet de contrôler le personnage avec le clavier
	 */
	class ClavierListener implements KeyListener{
		public void keyReleased(KeyEvent arg0){}

		public void keyTyped(KeyEvent arg0){}

		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==left){
				deplacer(1,0);
			}else if(e.getKeyCode()==down){
				deplacer(0,1);
			}else if(e.getKeyCode()==right){
				deplacer(-1,0);
			}else if(e.getKeyCode()==up){
				deplacer(0,-1);
			}
		}
	}
}
