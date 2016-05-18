import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OpenIndexUp implements ActionListener {
	private SignUp fen;
	public OpenIndexUp(SignUp a) {
		fen = a;
	}

	public void actionPerformed(ActionEvent ae){
     	Index fen2 = new Index();
     	fen.setVisible(false);
   	}
}