package jeux;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import maze.MazeGenerator;
import personnages.Personnage;

/**
 * 
 * @author Meuleman
 * Panneau sur lequel tout sera dessiné
 */
public class Panneau extends JPanel{

	private static final long serialVersionUID = 1L;
	int tailleCase;
	BufferedImage buffImg;
	Graphics2D gbi;
	ArrayList<Personnage> tabPers;
	
	/**
	 * Constructeur de Panneau, stocke l'image de fond du labyrinthe dans buffImg puis l'affiche avec les personnages
	 * @param tailleCase Taille d'une case du labyrinthe en pixels
	 * @param nbCases nombre de cases de la longueur du labyrinthe
	 * @param tailleFenetre Taille de la longueure du labyrinthe en pixels
	 * @param tabP Tableau contennant tous les personnages en jeu
	 * @param lab Le labyrinthe au format MazeGenerator
	 * @param flag Position du drapeau
	 */
	public Panneau(int tailleCase, int nbCases, int tailleFenetre, ArrayList<Personnage> tabP, MazeGenerator lab, int[] flag){
		this.tailleCase=tailleCase;
		buffImg = new BufferedImage(tailleFenetre , tailleFenetre,BufferedImage.TYPE_INT_ARGB);
		gbi = buffImg.createGraphics();
		gbi.setPaint(new Color(30,0,50));
		gbi.fillRect(0, 0, tailleFenetre, tailleFenetre);
		genererCarte(nbCases, lab, flag);
		rafraichir(tabP);
	}
	
	/**
	 * Dessine la carte de fond du labyrinthe
	 * @param nbCases nombre de cases de la longueur du labyrinthe
	 * @param lab Le labyrinthe au format MazeGenerator
	 * @param flag Position du drapeau
	 */
	private void genererCarte(int nbCases, MazeGenerator lab, int[] flag){
		int x,y;
		gbi.setPaint(Color.gray);
		for(int j=0;j<nbCases;j++){
			for(int i=0;i<nbCases;i++){
				x=i*tailleCase;
				y=j*tailleCase;
				if(lab.isClosedOnLeft(i, j)){
					gbi.drawLine(x, y, x, y+tailleCase);
				}
				if(lab.isClosedOnTop(i, j)){
					gbi.drawLine(x, y, x+tailleCase, y);
				}
			}
		}
		gbi.setPaint(Color.orange);
		gbi.fillRect(flag[0]*tailleCase+tailleCase/4, flag[1]*tailleCase+tailleCase/4, tailleCase/2, tailleCase/2);
	}
	
	/**
	 * Redessine le panneau
	 * @param tabP Tableau de tous les personnages en jeu
	 */
	public synchronized void rafraichir(ArrayList<Personnage> tabP){	
		this.tabPers=tabP;
		this.repaint();
	}
	
	/**
	 * La façon dont les éléments doivent être dessinés sur le panneau
	 */
	public synchronized void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.drawImage(buffImg, 0, 0, null);
		int tp1=tailleCase/2;
		for(Personnage p : tabPers){
			g2d.setColor(p.getCouleur());
			g2d.fillOval(p.getPosX(), p.getPosY() , tp1, tp1);
		}
		
	}
}
	

