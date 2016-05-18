import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OpenWindow implements ActionListener {
	private Index fen;
	public OpenWindow(Index a) {
		fen = a;
	}

	public void actionPerformed(ActionEvent ae){
     	NeWindow fen2 = new NeWindow("Guest","0");
     	fen.setVisible(false);
     	fen.stopMusic();
   	}
   
}
