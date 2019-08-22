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

public class ManagerCommandExecutor {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ManagerCommandExecutor.class);
	
	private static DBManager dbManager=DBManager.getInstance();;

	
	static void executeGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String command=request.getParameter("commandManager");
		LOG.trace("command - "+command+" --- request from get"+request);
		
		if(command==null){
			List<Order> orders=dbManager.selectAllOrders();
			request.setAttribute("orders", orders);
			request.getRequestDispatcher("/WEB-INF/jsp/manager/MainManager.jsp").forward(request, response);
		}else if(command.equals("new orders")){
			findOrderByState(request, response, Order.STATE_NEW_ORDER);
		}else if(command.equals("waiting for payment")){
			findOrderByState(request, response, Order.STATE_WAITING_FOR_PAYMENT);
		}else if(command.equals("rejected orders")){
			findOrderByState(request, response, Order.STATE_REJECTED);
		}else if(command.equals("paid orders")){
			findOrderByState(request, response, Order.STATE_PAID);
		}else if(command.equals("crush orders")){
			findOrderByState(request, response, Order.STATE_CRUSH_CAR);
		}else if(command.equals("closed orders")){
			findOrderByState(request, response, Order.STATE_CLOSED);
		}else if(command.equals("close orders")){
			List<Order> orders=dbManager.selectWillCloseOrders();
			request.setAttribute("orders", orders);
			request.setAttribute("commandManager",command);
			request.getRequestDispatcher("/WEB-INF/jsp/manager/MainManager.jsp").forward(request, response);
		}else if(command.equals("all orders")){
			List<Order> orders=dbManager.selectAllOrders();
			request.setAttribute("orders", orders);
			request.setAttribute("commandManager",command);
			request.getRequestDispatcher("/WEB-INF/jsp/manager/MainManager.jsp").forward(request, response);
		}
	}
	
	private static void findOrderByState(HttpServletRequest request, HttpServletResponse response,String state)
			throws ServletException, IOException {
		List<Order> orders=dbManager.selectAllOrdersByState(state);
		String command=request.getParameter("commandManager");
		request.setAttribute("orders", orders);
		request.setAttribute("commandManager",command);
		request.getRequestDispatcher("/WEB-INF/jsp/manager/MainManager.jsp").forward(request, response);
	}
	
	static void executePost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String command=request.getParameter("commandManager");
		LOG.trace("command - "+command+" --- request from post"+request);
		
		String causeOrder=request.getParameter("causeOrderManager");
		Integer idOrder=Integer.parseInt(request.getParameter("idOrderManager"));
		if(command.equals("noAcceptOrder")){
			dbManager.updateRejectOrder(idOrder,causeOrder);
			response.sendRedirect("ManagerController");
		}else if(command.equals("acceptOrder")){			
			dbManager.updateWaitPaymantOrder(idOrder, causeOrder);
			response.sendRedirect("ManagerController");			
		}else if(command.equals("closeOrder")){
			dbManager.updateCloseOrder(idOrder, causeOrder);
			response.sendRedirect("ManagerController");	
			
		}else if(command.equals("crushCarOrder")){
			Double priceCrush= Double.parseDouble(request.getParameter("priceCrush"));
			dbManager.updateCrushCarOrder(idOrder,priceCrush, causeOrder);
			response.sendRedirect("ManagerController");	
			
		}else if(command.equals("payCrushCarOrder")){
			dbManager.updatePayCrushCarOrder(idOrder, causeOrder);
			response.sendRedirect("ManagerController");			
		}

	}
						
	
}
