import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.*;
import java.util.LinkedList;
import java.io.*;
//Pour la musique
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.swing.JOptionPane;

public class NeWindow extends JFrame implements ActionListener{
	
    private SurfaceJeu sj; //Surface de jeu
    private CursorPanel cp; //Classe qui dessine les armes
    private JPanel caja; //Conteneur des panneau
    private JPanel infoTab; //Panneal qui contient les infos
    private JButton goBack; //Bouton qui nous renvoi au menu
    private JLabel scoreLabel; //Label qui affiche le score
    private ImageIcon iconRet; 
    private int score=0; //Score du joueur
    private int tempsEcoule=0; //Temps ecoule depuis le debut de la partie
    private int tirs=0; //Tirs effectues dans toute la partie
    private Timer waveTimer; //Timer qui mantient sur scene un message pendant deux seconds
    private Timer timerMAJ; //Timer qui compte le temps ecoule
    private Timer timerResetGame; //timer qui compte 3 seconds et renitialise le jeu
    private String username,password; //Donnees du joueur
    private JPanel resourcesTab;//Paneau qui garde des infos sur l'arme et la munition
    private JLabel switchGun; //Message qui montre le nom de la wave et l'arme
    private JLabel ammo; //label qui affiche la munition restante
    private JTextPane temp; //paneau temporel affiche entre vagues
    private String ballesAffiche; //String qui garde la muniton restante et la munition maximal
    private final int ballesMax = 50; //munition maximale par magasine
    private int balles=0; //munition restante
    private boolean reload=false; //indique si l`arme peut etre recharge
    private int wave=1; //numero de la vague
    private int temps=5; //Temps d'attente pour recommencer la partie
    private SaveScore save; //Classe qui ecrit le score sur un fichier de texte
	
    private Clip clip; //Clip utilise pour reproduire de la musique
   
    public NeWindow(String user, String pass){
	super("Duck Hunt Game");
		
	//Obtention du username du joueur et password
	username = user;
	password = pass;
	save = new SaveScore(username, password);
		
	//SurfaceJeu
	sj = new SurfaceJeu();
	sj.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
	sj.setOpaque(false);
		
	//CursorPanel
	cp = sj.getCursorPanel();
		
	//Texte
	String texte = "<html><font size='6' face='courier' color ='red'><b>SCORE = <font color='green'>"+score+
	    "</font> : DUCKS LEFT = <font color='blue'>"+sj.getCiblesRestantes()+
	    "</font> : ELAPSED TIME = <font color='yellow'>"+tempsEcoule+"</font> : PLAYER = <font color='orange'>"+username+"</font></html>";
	//ScoreLabel
	scoreLabel = new JLabel(texte);
		
	//Button goBack
	iconRet = new ImageIcon("media//quitgame.png");
	goBack = new JButton(iconRet);
	goBack.setBackground(Color.BLACK);
	goBack.setBorder(BorderFactory.createLineBorder(null, 0));
	goBack.setFocusable(false);
		
	//infoTab
	infoTab = new JPanel(new BorderLayout());
	infoTab.setBackground(Color.BLACK);
	infoTab.add(scoreLabel, BorderLayout.WEST);
	infoTab.add(goBack, BorderLayout.EAST);
		
	//ResourcesTab Panel
	resourcesTab = new JPanel(new BorderLayout());
	resourcesTab.setBackground(Color.BLACK);
	switchGun = new JLabel("<html><font color='white' size=6 face='courier'>WAVE "+wave+
			       " - Press A to change weapon : WEAPON = <font color='green'>"+cp.getGunName()+"</font><html>");
	balles = ballesMax;
	ballesAffiche = "<html><font color=green size=6 face=courier>AMMO:"+balles+"/"+ballesMax+"</html>";
	ammo = new JLabel(ballesAffiche);
	resourcesTab.add(switchGun, BorderLayout.WEST);
        resourcesTab.add(ammo, BorderLayout.EAST);
        
        //Caja 
	caja = new JPanel(new BorderLayout());
	caja.add(sj, BorderLayout.CENTER);
	caja.add(infoTab, BorderLayout.NORTH);
	caja.add(resourcesTab, BorderLayout.SOUTH);
		
	//Temp
	temp = new JTextPane();
	temp.setBackground(Color.BLACK);
	temp.setContentType("text/html");
	temp.setEditable(false);
	temp.setHighlighter(null);
		
	//JFRAME
	setContentPane(caja);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setLocationRelativeTo(null);
	setSize(1500,600);
	setExtendedState(MAXIMIZED_BOTH);
	setVisible(true);
		
	//On initialise la mise en jour
	waveTimer=new Timer(2000, this);
	timerResetGame=new Timer(1000, reseterListener);
	timerMAJ = new Timer(1000, miseAJour); 
	timerMAJ.start();
		
	//Ecouteurs
	goBack.addActionListener(new OpenIndex(this));
	sj.addMouseMotionListener(new SourisListener(this));
	sj.addMouseListener(new ShootListener(this));
	sj.addKeyListener(new GunChangerListener(this));
		
    }
    //GETTEURS ET SETTEURS
    public SurfaceJeu getSurfaceJeu(){return sj;}
    public boolean getReload(){return reload;}
    public String getPlayerName(){return username;}
    public String getPlayerPassword(){return password;}
    public int getPlayerScore(){return score;}
    public void setReload(boolean a){reload=a;}
    public Clip getClip(){return clip;}
	
    //METHODE QUI DONNES LES VALEUR DE LA SOURIS POUR LE MOUVEMENT DES ARMES
    public void setXY(int x, int y){
	cp.setX(x);
	cp.setY(y);
    }
    public void click(Point p){
	tirs++;
	cp.click(p);
    }
	
    /*ORDRE LOGIQUE
     * 1. On initialise le timer de  mise en jour qui va augmenter le temps ecoule et va mettre a jour les messages.
     * Elle verifie aussi que la "wave" se termine.
     * 
     * 2. Si la vague est terminee on va endWave(). On arrete le timer de mise a jour.
     * On affiche le message entre vagues et on reproduit un musique en initialisant le timer waveTimer.
     * 
     * 3. Ce waveTimer va laisse passer deux seconds, va s'arreter et va nettoyer l'ecran : cleanScreen()
     * 
     * 4. CleanScreen() va incrementer wave, va remmettre la surface de jeu et va demarrer la vague suivante.
     * Cette methode va remettre en marche le timer de mise en jour. Elle verifie aussi si le jeu est fini cad wave==10.
     * 
     * 5. Si le jeu est fini ,cleanScreen s'en rendra compte et va finir le jeu finirJeu()
     * 
     * 6. finirJeu va calculer le score et initialiser un timer qui va attendre 30 seconds avant de recommencer le jeu.
     * 
     * */
	 
    //Override de l'implementation d'ACTION LISTENER
    public void actionPerformed(ActionEvent ae){
	//On joue la musique de transition entre les vagues
	cleanScreen();
	waveTimer.stop();
    }
    //Methode qui fini la vague et qui initialise la suivante
    public void endWave(){
	timerMAJ.stop();
	if(wave==9) temp.setText("<html><font size='7' face='serif' color ='white'><b>WAVE "+wave+
				 " COMPLETED</b><br>Brace yourself for the final wave...</html>");
	else temp.setText("<html><font size='7' face='courier' color ='white'><b>WAVE "+wave+
			  " COMPLETED</b><br><br>THEY'RE COMING...</html>");
	//On change les Components
	ImageIcon ico = new ImageIcon("media//duck3.png");
	temp.setBorder(BorderFactory.createMatteBorder(getHeight()/3,getWidth()/3,getHeight()/3,getWidth()/3,ico));
	caja.remove(sj);
	caja.add(temp);
	caja.validate();
	playSound("/media//wave.wav");
	//On initialise le timer qui va afficher le message pendant 2 seconds avant de commencer la vague suivante
	waveTimer.restart();
    }
    //Methode qui nettoie l'ecran et remet la surface de jeu
    public void cleanScreen(){
	if(wave==10){
	    finirJeu();
	}
	else{
	    timerMAJ.start();
	    wave++;
	    caja.remove(temp);
	    caja.add(sj);
	    sj.requestFocusInWindow();
	    caja.validate();
	    sj.nextWave(wave);
	}
    }
    //ACTION LISTENER QUI DIMINUE LE TEMPS RESTANTS et met a jour les messages
    ActionListener miseAJour= new ActionListener(){
	    public void actionPerformed(ActionEvent ae){
		tempsEcoule++;
		setScore();
		if(sj.gameOver()){
		    endWave();
		}
	    }
	};
    //ACTION LISTENER QUI RECOMMENCE UNE AUTRE PARTIE
    ActionListener reseterListener = new ActionListener(){
	    public void actionPerformed(ActionEvent ae){
		if(temps==0) {
		    //On arrete de compter
		    timerResetGame.stop();
		    //On renitialise tous les valeur
		    sj.resetCibles();
		    wave=1;
		    temps=5;
		    tirs=0;
		    tempsEcoule=0;
		    score=0;
		    //On renitialise les timers
		    waveTimer.start();
		}
		else  {
		    temps--; 
		    temp.setText("<html><font size='8' face='serif' color ='red'><b>GAME OVER<br>Your final score is:"+score+
				 "<br><font color='white' face='courier'>Replay in "+temps+" secs</font></html>");
		}
	    }
	};
    //Methode qui fini le jeu et recommence une nouvelle partie
    public void finirJeu(){
	//On arrete d'abord les timeurs.
	timerMAJ.stop();
	waveTimer.stop();
	//On calcule le score et on l'enregistre
	calculeScore();
	save.addScore(score);
	playSound("/media//song1.wav");
	//On affiche les score
	temp.setText("<html><font size='8' face='serif' color ='red'><b>GAME OVER<br>Your final score is:"+score+
		     "<br><font color='white' face='courier'>Replay in "+temps+" secs</font></html>");
	//On initialises le timer qui va compter 5 seconds avant de recommencer une partie
	timerResetGame.start();
    }
    //Methode qui calcule le score
    public void calculeScore(){
	int cibles = sj.getListeCibles().size()+10;
	score = score-(int)(tempsEcoule/(4*cibles))-(score/2)*(int)(tirs-cibles);
    }
    //Methode qui met a jour le score et d'autres messages
    public void setScore(){
	score = sj.getScore();
	String texte = "<html><font size='6' face='courier' color ='red'><b>SCORE = <font color='green'>"+score+
	    "</font> : DUCKS LEFT = <font color='blue'>"+sj.getCiblesRestantes()+
	    "</font> : ELAPSED TIME = <font color='yellow'>"+tempsEcoule+"</font> : PLAYER = <font color='orange'>"+username+"</font></html>";
	scoreLabel.setText(texte);
	switchGun.setText("<html><font color='white' size=6 face='courier'>WAVE "+wave+
			  " - Press A to change weapon : WEAPON = <font color='green'>"+cp.getGunName()+"</font><html>");
    }
    //Methode qui recharge le magasine de l'arme
    public void reloadGun(){
	if (reload){
	    reload = false;
	    balles = ballesMax;
	    ballesAffiche = "<html><font color=green size=6 face=courier>AMMO:"+balles+"/"+ballesMax+"</html>";
	    ammo.setText(ballesAffiche);
	}
    }
    //Methode qui diminue le nombre de balles et actualise les labels
    public void balaMenos(){
	if (balles<1){
	    ammo.setText("<html><font color=red size=6 face=courier>NO AMMO! Press SPACE to reload.<html>");
	    reload = true;
	} else{
	    balles--;
	    ballesAffiche = "<html><font color=green size=6 face=courier>AMMO:"+balles+"/"+ballesMax+"</html>";
	    ammo.setText(ballesAffiche);
	}
    }
    //MUSIQUE. On s'est servi de la bibliotheque javax.sound.*
    public void stopSound(){
	clip.stop();
	clip.close();
    }
    //Methode pour reproduire du son
    synchronized void playSound(String music){
	if (clip!=null) clip.stop();
	try{
	    clip = AudioSystem.getClip();
	    clip.open(AudioSystem.getAudioInputStream(this.getClass().getResource(music)));
	}catch(Exception ex){
	    System.out.println("Probleme de telechargement de fichier audio");
	}
	clip.drain();
	clip.setFramePosition(0);
	clip.start();
    }
}
