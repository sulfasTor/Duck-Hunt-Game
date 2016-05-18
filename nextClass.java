import javax.swing.*;
import java.awt.event.*;

class nextClass implements ActionListener{
	SurfaceJeu win;
	
	public nextClass(SurfaceJeu fen){
		win = fen;
	}
	public void actionPerformed(ActionEvent ae){
		win.next_Sec();
	}
}
