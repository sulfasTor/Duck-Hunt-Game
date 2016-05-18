import javax.swing.*;
import java.awt.*;
import java.lang.Math.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
import java.awt.event.*;
import javax.sound.sampled.*;

class Cible {
	private int taille; //diametre de la cible
	private int duree; //longueur de temps (en millisecondes) que la cible reste active
	private int tReste; //temps restant (en secondes) avant que la cible se desactive
	private int valeur; //nombre de points que vaut cette cible
	private boolean actif; //vrai si actif
	private boolean detruit; //vrai si detruit, est verifie a chaque tir
	private boolean aleteo = false;
	private boolean repetion = true;
	private int[] position; //{abcisse, ordonnée} du point le plus en haut, à gauche de la cible
	private int numCible; //numero qui repere individuellement chaque cible
	private BufferedImage ducks[] = new BufferedImage[4];
	private BufferedImage mort=null;
	private Timer t = null;
	private final int duck;
	private Clip clip;
	
	private static int nbCibles = 0; //nombre total de cible crées
	
	//Constructeur
	public Cible(int x, int y, int t, int d, int v){
		position = new int[2];
		position[0] = x;
		position[1] = y;
		
		taille = t;
		duree = d;
		tReste = d*1000;
		valeur = v;
		
		nbCibles++;
		numCible = nbCibles;
		actif = false; //les cibles sont crées inactives
		detruit = false;
		
		duck = (int)(Math.random()*2);
		try{
			for(int i =0;i<ducks.length;i++) ducks[i] = ImageIO.read(new File("media//duck"+i+".png"));
		} catch(IOException io){io.printStackTrace();}
		
		try{
			mort = ImageIO.read(new File("media//dead1.png"));
		} catch(IOException io){io.printStackTrace();}
	}
	
	//geteurs:
	public int[] getPos(){ return position;}
	public int[] getPosCentre(){
		int tempPos[] = { (position[0] + (taille*10/2) ), (position[1] + (taille*10/2) ) };
		return tempPos;
	}
	public int getXAncrage(){ return position[0];}
	public int getYAncrage(){ return position[1];}
	public int getTaille(){ return taille;}
	public int getDuree(){ return duree;}
	public int gettReste(){ return tReste;}
	public int getValeur(){ return valeur;}
	public int getNum(){ return numCible; }
	public boolean getActif(){ return actif;}
	public boolean getDetruit(){ return detruit;}
	
	//seteurs
	public void setX(int x){ position[0] = x; }
	public void setY(int y){ position[1] = y; }
	public void setTaille(int t){taille = t; }
	public void setValeur(int v){valeur = v; }
	
	
	public void activer(){ if(!detruit) actif = true;}
	public void detruire(){ detruit = true; actif = false; }
	
	//fonction tir
	public boolean tir(int x, int y, int radio){
		boolean hit = false;
		if( Math.pow( (getPosCentre()[0] - x), 2) + Math.pow( (getPosCentre()[1] - y), 2) <= Math.pow(taille*radio, 2) ){
			detruire();
			hit = true;
		}
		return hit;
	}
	ActionListener listener = new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
				mort = null;
                t.stop();
             }
        };
	
	public void dessiner(Graphics g){
		
		if(actif) {
			if(aleteo) {
				g.drawImage(ducks[duck],position[0],position[1],taille*10,taille*10,null);
				aleteo = false;
			} else{
				g.drawImage(ducks[duck+2],position[0],position[1],taille*10,taille*10,null);
				aleteo = true;
			}
		} else if(detruit&&repetion){
			repetion=false;
			g.drawImage(mort,position[0],position[1],taille*10,taille*10,null);
			t = new Timer(200, listener);
			t.start();
		}
	}
	
	//next() conçu pour être appellé toutes les secondes
	public void next(){
		if(actif){
			if(tReste > 0){
				tReste = tReste - 1000;
			}
			else{ actif = false; }
		}
	}
	
	//nextMillis() conçu pour être appellé à chaque milliseconde
	public void nextMillis(){
		if(actif){
			if(tReste > 0){
				tReste--;
			}
			else{ actif = false; }
		}
	}
	
	//réinitialise la cible
	public void reset(){
		tReste = duree*1000;
		actif = false;
		detruit = false;
	}
	
	public String toString(){
		String conditionnelle;
		if(detruit) conditionnelle = "détruite";
		else if(actif) conditionnelle = "active";
		else conditionnelle = "désactivée";
		
		return "Cible #"+numCible+" "+conditionnelle+" en ("+position[0]+", "+position[1]+") dure "+duree+" secondes et vaut "+valeur+" points. Il reste "+(tReste/1000)+" secondes.";
	}
	
	public boolean equals(Object o){
		if(o == null) return false;
		if(o instanceof Cible){
			Cible c = (Cible)o;
			return ( position[0] == c.getPos()[0] && position[1] == c.getPos()[1] && taille == c.getTaille() && duree == c.getDuree() && valeur == c.getValeur() && actif == c.getActif() && detruit == c.getDetruit() );
		}
		return false;
	}
}
