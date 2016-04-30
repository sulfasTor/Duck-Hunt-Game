import java.awt.event.*;
class OpenRegister implements ActionListener{
	private Index fen;
	public OpenRegister(Index a){
		fen = a;
	}

	public void actionPerformed(ActionEvent e){
		SignUp fen2 = new SignUp();
		fen.setVisible(false);
		fen.stopMusic();
	}
}
