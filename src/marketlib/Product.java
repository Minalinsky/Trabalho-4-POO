package marketlib;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Product implements Serializable{

	private String name;
	private String ID;
	private float price;
	private String expirationDate;
	private String provider;
	private int quantity;
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public void setID(String ID) {
		this.ID = ID;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
	
	
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	public String getName() {
		return name;
	}
	
	
	public String getID() {
		return ID;
	}
	
	public float getPrice() {
		return price;
	}
	
	
	public String getExpirationDate() {
		return expirationDate;
	}
	
	
	public String getProvider() {
		return provider;
	}
	
	
	public int getQuantity() {
		return quantity;
	}
}
