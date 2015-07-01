package marketlib;

import java.io.Serializable;

public class Costumer implements Serializable{

	
	//private static final long serialVersionUID = -991896904875776005L;
	private String name;
	private String address;
	private String phone;
	private String email;
	private String ID;
	private String password;
	
	
	public Costumer() {
		
	}
		

	public void setName(String name) {
		this.name = name;
	}	
	
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public void setID(String ID) {
		this.ID = ID;
	}
	
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getName() {
		return name;
	}
	
	
	public String getAddress() {
		return address;
	}
	
	
	public String getPhone() {
		return phone;
	}
	
	
	public String getEmail() {
		return email;
	}
	
	
	public String getID() {
		return ID;
	}
	
	
	public String getPassword() {
		return password;
	}
}
