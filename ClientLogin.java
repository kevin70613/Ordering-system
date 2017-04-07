import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.*;

public class ClientLogin extends JFrame implements ActionListener{
	
	private JLabel title;
	private JLabel username;
    private JLabel passwd;
    private JLabel message;
    private JTextField name;
    private JTextField wd;

    public static void main(String[] args){
    	ClientLogin frame = new ClientLogin();
    	frame.setVisible(true);
    }
    
	public ClientLogin(){

		JLabel image = new JLabel(new ImageIcon("background.jpg"));
		image.setSize(600,400);
		setSize(600,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(image);
		setLayout(null);
		
		title = new JLabel();
		title.setText("會員登入 ");
		title.setLocation(200,50);
		title.setSize(100,30);
		add(title);
		
		username = new JLabel();
		username.setText("username : ");
		username.setLocation(100,100);
		username.setSize(100,30);
		add(username);
		
		passwd = new JLabel();
		passwd.setText("passwd : ");
		passwd.setLocation(100,150);
		passwd.setSize(100,30);
		add(passwd);
		
	    name = new JTextField();
		name.setText("");
		name.setLocation(200,100);
		name.setSize(150,30);
		add(name);
		
		wd = new JTextField();
		wd.setText("");
		wd.setLocation(200,150);
		wd.setSize(150,30);
		add(wd);
		
		JButton loginButton = new JButton("Login");
		loginButton.setLocation(200,220);
		loginButton.setSize(100,30);
		loginButton.addActionListener(this);
		add(loginButton);
		
		JButton regButton = new JButton("Regist");
		regButton.setLocation(320,220);
		regButton.setSize(100,30);
		regButton.addActionListener(this);
		add(regButton);
		
		message = new JLabel();
		message.setText("");
		message.setLocation(100,280);
		message.setSize(400,50);
		add(message);
		
	}

	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Login")){
			String user = name.getText();
			String psw = wd.getText();
			Member member = new Member(user,psw);
			MemberSQL memberSQL = new MemberSQL();
	        Member[] members = memberSQL.SelectTable();
			for(int i=0;i<members.length;i++){
				if(members[i].getUserName().equals(member.getUserName()) && members[i].getPassWd().equals(member.getPassWd()))	
				     callClientGUI();
			}
			message.setText("輸入帳號密碼有誤! 請重新輸入 (若不是會員請先註冊) ");
			name.setText("");
			wd.setText("");
		}	
		if (actionCommand.equals("Regist")){
			String user = name.getText();
			String psw = wd.getText();
			Member member = new Member(user,psw);
			MemberSQL memberSQL = new MemberSQL();
	        Member[] members = memberSQL.SelectTable();
			for(int i=0;i<members.length;i++){
				if(!members[i].getUserName().equals(member.getUserName()) && !members[i].getPassWd().equals(member.getPassWd()))	
					 memberSQL.insertTable(member.getUserName(),member.getPassWd());
			}
			message.setText("此帳號密碼已經有人註冊，請重新輸入! ");
			name.setText("");
			wd.setText("");
		}
		
	}
    
	public void callClientGUI(){
		 ClientGUI menu = new ClientGUI();
		 menu.setVisible(true);
	}
   
}
