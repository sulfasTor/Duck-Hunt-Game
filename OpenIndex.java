import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OpenIndex implements ActionListener {
	private NeWindow fen;
	public OpenIndex(NeWindow a) {
		fen = a;
	}

	public void actionPerformed(ActionEvent ae){
     	Index fen2 = new Index();
     	fen.setVisible(false);
     	if(fen.getClip()!=null) fen.stopSound();
     	new SaveScore(fen.getPlayerName(), fen.getPlayerPassword()).addScore(fen.getPlayerScore());
     	JOptionPane.showMessageDialog(fen2, "Your final score is: "+fen.getPlayerScore());
   	}
}
