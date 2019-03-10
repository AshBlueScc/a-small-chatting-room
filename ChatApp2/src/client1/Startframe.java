package client1;

import java.awt.Color;

import java.awt.Dimension;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.mysql.jdbc.log.NullLogger;

import database.User;
import database.UserInfoOperation;
import util.*;

public class Startframe extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel = new JPanel();//panel 容器面板
	private JLabel label, label2;
	
	//设置按钮组件,登陆，注册，忘记密码
	private JButton login = new JButton("login"), 
			registered = new JButton("register"), 
			forgetPassword = new JButton("forgetPassword");

	//设置文本框组件
	private JTextField admin = new JTextField();
	private JTextField password1 = new JPasswordField("Enter your password!");		
	private JPasswordField password = new JPasswordField();
	
	
	//设置复选框组件
	private JCheckBox rememberAdmin = new JCheckBox("rememberAdmin");
//			,rememberPassword = new JCheckBox("remberPassword");
	
	public Startframe() {
		UserInfoOperation us=new UserInfoOperation();
		try {
			if(us.getRemember().getUserName()!=null){
				admin.setText(us.getRemember().getUserName());
			}else{
				admin.setText("account/email/phonenumber");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		//password.setText("password");
		
		ImageIcon image1 = new ImageIcon("Icon\\background.JPG");
		ImageIcon image2 = new ImageIcon("Icon\\touming.JPG");
		
//		JLabel backLabel = new JLabel();
//		JLabel backLabel2 = new JLabel();
//		backLabel.setIcon(image1);
//		backLabel2.setIcon(image2);
		
		label = new JLabel(image1);
		label2 = new JLabel(image2);
		
		//设置标签大小与位置
		label.setBounds(0, 0, 500, 350);
		label2.setBounds(0, 0, 501, 350);
		
		this.getLayeredPane().add(label2, new Integer(Integer.MIN_VALUE));//为前面创建的标签 设置为最底层
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
	    //将内容面板设置为透明，就能够看见添加在LayeredPane上的背景。
		((JPanel)this.getContentPane()).setOpaque(false);
		
		//添加组件到contentPane容器中
		contentPanel.setLayout(null);
		add(password1);
		add(admin);
		add(password);
		add(login);
		add(rememberAdmin);
//        add(rememberPassword);
        add(registered);
        add(forgetPassword);

        //设置组件绝对位置
        admin.setBounds(95, 130, 300, 25);
        password.setBounds(95, 154, 300, 25);
        rememberAdmin.setBounds(95, 180, 200, 14);
//        rememberPassword.setBounds(295, 180, 200, 14);
        registered.setBounds(95, 225, 90, 20);
        forgetPassword.setBounds(315, 225, 150, 20);
        login.setBounds(95, 255, 90, 20);
        setTitle("ClientLogin");
        
        setLocation(450, 150);
        setSize(500, 350);
        
        //组件透明
        admin.setOpaque(false);
        password.setOpaque(false);
        contentPanel.setOpaque(false);
        rememberAdmin.setOpaque(false);
//        rememberPassword.setOpaque(false);
        getContentPane().add(contentPanel);
        
        //组件边框颜色
        textSet(admin);
        textSet(password);
        
        //监听事件      
        login.addActionListener(this);
        registered.addActionListener(this);
        forgetPassword.addActionListener(this);
        admin.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                int c = e.getButton();
                if(c == MouseEvent.BUTTON1 && admin.getText().equals("account/email/phonenumber") &&e.getClickCount()==1) {
                    admin.setText(null);
                   // password.setText("password");
                }
            }
        });
        
        admin.addCaretListener(new CaretListener(){
            public void caretUpdate(CaretEvent e){

            }
        });

        password.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                int c = e.getButton();
                if(c == MouseEvent.BUTTON1 && password.getText().equals("password") &&e.getClickCount()==1) {
                    password.setText(null);
                  //  admin.setText("account/email/phonenumber");
                }
            }
        });
                
	}
	
	private void textSet(JTextField field) {
		field.setBackground(new Color(255, 255, 255));  
        field.setPreferredSize(new Dimension(150, 28));  
        MatteBorder border = new MatteBorder(0, 0, 2, 0, new Color(192, 192, 192));  
        field.setBorder(border);  
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==login){
			UserInfoOperation us=new UserInfoOperation();
	        User u=new User();
			try {
				u=us.get(admin.getText().trim());	
				if(u!=null){
					if(rememberAdmin.isSelected()){
						us.updateRememberUserName(admin.getText().trim());
					}					
					if(password.getText().trim().equals(u.getPassword())){
						this.dispose();	
						new Userinterface_client().setVisible(true);
					}else{
						JOptionPane.showMessageDialog(getContentPane(), "The passwotd is not correct, please enter again!!", "Error", JOptionPane.ERROR_MESSAGE);
					}
		        }else{
		        	JOptionPane.showMessageDialog(getContentPane(), "The username doesn't exist, please enter again!!", "Error", JOptionPane.ERROR_MESSAGE);
		        }
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}					
		}else if(e.getSource()==registered){
			this.dispose();
			new Register().setVisible(true);
		}else if(e.getSource()==forgetPassword){
			this.dispose();
			new ForgetPD().setVisible(true);
		}
	}
	
	public static void main(String[] args) {
		System.setProperty("java.net.preferIPv4Stack", "true");
		myWindow.run(new Startframe(), 500, 350);
		
	}
}
