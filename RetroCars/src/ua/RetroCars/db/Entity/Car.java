package ua.RetroCars.db.Entity;

import java.io.Serializable;
/**
 * Car entity 
 */
public class Car implements Serializable{
	
	private static final long serialVersionUID = -4454547886758072669L;
	private int id;
	private String producer;
	private String make;
	private String rank;
	private int year;
	private double price;
	private String imageLocAdress;
	private boolean active;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImageLocAdress() {
		return imageLocAdress;
	}
	public void setImageLocAdress(String imageLocAdress) {
		this.imageLocAdress = imageLocAdress;
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "Car [id=" + id + ", producer=" + producer + ", make=" + make
				+ ", rank=" + rank + ", year=" + year + ", price=" + price
				+ ", imageLocAdress=" + imageLocAdress + "]";
	}
	
	
	

}
