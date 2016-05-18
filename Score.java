import java.awt.event.*;
class Score implements ActionListener{
	private Index fen;
	public Score(Index a){
		fen = a;
	}

	public void actionPerformed(ActionEvent e){
		SignIn fen2 = new SignIn();
     	fen.setVisible(false);
     	fen.stopMusic();
	}
}
