import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.LinkedList;

import javax.sound.sampled.*;
import sun.audio.*;
 
public class CursorPanel{
       
	private int x, y;
    private int gunNum = 0; 
    private int imgWidth = 500;
	private int imgHeight = 500;
	private boolean reload=false;
	private Point punto = null;
	private SurfaceJeu sj;
	private BufferedImage arme = null;
    private BufferedImage vise = null;
     
   public CursorPanel(SurfaceJeu sj){
      this.sj=sj;
      
		try{
			vise = ImageIO.read(new File("media//sight.png"));
		} catch(IOException io){io.printStackTrace();}
	}
	public void setX(int a){x = a;}
    public void setY(int b){y = b;}
    public Point getPunto(){
		return punto;
	}
	public String getGunName(){
		String nom="";
		switch(gunNum){
			case 0 : nom="HK-MP5"; break;
			case 1 : nom="COD-PeaceKeeper"; break;
			case 2 : nom="Shotgun"; break;
			case 3 : nom="Pistol"; break;
			case 4 : nom="Sniper Rifle"; break;
			case 5 : nom= "AK-47"; break;
			default: nom="";break;
		}
		return nom;
	}
	public int getGunIndex(){
	    return gunNum;
	}
	public int getShootRadio(){
	    if(gunNum!=2 & gunNum!=4) return 5;
	    if(gunNum==4) return 10;
	    else return 20;
	}
	//Methode qui dit si une arme peut tirer en mode automatique
	public boolean isAutomatic(){
	    return gunNum!=2 && gunNum!=3 && gunNum!=4;
	}
    
    //Methodes qui obtient la coordonnes X et Y des positions d ela souris
	   public void click(Point circle){
			punto = circle;
	   }
	  //Methode qui change l'index de l'arme
	  public void gunChange(){
		if (gunNum==5) gunNum = 0;
		else gunNum++;
	 }
	  //Methode qui change l'orientation des armes
	  public void gunOrientChange(){
		 int limite = sj.getWidth()/2;
		 if (x>=limite){
			 imgWidth=-500;
		} else {imgWidth = 500;}
	  } 
	  //Methode qui dessine les armes
	  public void dessinerArme(Graphics g){
			
            g.drawImage(vise, x - 25, y - 25, 50, 50, null);
            gunOrientChange();
            try{
				arme = ImageIO.read(new File("media//gun"+gunNum+".png"));
			} catch(IOException io){io.printStackTrace();}
			
            int yec = y;
            if (yec > sj.getHeight()/2) yec +=100;
            else yec = sj.getHeight()/2;
			g.drawImage(arme, x - imgWidth/2, yec,imgWidth, imgHeight, null);
	}
}
