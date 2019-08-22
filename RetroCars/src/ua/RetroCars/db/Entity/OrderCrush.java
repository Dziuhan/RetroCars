package ua.RetroCars.db.Entity;

public class OrderCrush {
	private int id;
	private double priceCrush;
	private String stateCrush;
	private int idOrder;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getPriceCrush() {
		return priceCrush;
	}
	public void setPriceCrush(double priceCrush) {
		this.priceCrush = priceCrush;
	}
	public String getStateCrush() {
		return stateCrush;
	}
	public void setStateCrush(String stateCrush) {
		this.stateCrush = stateCrush;
	}
	public int getIdOrder() {
		return idOrder;
	}
	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}
	@Override
	public String toString() {
		return "OrderCrush [id=" + id + ", priceCrush=" + priceCrush
				+ ", stateCrush=" + stateCrush + ", idOrder=" + idOrder + "]";
	}
	
	
}
