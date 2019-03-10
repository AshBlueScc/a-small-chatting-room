package server1;

import java.awt.Button;
import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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

public class Userinterface_server extends JFrame{
	private JTextArea contentPane = new JTextArea();
	private JTextField text = new JTextField();
//	private JTextArea listPane = new JTextArea();
	private JButton confirm = new JButton();
	private JButton exit = new JButton();
	private JButton start = new JButton("start");
	private JButton stop = new JButton("stop");
	private JButton kick = new JButton("tick");
	private JButton states = new JButton("state");
	private JTextArea txt_port=new JTextArea();
	private ServerSocket serverSocket;  
	private ServerThread serverThread; 
	private ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	private boolean isStart = false;  
	private DefaultListModel listModel;//create and set data model(�������������б�����ģ��)
	private JList userList;
	
	private JLabel label;
	
	//confirm events
	public void send(){
		if(!isStart){
			JOptionPane.showMessageDialog(getContentPane(), "Server has not been started, you can not send messages!","Error",JOptionPane.ERROR_MESSAGE);//showMessageDialog(Component parentComponent, Object message, String title, int messageType)
			return;
		}
		if (clients.size() == 0) {  
            JOptionPane.showMessageDialog(getContentPane(), "No users is online, you can not send messages!", "Error", JOptionPane.ERROR_MESSAGE);  
            return;  
        }  
		String message = text.getText().trim();//get a copy without space in head or tail(�õ�һ��ȥ��ͷ��β�ո��text����)
		if (message == null || message.equals("")) {  
            JOptionPane.showMessageDialog(getContentPane(), "Messages can not be none!", "Error", JOptionPane.ERROR_MESSAGE);  
            return;  
        }  
		sendServerMessage(message);//send messages to all users(Ⱥ����������Ϣ)
		contentPane.append("Server��" + text.getText() + "\r\n"); 
		text.setText("");
	}
	
	 // send messages to all users(Ⱥ����������Ϣ)  
    public void sendServerMessage(String message) {  
        for (int i = clients.size() - 1; i >= 0; i--) {  
            clients.get(i).getWriter().println("Server��" + message + "(to all)");  
            clients.get(i).getWriter().flush();  
        }  
    }  

	//Constructor(������)
	public Userinterface_server(){
		super();
		setBounds(100, 100, 500, 375);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setTitle("ChatApplication_server");
		
		listModel = new DefaultListModel();  
        userList = new JList(listModel);  

		getContentPane().setLayout(null);
		
		ImageIcon image1 = new ImageIcon("Icon\\background8.JPG");
		label = new JLabel(image1);
		label.setBounds(0, 0, 500, 350);
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		((JPanel)this.getContentPane()).setOpaque(false);
		
		
		
		
		JLabel jl=new JLabel("Port");
		getContentPane().add(jl);
		jl.setBounds(0,0,60,60);
		
		
		getContentPane().add(txt_port);
		txt_port.setBounds(60,20,60,23);
		
		getContentPane().add(start);
		start.setBounds(280,20,80,33);
		
		getContentPane().add(stop);
		stop.setBounds(380,20,80,33);
			
		JScrollPane scrollpane = new JScrollPane(contentPane);
		contentPane.setEditable(false);
		contentPane.setForeground(Color.blue);  
		scrollpane.setBorder(new TitledBorder("Text_Area")); 
		getContentPane().add(scrollpane);
		scrollpane.setBounds(0, 75, 348, 168);
		scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JScrollPane scrollpanetext = new JScrollPane(text);
		scrollpanetext.setBorder(new TitledBorder("Broadcast")); 
		getContentPane().add(scrollpanetext);
		scrollpanetext.setBounds(0, 244, 350, 42);
		
		JScrollPane scrollpanelistPane = new JScrollPane(userList);
		scrollpanelistPane.setBorder(new TitledBorder("Online_List")); 
	//	listPane.setEditable(false);
		getContentPane().add(scrollpanelistPane);
		scrollpanelistPane.setBounds(350, 75, 100, 180);
		scrollpanelistPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		getContentPane().add(kick);
		kick.setBounds(350, 260, 100, 28);
		
		getContentPane().add(states);
		states.setBounds(350, 290, 100, 28);
		
		getContentPane().add(confirm);
		confirm.setText("send");
		confirm.setBounds(36, 292, 100, 28);
		
		getContentPane().add(exit);
		exit.setText("Exit");
		exit.setBounds(150, 292, 106, 28);
		
//		kick.addActionListener(new ActionListener() {
//	    	   @Override
//				public void actionPerformed(ActionEvent e) {
//	    		   if(userList.getSelectedValue()==null){
//	    			   JOptionPane.showMessageDialog(getContentPane(), "����ѡ��Ҫ�߳����û���", "����", JOptionPane.ERROR_MESSAGE); 
//	    			   return;
//	    		   }
//	    		   clients.get(userList.getSelectedIndex()).getWriter().println("CLOSE"); 
//	    	    }
//	     });
		
		//click on frame(�رմ���ʱ�¼�)  
		this.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {  
                if (isStart) {  
                    closeServer();//close the server(�رշ�����)  
                }  
                System.exit(0);//exit(�˳�����)  
            }  
        });  
		
		//Use Enter on keyboard(�ı��򰴻س���ʱ�¼�)  
        text.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                send();  
            }  
        });  
		
       //click on send(�������Ͱ�ťʱ�¼�)	
       confirm.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent arg0) {  
                send();  
            }  
        });  
       
       //click on start event(����������������ťʱ�¼�)  
       start.addActionListener(new ActionListener() {
    	   @Override
			public void actionPerformed(ActionEvent e) {
    		   if (isStart) {  
                   JOptionPane.showMessageDialog(getContentPane(), "Server is starting, you can not start again!", "Error", JOptionPane.ERROR_MESSAGE);  
                   return;  
               }  
    		   int port;
    		   try {
    			   try {  
                       port = Integer.parseInt(txt_port.getText());  
                   } catch (Exception e1) {  
                       throw new Exception("Port number should be positive integer��");  
                   }  
                   if (port <= 0) {  
                       throw new Exception("Port number should be positive integer��");  
                   }  
                   serverStart(port);
                   contentPane.append("Server starts successfully!" + ",port��" + port   + "\r\n");
                   JOptionPane.showMessageDialog(getContentPane(), "Server starts successfully!"); 
                   start.setEnabled(false);
                   txt_port.setEnabled(false); 
                   stop.setEnabled(true); 
    		   	   }catch (Exception exc) {  
    		   		   JOptionPane.showMessageDialog(getContentPane(), exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);  
    		   	   }  		
    	   		}  
       
       });
       
       //click on stop event(����ֹͣ��������ťʱ�¼�)  
       stop.addActionListener(new ActionListener() {
		@Override
	       public void actionPerformed(ActionEvent arg0) {
		       if (!isStart) {  
		    	   JOptionPane.showMessageDialog(getContentPane(), "Server has not been started, you needn't stop!", "Error", JOptionPane.ERROR_MESSAGE);  
		    	   return;  
		       } 
		       try {  
                   closeServer();  
                   start.setEnabled(true);                  
                   txt_port.setEnabled(true);  
                   stop.setEnabled(false);  
                   contentPane.append("Stop the server successfully!\r\n");  
                   JOptionPane.showMessageDialog(getContentPane(), "Stop the server successfully!");  
               } catch (Exception exc) {  
                   JOptionPane.showMessageDialog(getContentPane(), "Stop the server in exception!", "Error", JOptionPane.ERROR_MESSAGE);  
               }              		       
		   } 
       });   
       
       //click on kick event(�߳��û�)      
   			kick.addActionListener(new ActionListener() {
   				@Override
   				public void actionPerformed(ActionEvent e) {    
   				if(clients.size()!=0){
   					int j=userList.getSelectedIndex();
   					String usname=clients.get(j).getUser().getName();
   					contentPane.append(clients.get(j).getUser().getName()+" was kicked off by the server!\r\n"); 
   					listModel.removeElement(clients.get(j).getUser().getName());// ���������б�   
   					//broadcast to all others that the user has been kicked off(�����������û����͸��û�������������)  
   						for (int i = clients.size() - 1; i >= 0; i--) {  
   							clients.get(i).getWriter().println("KICK@" +usname);  
   							clients.get(i).getWriter().flush();  
   						}                       		
   						//delete the kicked user's thread in server(ɾ�������ͻ��˷����߳�)  
   						for (int i = clients.size() - 1; i >= 0; i--) {  
   							if (clients.get(i).getUser().getName() == usname) {  
   								ClientThread temp = clients.get(i);  
   								clients.remove(i);// delete the kicked user's thread in serve(ɾ�����û��ķ����߳�)  
   								temp.stop();//stop the kicked user's thread in serve(ֹͣ���������߳�)  
   								return;  
   							}  
   						}   						         				                                          
   					}
   				}
   			});
   			
   		//(show how many times the user used commands)�鿴�û�ʹ�ù�������
   			states.addActionListener(new ActionListener() {
   				@Override
   				public void actionPerformed(ActionEvent e) {    
   				if(clients.size()!=0){
   					int j=userList.getSelectedIndex();
   				//	String usname=clients.get(j).getUser().getName();
   					JOptionPane.showMessageDialog(getContentPane(), "BROADCAST:"+clients.get(j).getUser().getStates(), "Message", JOptionPane.INFORMATION_MESSAGE); 
   					}
   				}
   			});
   			
   		//exit(�˳�)
   			exit.addActionListener(new ActionListener() {
   				@Override
   				public void actionPerformed(ActionEvent e) {    
   					if (isStart) {  
   	                    closeServer();//closeServer(�رշ�����)  
   	                }  
   	                System.exit(0);//exit(�˳�����)  
   					
   				}
   			});
       
	}
	
	
	class ServerThread extends Thread {  
	        private ServerSocket serverSocket;  
	  
	        //create server thread(�������̵߳Ĺ��췽��)  
	        public ServerThread(ServerSocket serverSocket) {  
	            this.serverSocket = serverSocket;  
	        }
	        
	        public void run() {  
	            while (true) {//accept connections from users continually(��ͣ�ĵȴ��ͻ��˵�����)  
	                try {  
	                    Socket socket = serverSocket.accept();  
//	                    if (clients.size() == max) {// ����Ѵ���������  
//	                        BufferedReader r = new BufferedReader(  
//	                                new InputStreamReader(socket.getInputStream()));  
//	                        PrintWriter w = new PrintWriter(socket  
//	                                .getOutputStream());  
//	                        // ���տͻ��˵Ļ����û���Ϣ  
//	                        String inf = r.readLine();  
//	                        StringTokenizer st = new StringTokenizer(inf, "@");  
//	                        User user = new User(st.nextToken(), st.nextToken());  
//	                        // �������ӳɹ���Ϣ  
//	                        w.println("MAX@���������Բ���" + user.getName()  
//	                                + user.getIp() + "�����������������Ѵ����ޣ����Ժ������ӣ�");  
//	                        w.flush();  
//	                        // �ͷ���Դ  
//	                        r.close();  
//	                        w.close();  
//	                        socket.close();  
//	                        continue;  
//	                    }  
	                    ClientThread client = new ClientThread(socket);  
	                    client.start();//start to serve the client connected(�����Դ˿ͻ��˷�����߳�)  
	                    clients.add(client);  
	                    listModel.addElement(client.getUser().getName());//update Online_List(���������б�)  
	                    contentPane.append(client.getUser().getName() + " is on line!\r\n");  	                    	                                   		                       
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                }  
	            }	           	            
	        }  
	    }  
	
	
	// start the server(����������) 
	protected void serverStart(int port) throws java.net.BindException { 
		try {  
		//	clients = new ArrayList<ClientThread>();  
	        serverSocket = new ServerSocket(port);  
	        serverThread = new ServerThread(serverSocket);  
	        serverThread.start();  
	        isStart = true;  
	        } catch (BindException e) {  
	            isStart = false;  
	            throw new BindException("Port exists, try another!");  
	        } catch (Exception e1) {  
	            e1.printStackTrace();  
	            isStart = false;  
	            throw new BindException("Start the server in exception!");  
	        } 
	}

	@SuppressWarnings("deprecation")
	protected void closeServer() {
		try {  
			if (serverThread != null)  
				serverThread.stop();//stop server thread(ֹͣ�������߳�  )
	            for (int i = clients.size() - 1; i >= 0; i--) {  
	                //send close command to all online users(�����������û����͹ر�����)  
	                clients.get(i).getWriter().println("CLOSE");  
	                clients.get(i).getWriter().flush();  
	                //(release resources)�ͷ���Դ  
	                clients.get(i).stop();//(stop the thread served for the client)ֹͣ����Ϊ�ͻ��˷�����߳�  
	                clients.get(i).reader.close();  
	                clients.get(i).writer.close();  
	                clients.get(i).socket.close();  
	                clients.remove(i);  
	            }  
	            if (serverSocket != null) {  
	                serverSocket.close();//close the server(�رշ�����������) 
	            }  
	            listModel.removeAllElements();//clean up the Online_List(����û��б�)  
	            isStart = false;  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	            isStart = true;  
	        } 		
	}
	 
	class ClientThread extends Thread {
		private Socket socket;  
        private BufferedReader reader;  
        private PrintWriter writer;  
        private User user;  
  
        public BufferedReader getReader() {  
            return reader;  
        }  
  
        public PrintWriter getWriter() {  
            return writer;  
        }  
  
        public User getUser() {  
            return user;  
        }  
        //(construct the thread of clients)�ͻ����̵߳Ĺ��췽��  
        public ClientThread(Socket socket) {  
            try {  
                this.socket = socket;  
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
                writer = new PrintWriter(socket.getOutputStream());  
                //(accept the user information)���տͻ��˵Ļ����û���Ϣ  
                String inf = reader.readLine();  
                StringTokenizer st = new StringTokenizer(inf, "@");  //split the command and the user name(����split������Ϊ�ֽ��)
                user = new User(st.nextToken());  
                writer.println(user.getName() + " connects the server successfully!");
                writer.flush(); 
                //(send the message to all online users)�������ӳɹ���Ϣ-----------------//  
                if (clients.size() > 0) {  
                    for (int i = clients.size() - 1; i >= 0; i--) {  
                    	clients.get(i).getWriter().println(user.getName() + " connects the server successfully!");  
                    	clients.get(i).getWriter().flush();   
                    }    
                } 
                                  
                //(tell the online users to update their Online_List)������ǰ�����û���Ϣ --------------// 
                if (clients.size() > 0) {  
                    String temp = "";  
                    for (int i = clients.size() - 1; i >= 0; i--) {  
                        temp += clients.get(i).getUser().getName() + "@";  
                    }  
                    writer.println("USERLIST@" + clients.size() + "@" + temp);  
                    writer.flush();  
                }  
                //(tell the online users to add the new user to their Online_List)�����������û����͸��û���������  
                for (int i = clients.size() - 1; i >= 0; i--) {  
                    clients.get(i).getWriter().println("ADD@" + user.getName());  
                    clients.get(i).getWriter().flush();  
                }                            	
            } catch (IOException e) {  
                e.printStackTrace();  
            }              
        }
        
        
        
        @SuppressWarnings("deprecation")  
        public void run() {//(accept messages from users continually and deal with them)���Ͻ��տͻ��˵���Ϣ�����д���  
            String message = null;  
            while (true) {  
                try {  
                    message = reader.readLine();//(accept messages from users)���տͻ�����Ϣ  
                    user.setStates();
                    if (message.equals("CLOSE"))//(the user tell the server it is off line)��������  
                    {  
                    	contentPane.append(this.getUser().getName()+" is off line!\r\n");  
                        //(disconnect and release resources)�Ͽ������ͷ���Դ                      	                   	
                        reader.close();  
                        writer.close();  
                        socket.close();  
  
                        //(tell all users that it was off line)�����������û����͸��û����������� 
                        for (int i = clients.size() - 1; i >= 0; i--) {  
                            clients.get(i).getWriter().println("DELETE@" + user.getName());  
                        	clients.get(i).getWriter().println(this.getUser().getName() + " disconnects to the server initiatively!");  
                            clients.get(i).getWriter().flush();  
                        }    
                        listModel.removeElement(user.getName());//(update the Online_List)���������б�  
  
                        //(delete the server thread for the client)ɾ�������ͻ��˷����߳�  
                        for (int i = clients.size() - 1; i >= 0; i--) {  
                            if (clients.get(i).getUser() == user) {  
                                ClientThread temp = clients.get(i);  
                                clients.remove(i);//(delete the server thread for the client)ɾ�����û��ķ����߳�  
                                temp.stop();//(stop the server thread for the client)ֹͣ���������߳�  
                                return;  
                            }  
                        } 
                    } else {  
                        dispatcherMessage(message);//(dispatcher normal messages which are not commands)ת����Ϣ  
                    }  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }

		private void dispatcherMessage(String message) {
			StringTokenizer stringTokenizer = new StringTokenizer(message, "@");  
            String source = stringTokenizer.nextToken();  
            String owner = stringTokenizer.nextToken();  
            String content = stringTokenizer.nextToken();  
            message = source + " say��" + content;  
            contentPane.append(message + "\r\n");  
            if (owner.equals("ALL")) {//(to all users)Ⱥ��  
                for (int i = clients.size() - 1; i >= 0; i--) {  
                    clients.get(i).getWriter().println(message + "(to all users)");  
                    clients.get(i).getWriter().flush();  
                }  
            }  		
		}  
        
	}
			
	public static void main(String[] args) {
		Userinterface_server frame = new Userinterface_server();
		frame.setVisible(true);
	}

	
}
