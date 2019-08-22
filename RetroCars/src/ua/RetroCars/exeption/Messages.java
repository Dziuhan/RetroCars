package ua.RetroCars.exeption;

/**
 * Holder for messages of exceptions.
 * 
 */
public class Messages {

	private Messages() throws Exception {
		throw new Exception();
	}
	
	public static final String ERR_CANNOT_INSERT_NEW_CAR = "Cannot insert a new car";	
	public static final String ERR_CANNOT_INSERT_NEW_USER = "Cannot insert a new user";	
	public static final String ERR_CANNOT_INSERT_NEW_ORDER = "Cannot insert a new order";	
	public static final String ERR_CANNOT_INSERT_NEW_REVIEW_CAR = "Cannot insert a new car's review";	

	
	public static final String ERR_CANNOT_UPDATE_CAR = "Cannot update a  car";
	public static final String ERR_CANNOT_UPDATE_ORDER = "Cannot update a order";
	public static final String ERR_CANNOT_UPDATE_ORDER_BY_STATE = "Cannot update the order by state";	
	public static final String ERR_CANNOT_UPDATE_USER_BY_LOGIN = "Cannot update the user by login";
	
	
	public static final String ERR_CANNOT_OBTAIN_USERS = "Cannot obtain all users";
	public static final String ERR_CANNOT_OBTAIN_USER_BY_ID = "Cannot obtain a user by id";
	public static final String ERR_CANNOT_OBTAIN_USER_BY_LOGIN = "Cannot obtain a user by login";
	public static final String ERR_CANNOT_OBTAIN_CAR_BY_ID = "Cannot obtain a car by  id";
	public static final String ERR_CANNOT_OBTAIN_CARS = "Cannot obtain all cars";
	public static final String ERR_CANNOT_OBTAIN_PRODUCERS_CAR = "Cannot obtain all car's producers";
	public static final String ERR_CANNOT_OBTAIN_REVIEWS_CAR = "Cannot obtain all car's reviews";
	public static final String ERR_CANNOT_OBTAIN_RANKS_CAR = "Cannot obtain all car's ranks";
	public static final String ERR_CANNOT_OBTAIN_ORDERS = "Cannot obtain all orders";
	public static final String ERR_CANNOT_OBTAIN_ORDERS_BY_LOGIN = "Cannot obtain all orders by login";
	public static final String ERR_CANNOT_OBTAIN_ORDERS_BY_STATE = "Cannot obtain all orders by state";
	public static final String ERR_CANNOT_OBTAIN_CONNECTION = "Cannot obtain a connection from the pool";
	public static final String ERR_CANNOT_OBTAIN_DATA_SOURCE = "Cannot obtain the data source";


	public static final String ERR_CANNOT_CLOSE_CONNECTION = "Cannot close a connection";
	public static final String ERR_CANNOT_CLOSE_RESULTSET = "Cannot close a result set";
	public static final String ERR_CANNOT_CLOSE_STATEMENT = "Cannot close a statement";
	
}