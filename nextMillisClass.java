import javax.swing.*;
import java.awt.event.*;

class nextMillisClass implements ActionListener{
	SurfaceJeu win;
	
	public nextMillisClass(SurfaceJeu fen){
		win = fen;
	}
	public void actionPerformed(ActionEvent ae){
		win.next_Millis();
	}
}
