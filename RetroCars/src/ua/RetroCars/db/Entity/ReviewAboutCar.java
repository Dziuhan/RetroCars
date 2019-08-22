package ua.RetroCars.db.Entity;

import java.sql.Date;

/**
 * Review about car entity
 */
public class ReviewAboutCar {
	private int id;
	private String login;
	private int idCar;
	private Date dateReview;
	private String review;
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
	public int getIdCar() {
		return idCar;
	}
	public void setIdCar(int idCar) {
		this.idCar = idCar;
	}
	public Date getDateReview() {
		return dateReview;
	}
	public void setDateReview(Date dateReview) {
		this.dateReview = dateReview;
	}
	
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}	
	
}
