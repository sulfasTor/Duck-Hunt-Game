/*
 * SourisListener.java
 * 
 * Copyright 2015 moises <moises@RL-PC>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class SourisListener implements MouseMotionListener{
	
	private NeWindow cp;
	private SurfaceJeu sj;
	private CursorPanel cl;
	
	public SourisListener(NeWindow cp){
		this.cp = cp;
		sj=cp.getSurfaceJeu();
		cl=sj.getCursorPanel();
	}
	
	public void mouseMoved(MouseEvent me){
		
		int x = me.getX();
		int y = me.getY();
		cp.setXY(x,y);
		cp.repaint();
	}
	public void mouseDragged(MouseEvent me){
			int x = me.getX();
			int y = me.getY();
			cp.setXY(x,y);
		if(!cp.getReload()&& cl.isAutomatic()){
			cp.click(me.getPoint());
			sj.tir(me.getPoint());
			cp.playSound("/media/shot"+cl.getGunIndex()+".wav");
			cp.balaMenos();
			cp.repaint();
		}
	}
}
