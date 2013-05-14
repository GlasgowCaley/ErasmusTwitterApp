/**
 * This class represents direct messages sent to the authenticated user
 * @author Vincent
 *
 */
public class DirectMessage{
	
	/*Attributes*/
	
	private String text; //Text of the message
	private User sender; //The sender
	private String created_at; //Date of the creation of the message
	private Entities entities; 
	private String id_str;
	
	/*Getters*/
	public String getMsg(){
		return this.text;
	}
	
	public User getSender(){
		return this.sender;
	}
	
	public String date(){
		return this.created_at;
	}
	
	public Entities getEnt(){
		return this.entities;
	}
	
	public String getId(){
		return this.id_str;
	}
	
	public String toString(){
		StringBuffer s = new StringBuffer();
		s.append("From : "+sender.getScreenName()+"\n");
		s.append("Message : "+text+"\n");
		s.append("\nAt : "+created_at);
		
		return s.toString();
	}
}