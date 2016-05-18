import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.event.*;
import java.util.*;
public class SaveScore{
	
	private String nombre;
	private String pass;

	public SaveScore(String name, String password){
		nombre = name;
		pass = password;	
	}

	public void addScore(int points) {
		String score = Integer.toString(points);
		
		File fl = new File("Archivo.txt");
		String delimiter = ",";
		String username,password;
		/*On utilise des linkedlist pour stocker les valeurs écrits dans notre fichier et les écrire en cas de
		perte du fichier*/
		LinkedList<String> usernameList = new LinkedList<String>();
		LinkedList<String> passwordList = new LinkedList<String>();
		LinkedList<String> mailList = new LinkedList<String>();
		LinkedList<String> scoreList = new LinkedList<String>();
		String[] temp = new String[4];				
		
		try {
		/*On lit les valeurs stockees dans notre fichier*/
			BufferedReader bufferedReader = new BufferedReader(new FileReader("Archivo.txt"));	
			String line = "";
			while((line = bufferedReader.readLine())!=null){
	        	System.out.println(line);
	        	temp = line.split(delimiter);
	        	usernameList.add(temp[0]);
				passwordList.add(temp[1]);
			   	mailList.add(temp[2]);
				scoreList.add(temp[3]);
			}
		} catch (FileNotFoundException ex) {ex.printStackTrace();
		} catch (IOException ex) {ex.printStackTrace();}
		
		//Mets le score  qui va etre recupere pour l'assigner au bon utilisateur
		for(int j = 0; j< usernameList.size(); j++){
			if(nombre.equals(usernameList.get(j)) && pass.equals(passwordList.get(j))){
				scoreList.add(j,score);
				//break;
				//Pour optimiser le proces il conviendrait de mettre un break mais on ne l'utilise que pour les boucles while à l'INSA
			}
		}
		try {	
			java.io.BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Archivo.txt"));	
			for(int i = 0; i < usernameList.size();i++){
				bufferedWriter.append(usernameList.get(i)+","+passwordList.get(i)+","+mailList.get(i)+","+scoreList.get(i));
				bufferedWriter.flush();
				bufferedWriter.newLine();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}
}
