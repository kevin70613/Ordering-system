
import java.io.Serializable;

public class Member implements Serializable{
	
	private String username;
    private String passwd;
	
	public Member(String name,String pass){
		this.username = name;
		this.passwd = pass;

	}
    
	public Member(Member obj){
		this.username = obj.getUserName();
		this.passwd = obj.getPassWd();

	}
	
	public String getUserName(){
		return username;
	}
	
	public String getPassWd(){
		return passwd;
	}
	
	public void setUserName(String username)
	{
		this.username = username;
	} 
	
	public void setPassWd(String passwd)
	{
		this.passwd = passwd;
	} 
		
}