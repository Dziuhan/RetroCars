package ua.RetroCars.db;

import static ua.RetroCars.db.Fields.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.RetroCars.db.Entity.Car;
import ua.RetroCars.db.Entity.Order;
import ua.RetroCars.db.Entity.ReviewAboutCar;
import ua.RetroCars.db.Entity.User;
import ua.RetroCars.exeption.DBException;
import ua.RetroCars.exeption.Messages;

/**
 * DB manager. Works with Apache Derby DB.
 */
public class DBManager {

	private static final String SQL_SELECT_ALL_CARS = "SELECT * FROM cars";
	private static final String SQL_SELECT_ALL_PRODUCERS_CAR = "SELECT DISTINCT producer FROM cars ORDER BY producer";
	private static final String SQL_SELECT_ALL_RANKS_CAR = "SELECT DISTINCT rank FROM cars ORDER BY rank";
	private static final String SQL_SELECT_CARS_BY_PRODUCERS = "SELECT * FROM cars WHERE ";
	private static final String SQL_SELECT_CAR_BY_ID = "SELECT * FROM cars where id=?";
	private static final String SQL_INSERT_CAR = "INSERT INTO cars VALUES(DEFAULT,?,?,?,?,?,?,?)";
	private static final String SQL_UPDATE_CAR = "UPDATE cars SET producer=?, make=?,rank=?,year_car=?, price=?, imageLocAdress=?, active=? where id=?";
	private static final String SQL_DELETE_CAR_BY_ID = "DELETE FROM cars where id=?";
	
	private static final String SQL_SELECT_ALL_USER = "SELECT * FROM users left join roles on role_id=roles.id";
	private static final String SQL_SELECT_USER = "SELECT * FROM users left join roles on role_id=roles.id where login=?";
	private static final String SQL_SELECT_USER_BY_LIKE_LOGIN = "SELECT * FROM users left join roles on role_id=roles.id where login LIKE ?";
	private static final String SQL_INSERT_USER = "INSERT INTO users(login, password, role_id) select ?,?,id from roles where role=?";
	private static final String SQL_UPDATE_ROLE_USER = "UPDATE users SET role_id=(SELECT id FROM roles WHERE role=?) WHERE login=?";
	private static final String SQL_UPDATE_BAN_USER = "UPDATE users SET ban=? where login=?";
		
	private static final String SQL_SELECT_ALL_ORDERS = "SELECT * FROM orders LEFT JOIN users ON user_id=users.id LEFT JOIN cars ON car_id=cars.id";
	private static final String SQL_SELECT_ALL_ORDERS_BY_LOGIN = "SELECT * FROM orders LEFT JOIN users ON user_id=users.id LEFT JOIN cars ON car_id=cars.id WHERE login=?";
	private static final String SQL_SELECT_ORDERS_BY_LOGIN = "SELECT * FROM orders LEFT JOIN users ON user_id=users.id LEFT JOIN cars ON car_id=cars.id  WHERE login=? AND state IN (?,?,?) ORDER BY state";
	
	private static final String SQL_SELECT_ORDERS_BY_STATE = "SELECT * FROM orders LEFT JOIN users ON user_id=users.id LEFT JOIN cars ON car_id=cars.id WHERE state=?";
	private static final String SQL_SELECT_ORDERS_CLOSE = "SELECT * FROM orders LEFT JOIN users ON user_id=users.id LEFT JOIN cars ON car_id=cars.id WHERE (state=? AND finish_rent <= ? ) OR(state=?)";
	private static final String SQL_SELECT_ORDER_BY_ID = "SELECT * FROM orders LEFT JOIN users ON user_id=users.id LEFT JOIN cars ON car_id=cars.id WHERE orders.id=?";
	private static final String SQL_INSERT_ORDER = "INSERT INTO orders( user_id,car_id,start_rent,finish_rent,driver,priceTotal) select users.id,?, ?, ?, ?, ? from users where login=? ";
	private static final String SQL_UPDATE_ORDER = "UPDATE orders SET state=? WHERE id=?";
	private static final String SQL_UPDATE_ORDER_PRICE_CRUSH = "UPDATE orders SET state=?, priceCrush=? WHERE id=?";

	private static final String SQL_SELECT_REVIEWS_CAR_BY_ID = "SELECT * FROM reviewsCars LEFT JOIN users ON user_id=users.id WHERE car_id=? ORDER BY dateReview DESC";
	private static final String SQL_INSERT_REVIEW_ABOUT_CAR = "INSERT INTO reviewsCars( user_id, car_id, dateReview, review) select users.id, ?, ?, ? from users where login=?";

	
	private static final String SQL_COUNT_ORDERS_BY_STATE_PRODUCER="SELECT count (orders.id) FROM orders LEFT JOIN cars ON orders.car_id = cars.id WHERE state=? and cars.producer=?";
	private static final String SQL_SELECT_LOGINS_ORDERS_BY_STATE_PRODUCER="SELECT users.login FROM orders LEFT JOIN cars ON orders.car_id = cars.id LEFT JOIN users ON orders.user_id=users.id WHERE state=? and cars.producer=?";
	
	
	private static final Logger LOG = Logger.getLogger(DBManager.class);
	private static DBManager instance;
	private DataSource ds;
	
	/**
	 *  Singleton
	 * @return
	 * @throws DBException
	 */

	public static synchronized DBManager getInstance() throws DBException {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}
	private DBManager() throws DBException {
		Context initCtx;
		Context envCtx;
		try {
			initCtx = new InitialContext();
			envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup("jdbc/SummaryTask4");
			LOG.trace("Data source ==> " + ds);
		} catch (NamingException e) {
			LOG.error("ERR_CANNOT_OBTAIN_DATA_SOURCE", e);
			throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, e);
		}

	}
	
	public int selectCountOrdersByStateProducer(String state, String producer) {		 
		Connection con = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		int count=0;
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_COUNT_ORDERS_BY_STATE_PRODUCER);
				pstat.setString(1, state);
				pstat.setString(2, producer);
				rs = pstat.executeQuery();
				if (rs.next()) {
					count = rs.getInt(1);
				}
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_OBTAIN_CAR_BY_ID, ex);
				throw new DBException(Messages.ERR_CANNOT_OBTAIN_CAR_BY_ID, ex);
			} finally {
				close(rs);
				close(pstat);
			}
		} finally {
			close(con);
		}
		return count;
	}
	
	public List<String> selectLoginsOrdersByStateProducer(String state, String producer) {		 
		Connection con = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		List<String> list=new ArrayList<>();
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_SELECT_LOGINS_ORDERS_BY_STATE_PRODUCER);
				pstat.setString(1, state);
				pstat.setString(2, producer);
				rs = pstat.executeQuery();
				while (rs.next()) {
					list.add(rs.getString(USERS_LOGIN));
				}
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_OBTAIN_CAR_BY_ID, ex);
				throw new DBException(Messages.ERR_CANNOT_OBTAIN_CAR_BY_ID, ex);
			} finally {
				close(rs);
				close(pstat);
			}
		} finally {
			close(con);
		}
		return list;
	}

	/**
	 * 	 Inserts a car with given parameters
	 */
	public void createNewCar(String producer, String make, String rank,
			int year, double price, String imageLocAdress,boolean active) throws DBException {
		Connection con = null;
		PreparedStatement pstat = null;
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_INSERT_CAR);
				pstat.setString(1, producer);
				pstat.setString(2, make);
				pstat.setString(3, rank);
				pstat.setDouble(4, price);
				pstat.setInt(5, year);
				pstat.setString(6, imageLocAdress);
				pstat.setBoolean(7,active);
				pstat.execute();
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_INSERT_NEW_CAR, ex);
				throw new DBException(Messages.ERR_CANNOT_INSERT_NEW_CAR, ex);
			} finally {
				close(pstat);
			}
		} finally {
			close(con);
		}
	}
/**
 * Update a car 
 */
	public void updateCar(String producer, String make, String rank, int year,
			double price, int id, String imageLocAdress,boolean active) {
		Connection con = null;
		PreparedStatement pstat = null;
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_UPDATE_CAR);
				pstat.setString(1, producer);
				pstat.setString(2, make);
				pstat.setString(3, rank);
				pstat.setInt(4, year);
				pstat.setDouble(5, price);
				pstat.setString(6, imageLocAdress);
				pstat.setBoolean(7,active);
				pstat.setInt(8, id);
				pstat.execute();
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_UPDATE_CAR, ex);
				throw new DBException(Messages.ERR_CANNOT_UPDATE_CAR, ex);
			} finally {
				close(pstat);
			}

		} finally {
			close(con);
		}
	}

	/**
	 * Returns a car with given identifier
	 */
  	public Car selectCarById(int id) {
	 
		Connection con = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		Car car = null;
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_SELECT_CAR_BY_ID);
				pstat.setInt(1, id);
				rs = pstat.executeQuery();
				while (rs.next()) {
					car = createCar(rs);
				}
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_OBTAIN_CAR_BY_ID, ex);
				throw new DBException(Messages.ERR_CANNOT_OBTAIN_CAR_BY_ID, ex);
			} finally {
				close(rs);
				close(pstat);
			}
		} finally {
			close(con);
		}
		return car;
	}
	  	
	/**
	 * Returns all car's producers
	 */
	public List<String> selectAllProducersCar() {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		List<String> producers = new ArrayList<>();
		try {
			con = getConnection();
			try {
				stat = con.createStatement();
				rs = stat.executeQuery(SQL_SELECT_ALL_PRODUCERS_CAR);
				while (rs.next()) {
					producers.add(rs.getString("producer"));
				}
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_OBTAIN_PRODUCERS_CAR, ex);
				throw new DBException(Messages.ERR_CANNOT_OBTAIN_PRODUCERS_CAR,
						ex);
			} finally {
				close(rs);
				close(stat);
			}
		} finally {
			close(con);
		}
		return producers;
	}

	/**
	 * Returns all car'c ranks
	 */
	public List<String> selectAllRanksCar() {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		List<String> producers = new ArrayList<>();
		try {
			con = getConnection();
			try {
				stat = con.createStatement();
				rs = stat.executeQuery(SQL_SELECT_ALL_RANKS_CAR);
				while (rs.next()) {
					producers.add(rs.getString("rank"));
				}
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_OBTAIN_RANKS_CAR, ex);
				throw new DBException(Messages.ERR_CANNOT_OBTAIN_RANKS_CAR, ex);
			} finally {
				close(rs);
				close(stat);
			}
		} finally {
			close(con);
		}
		return producers;
	}

	/**
	 * Returns all cars
	 */
	public List<Car> selectAllCars() {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		Car car;
		List<Car> cars = new ArrayList<>();
		try {
			con = getConnection();
			try {
				stat = con.createStatement();
				rs = stat.executeQuery(SQL_SELECT_ALL_CARS);
				while (rs.next()) {
					car = createCar(rs);
					cars.add(car);
				}
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_OBTAIN_CARS, ex);
				throw new DBException(Messages.ERR_CANNOT_OBTAIN_CARS, ex);
			} finally {
				close(rs);
				close(stat);
			}
		} finally {
			close(con);
		}
		return cars;
	}
	
	/**
	 * Returns cars with given producers and ranks	
	 */
	public List<Car> selectCarsByProducersRanks(String[] producers,
			String[] ranks) {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		Car car;
		List<Car> cars = new ArrayList<>();
		try {
			con = getConnection();
			try {
				stat = con.createStatement();
				StringBuilder sql = new StringBuilder(
						SQL_SELECT_CARS_BY_PRODUCERS);
				if (producers != null) {
					sql.append("(producer='" + producers[0] + "'");
					for (int i = 1; i < producers.length; i++) {
						sql.append("or producer='" + producers[i] + "'");
					}
					sql.append(")");
					if (ranks != null) {
						sql.append("AND");
						sqlAppendRanks(sql, ranks);
					}

				} else if (ranks != null) {
					sqlAppendRanks(sql, ranks);
				} else {
					return selectAllCars();
				}
				rs = stat.executeQuery(sql.toString());
				while (rs.next()) {
					car = createCar(rs);
					cars.add(car);
				}
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_OBTAIN_CARS, ex);
				throw new DBException(Messages.ERR_CANNOT_OBTAIN_CARS, ex);
			} finally {
				close(rs);
				close(stat);
			}
		} finally {
			close(con);
		}
		return cars;
	}
	
	private void sqlAppendRanks(StringBuilder sql, String[] ranks) {
		sql.append("(rank='" + ranks[0] + "'");
		for (int i = 1; i < ranks.length; i++) {
			sql.append("or rank='" + ranks[i] + "'");
		}
		sql.append(")");
	}
	
	public void deleteCarById(int id) {		 
		Connection con = null;
		PreparedStatement pstat = null;	
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_DELETE_CAR_BY_ID);
				pstat.setInt(1, id);
				pstat.execute();				
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_OBTAIN_CAR_BY_ID, ex);
				throw new DBException(Messages.ERR_CANNOT_OBTAIN_CAR_BY_ID, ex);
			} finally {				
				close(pstat);
			}
		} finally {
			close(con);
		}	
	}
	
	/**
	 * Extracts a car entity from the result set.
	 */
	private Car createCar(ResultSet rs) throws SQLException {
		Car car = new Car();
		car.setId(rs.getInt(CARS_ID));
		car.setProducer(rs.getString(CARS_PRODUCER));
		car.setMake(rs.getString(CARS_MAKE));
		car.setRank(rs.getString(CARS_RANK));
		car.setYear(rs.getInt(CARS_YEAR));
		car.setPrice(rs.getDouble(CARS_PRICE));
		car.setImageLocAdress(rs.getString(CARS_IMAGE_LOC_ADRESS));
		car.setActive(rs.getBoolean(CARS_ACTIVE));
		return car;
	}
	
	/**
	 * Returns all users
	 */
	public List<User> selectAllUsers() {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<>();
		User user = null;
		try {
			con = getConnection();
			try {
				stat = con.createStatement();
				rs = stat.executeQuery(SQL_SELECT_ALL_USER);
				while (rs.next()) {
					user = extractUser(rs);
					users.add(user);
				}
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_OBTAIN_USERS, ex);
				throw new DBException(Messages.ERR_CANNOT_OBTAIN_USERS, ex);
			} finally {
				close(rs);
				close(stat);
			}
		} finally {
			close(con);
		}
		return users;
	}

	/**
	 * Returns the user with given login
	 */
	public User selectUserByLogin(String login) throws DBException {
		Connection con = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		User user = null;
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_SELECT_USER);
				pstat.setString(1, login);				
				rs = pstat.executeQuery();
				if (rs.next()) {					
					user = extractUser(rs);
				}
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, ex);
				throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN,
						ex);
			} finally {
				close(rs);
				close(pstat);
			}
		} finally {
			close(con);
		}
		return user;
	}

	/**
	 * Returns users with login like	 
	 */
	public List<User> searchUserByLogin(String login) {
		Connection con = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		User user = null;
		List<User> users = new ArrayList<>();
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_SELECT_USER_BY_LIKE_LOGIN);
				pstat.setString(1, "%" + login + "%");
				
				rs = pstat.executeQuery();
				while (rs.next()) {				
					user = extractUser(rs);
					users.add(user);
				}
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_OBTAIN_USERS, ex);
				throw new DBException(Messages.ERR_CANNOT_OBTAIN_USERS, ex);
			} finally {
				close(rs);
				close(pstat);
			}
		} finally {
			close(con);
		}
		return users;
	}

	/**
	 * Inserts the user to db
	 * @param login
	 * @param password
	 */
	public void createNewUser(String login, String password) {
		Connection con = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_INSERT_USER);
				pstat.setString(1, login);
				pstat.setString(2, password);
				pstat.setString(3, "client");
				pstat.execute();
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_INSERT_NEW_USER, ex);
				throw new DBException(Messages.ERR_CANNOT_INSERT_NEW_USER, ex);
			} finally {
				close(rs);
				close(pstat);
			}
		} finally {
			close(con);
		}
	}

	/**
	 * Update the user's role with login
	 * @param login
	 * @param role
	 */
	public void updateRoleUserByLogin(String login, String role) {
		Connection con = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_UPDATE_ROLE_USER);
				pstat.setString(1, role);
				pstat.setString(2, login);
				pstat.execute();
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_UPDATE_USER_BY_LOGIN, ex);
				throw new DBException(Messages.ERR_CANNOT_UPDATE_USER_BY_LOGIN,
						ex);
			} finally {
				close(rs);
				close(pstat);
			}
		} finally {
			close(con);
		}
	}

	/**
	 * Update the user's ban with login
	 * @param login
	 * @param ban
	 */
	public void updateBanUserByLogin(String login, boolean ban) {
		Connection con = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_UPDATE_BAN_USER);
				pstat.setBoolean(1, ban);
				pstat.setString(2, login);
				pstat.execute();
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_UPDATE_USER_BY_LOGIN, ex);
				throw new DBException(Messages.ERR_CANNOT_UPDATE_USER_BY_LOGIN,
						ex);
			} finally {
				close(rs);
				close(pstat);
			}
		} finally {
			close(con);
		}
	}

	/**
	 * Extracts user entity from result set
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setLogin(rs.getString("login"));
		user.setPassword(rs.getString("password"));
		user.setRoleId(rs.getString("role"));
		user.setBan(rs.getBoolean("ban"));
		return user;
	}

	/**
	 * Returns all orders
	 */
	public List<Order> selectAllOrders() {
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		Order order;
		List<Order> orders = new ArrayList<>();
		try {
			con = getConnection();
			try {
				stat = con.createStatement();
				rs = stat.executeQuery(SQL_SELECT_ALL_ORDERS);
				while (rs.next()) {
					order = extractOrder(rs);
					orders.add(order);
				}
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_OBTAIN_ORDERS, ex);
				throw new DBException(Messages.ERR_CANNOT_OBTAIN_ORDERS, ex);
			} finally {
				close(rs);
				close(stat);
			}
		} finally {
			close(con);
		}
		return orders;
	}

	/**
	 * Return actual orders for the user with given login
	 */
	public List<Order> selectOrdersByLogin(String login) {
		Connection con = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		Order order;
		List<Order> orders = new ArrayList<>();
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_SELECT_ORDERS_BY_LOGIN);
				pstat.setString(1, login);
				pstat.setString(2, Order.STATE_NEW_ORDER);
				pstat.setString(3, Order.STATE_WAITING_FOR_PAYMENT);
				pstat.setString(4, Order.STATE_CRUSH_CAR);
				rs = pstat.executeQuery();
				while (rs.next()) {
					order = extractOrder(rs);
					orders.add(order);
				}
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_OBTAIN_ORDERS_BY_LOGIN, ex);
				throw new DBException(
						Messages.ERR_CANNOT_OBTAIN_ORDERS_BY_LOGIN, ex);
			} finally {
				close(rs);
				close(pstat);
			}
		} finally {
			close(con);
		}
		return orders;
	}
	
	/**
	 * Return all orders for the user with given login
	 */
	public List<Order> selectAllOrdersByLogin(String login) {
		Connection con = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		Order order;
		List<Order> orders = new ArrayList<>();
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_SELECT_ALL_ORDERS_BY_LOGIN);
				pstat.setString(1, login);
				rs = pstat.executeQuery();
				while (rs.next()) {
					order = extractOrder(rs);
					orders.add(order);
				}
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_OBTAIN_ORDERS_BY_LOGIN, ex);
				throw new DBException(
						Messages.ERR_CANNOT_OBTAIN_ORDERS_BY_LOGIN, ex);
			} finally {
				close(rs);
				close(pstat);
			}
		} finally {
			close(con);
		}
		return orders;
	}
	
	/**
	 * Returns order for the user with given id
	 */
	public Order selectOrderById(int id) {
		Connection con = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		Order order=null;
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_SELECT_ORDER_BY_ID);
				pstat.setInt(1, id);
				
				rs = pstat.executeQuery();
				while(rs.next()) {				
					order = extractOrder(rs);					
				}				
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_OBTAIN_ORDERS_BY_LOGIN, ex);
				throw new DBException(
						Messages.ERR_CANNOT_OBTAIN_ORDERS_BY_LOGIN, ex);
			} finally {
				close(rs);
				close(pstat);
			}
		} finally {
			close(con);
		}
		return order;
	}

	/**
	 *  Return all orders with given state
	 * @param state
	 * @return
	 */
	public List<Order> selectAllOrdersByState(String state) {
		Connection con = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		Order order;
		List<Order> orders = new ArrayList<>();
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_SELECT_ORDERS_BY_STATE);
				pstat.setString(1, state);
				rs = pstat.executeQuery();
				while (rs.next()) {
					order = extractOrder(rs);
					orders.add(order);
				}
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_OBTAIN_ORDERS_BY_STATE, ex);
				throw new DBException(
						Messages.ERR_CANNOT_OBTAIN_ORDERS_BY_STATE, ex);
			} finally {
				close(rs);
				close(pstat);
			}
		} finally {
			close(con);
		}
		return orders;
	}

	/**
	 * Returns orders which can be closed
	 * @return
	 */
	public List<Order> selectWillCloseOrders() {
		Connection con = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		Order order;
		List<Order> orders = new ArrayList<>();
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_SELECT_ORDERS_CLOSE);
				pstat.setString(1, Order.STATE_PAID);
				pstat.setDate(2, new Date(Calendar.getInstance()
						.getTimeInMillis()));
				pstat.setString(3, Order.STATE_PAID_CRUSH_CAR);
				rs = pstat.executeQuery();
				while (rs.next()) {
					order = extractOrder(rs);
					orders.add(order);
				}
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_OBTAIN_ORDERS_BY_STATE, ex);
				throw new DBException(
						Messages.ERR_CANNOT_OBTAIN_ORDERS_BY_STATE, ex);
			} finally {
				close(rs);
				close(pstat);
			}
		} finally {
			close(con);
		}
		return orders;
	}

	/**
	 * Inserts order with given parameters
	 */
	public void createNewOrder(String login, int make, Date startRent,
			Date finishRent, boolean driver,double priceTotal) {
		Connection con = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_INSERT_ORDER);
				pstat.setInt(1, make);
				pstat.setDate(2, startRent);
				pstat.setDate(3, finishRent);
				pstat.setBoolean(4,driver);
				pstat.setDouble(5, priceTotal);
				pstat.setString(6, login);				
				pstat.execute();
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_INSERT_NEW_ORDER, ex);
				throw new DBException(Messages.ERR_CANNOT_INSERT_NEW_ORDER, ex);
			} finally {
				close(rs);
				close(pstat);
			}
		} finally {
			close(con);
		}

	}

	public void updateRejectOrder(Integer id, String cause) {
		updateOrderByState(Order.STATE_REJECTED, id, cause);
	}

	public void updatePayOrder(Integer id, String cause) {
		updateOrderByState(Order.STATE_PAID, id, cause);
	}

	public void updateWaitPaymantOrder(Integer id, String cause) {
		updateOrderByState(Order.STATE_WAITING_FOR_PAYMENT, id, cause);
	}
	
	/*public void updateWaitPaymantOrder(Integer id,double priceTotal, String cause) {
		
	}*/
	public void updateCloseOrder(Integer id, String cause) {
		updateOrderByState(Order.STATE_CLOSED, id, cause);
	}

	public void updateCrushCarOrder(Integer id, double priceCrush, String cause) {
		Connection con = null;
		PreparedStatement pstat = null;
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_UPDATE_ORDER_PRICE_CRUSH);
				pstat.setString(1,Order.STATE_CRUSH_CAR);
				pstat.setDouble(2,priceCrush);
				pstat.setInt(3, id);
				// pstat.setString(2,cause);
				pstat.execute();
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_UPDATE_ORDER_BY_STATE, ex);
				throw new DBException(
						Messages.ERR_CANNOT_UPDATE_ORDER_BY_STATE, ex);
			} finally {
				close(pstat);
			}
		} finally {
			close(con);
		}
		//updateOrderByState(Order.STATE_CRUSH_CAR, id, cause);
	}

	public void updatePayCrushCarOrder(Integer id, String cause) {
		updateOrderByState(Order.STATE_PAID_CRUSH_CAR, id, cause);
	}

	/**
	 * Update the order's state
	 */
	private void updateOrderByState(String stateOrder, Integer id, String cause) {
		Connection con = null;
		PreparedStatement pstat = null;
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_UPDATE_ORDER);
				pstat.setString(1, stateOrder);
				pstat.setInt(2, id);
				// pstat.setString(2,cause);
				pstat.execute();
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_UPDATE_ORDER_BY_STATE, ex);
				throw new DBException(
						Messages.ERR_CANNOT_UPDATE_ORDER_BY_STATE, ex);
			} finally {
				close(pstat);
			}
		} finally {
			close(con);
		}
	}
	
	/*public void updateOrderByPrice(Integer id,double price) {
		Connection con = null;
		PreparedStatement pstat = null;
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_UPDATE_ORDER_PRICE);
				pstat.setDouble(1, price);
				pstat.setInt(2, id);
				// pstat.setString(2,cause);
				pstat.execute();
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_UPDATE_ORDER_BY_STATE, ex);
				throw new DBException(
						Messages.ERR_CANNOT_UPDATE_ORDER_BY_STATE, ex);
			} finally {
				close(pstat);
			}
		} finally {
			close(con);
		}
	}*/

	/**
	 * Extracts the order from the result set.
	 */
	private Order extractOrder(ResultSet rs) throws SQLException {
		Order order = new Order();
		order.setId(rs.getInt(ORDERS_ID));
		order.setLogin(rs.getString(USERS_LOGIN));
		order.setMake(rs.getString(CARS_MAKE));
		order.setStartRent(rs.getDate(ORDERS_START_RENT));
		order.setFinishRent(rs.getDate(ORDERS_FINISH_RENT));
		order.setState(rs.getString(ORDERS_STATE));
		order.setProducer(rs.getString(CARS_PRODUCER));
		order.setYearCar(rs.getInt(CARS_YEAR));
		order.setPriceCar(rs.getDouble(CARS_PRICE));
		order.setPriceTotal(rs.getDouble(ORDERS_PRICE));
		order.setDriver(rs.getBoolean(ORDERS_DRIVER));
		order.setPriceTotal(rs.getDouble(ORDERS_PRICE_TOTAL));
		order.setPriceCrush(rs.getDouble(ORDERS_PRICE_CRUSH));
		return order;
	}

	/**
	 * Returns all reviews about a car with given identifier
	 */
	public List<ReviewAboutCar> selectAllReviewsAboutCar(int id) {
		Connection con = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		ReviewAboutCar review;
		List<ReviewAboutCar> reviews = new ArrayList<>();
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_SELECT_REVIEWS_CAR_BY_ID);
				pstat.setInt(1, id);
				rs = pstat.executeQuery();
				while (rs.next()) {
					review = extractReviewAboutCar(rs);
					reviews.add(review);
				}
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_OBTAIN_REVIEWS_CAR, ex);
				throw new DBException(Messages.ERR_CANNOT_OBTAIN_REVIEWS_CAR,
						ex);
			} finally {
				close(rs);
				close(pstat);
			}
		} finally {
			close(con);
		}
		return reviews;
	}

	/**
	 * Inserts review about car
	 * @param login
	 * @param idCar
	 * @param date
	 * @param review
	 */
	public void createNewReviewAboutCar(String login, int idCar, Date date,
			String review) {
		Connection con = null;
		PreparedStatement pstat = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			try {
				pstat = con.prepareStatement(SQL_INSERT_REVIEW_ABOUT_CAR);
				pstat.setInt(1, idCar);
				pstat.setDate(2, date);
				pstat.setString(3, review);
				pstat.setString(4, login);
				pstat.execute();
				con.commit();
			} catch (SQLException ex) {
				rollback(con);
				LOG.error(Messages.ERR_CANNOT_INSERT_NEW_REVIEW_CAR, ex);
				throw new DBException(
						Messages.ERR_CANNOT_INSERT_NEW_REVIEW_CAR, ex);
			} finally {
				close(rs);
				close(pstat);
			}
		} finally {
			close(con);
		}

	}

	/**
	 * Extracts a review about car from the result set.
	 */

	private ReviewAboutCar extractReviewAboutCar(ResultSet rs)
			throws SQLException {
		ReviewAboutCar review = new ReviewAboutCar();
		review.setId(rs.getInt(REVIEWS_CAR_ID));
		review.setLogin(rs.getString(USERS_LOGIN));
		review.setIdCar(rs.getInt(REVIEWS_CAR_ID));
		review.setDateReview(rs.getDate(REVIEWS_CAR_DATE_REVIEW));
		review.setReview(rs.getString(REVIEWS_CAR_REVIEW));
		return review;
	}

	/**
	 * Closes a result set object.
	 */

	private void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				LOG.error("ERR_CANNOT_CLOSE_RESULT_SET", e);
			}
		}
	}

	/**
	 * Closes a statement object.
	 */
	private void close(Statement stat) {
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				LOG.error("ERR_CANNOT_CLOSE_STATEMENT", e);
			}
		}
	}

	/**
	 * Closes a connection.
	 */
	private void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				LOG.error("ERR_CANNOT_CLOSE_CONNECTION", e);
			}
		}
	}

	/**
	 * Rollbacks a connection.
	
	 */
	private void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				LOG.error("Cannot rollback transaction", ex);
			}
		}
	}

	/**
	 * Returns a DB connection from the Pool Connections.
	 * 
	 */
	private Connection getConnection() {
		try {
			Connection con = ds.getConnection();
			return con;
		} catch (SQLException ex) {
			LOG.error("ERR_CANNOT_OBTAIN_CONNECTION", ex);
			 throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, ex);
		}
	}
}
