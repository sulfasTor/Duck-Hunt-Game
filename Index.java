import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
//Pour la musique
import javax.sound.sampled.*;

public class Index extends JFrame {
	
	private JButton btnJouer;
	private JButton btnOptions;
	private JButton btnScores;
	private JButton btnSignUp;
	private ImageIcon icon,icon2,icon3;
	private OpenWindow opWin;
	private Score sc;
	private Clip clip;
	private OpenRegister rg;
	private String music = "/media/epic2.wav";
	
	public Index() {
		super("Welcome");
		
		// Instanciation des Widgets
		opWin = new OpenWindow(this);
		sc = new Score(this);
		rg = new OpenRegister(this);
		icon = new ImageIcon("media//icon.png");
		icon2 = new ImageIcon("media//singinButton.png");
		icon3 = new ImageIcon("media//icon3.png");
		btnSignUp = new JButton(icon3);
		btnJouer = new JButton(icon);
		btnScores = new JButton(icon2);
		
		JPanel cadrePrincipal= new JPanel(null){
		
		public void paintComponent(Graphics g){
			BufferedImage img = null;
			try{
				img = ImageIO.read(new File("media//FondoIndex.png"));
			}catch(IOException io){
				io.printStackTrace();
			}
			g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		}
		};
		cadrePrincipal.repaint();
		
		btnJouer.setBorder(null);
		btnJouer.setContentAreaFilled(false);
		btnJouer.addActionListener(opWin);
		
		btnScores.setBorder(null);
		btnScores.addActionListener(sc);

		btnScores.setContentAreaFilled(false);

		btnSignUp.setContentAreaFilled(false);
		btnSignUp.setBorder(null);

		btnSignUp.addActionListener(rg);
		
		cadrePrincipal.add(btnJouer);
		cadrePrincipal.add(btnScores);
		cadrePrincipal.add(btnSignUp);
		cadrePrincipal.repaint();

		btnJouer.setBounds(230,150,120,40);
		btnScores.setBounds(230,200,120,40);
		btnSignUp.setBounds(230,250,120,40);
		
		playMusic();

		// Finalisation de la fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(cadrePrincipal);
		setSize(600,500);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	public void stopMusic(){
		clip.stop();
		clip.close();
	}
	//Background music
	public void playMusic(){
		try{
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(this.getClass().getResource(music)));
			FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gain.setValue(-20.0f);
		}catch(Exception ex){
			System.out.println("Probleme de telechargement de fichier audio");
		}
		clip.setFramePosition(0);
		clip.loop(0);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.drain();
		clip.start();

	}
	
	public static void main (String args[]) {
		new Index();
	}
}

