package user;

//用户信息类  
public class User{  
	private String name;  
//	private String ip; 
	private int states=0;  
	

	public User(String name) {  
		this.name = name;  
		this.states = states;
	//	this.ip = ip;  
	}  

	public String getName() {  
		return name;  
	}  
	
	public int getStates() {  
		return states;  
	} 

	public void setName(String name) {  
		this.name = name;  
	}  
	
	public void setStates() {  
		this.states = states+1;  
		
	} 
	
//	public String getIp() {  
//		return ip;  
//	}  
//	
//	public void setIp(String ip) {  
//		this.ip = ip;  
//	}  
}  