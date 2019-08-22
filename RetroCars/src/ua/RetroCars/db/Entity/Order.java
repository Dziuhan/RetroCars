package ua.RetroCars.db.Entity;

import java.io.Serializable;
import java.sql.Date;

/**
 * Order entity
 */
public class Order implements Serializable {
	private static final long serialVersionUID = 1137395714996084726L;
	
	public static final String STATE_NEW_ORDER="new order";	
	public static final String STATE_WAITING_FOR_PAYMENT="waiting for payment";
	public static final String STATE_PAID="paid";
	public static final String STATE_REJECTED="rejected";
	public static final String STATE_CRUSH_CAR="pay crush car";
	public static final String STATE_PAID_CRUSH_CAR="paid crush car";
	public static final String STATE_CLOSED="closed";

	private int id;
	private String login;
	private String producer;
	private String make;
	private int yearCar ;
	private Date startRent;
	private Date finishRent;
	private boolean driver;
	private String state = "new order";
	private double priceCar;
	private double priceTotal;
	private double priceCrush;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public Date getStartRent() {
		return startRent;
	}

	public void setStartRent(Date startRent) {
		this.startRent = startRent;
	}

	public Date getFinishRent() {
		return finishRent;
	}

	public void setFinishRent(Date finishRent) {
		this.finishRent = finishRent;
	}

	public boolean isDriver() {
		return driver;
	}

	public void setDriver(boolean driver) {
		this.driver = driver;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public int getYearCar() {
		return yearCar;
	}

	public void setYearCar(int yearCar) {
		this.yearCar = yearCar;
	}
	

	public double getPriceCar() {
		return priceCar;
	}

	public void setPriceCar(double priceCar) {
		this.priceCar = priceCar;
	}

	public double getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(double priceTotal) {
		this.priceTotal = priceTotal;
	}
	

	public double getPriceCrush() {
		return priceCrush;
	}

	public void setPriceCrush(double priceCrush) {
		this.priceCrush = priceCrush;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", login=" + login + ", producer="
				+ producer + ", make=" + make + ", yearCar=" + yearCar
				+ ", startRent=" + startRent + ", finishRent=" + finishRent
				+ ", state=" + state + ", priceCar=" + priceCar
				+ ", priceTotal=" + priceTotal + "]";
	}

	

}
