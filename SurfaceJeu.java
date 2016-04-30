import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.LinkedList;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
import java.util.Random;


public class SurfaceJeu extends JPanel{
	
	private LinkedList<Cible> listeCibles;
	private BufferedImage scene = null;
	private CursorPanel cp;
	private Timer t_Sec = new Timer(100, new nextClass(this) ); //chaque seconde
	private Timer t_Millis = new Timer(10, new nextMillisClass(this) ); //chaque milliseconde
	private int score = 0;
	private int nbCibles = 2;
	private int nbCiblesMobiles = 0;
	private int ciblesRestantes=-1;
	private int radioArme;
	
	private Image dbImage;
	private Graphics dbg;
	
	TypeMouvement trajT1 = TypeMouvement.DROITE_DROITE;
	TypeMouvement trajT2 = TypeMouvement.DROITE_GAUCHE;
	TypeMouvement trajT3 = TypeMouvement.DROITE_HAUT;
	TypeMouvement trajT4 = TypeMouvement.DROITE_BAS;
	TypeMouvement trajD1 = TypeMouvement.DIAG_MONTE_GAUCHE;
	TypeMouvement trajD2 = TypeMouvement.DIAG_MONTE_DROITE;
	TypeMouvement trajD3 = TypeMouvement.DIAG_BAISSE_GAUCHE;
	TypeMouvement trajD4 = TypeMouvement.DIAG_BAISSE_DROITE;
	TypeMouvement trajR = TypeMouvement.RANDOM;
	TypeMouvement trajRH = TypeMouvement.RANDOM_HARD;
	TypeMouvement trajCD = TypeMouvement.CERCLE_DIRECT;
	TypeMouvement trajCH = TypeMouvement.CERCLE_HORAIRE;
	TypeMouvement[] tp = {trajT1, trajT2, trajT3, trajT4, trajD1, trajD2, trajD3, trajD4, trajR, trajRH, trajCD, trajCH};
	
	public SurfaceJeu(){
		
		cp = new CursorPanel(this);
		listeCibles = new LinkedList<Cible>();
		
		try{
			scene = ImageIO.read(new File("media//scene.png"));
		} catch(IOException io){io.printStackTrace();}
		
		setLayout(new BorderLayout());
		setOpaque(false);
		setFocusable(true);
		//On fait apparaitre les cannards
		timerStart();
		nextWave(0);
		
	}
	//GETTEURS ET SETTEURS
	public CursorPanel getCursorPanel(){
		return cp;
	}
	public int getScore(){
		return score;
	}
	public int getCiblesRestantes(){
		return ciblesRestantes;
	}
	public void resetCibles(){
		nbCibles=2;
		nbCiblesMobiles=0;
		ciblesRestantes=-1;
	}
	public void nextWave(int wave){
		//CREE LES CIBLES
		if (wave==10) {
			for(int j=0;j<5;j++) {
				int t1=(int)(Math.random()*1100);
				int t2=(int)(Math.random()*500);
				int t3=(int)(4+Math.random()*6);
				listeCibles.add(new CibleMobile(t1, t2, t3,1, 100,trajCD));
				listeCibles.add(new CibleMobile(t1, t2, t3,1, 100,trajCH));
				ciblesRestantes = listeCibles.size();
			}
			
		} else{
			for(int j=0;j<nbCibles;j++) listeCibles.add(randomCible()); //Cree des cibles statiques
			for(int j=0;j<nbCiblesMobiles;j++) listeCibles.add(randomCibleMobile());
			nbCibles++;
			nbCiblesMobiles++;
			ciblesRestantes = listeCibles.size();
			
		}
	}
	public void tir(Point p){
		radioArme = cp.getShootRadio();
		int tempX = (int)p.getX();
		int tempY = (int)p.getY();
		score=0;
		ciblesRestantes=listeCibles.size();
		for(int a = 0; a<listeCibles.size(); a++){
			listeCibles.get(a).tir(tempX, tempY, radioArme);
			if(listeCibles.get(a).getDetruit()){
				score+=listeCibles.get(a).getValeur();
				ciblesRestantes--;
			}
		}
	}
	public boolean gameOver(){
		return ciblesRestantes==0;
	}
	public Cible randomCible(){
		int t1=(int)(Math.random()*1000);
		int t2=(int)(Math.random()*600);
		int t3=(int)(5 + Math.random()*10);
		return new Cible(t1,t2,t3,1,50);
	}
	public CibleMobile randomCibleMobile(){
		
		int hazard = (int)(Math.random()*tp.length);
		int t1=(int)(Math.random()*1000);
		int t2=(int)(Math.random()*600);
		int t3=(int)(5 + Math.random()*10);
		
		return new CibleMobile(t1, t2, t3,1, 100,tp[hazard]);
	}
	
	public void next_Sec(){
		for(int a = 0; a<listeCibles.size(); a++){
			if(a == 0 && !listeCibles.get(0).getDetruit()){
				listeCibles.get(a).activer();
			}
			if(a>0 && (listeCibles.get(a-1).getDetruit() || !listeCibles.get(a).getActif()) ){
				listeCibles.get(a).activer();
			}
		}
		repaint();
	}
	public void next_Millis(){
		for(int a = 0; a<listeCibles.size(); a++){
			listeCibles.get(a).nextMillis();
		}
		repaint();
	}
	
	public void timerStart(){
		t_Sec.start();
		t_Millis.start();
		repaint();
	}
		
	public LinkedList<Cible> getListeCibles(){ return listeCibles;}
	
	//Double buffer
	public void paint(Graphics g){
		dbImage = createImage(getWidth()*2, getHeight()*2);
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage,0,0,this);
		g.drawImage(scene, 0, 0, getWidth(), getHeight(), null);
		for(int a = 0; a<listeCibles.size(); a++){
			listeCibles.get(a).dessiner(g);
		}
		cp.dessinerArme(g);
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		repaint();
	}	
	  
}
