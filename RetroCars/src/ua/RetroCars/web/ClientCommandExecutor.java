package ua.RetroCars.web;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.RetroCars.db.DBManager;
import ua.RetroCars.db.Entity.Car;
import ua.RetroCars.db.Entity.Order;
import ua.RetroCars.db.Entity.User;
import ua.RetroCars.web.Util.Filter;
import ua.RetroCars.web.Util.Sorter;

public class ClientCommandExecutor {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ClientCommandExecutor.class);
	
	private static DBManager dbManager=DBManager.getInstance();;

	
	static void executeGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String command = request.getParameter("command");
		LOG.trace("command - "+command+" --- request from get"+request);
		
		HttpSession session = request.getSession();
		session.setAttribute("currentDate", new Date(Calendar.getInstance().getTimeInMillis()));
		if (command == null) {
			session.setAttribute("cars", dbManager.selectAllCars());			
			session.setAttribute("producers", dbManager.selectAllProducersCar());
			session.setAttribute("ranks",dbManager.selectAllRanksCar());
			Filter.filterCar(request, response);
			request.getRequestDispatcher("/main.jsp").forward(request, response);
			
		}else if(command.equals("View")){
			session.setAttribute("viewFormat",request.getParameter("viewFormat"));
			request.getRequestDispatcher("/main.jsp")
			.forward(request, response);	
			
		}else if (command.equals("Logout")) {
			session.invalidate();
			response.sendRedirect("ClientController");

		} else if (command.equals("Choose")) {			
			chooseCar(request, response);
			
		} else if (command.equals("CabinetClient")) {
			cabinetUser(request, response);
			
		} else if (command.equals("CabinetHistoryClient")) {
			cabinetHistoryUser(request, response);
			
		} else if (command.equals("Filter")) {
			Filter.filterCar(request, response);
			request.getRequestDispatcher("/main.jsp").forward(request, response);

		} else if (command.equals("Language")) {		
			changeLanguage(request, response);
			
		}else if (command.equals("makeOrder")) {
			makeOrder(request, response);
		}else if (command.equals("noAgreeOrder")) {
			noAgreeOrder(request, response);
		} else if (command.equals("changePageView")) {		
			String indexPage=request.getParameter("indexPage");
			request.setAttribute("indexPage", indexPage);		
			request.getRequestDispatcher("/main.jsp").forward(request, response);

		} else if (command.equals("ReviewsCar")) {
			reviewsCar(request, response);
			
		} else if (command.equals("Sort")) {
			Sorter.sortCars(request, response);
			request.getRequestDispatcher("main.jsp").forward(request, response);
			
		} else {
			session.setAttribute("cars", dbManager.selectAllCars());			
			session.setAttribute("producers", dbManager.selectAllProducersCar());
			session.setAttribute("ranks",dbManager.selectAllRanksCar());
			Filter.filterCar(request, response);
			request.getRequestDispatcher("/main.jsp").forward(request, response);
		}	
	}
	
	private static void chooseCar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("idByChoose"));
		Car car = dbManager.selectCarById(id);
		request.setAttribute("chooseCar", car);
		request.getRequestDispatcher("/WEB-INF/jsp/client/Order.jsp")
				.forward(request, response);
	}

	private static void cabinetUser(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String login=(String) session.getAttribute("login");		
		request.setAttribute("ordersClient", dbManager
				.selectOrdersByLogin(login));
		request.getRequestDispatcher("WEB-INF/jsp/client/CabinetClient.jsp")
				.forward(request, response);
	}
	
	private static void cabinetHistoryUser(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String login=(String) session.getAttribute("login");		
		request.setAttribute("ordersClient", dbManager
				.selectAllOrdersByLogin(login));
		request.getRequestDispatcher("WEB-INF/jsp/client/CabinetClient.jsp")
				.forward(request, response);
	}
	
	private static void changeLanguage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String local=request.getParameter("Language");	
		if(local!=null){			
			session.setAttribute("local",local);
		}
		request.getRequestDispatcher("/main.jsp").forward(request, response);			
	}	
	
	private static void makeOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {	
		Date startRent = Date.valueOf(request.getParameter("startRentOrder"));
		Date finishRent = Date.valueOf(request.getParameter("finishRentOrder"));
		Boolean driver=false;
		double priceDriver=0;
		if(request.getParameter("driverOrder")!=null){
			driver =true;
			priceDriver=10;
		}		
		Integer id=Integer.parseInt(request.getParameter("idCar"));
		Car car = dbManager.selectCarById(id);
		if(!startRent.after(finishRent)){
			long days=(finishRent.getTime()-startRent.getTime())/ 1000 / 60 / 60 / 24 + 1;
			double priceTotal=days*(car.getPrice()+priceDriver);		
			request.setAttribute("startRentOrder", startRent);
			request.setAttribute("finishRentOrder", finishRent);
			request.setAttribute("orderDriver",driver);					
			request.setAttribute("chooseCar", car);		
			request.setAttribute("priceTotal", priceTotal);
			request.getRequestDispatcher("/WEB-INF/jsp/client/OrderAgreement.jsp")
			.forward(request, response);
		}else{
			request.setAttribute("orderDriver",driver);					
			request.setAttribute("chooseCar", car);
			request.setAttribute("messeageForClient","Invalid date");
			request.getRequestDispatcher("/WEB-INF/jsp/client/Order.jsp")
					.forward(request, response);
			
		}		
	}
	private static void noAgreeOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Integer id=Integer.parseInt(request.getParameter("idCar"));		
		Car car = dbManager.selectCarById(id);			
		request.setAttribute("chooseCar", car);		
		request.getRequestDispatcher("/WEB-INF/jsp/client/Order.jsp")
				.forward(request, response);
	}
	private static void reviewsCar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int idCar=Integer.parseInt(request.getParameter("carId"));
		request.setAttribute("reviewsAboutCar",dbManager.selectAllReviewsAboutCar(idCar));
		request.setAttribute("idCar",idCar);
		request.getRequestDispatcher("WEB-INF/jsp/client/ReviewsAboutCar.jsp").forward(request, response);
	}
	
	static void executePost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String command = request.getParameter("command");
		LOG.trace("command - "+command+" --- request from post"+request);
		
		if (command.equals("login/Registration")) {
			loginRegistration(request, response);
			
		} else if (command.equals("agreeOrder")) {
			agreeOrder(request, response);
			
		} else if (command.equals("PayOrder")) {
			payOrder(request, response);
			
		}else if (command.equals("PayCrushOrder")) {
			payCrushOrder(request, response);
			
		}else if (command.equals("createReviewAboutCar")) {			
				makeReviewAboutCar(request, response);	
				
		}else {
			request.getRequestDispatcher("/main.jsp").forward(request, response);
		}
				
	}
	private static void loginRegistration(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String login = request.getParameter("login");
		User user = dbManager.selectUserByLogin(login);	
		String password = request.getParameter("password");
		if (user != null) {			
			if (password.equals(user.getPassword())) {
				session.setAttribute("login", login);
				session.setAttribute("role", user.getRole());
				if(user.isBan()){
					session.setAttribute("Banned",true);
				}else{
					session.removeAttribute("Banned");
				}
			} else {
				session.setAttribute("messageForGuest", "invalid password");
			}
		} else {
			dbManager.createNewUser(login, password);
			session.setAttribute("login", login);		
		}
		response.sendRedirect("ClientController");
	}
	
	private static void agreeOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Date startRent = Date.valueOf(request.getParameter("startRentOrder"));
		Date finishRent = Date.valueOf(request.getParameter("finishRentOrder"));
		Boolean driver=false;
		double priceDriver=0;
		if(request.getParameter("driverOrder")!=null){
			driver =true;
			priceDriver=10;
		}							
		Integer id=Integer.parseInt(request.getParameter("idCar"));
		String login = (String) session.getAttribute("login");
		Car car = dbManager.selectCarById(id);
		long days=(finishRent.getTime()-startRent.getTime())/ 1000 / 60 / 60 / 24 + 1;
		double priceTotal=days*(car.getPrice()+priceDriver);			
		dbManager.createNewOrder(login, id, startRent, finishRent, driver,priceTotal);
		response.sendRedirect("ClientController");
				
	}
	
	private static void payOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Integer id = Integer.parseInt(request.getParameter("idOrder"));
		dbManager.updatePayOrder(id, "Okey");
		response.sendRedirect("ClientController?command=CabinetClient");
	}
	
	private static void payCrushOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Integer id = Integer.parseInt(request.getParameter("idOrder"));
		dbManager.updatePayCrushCarOrder(id, "Okey");
		response.sendRedirect("ClientController?command=CabinetClient");

	}
	
	private static void makeReviewAboutCar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Integer idCar = Integer.parseInt(request.getParameter("idCar"));
		String review=request.getParameter("reviewAboutCarFromUser");
		String login=(String) request.getSession().getAttribute("login");
		dbManager.createNewReviewAboutCar(login, idCar, new Date(Calendar.getInstance().getTimeInMillis()), review);
		response.sendRedirect("ClientController?command=ReviewsCar&carId="+idCar);
	}
}
