package client1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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

public class ForgetPD extends JFrame implements ActionListener{
	
	private JPanel contentPanel = new JPanel();//panel 容器面板
	private JLabel label, label2;
	
	private JLabel lbid = new JLabel("Identity: ");
	private JTextField id = new JTextField(20);
	
	private JLabel lbphone = new JLabel("PhoneNumb:");
	private JTextField phone = new JTextField(20);
	
	private JLabel lbNewPassWord = new JLabel("NewPassWord:");
	private JPasswordField newPassWord = new JPasswordField(20); 
	
	private JButton confirm = new JButton("Confirm");
	private JButton cancel = new JButton("Cancel");
	
	public ForgetPD(){
		ImageIcon image1 = new ImageIcon("Icon\\background3.JPEG");
		ImageIcon image2 = new ImageIcon("Icon\\touming3.GIF");
		
		label = new JLabel(image1);
		label2 = new JLabel(image2);
		
		//设置标签大小与位置
		label.setBounds(0, 0, 500, 350);
		label2.setBounds(0, 0, 501, 350);
		
		this.getLayeredPane().add(label2, new Integer(Integer.MIN_VALUE));//为前面创建的标签 设置为最底层
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
	    //将内容面板设置为透明，就能够看见添加在LayeredPane上的背景。
		((JPanel)this.getContentPane()).setOpaque(false);
		
		
		contentPanel.setLayout(null);
		add(lbid);
	    add(id);
	    add(lbphone);
	    add(phone);
	    add(lbNewPassWord);      
	    add(newPassWord); 
	    add(confirm);      
	    add(cancel);
	    
	    confirm.addActionListener(this);
        cancel.addActionListener(this);
        
                
        lbid.setBounds(135, 110, 70, 20);
        lbphone.setBounds(105, 160, 90, 20);
        lbNewPassWord.setBounds(95, 210, 90, 20);
        id.setBounds(185, 110, 100, 20);
        phone.setBounds(185, 160, 100, 20);
        newPassWord.setBounds(185, 210, 100, 20);
        confirm.setBounds(125, 260, 80, 20);
        cancel.setBounds(235, 260, 80, 20);
        setTitle("ClientForgrtPD");
        
        setLocation(450, 150);
        setSize(500, 350);
        
        contentPanel.setOpaque(false);
        lbid.setOpaque(false);
        lbphone.setOpaque(false);
        lbNewPassWord.setOpaque(false);
        id.setOpaque(false);
        phone.setOpaque(false);
        newPassWord.setOpaque(false);
        confirm.setOpaque(false);
        cancel.setOpaque(false);        
        this.getContentPane().add(contentPanel);
  
         
	}

//	 public static void main(String[] args) {
//	    	myWindow.run(new ForgetPD(), 500, 350);    		
//	    }
//	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==confirm){
			if(id.getText().trim().equals("")){
				JOptionPane.showMessageDialog(getContentPane(), "Id number must not be empty!!!", "Error", JOptionPane.ERROR_MESSAGE);
			}else if(phone.getText().trim().equals("")){
				JOptionPane.showMessageDialog(getContentPane(), "Phone number must not be empty!!!", "Error", JOptionPane.ERROR_MESSAGE);
			}else if(newPassWord.getText().trim().equals("")){
				JOptionPane.showMessageDialog(getContentPane(), "New password must not be empty!!!", "Error", JOptionPane.ERROR_MESSAGE);
			}else{
				UserInfoOperation us=new UserInfoOperation();
				User u=new User(), y=new User();
				try {
					u=us.get(Integer.parseInt(id.getText().trim()),phone.getText().trim());	
					if(u!=null){					
						y.setId(u.getId());
						y.setPassWord(newPassWord.getText().trim());
						y.setPhoneNumber(u.getPhoneNumber());
						y.setUserName(u.getUserName());
						us.updateUser(y);									
						this.dispose();	
						new Startframe().setVisible(true);
					}else{
						JOptionPane.showMessageDialog(getContentPane(), "The phone number is not related to the ID, please enter again!!", "Error", JOptionPane.ERROR_MESSAGE);
					}								
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}else if(e.getSource()==cancel){
			this.dispose();	
			new Startframe().setVisible(true);
		}
	}
}
