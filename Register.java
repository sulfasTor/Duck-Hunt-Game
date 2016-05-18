import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JOptionPane;
/*Cet écouteur fait un registre dans le fichier utilisé pour simuler une base de données en format .txt*/
class Register implements ActionListener{
	private SignUp fen;
	private Valida val = new Valida();
	public Register(SignUp a){
		fen = a;
	}

	public void actionPerformed(ActionEvent e){
		String nombre;
		nombre = fen.getUsername();
		String pass;
		pass = fen.getPassword();
		String mail;
		mail = fen.getMail();
		String scoreA ="0";
		

		File fl = new File("Archivo.txt");
		String delimiter = ",";
		String username,password,mailadd;
		/*On utilise des linkedlist pour stocker les valeurs écrits dans notre fichier et les écrire en cas de
		perte du fichier*/
		LinkedList<String> usernameList = new LinkedList<String>();
		LinkedList<String> passwordList = new LinkedList<String>();
		LinkedList<String> mailList = new LinkedList<String>();
		LinkedList<String> scoreList = new LinkedList<String>();

		String[] temp = new String[4];
		JFrame frame = new JFrame();
		String name;
		if(nombre.equals("") && pass.equals("") && mail.equals("")){
			JOptionPane.showMessageDialog(frame, "Please, fill the required fields");
		}
		if(!nombre.equals("") || !pass.equals("") || !mail.equals("")){
			if(val.validateEmail(mail)){
				/*On verifie si le fichier existe et si ce n'est pas le cas on le crée*/
				if(!fl.exists()){
				try {
					//Dans le code qui suit on fait l'écriture d'un registre par les methodes append de la classe BufferedWriter
					java.io.BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Archivo.txt"));	
					bufferedWriter.append(nombre+","+pass+","+mail+","+"0");
					bufferedWriter.flush();
					bufferedWriter.newLine();
					JOptionPane.showMessageDialog(frame, "Welcome to Duck Hunt");
					NeWindow fen2 = new NeWindow(nombre,pass);
					fen.setVisible(false);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}else{
				name = nombre;
				password = pass;
				mailadd = mail;  
				System.out.println("\n");
				usernameList.add(name);
				passwordList.add(password);
				mailList.add(mailadd);
				scoreList.add(scoreA);

				try {
					/*On lit les valeurs stockees dans notre fichier*/
					BufferedReader bufferedReader = new BufferedReader(new FileReader("Archivo.txt"));	

					String line = "";
					System.out.println("USERNAME / PASSWORD");
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

				try {
				
					java.io.BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Archivo.txt"));	
					for(int i = 0; i < usernameList.size();i++){
						bufferedWriter.append(usernameList.get(i)+","+passwordList.get(i)+","+mailList.get(i)+","+scoreList.get(i));
						bufferedWriter.flush();
						bufferedWriter.newLine();
					}
					JOptionPane.showMessageDialog(frame, "Welcome to Duck Hunt");
					fen.setVisible(false);
					NeWindow fen2 = new NeWindow(nombre, pass);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			}
			else {JOptionPane.showMessageDialog(frame, "Please, fill the required fields");}
			
		}
	}
}	
