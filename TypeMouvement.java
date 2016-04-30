import java.lang.Math;

public enum TypeMouvement{
	
	DROITE_HAUT, DROITE_BAS, DROITE_GAUCHE, DROITE_DROITE, DIAG_MONTE_GAUCHE, DIAG_MONTE_DROITE, DIAG_BAISSE_GAUCHE,
	 DIAG_BAISSE_DROITE, RANDOM, RANDOM_HARD , CERCLE_DIRECT, CERCLE_HORAIRE;
	
	private int c = 0;
	
	//9 types de trajectoires differents -> 0 to 8
	public static TypeMouvement choixTrajectoire(int choix){
		TypeMouvement sortie;
		
		switch(choix){
		case 0 : sortie = DROITE_HAUT; break;
		case 1 : sortie = DROITE_BAS; break;
		case 2 : sortie = DROITE_GAUCHE; break;
		case 3 : sortie = DROITE_DROITE; break;
		case 4 : sortie = DIAG_MONTE_GAUCHE; break;
		case 5 : sortie = DIAG_MONTE_DROITE; break;
		case 6 : sortie = DIAG_BAISSE_GAUCHE; break;
		case 7 : sortie = DIAG_BAISSE_DROITE; break;
		case 8 : sortie = RANDOM; break;
		case 9 : sortie = RANDOM_HARD; break;
		case 10 : sortie = CERCLE_DIRECT; break;
		default : sortie = null;
		}
		
		
		return sortie;
	}
	public int[] bouge(int x, int y){
		int[] pos = {x, y};
		if(Math.abs(x)>=1300 || Math.abs(y)>=660){
			x=(int)(Math.random()*900);
			y=(int)(Math.random()*610);
		}
			
		
		if(this == DROITE_HAUT){
			y--;
			pos[1] = y;
		}
		else if(this == DROITE_BAS){
			y++;
			pos[1] = y;
		}
		else if(this == DROITE_DROITE){
			x++;
			pos[0] = x;
		}
		else if(this == DROITE_GAUCHE){
			x--;
			pos[0] = x;
		}
		else if(this == DIAG_MONTE_GAUCHE){
			x--;
			y--;
			pos[0] = x;
			pos[1] = y;
		}
		else if(this == DIAG_MONTE_DROITE){
			x++;
			y--;
			pos[0] = x;
			pos[1] = y;
		}
		else if(this == DIAG_BAISSE_GAUCHE){
			x--;
			y++;
			pos[0] = x;
			pos[1] = y;
		}
		else if(this == DIAG_BAISSE_DROITE){
			x++;
			y++;
			pos[0] = x;
			pos[1] = y;
		}
		
		else if(this == RANDOM){
			int r = (int)(Math.random()*9) ;
			if(r == 1) x++;
			else if(r == 2) x--;
			else if(r == 3) y++;
			else if(r == 4) y--;
			else if(r == 5){ x++; y++;}
			else if(r == 6){ x--; y++;}
			else if(r == 7){ x++; y--;}
			else if(r == 8){ x--; y--;}
			
			pos[0] = x;
			pos[1] = y;
		}
		
		else if(this == RANDOM_HARD){
			int r = (int)(Math.random()*9) ;
			if(r == 1) x = x+5;
			else if(r == 2) x = x-5;
			else if(r == 3) y = y+5;
			else if(r == 4) y = y-5;
			else if(r == 5){ x = x+5 ; y = y+5;}
			else if(r == 6){ x = x-5; y = y+5;}
			else if(r == 7){ x = x+5; y = y-5;}
			else if(r == 8){ x = x-5; y = -5;}
			
			pos[0] = x;
			pos[1] = y;
		}
		else if(this==CERCLE_DIRECT){
			int xInitial = x;
			int yInitial = y;
			double angle = Math.toRadians(++c);
			int rayon = (int)(20+Math.random()*50);
			x = (int)(xInitial + rayon*Math.cos(angle));
			y = (int)(yInitial + rayon*Math.sin(angle));
			pos[0] = x;
			pos[1] = y;
		}
		else if(this==CERCLE_HORAIRE){
			int xInitial = x;
			int yInitial = y;
			double angle = Math.toRadians(--c);
			int rayon = (int)(20+Math.random()*50);
			x = (int)(xInitial + rayon*Math.cos(angle));
			y = (int)(yInitial + rayon*Math.sin(angle));
			pos[0] = x;
			pos[1] = y;
		}
		return pos;
	}
	
	
}
