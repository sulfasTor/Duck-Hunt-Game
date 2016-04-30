class CibleMobile extends Cible{
	
	private TypeMouvement trajectoire;
	
	private int posXIni;
	private int posYIni;
	
	
	public CibleMobile(int x, int y, int t, int d, int v,TypeMouvement traj){
		super(x, y, t, d, v);
		//posX, posY, taille, duree, valeur (points)
		posXIni = x;
		posYIni = y;
		trajectoire = traj;
	}
	
	public TypeMouvement getTraj(){return trajectoire;}
	
	public void setTraj(int choix){
		trajectoire.choixTrajectoire(choix);
	}
	public boolean demarrerMouvement(){
		boolean fonctionne = true;
		if(super.getActif()){
			fonctionne = false;
			System.out.println("Mouvement déja démarré ! ");
		}
		super.activer();
		return fonctionne;
	}
	
	public void reset(){
		super.reset();
		super.setX(posXIni);
		super.setY(posYIni);
	}
	
	public void nextMillis(){
		super.nextMillis();
		if(super.getActif()){
			int newPos[] = new int[2];
			newPos = trajectoire.bouge(getXAncrage(), getYAncrage());
			super.setX(newPos[0]);
			super.setY(newPos[1]);
		}
	}
}
