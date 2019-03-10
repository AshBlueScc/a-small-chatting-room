package client1;

import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import user.User;

public class Userinterface_client extends JFrame{
	private String id="client1";
	private JTextArea contentPane = new JTextArea();
	private JTextField text = new JTextField();
	//private JTextArea listPane = new JTextArea();
	private JButton confirm = new JButton();
	private JButton exit = new JButton();
	private JButton connect = new JButton("connect");
	private JButton stop = new JButton("disconnect");
	private JButton states = new JButton("state");
	private JTextArea txt_port=new JTextArea();
	private JTextArea nameid=new JTextArea();
	private JList userList; 
	private JTextField ipAddress = new JTextField();
	
	private DefaultListModel listModel;  
    private boolean isConnected = false; 
    
    private Socket socket;  
    private PrintWriter writer;  
    private BufferedReader reader;  
    private MessageThread messageThread;//(accept messages)负责接收消息的线程  
    private Map<String, User> onLineUsers = new HashMap<String, User>();//(all online users)所有在线用户  
    
    private JLabel label;
	
	public Userinterface_client(){
		super();
		setBounds(100, 100, 500, 375);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setTitle("ChatApplication_client");
		
		listModel = new DefaultListModel();  
	    userList = new JList(listModel);  
		
		getContentPane().setLayout(null);
		
		
		
		ImageIcon image1 = new ImageIcon("Icon\\background7.JPG");
//		ImageIcon image2 = new ImageIcon("Icon\\touming.JPG");
		
		label = new JLabel(image1);
//		label2 = new JLabel(image2);
		
		//设置标签大小与位置
		label.setBounds(0, 0, 500, 350);
//		label2.setBounds(0, 0, 501, 350);
		
//		this.getLayeredPane().add(label2, new Integer(Integer.MIN_VALUE));//为前面创建的标签 设置为最底层
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
	    //将内容面板设置为透明，就能够看见添加在LayeredPane上的背景。
		((JPanel)this.getContentPane()).setOpaque(false);
	
		
		JLabel jl=new JLabel("Port");
		getContentPane().add(jl);
		jl.setBounds(0,0,60,60);
		jl.setOpaque(false);
		
		
		getContentPane().add(txt_port);
		txt_port.setBounds(25,20,60,23);
		
		
		JLabel jname=new JLabel("Username");
		getContentPane().add(jname);
		jname.setBounds(120,0,60,60);
		
		
		
		getContentPane().add(nameid);
		nameid.setBounds(180,20,60,23);
		
		
		JLabel jlIpAddress = new JLabel("Ip");
		getContentPane().add(jlIpAddress);
		jlIpAddress.setBounds(0,30,60,60);
	
		
		getContentPane().add(ipAddress);
		ipAddress.setBounds(25,50,100,23);
		
		
		getContentPane().add(connect);
		connect.setBounds(280,20,80,33);
		connect.setOpaque(false);
		
		getContentPane().add(stop);
		stop.setBounds(380,20,100,33);
		stop.setOpaque(false);
			
		JScrollPane scrollpane = new JScrollPane(contentPane);
		contentPane.setEditable(false);
		contentPane.setForeground(Color.blue);  
		scrollpane.setBorder(new TitledBorder("Text_Area")); 
		getContentPane().add(scrollpane);
		scrollpane.setBounds(0, 75, 348, 168);
		scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollpane.setOpaque(false);
		
		JScrollPane scrollpanetext = new JScrollPane(text);
		scrollpanetext.setBorder(new TitledBorder("Broadcast")); 
		getContentPane().add(scrollpanetext);
		scrollpanetext.setBounds(0, 244, 350, 42);
		scrollpanetext.setOpaque(false);
		
		JScrollPane scrollpanelistPane = new JScrollPane(userList);
		scrollpanelistPane.setBorder(new TitledBorder("Online_List")); 
		//listPane.setEditable(false);
		getContentPane().add(scrollpanelistPane);
		scrollpanelistPane.setBounds(350, 75, 100, 210);
		scrollpanelistPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollpanelistPane.setOpaque(false);
				
		getContentPane().add(confirm);
		confirm.setText("send");
		confirm.setBounds(36, 292, 100, 28);
		confirm.setOpaque(false);
		
		getContentPane().add(exit);
		exit.setText("Exit");
		exit.setBounds(150, 292, 106, 28);
		exit.setOpaque(false);
		
		//Use Enter on keyboard(写消息的文本框中按回车键时事件)  
        text.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent arg0) {  
                send();  
            }  
        });  
			
        //click on send(单击发送按钮时事件)	
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();  
//				int j=0;
//				for (int i=0;i <userList.getModel().getSize(); i++) {  
//					if(((User)userList.getModel().getElementAt(i)).getName().equals(nameid)){
//						j=j+1;
//						((User)userList.getModel().getElementAt(i)).setStates("BROADCAST:"+j);
//					}
//                } 
			}
		});
		
		//click on connect event(单击连接按钮时事件) 
		connect.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                int port;  
                if (isConnected) {  
                    JOptionPane.showMessageDialog(getContentPane(), "You have been connected, don't try again!", "Error", JOptionPane.ERROR_MESSAGE);  
                    return;  
                }  
                try {  
                    try {  
                        port = Integer.parseInt(txt_port.getText().trim());  
                    } catch (NumberFormatException e2) {  
                        throw new Exception("Port number is not required!Port number shuould be integer!");  
                    }  
                    String name = nameid.getText().trim();  
                    if (name.equals("")) {  
                        throw new Exception("Username should not be none!");  
                    }  
//                    if(ipAddress.getText().trim().equals("")){
//                    	throw new Exception("IPAddress should not be none!");
//                    }
                    boolean flag = connectServer(port, name);  
                    if (flag == false) {  
                        throw new Exception("Disconnect abortively!");  
                    }  
                    JOptionPane.showMessageDialog(getContentPane(), "Connect successfully!");  
                } catch (Exception exc) {  
                    JOptionPane.showMessageDialog(getContentPane(), exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);  
                }  
            }  
        });  
		
		 //click on stop event(单击断开按钮时事件)  
		stop.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                if (!isConnected) {  
                    JOptionPane.showMessageDialog(getContentPane(), "You have been disconnected, don't try again!", "Error", JOptionPane.ERROR_MESSAGE);  
                    return;  
                }  
                try {  
                    boolean flag = closeConnection();//disconnect(断开连接)  
                    if (flag == false) {  
                        throw new Exception("Disconnect in exception!");  
                    }  
                    JOptionPane.showMessageDialog(getContentPane(), "Disconnect successfully!");  
                    txt_port.setText("");
                	nameid.setText("");
                	listModel.removeAllElements();
                	closeConnection();        
                	return;
                } catch (Exception exc) {  
                    JOptionPane.showMessageDialog(getContentPane(), exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);  
                }  
            }  
        });
		
		 //click on frame(关闭窗口时事件)  
		this.addWindowListener(new WindowAdapter() {    
	            public void windowClosing(WindowEvent e) {  
	                if (isConnected) {  
	                    closeConnection();//close connection(关闭连接)  
	                }  
	                System.exit(0);//exit(退出程序)  
	            }  
	    }); 
		
		exit.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent arg0) {  
            	 if (isConnected) {  
	                    closeConnection();//close connection(关闭连接)  
	                }  
	                System.exit(0);//exit(退出程序)  
            }  
        });  
	}
	
	public boolean connectServer(int port, String name) { //connect server(连接服务器)  
        try {  

        	InetAddress remoteAddr=InetAddress.getByName(ipAddress.getText().trim());
        	InetAddress localAddr=InetAddress.getLocalHost();       	
            socket = new Socket( remoteAddr, port, localAddr, 6666);//(connect server by port)根据端口号和服务器ip建立连接  ***********************************************************
            writer = new PrintWriter(socket.getOutputStream());  
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
            //(send user_name to server)发送客户端用户基本信息(用户名)  
            sendMessage(name);  
            //start the thread for accepting messages(开启接收消息的线程)  
            messageThread = new MessageThread(reader, contentPane);  
            messageThread.start();  
            isConnected = true;//connected to server(已经连接上了)  
            return true;  
        } catch (Exception e) {  
        	e.printStackTrace();
            contentPane.append("connected to the server of port：" + port + "abortively!" + "\r\n");  
            isConnected = false;//disconnected(未连接上)  
            return false;  
        }  
    }
	
	 //send method(执行发送)  
    public void send() {  
        if (!isConnected) {  
            JOptionPane.showMessageDialog(getContentPane(), "You have not been connected to the server, can't send messages!", "Error", JOptionPane.ERROR_MESSAGE);  
            return;  
        }  
        String message = text.getText().trim();  
        if (message == null || message.equals("")) {  
            JOptionPane.showMessageDialog(getContentPane(), "Messages cann't be none!", "Error", JOptionPane.ERROR_MESSAGE);  
            return;  
        }  
        sendMessage(nameid.getText() + "@" + "ALL" + "@" + message);  
        text.setText(null);  
    }  
    
	private void sendMessage(String string) {
		 writer.println(string);  
	     writer.flush();  
	}
	
	@SuppressWarnings("deprecation")
	public synchronized boolean closeConnection() {
		try {  
			sendMessage("CLOSE");//send disconnect command to the server(发送断开连接命令给服务器)  
	        messageThread.stop();//stop accepting messages thread(停止接受消息线程)  
	        //release resources(释放资源 ) 
	        if (reader != null) {  
	            reader.close();  
	        }  
	        if (writer != null) {  
	            writer.close();  
	        }  
	        if (socket != null) {  
	            socket.close();  
	        }  
	        isConnected = false;  
	        return true;
	        } catch (IOException e1) {  
	        	e1.printStackTrace();  
	            isConnected = true;  
	            return false;  
	        }  
		 }

	
	class MessageThread extends Thread {
		private BufferedReader reader;  
		private JTextArea textArea;  
		
		//create accepting messages thread(接收消息线程的构造方法)  
	    public MessageThread(BufferedReader reader, JTextArea textArea) {  
	        this.reader = reader;  
	        this.textArea = textArea;  
	    }  
	    //disconnected by the server(被动的关闭连接)  
        public synchronized void closeCon() throws Exception {  
            //clear up User_List(清空用户列表)  
            listModel.removeAllElements();  
            //release resources(被动的关闭连接释放资源)  
            if (reader != null) {  
                reader.close();  
            }  
            if (writer != null) {  
                writer.close();  
            }  
            if (socket != null) {  
                socket.close();  
            }  
            isConnected = false;//disconnect state(修改状态为断开)  
        }  
  
        public void run() {  
            String message = "";  
            while (true) {  
                try {  
                    message = reader.readLine();  
                    StringTokenizer stringTokenizer = new StringTokenizer(message, "/@");  
                    String command = stringTokenizer.nextToken();//command(命令)  
                    if (command.equals("CLOSE"))//get close command(服务器已关闭命令)
                    {  
                        textArea.append("The server has been closed!\r\n");  
                        closeCon();//disconnected by the server(被动的关闭连接)  
                        return;//finish the thread(结束线程)  
                    } else if (command.equals("ADD")) {//add new user to User_List(有用户上线更新在线列表 ) 
                        String username = "";  
                        if ((username = stringTokenizer.nextToken()) != null) {  
                            User user = new User(username);   
                            onLineUsers.put(username, user);  
                            listModel.addElement(username);  
                        }  
                    } else if (command.equals("DELETE")) {//delete a user from User_List(有用户下线更新在线列表 ) 
                        String username = stringTokenizer.nextToken();  
                        User user = (User) onLineUsers.get(username);  
                        onLineUsers.remove(user);  
                        listModel.removeElement(username);  
                    } else if (command.equals("USERLIST")) {//update User_List(加载在线用户列表)  
                        int size = Integer.parseInt(stringTokenizer.nextToken());  
                        String username = null;   
                        for (int i = 0; i < size; i++) {  
                            username = stringTokenizer.nextToken();    
                            User user = new User(username);  
                            onLineUsers.put(username, user);  
                            listModel.addElement(username);  
                        }  
                    }else if (command.equals("KICK")) {//kick command(被服务器踢出的用户)
                        String username = stringTokenizer.nextToken();                        
                        String uname = nameid.getText().trim();
                        if(!username.equals(uname)){//kick command to the other user(不是被踢出的用户执行)
                        	User user = (User) onLineUsers.get(username);  
                        	onLineUsers.remove(user);  
                        	listModel.removeElement(username);   
                        	textArea.append(username+" have been kicked by the server!\r\n");
                        }else{//kick command to the kicked user(被踢出的用户执行，结束线程)
                        	textArea.append("You have been kicked by the server!\r\n");  
                        	txt_port.setText("");
                        	nameid.setText("");
                        	listModel.removeAllElements();
                        	closeCon();//disconnected by the server(被动的关闭连接)       
                        	return;
                        }
                    }else {//normal messages(普通消息)  
                        textArea.append(message + "\r\n");  
                    }  
                } catch (IOException e) {  
                    e.printStackTrace();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
	
	
	public static void main(String[] args) {
		System.setProperty("java.net.preferIPv4Stack", "true");
		Userinterface_client frame = new Userinterface_client();
		frame.setVisible(true);
	}

	
}

