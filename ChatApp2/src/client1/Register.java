package client1;


import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
	 
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import database.User;
import database.UserInfoOperation;
import util.myWindow;
	
	@SuppressWarnings("serial")
	public class Register extends JFrame implements ActionListener{   
		private JPanel contentPanel = new JPanel();
		private JLabel label,label2;
		
		private JLabel lbid = new JLabel("Identity: ");
		private JTextField id = new JTextField(20);
		
		private JLabel lbphone = new JLabel("PhoneNumb:");
		private JTextField phone = new JTextField(20);
		
		private JLabel lbl1 = new JLabel("Username:");
		private JLabel lbl2 = new JLabel("Password:"); 
		private JLabel lbl3 = new JLabel("IdentifyPwd:"); 
		private JButton confirm = new JButton("Confirm");
		private JButton cancel = new JButton("Cancel");
		private JTextField admin = new JTextField(20);
		private JPasswordField password = new JPasswordField(20);   
		private JPasswordField idpassword = new JPasswordField(20); 
	  
	    public Register(){      
	        ImageIcon image1 = new ImageIcon("Icon\\background1.JPG");
			ImageIcon image2 = new ImageIcon("Icon\\touming1.GIF");
			
//			JLabel backLabel = new JLabel();
//			JLabel backLabel2 = new JLabel();
//			backLabel.setIcon(image1);
//			backLabel2.setIcon(image2);
			
			label = new JLabel(image1);
			label2 = new JLabel(image2);
			
			//设置标签大小与位置
			label.setBounds(0, 0, 500, 350);
			label2.setBounds(0, 0, 501, 350);
			
			this.getLayeredPane().add(label2, new Integer(Integer.MIN_VALUE));//为前面创建的标签 设置为最底层
			this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		    //将内容面板设置为透明，就能够看见添加在LayeredPane上的背景。
			((JPanel)this.getContentPane()).setOpaque(false);
	         
	        password.setEchoChar('X');
	         
	        contentPanel.setLayout(null);
	        add(lbphone);
	        add(phone);
	        add(lbid);
	        add(id);
	        add(lbl1);
	        add(lbl2);
	        add(lbl3);
	        add(password);
	        add(confirm);      
	        add(cancel); 
	        add(admin); 
	        add(idpassword);
	        
	        confirm.addActionListener(this);
	        cancel.addActionListener(this);
	        
	        lbphone.setBounds(105, 10, 80, 20);
	        phone.setBounds(185, 10, 100, 20);
	        lbid.setBounds(135, 60, 70, 20);
	        id.setBounds(185, 60, 100, 20);
	        lbl1.setBounds(115, 110, 70, 20);
	        lbl2.setBounds(115, 160, 70, 20);
	        lbl3.setBounds(115, 210, 70, 20);
	        admin.setBounds(185, 110, 100, 20);
	        password.setBounds(185, 160, 100, 20);
	        idpassword.setBounds(185, 210, 100, 20);
	        confirm.setBounds(125, 260, 80, 20);
	        cancel.setBounds(235, 260, 80, 20);
	        setTitle("ClientRegistrar");
	        
	        setLocation(450, 150);
	        setSize(500, 350);
	        
	        lbphone.setOpaque(false);
	        phone.setOpaque(false);
	        lbid.setOpaque(false);
	        id.setOpaque(false);
	        contentPanel.setOpaque(false);
	        lbl1.setOpaque(false); 
	        lbl2.setOpaque(false);
	        lbl3.setOpaque(false);
	        admin.setOpaque(false);
	        password.setOpaque(false);
	        idpassword.setOpaque(false);
	        confirm.setOpaque(false);
	        cancel.setOpaque(false);
	        this.getContentPane().add(contentPanel);
	    }  
	 
//	    public static void main(String[] args) {
//	    	myWindow.run(new Register(), 500, 350);    		
//	    }

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==confirm){
				if(phone.getText().trim().equals("")){
					JOptionPane.showMessageDialog(getContentPane(), "Phone number must not be empty!!!", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(id.getText().trim().equals("")){
					JOptionPane.showMessageDialog(getContentPane(), "ID number must not be empty!!!", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(admin.getText().trim().equals("")){
					JOptionPane.showMessageDialog(getContentPane(), "UserName must not be empty!!!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if(password.getText().trim().equals("")){
					JOptionPane.showMessageDialog(getContentPane(), "Password must not be empty!!!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else if(idpassword.getText().trim().equals("")){
					JOptionPane.showMessageDialog(getContentPane(), "IDPassword must not be empty!!!", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(!idpassword.getText().trim().equals(password.getText().trim())){
					JOptionPane.showMessageDialog(getContentPane(), "IDPassword must be the same with password!!!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else{
					UserInfoOperation us=new UserInfoOperation();
			        User u=new User();
			        u.setPhoneNumber(phone.getText().trim());
			        u.setId(Integer.parseInt(id.getText().trim()));
			        u.setUserName(admin.getText().trim());			        
			        u.setPassWord(password.getText().trim());
			        try {
						us.addUser(u);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					this.dispose();
					new Startframe().setVisible(true);
				}
			}else if(e.getSource()==cancel){
				this.dispose();
				new Startframe().setVisible(true);
			}
		}
	
}
