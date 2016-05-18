import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class GunChangerListener implements KeyListener {
	
	private NeWindow cp;
	private SurfaceJeu sj;
	
	public GunChangerListener(NeWindow cp){
		this.cp = cp;
		sj=cp.getSurfaceJeu();
	}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A) sj.getCursorPanel().gunChange();
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(cp.getReload()){
				cp.playSound("/media//reload.wav");
				cp.reloadGun();
			}
		}
	}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
}
