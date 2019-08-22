package ua.RetroCars.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.RetroCars.db.DBManager;
import ua.RetroCars.db.Entity.Order;
import ua.RetroCars.db.Entity.User;
import ua.RetroCars.web.Util.Filter;

public class AdminCommandExecutor {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(AdminCommandExecutor.class);
	
	private static DBManager dbManager=DBManager.getInstance();;

	
	static void executeGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String commandAdmin = request.getParameter("commandAdmin");
		LOG.trace("command - "+commandAdmin+" --- request from get"+request);
		//HttpSession session = request.getSession();
		if (commandAdmin != null) {
			request.setAttribute("commandAdmin", commandAdmin);
			if (commandAdmin.equals("New car")) {
				request.getRequestDispatcher(
						"/WEB-INF/jsp/admin/MainAdmin.jsp").forward(request, response);

			} else if (commandAdmin.equals("All cars")) {
				searchAllcars(request, response);

			} else if (commandAdmin.equals("Edit car")) {
				editCar(request, response);

			} else if (commandAdmin.equals("All users")) {
				searchAllUsers(request, response);

			} else if (commandAdmin.equals("Search user")) {
				searchUsers(request, response);

			} else if (commandAdmin.equals("Edit user")) {
				editUser(request, response);
				
			}else if (commandAdmin.equals("Filter")) {				
				Filter.filterCar(request, response);				
				request.getRequestDispatcher("/WEB-INF/jsp/admin/MainAdmin.jsp").forward(request, response);
			}		
			
		} else {
			request.getRequestDispatcher("/WEB-INF/jsp/admin/MainAdmin.jsp").forward(request,response);
		}
	}
	
	static void executePost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String commandAdmin = request.getParameter("commandAdmin");
		LOG.trace("command - "+commandAdmin+" --- request from post"+request);
		
		if (commandAdmin.equals("Create new car")) {
			createNewCar(request, response);

		} else if (commandAdmin.equals("Save car")) {
			saveCar(request, response);

		} else if (commandAdmin.equals("Delete car")) {
			deleteCar(request, response);

		} else if (commandAdmin.equals("Save user")) {
			saveUser(request, response);

		}
		
	}
	
	private static void searchAllcars(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Filter.filterCar(request, response);
		request.setAttribute("cars", dbManager.selectAllCars());
		request.getRequestDispatcher("/WEB-INF/jsp/admin/MainAdmin.jsp")
				.forward(request, response);
	}

	private static void editCar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("idCar"));
		request.setAttribute("editCar", dbManager.selectCarById(id));
		request.getRequestDispatcher("/WEB-INF/jsp/admin/MainAdmin.jsp")
				.forward(request, response);
	}

	private static void searchAllUsers(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("users", dbManager.selectAllUsers());
		request.getRequestDispatcher("/WEB-INF/jsp/admin/MainAdmin.jsp")
				.forward(request, response);
	}

	private static void searchUsers(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("loginUserSearchAdmin");
		List<User> users = dbManager.searchUserByLogin(login);

		if (users != null) {
			request.setAttribute("users", users);
		} else {
			request.removeAttribute("usersFindByLogin");
		}

		request.getRequestDispatcher("/WEB-INF/jsp/admin/MainAdmin.jsp")
				.forward(request, response);

	}

	private static void editUser(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("loginUserAdmin");
		User user = dbManager.selectUserByLogin(login);
		if (user != null) {
			request.setAttribute("userFindByLogin", user);
		} else {
			request.setAttribute("commandAdmin", "All users");
		}
		request.getRequestDispatcher("/WEB-INF/jsp/admin/MainAdmin.jsp")
				.forward(request, response);
	}
	
	////
	
	private static void createNewCar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String producer = request.getParameter("producer");
		String make = request.getParameter("make");
		String rank = request.getParameter("rank");
		int year =Integer.parseInt(request.getParameter("year"));
		Double price = Double.parseDouble(request.getParameter("price"));
		String imageLocAdress = request.getParameter("imageLocAdress");
		//String active= request.getParameter("Active");
		dbManager.createNewCar(producer, make, rank,year, price,imageLocAdress,false);
		searchAllcars(request, response);
	}

	private static void saveCar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String producer = request.getParameter("producer");
		String make = request.getParameter("make");
		String rank = request.getParameter("rank");
		int year =Integer.parseInt(request.getParameter("year"));
		Double price = Double.parseDouble(request.getParameter("price"));
		Integer id = Integer.parseInt(request.getParameter("id"));
		String imageLocAdress = request.getParameter("imageLocAdress");
		//String active= request.getParameter("Active");
		dbManager.updateCar(producer, make, rank,year, price, id,imageLocAdress,true);
		request.setAttribute("commandAdmin", "All cars");
		searchAllcars(request, response);
	}
	
	private static void deleteCar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Integer id = Integer.parseInt(request.getParameter("id"));
		dbManager.deleteCarById(id);		
		searchAllcars(request, response);
	}

	private static void saveUser(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String ban = request.getParameter("banUserAdmin");
		String role = request.getParameter("roleUserAdmin");
		String login = request.getParameter("loginUserAdmin");
		if (ban == null) {
			dbManager.updateBanUserByLogin(login, false);
		} else if (ban.equals("on")) {
			dbManager.updateBanUserByLogin(login, true);
		}
		dbManager.updateRoleUserByLogin(login, role);		
		request.setAttribute("commandAdmin", "All users");
		searchAllUsers(request, response);
	}

}
