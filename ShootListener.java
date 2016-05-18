import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class ShootListener implements MouseListener {
       
        private NeWindow cp;
        private SurfaceJeu sj;
        private CursorPanel cl;
        
        public ShootListener(NeWindow cp){
            this.cp = cp;
            sj = cp.getSurfaceJeu();
            cl = sj.getCursorPanel();
            
        }
        public void mouseClicked(MouseEvent me){
			if(!cp.getReload()){
				sj.tir(me.getPoint());
				cp.balaMenos();
				cp.click(me.getPoint());
				cp.playSound("/media/shot"+cl.getGunIndex()+".wav");
				sj.repaint();
			}
		}
        public void mouseEntered(MouseEvent me){}
        public void mouseExited(MouseEvent me){}
        public void mousePressed(MouseEvent me){}
		public void mouseReleased(MouseEvent me){}
}
