package ua.RetroCars.db;

/**
 *  Holder for fields names of DB tables and beans.
 */
public class Fields {
	
	public static final String ROLES_ID="id";
	public static final String ROLES_ROLE="role";
	
	public static final String USERS_ID="users.id";
	public static final String USERS_LOGIN="login";
	public static final String USERS_PASSWORD="password";
	public static final String USERS_ROLE_ID="roles_id";
	public static final String USERS_BAN="ban";
	
	public static final String CARS_ID="id";
	public static final String CARS_PRODUCER="producer";
	public static final String CARS_MAKE="make";
	public static final String CARS_RANK="rank";
	public static final String CARS_YEAR="year_car";
	public static final String CARS_PRICE="price";
	public static final String CARS_IMAGE_LOC_ADRESS="imageLocAdress";
	public static final String CARS_ACTIVE="active";
	
	public static final String ORDERS_ID="id";
	public static final String ORDERS_USER_ID="iser_id";
	public static final String ORDERS_CAR_ID="car_id";
	public static final String ORDERS_START_RENT="start_rent";
	public static final String ORDERS_FINISH_RENT="finish_rent";
	public static final String ORDERS_PRICE="price";	
	public static final String ORDERS_STATE="state";
	public static final String ORDERS_PRICE_TOTAL="priceTotal";
	public static final String ORDERS_PRICE_CRUSH="priceCrush";
	
	public static final String ORDERS_DRIVER="driver";
	
	public static final String REVIEWS_CAR_ID="id";
	public static final String REVIEWS_CAR_USER_ID="user_id";
	public static final String REVIEWS_CAR_CAR_ID="car_id";
	public static final String REVIEWS_CAR_DATE_REVIEW="dateReview";
	public static final String REVIEWS_CAR_REVIEW="review";
	
}
