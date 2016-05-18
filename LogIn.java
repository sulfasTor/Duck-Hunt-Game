import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JOptionPane;

public class LogIn implements ActionListener{
	private SignIn fen;
	private String nombre;
	private String pass;

	public LogIn(SignIn a){
		fen = a;
	}

	public void actionPerformed(ActionEvent e) {
		nombre = fen.getUsername();
		pass = fen.getPassword();
		String nombreaux=" ";
		String passaux=" ";
		String scoreaux=" ";
		

		File fl = new File("Archivo.txt");
		String delimiter = ",";
		String[] temp = new String[4];
		LinkedList<String> usernameList = new LinkedList<String>();
		LinkedList<String> passwordList = new LinkedList<String>();
		LinkedList<String> mailList = new LinkedList<String>();
		LinkedList<String> scoreList = new LinkedList<String>();
		JFrame frame = new JFrame();
		String password=" ";
		String username=" ";

		if(!fl.exists()){
			try{
				java.io.BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Archivo.txt"));
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}

		if(nombre.equals("") || pass.equals("")){
			JOptionPane.showMessageDialog(frame, "Please, fill the required fields");
		}
		while(!nombre.equals("") && !pass.equals("")){
			try {
					//Entrada
					
					BufferedReader bufferedReader = new BufferedReader(new FileReader("Archivo.txt"));	
					//Buscar si existe una palabra
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

					for(int k = 0; k < usernameList.size(); k++){
						password = passwordList.get(k);
						username = usernameList.get(k);
						if(nombre.equals(username) && pass.equals(password)){
							passaux = password;
							nombreaux = username;
							scoreaux = scoreList.get(k);
						}
					}

					if(!passaux.equals(pass) && !nombreaux.equals(nombre)){
						JOptionPane.showMessageDialog(frame,"The username or password doesn't exist");
					}else{
						JOptionPane.showMessageDialog(frame,"Hi " + nombreaux + " your actual score is " + scoreaux);
						NeWindow fen2 = new NeWindow(nombre, pass);
						fen.setVisible(false);
					}
					break;

				} catch (FileNotFoundException ex) {ex.printStackTrace();
			} catch (IOException ex) {ex.printStackTrace();}
		}
	}
}
