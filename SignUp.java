import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
public class SignUp extends JFrame{
	private JPanel panel;
	JTextField usernameText;
	JPasswordField passwordText;
	JTextField mailText;
	Register reg;
	OpenIndexUp oI;
	private ImageIcon icon, iconReturn;
	public SignUp(){
		super("Duck Hunt - Sign Up Window");
		icon = new ImageIcon("media//RegisterButton.png");
		iconReturn = new ImageIcon("media//return.png");
		oI = new OpenIndexUp(this);
		reg = new Register(this);
		panel = new JPanel(null){
			public void paintComponent(Graphics g){
				BufferedImage img = null;
				try{
					img = ImageIO.read(new File("media//FondoSignUp.png"));
				}catch(IOException io){
					io.printStackTrace();
				}
					g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
			}
		};
		panel.repaint();

		JLabel usernameLabel = new JLabel("Username: ");
		usernameLabel.setBounds(280, 110, 80, 25);
		panel.add(usernameLabel);
		usernameLabel.setForeground(Color.BLACK);

		usernameText = new JTextField(20);
		usernameText.setBounds(370, 110, 160, 25);
		panel.add(usernameText);

		JLabel passwordLabel = new JLabel("Password: ");
		passwordLabel.setBounds(280,140,80,25);
		panel.add(passwordLabel);
		passwordLabel.setForeground(Color.BLACK);

		passwordText = new JPasswordField(20);
		passwordText.setBounds(370,140,160,25);
		panel.add(passwordText);

		JLabel mailLabel = new JLabel("Mail");
		mailLabel.setBounds(280,170,80,25);
		panel.add(mailLabel);
		mailLabel.setForeground(Color.BLACK);

		mailText = new JTextField(20);
		mailText.setBounds(370,170,160,25);
		panel.add(mailText);

		JButton registerButton = new JButton(icon);
		registerButton.setBounds(265, 250, 120, 40);
		panel.add(registerButton);
		registerButton.setContentAreaFilled(false);
		registerButton.setBorder(null);
		registerButton.addActionListener(reg);

		JButton returnButton = new JButton(iconReturn);
		returnButton.setBounds(395, 250, 120, 40);
		panel.add(returnButton);
		returnButton.setContentAreaFilled(false);
		returnButton.setBorder(null);
		returnButton.addActionListener(oI);

		

		setContentPane(panel);
		setSize(600,500);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public String getUsername(){
		return usernameText.getText();
	}

	public String getPassword(){
		String nombre = usernameText.getText();
		char[] contrasena = passwordText.getPassword();
		String pass = new String(contrasena);
		return pass;
	}

	public String getMail(){
		return mailText.getText();
	}

	public static void main(String args[]){
		new SignUp();
	}
}
