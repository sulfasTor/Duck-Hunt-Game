import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OpenIndexIn implements ActionListener {
	private SignIn fen;
	public OpenIndexIn(SignIn a) {
		fen = a;
	}

	public void actionPerformed(ActionEvent ae){
     	Index fen2 = new Index();
     	fen.setVisible(false);
    }
}
